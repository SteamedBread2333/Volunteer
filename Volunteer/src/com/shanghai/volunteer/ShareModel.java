package com.shanghai.volunteer;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.bean.RequestType;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.EmailHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class ShareModel implements OnClickListener {

	Activity a;
	public static String QzoneApp_Id = "1103195825";
	public static String QzoneApp_Key = "JoZpZieCxpDwO47c";
	// ���΢��ƽ̨
	UMWXHandler wxHandler;
	// ֧��΢������Ȧ
	UMWXHandler wxCircleHandler;
	QZoneSsoHandler qZoneSsoHandler;
	SinaSsoHandler sinaHandler;
	TencentWBSsoHandler tencenthandler;
	EmailHandler emailHandler;
	private PopupWindow mPopupWindow;
	private String intro;
	private String name;
	private String Url;
	private UMImage ui;

	// ����������Activity��������³�Ա����
	final UMSocialService mController = UMServiceFactory.getUMSocialService(
			"com.umeng.share", RequestType.SOCIAL);

	public ShareModel(Activity a) {
		super();
		this.a = a;

	}

	public UMSocialService getmController() {
		return mController;
	}

	/*
	 * ����PopupWindow
	 */
	public PopupWindow initPopuptWindow() {
		LayoutInflater layoutInflater = LayoutInflater.from(a);
		View popupWindow = layoutInflater.inflate(R.layout.sharedialog, null);
		((TextView) popupWindow.findViewById(R.id.WXShare))
				.setOnClickListener(this);
		((TextView) popupWindow.findViewById(R.id.WXCriShare))
				.setOnClickListener(this);
		/*
		 * ((TextView) popupWindow.findViewById(R.id.QzoneShare))
		 * .setOnClickListener(this); ((TextView)
		 * popupWindow.findViewById(R.id.QQShare)) .setOnClickListener(this);
		 */
		// ����һ��PopupWindow
		// ����1��contentView ָ��PopupWindow������
		// ����2��width ָ��PopupWindow��width
		// ����3��height ָ��PopupWindow��height
		mPopupWindow = new PopupWindow(popupWindow,
				WindowManager.LayoutParams.MATCH_PARENT, Constants.dip2px(a,
						120));
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		return mPopupWindow;

	}

	public void SetController(final String intro, final String name,
			final String Url, final UMImage ui) {
		mController.setShareContent(intro + "->����" + Url);
		mController.setShareMedia(ui);
		this.intro = intro;
		this.name = name;
		this.Url = Url;
		this.ui = ui;
		// SinaWBShare
		/*
		 * sinaHandler = new SinaSsoHandler(); sinaHandler.addToSocialSDK();
		 * SinaShareContent sinacontent = new SinaShareContent("2870698508");
		 * sinacontent.setShareContent(intro); sinacontent.setShareImage(ui);
		 * sinacontent.setTitle(name); sinacontent.setTargetUrl(Url);
		 * mController.setShareMedia(sinacontent);
		 */
		// ��������SSO handler
		// mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// TencentWBShare
		/*
		 * tencenthandler = new TencentWBSsoHandler();
		 * tencenthandler.addToSocialSDK(); TencentWbShareContent tencentcontent
		 * = new TencentWbShareContent("801536216");
		 * tencentcontent.setTargetUrl(Url);
		 * tencentcontent.setShareContent(intro);
		 * tencentcontent.setShareImage(ui); tencentcontent.setTitle(name);
		 * mController.setShareMedia(tencentcontent);
		 */
		// ������Ѷ΢��SSO handler
		// mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

		// EmailShare
		/*
		 * emailHandler = new EmailHandler(); emailHandler.addToSocialSDK();
		 */
		// WXShare
		wxHandler = new UMWXHandler(a, Constants.WXappID, Constants.WXSecret);
		wxHandler.addToSocialSDK();
		// WXCriShare
		wxCircleHandler = new UMWXHandler(a, Constants.WXappID,
				Constants.WXSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}

	private void WXShare() {
		// TODO Auto-generated method stub
		WeiXinShareContent weixinContent = new WeiXinShareContent(ui);
		weixinContent.setShareContent(intro);
		weixinContent.setTitle(name);
		weixinContent.setTargetUrl(Url);
		weixinContent.setShareImage(ui);
		mController.setShareMedia(weixinContent);
		mController.postShare(a, SHARE_MEDIA.WEIXIN, null);
	}

	private void WXCriShare() {
		// TODO Auto-generated method stub
		CircleShareContent circleMedia = new CircleShareContent(ui);
		circleMedia.setShareContent(intro);
		circleMedia.setTitle(name);
		circleMedia.setTargetUrl(Url);
		circleMedia.setShareImage(ui);
		mController.setShareMedia(circleMedia);
		mController.postShare(a, SHARE_MEDIA.WEIXIN_CIRCLE, null);
	}

	private void qzoneShare() {
		// TODO Auto-generated method stub
		// QZoneShare
		qZoneSsoHandler = new QZoneSsoHandler(a, QzoneApp_Id, QzoneApp_Key);
		qZoneSsoHandler.setTargetUrl(Url);
		qZoneSsoHandler.addToSocialSDK();
		mController.postShare(a, SHARE_MEDIA.QZONE, listener);
	}

	public SnsPostListener listener = new SnsPostListener() {

		@Override
		public void onStart() {
			Toast.makeText(a, "��ʼ����.", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onComplete(SHARE_MEDIA platform, int eCode,
				SocializeEntity entity) {
			if (eCode == 200) {
				Toast.makeText(a, "����ɹ�.", Toast.LENGTH_SHORT).show();
			} else {
				String eMsg = "";
				if (eCode == -101) {
					eMsg = "û����Ȩ";
				}
				Toast.makeText(a, "����ʧ��[" + eCode + "] " + eMsg,
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.WXShare:
			WXShare();
			break;
		case R.id.WXCriShare:
			WXCriShare();
			break;
		/*
		 * case R.id.QzoneShare: qzoneShare(); break; case R.id.QQShare:
		 * qzoneShare(); break;
		 */
		}
	}
}
