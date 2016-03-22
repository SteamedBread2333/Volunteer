package com.shanghai.volunteer.activity;

import java.util.concurrent.Callable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanghai.volunteer.Constants;
import com.shanghai.volunteer.R;
import com.shanghai.volunteer.activity.util.BaseActivity;
import com.shanghai.volunteer.activity.util.Callback;
import com.shanghai.volunteer.bean.Active;
import com.shanghai.volunteer.net.WSError;
import com.shanghai.volunteer.net.impl.SHVolunteerApiImpl;

public class DetailActiverActivity extends BaseActivity {

	private TextView title, detailActivegotojoin, detailActiveover,
			detailActiveTitle, detailActiveTime, detailActiveUnit,
			detailActiveOrganize, detailActiveTelephone, detailActiveEMail,
			detailActiveAddress, detailActiveDetails, detailActiveCondition;
	private ImageView back, personl;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailactive);
		findView();
		getData();
	}

	private void findView() {
		// TODO Auto-generated method stub
		title = (TextView) findViewById(R.id.title);
		title.setText("־Ը��ļ����");
		detailActivegotojoin = (TextView) findViewById(R.id.detailActivegotojoin);
		detailActiveover = (TextView) findViewById(R.id.detailActiveover);
		detailActiveTitle = (TextView) findViewById(R.id.detailActiveTitle);
		detailActiveTime = (TextView) findViewById(R.id.detailActiveTime);
		detailActiveUnit = (TextView) findViewById(R.id.detailActiveUnit);
		detailActiveOrganize = (TextView) findViewById(R.id.detailActiveOrganize);
		detailActiveTelephone = (TextView) findViewById(R.id.detailActiveTelephone);
		detailActiveEMail = (TextView) findViewById(R.id.detailActiveEMail);
		detailActiveAddress = (TextView) findViewById(R.id.detailActiveAddress);
		detailActiveDetails = (TextView) findViewById(R.id.detailActiveDetails);
		detailActiveCondition = (TextView) findViewById(R.id.detailActiveCondition);
		back = (ImageView) findViewById(R.id.back);
		personl = (ImageView) findViewById(R.id.personl);
		detailActivegotojoin.setOnClickListener(this);
		back.setOnClickListener(this);
		personl.setOnClickListener(this);
	}

	private void getData() {
		// TODO Auto-generated method stub
		this.doAsync(new Callable<Active>() {

			@Override
			public Active call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().getDetailActive(getIntent()
							.getStringExtra("activeId"));
				} catch (WSError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, new Callback<Active>() {

			@Override
			public void onCallback(Active pCallbackValue) {
				// TODO Auto-generated method stub
				if (pCallbackValue != null) {
					detailActiveTitle.setText(pCallbackValue.getTitle());
					detailActiveTime.setText("ʱ�䣺" + pCallbackValue.getCreatTime().substring(0, 10));
					detailActiveUnit.setText("����λ��" + pCallbackValue.getUnit());
					detailActiveOrganize.setText("������֯��"
							+ pCallbackValue.getOrganize());
					detailActiveTelephone.setText("��ϵ�绰��"
							+ pCallbackValue.getTelephone());
					detailActiveEMail.setText("EMAIL��"
							+ pCallbackValue.getEMail());
					detailActiveAddress.setText("��ļ�ص㣺"
							+ pCallbackValue.getAddress());
					detailActiveDetails.setText(""
							+ pCallbackValue.getDetails());
					if (pCallbackValue.getCondition() == null
							|| pCallbackValue.getCondition().equals("null")) {
						detailActiveCondition.setText("");
					} else {
						detailActiveCondition.setText(""
								+ pCallbackValue.getCondition());
					}
					if (pCallbackValue.getStatus().equals("��ļ��")) {
						detailActivegotojoin.setVisibility(View.VISIBLE);
						detailActiveover.setVisibility(View.GONE);
					} else if (pCallbackValue.getStatus().equals("�ѽ���")) {
						detailActivegotojoin.setVisibility(View.GONE);
						detailActiveover.setVisibility(View.VISIBLE);
					} else {
						detailActivegotojoin.setVisibility(View.GONE);
						detailActiveover.setVisibility(View.GONE);
					}
				}else{
					showToast("�����������ӣ�");
					finish();
				}

			}
		});
	}

	private void applyItem() {
		// TODO Auto-generated method stub
		this.doAsync(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {
					return new SHVolunteerApiImpl().applyItem(
							Constants.mAccount.getUserID(), getIntent()
									.getStringExtra("activeId"));
				} catch (WSError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 0;
			}
		}, new Callback<Integer>() {

			@Override
			public void onCallback(Integer pCallbackValue) {
				// TODO Auto-generated method stub
				if (pCallbackValue == 1) {
					showToast("��Ŀ����ɹ�����Ҳ����ֱ�Ӻ���Ŀ��֯��ֱ����ϵ��");
				} else {
					showToast("�����ļ��Ϣ���Ѿ������ˣ�");
				}
			}
		});
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.detailActivegotojoin:
			if (Constants.mAccount == null) {
				intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else {
				applyItem();
			}
			break;
		case R.id.personl:

			break;
		}
	}
}
