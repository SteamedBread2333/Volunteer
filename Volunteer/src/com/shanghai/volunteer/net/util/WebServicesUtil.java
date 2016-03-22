package com.shanghai.volunteer.net.util;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.shanghai.volunteer.application.SHVolunteerApplication;
import com.shanghai.volunteer.net.WSError;


public class WebServicesUtil {
	
	
	private static Object detail;

	public static String doWebServicesRequest(SoapObject rpc,String url) throws WSError{
		//网络不可用时，return网络错误
		if(!NetAvailable.isNetAvailable(SHVolunteerApplication.getInstance())){
			return "{\"Success\":-1}";
		}
		// 生成调用WebService方法的SOAP请求信息
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc);
		// 创建HttpTransportsSE对象
		HttpTransportSE ht = new HttpTransportSE(url);
		ht.debug = true ;
		try {
			ht.call(rpc.getNamespace()+rpc.getName(), envelope);
			detail = envelope.getResponse();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			WSError wsError = new WSError();
			wsError.setMessage(e.getLocalizedMessage());
			throw wsError;
		} 
		return detail.toString();
	}

}
