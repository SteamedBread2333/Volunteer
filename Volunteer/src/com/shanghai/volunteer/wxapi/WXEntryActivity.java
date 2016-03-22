package com.shanghai.volunteer.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

	private IWXAPI api;
	Intent intent;
	String Openid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		api = WXAPIFactory.createWXAPI(this, Constants.WXappID, false);
		api.handleIntent(getIntent(), this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		// ����ɹ�
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			showToast("����ɹ�");
			finish();
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			// ����ȡ��
			showToast("����ȡ��");
			finish();
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			// ����ܾ�
			showToast("����ܾ�");
			finish();
			break;
		}

	}

}
