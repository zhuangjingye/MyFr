/**
 * 所有的http请求经过此类处理
 * 1:json 请求
 * 2:获取图片方法一
 * 3:获取图片并返回bitmap
 */

package com.base.net;

import org.json.JSONObject;

import android.graphics.Bitmap;

import com.base.MyBaseActivity;
import com.base.net.volley.Response;
import com.base.net.volley.VolleyError;
import com.base.net.volley.toolbox.ImageLoader;
import com.base.net.volley.toolbox.ImageRequest;
import com.base.net.volley.toolbox.JsonObjectRequest;
import com.base.net.volley.toolbox.ImageLoader.ImageCache;
import com.base.net.volley.toolbox.ImageLoader.ImageListener;

public class RequestManager {
	private static RequestManager requestManager = null;

	private RequestManager() {

	}

	public static RequestManager getInstance() {
		if (null == requestManager) {
			synchronized (RequestManager.class) {
				requestManager = new RequestManager();
			}
		}
		return requestManager;
	}

	/**
	 * 获取发送json请求
	 * 
	 * @param request
	 */
	public void requestJSONBy(MyBaseActivity myBaseActivity,
			final AbstractJsonRequest request) {

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				request.getMothed(), request.getUrl(), request.getBody(),
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						request.successed(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						request.failed();
					}
				});
		myBaseActivity.getRequestQueue().add(jsonObjectRequest);
	}

	/**
	 * 
	 * 方法描述 : 获取图片方法一 创建者：lixin 创建时间： 2015年1月16日 下午5:13:37
	 * 
	 * @param url
	 * @param listener
	 *            void
	 * 
	 */
	public void requestImage(MyBaseActivity myBaseActivity, String url,
			ImageListener listener) {
		ImageCache imageCache = new ImageCache() {
			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				// TODO Auto-generated method stub
				MyBaseActivity.lruCache.put(url, bitmap);
			}

			@Override
			public Bitmap getBitmap(String url) {
				// TODO Auto-generated method stub
				return MyBaseActivity.lruCache.get(url);
			}
		};
		ImageLoader imageLoader = new ImageLoader(
				myBaseActivity.getRequestQueue(), imageCache);
		imageLoader.get(url, listener);
	}

	/**
	 * 3:获取图片并返回bitmap
	 * @param myBaseActivity
	 * @param request
	 */
	public void requestImage(MyBaseActivity myBaseActivity,
			final AbstractImageRequest request) {
		ImageRequest imgRequest = new ImageRequest(request.getUrl(),
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap arg0) {
						// TODO Auto-generated method stub
						request.successed(arg0);
					}
				}, request.getImageWith(), request.getImageHeight(),
				request.getDecodeConfig(), new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						request.failed();
					}
				});
		myBaseActivity.getRequestQueue().add(imgRequest);
	}

}
