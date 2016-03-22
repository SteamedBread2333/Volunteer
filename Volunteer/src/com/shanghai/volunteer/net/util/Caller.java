package com.shanghai.volunteer.net.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.shanghai.volunteer.net.WSError;


public class Caller {

	/***
	 * doPostDefault DefaultHttpClient请求的实例
	 * 
	 * @param url
	 *            request URL
	 * @param content
	 *            request content
	 * @throws WSError
	 * */
	public static String doPostDefault(String url, String content)
			throws WSError {
		System.out.println("request:" + content);
		HttpPost request = new HttpPost(url);
		HttpClient httpClient = new DefaultHttpClient();
		// 绑定到请求 Entry
		// StringEntity se;
		ByteArrayEntity bae;
		try {
			// se = new StringEntity(content, "UTF-8");
			// request.setEntity(se);
			bae = new ByteArrayEntity(gZip(content.getBytes()));
			request.setEntity(bae);
			// 发送请求
			HttpResponse httpResponse = httpClient.execute(request);
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			// 得到应答的字符串
			ByteArrayInputStream stream = new ByteArrayInputStream(
					EntityUtils.toByteArray(httpResponse.getEntity()));
			String retSrc = new String(unGZip(stream), "UTF-8");
			// String retSrc = EntityUtils.toString(httpResponse.getEntity());
			System.out.println("response: " + retSrc);
			return retSrc;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			WSError wsError = new WSError();
			wsError.setMessage(e.getLocalizedMessage());
			throw wsError;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			WSError wsError = new WSError();
			wsError.setMessage(e.getLocalizedMessage());
			throw wsError;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			WSError wsError = new WSError();
			wsError.setMessage(e.getLocalizedMessage());
			throw wsError;
		} finally {
			// 释放网络连接资源
			httpClient.getConnectionManager().shutdown();
		}

	}

	/**
	 * GZip压缩数据
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] gZip(byte[] bContent) throws IOException {
		byte[] data = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GZIPOutputStream gOut = new GZIPOutputStream(out, bContent.length);
			// 压缩级别,缺省为1级
			DataOutputStream objOut = new DataOutputStream(gOut);
			objOut.write(bContent);
			objOut.flush();
			gOut.close();
			data = out.toByteArray();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return data;
	}

	/**
	 * GZip解压数据
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] unGZip(InputStream in) throws IOException {
		int BUFFERSIZE = 1024;
		byte[] data = new byte[BUFFERSIZE * 1000];
		try {
			// ByteArrayInputStream in = new ByteArrayInputStream(bContent);
			GZIPInputStream pIn = new GZIPInputStream(in);
			DataInputStream objIn = new DataInputStream(pIn);
			int len = 0;
			int count = 0;
			while ((count = objIn.read(data, len, len + BUFFERSIZE)) != -1) {
				len = len + count;
			}
			byte[] trueData = new byte[len];
			System.arraycopy(data, 0, trueData, 0, len);
			objIn.close();
			pIn.close();
			in.close();
			return trueData;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

    public static String doGet(String url) {
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpClient hc = new DefaultHttpClient();
            HttpResponse ht = hc.execute(httpGet);
            if (ht.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity he = ht.getEntity();
                InputStream is = he.getContent();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is));
                String response = "";
                String readLine = null;
                while ((readLine = br.readLine()) != null) {
                    response = response + readLine;
                }
                is.close();
                br.close();
                return response;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
	/**
	 * 根据给定的url地址访问网络，得到响应内容(这里为GET方式访问)
	 * 
	 * @param url
	 *            指定的url地址
	 * @return web服务器响应的内容，为<code>String</code>类型，当访问失败时，返回为null
	 * @throws UnsupportedEncodingException
	 */
	public static String getWebContent(String url) throws WSError {
		System.out.println("url:" + url);
		// 创建一个网络访问处理对象
		HttpClient httpClient = new DefaultHttpClient();
		// 创建一个http请求对象
		HttpGet request = new HttpGet(url);

		try {
			// 执行请求参数项
			HttpResponse response = httpClient.execute(request);
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 获得响应信息
				String content = EntityUtils.toString(response.getEntity());
				System.out.println("weather:" + content);
				return content;
			}

		} catch (Exception e) {
			WSError wsError = new WSError();
			wsError.setMessage(e.getLocalizedMessage());
			throw wsError;
		}finally {
			// 释放网络连接资源
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}
}
