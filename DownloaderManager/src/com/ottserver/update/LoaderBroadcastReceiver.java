package com.ottserver.update;
//package com.changhong.downloadermanager;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.util.JsonWriter;
//import android.util.Log;
//
//public class loaderBroadcastReceiver extends BroadcastReceiver {  
//	  
//	 static final String ACTION = "com.changhong.downloader.updateinfo";
//	 private static final String TAG = "loader";
//	 public static JSONObject object=null;
//	   
//	 /*
//	  * ���������
//	  * ����json����ʼ������ȫ�ֲ�������ʼ����
//	  * 
//	  * */
//	 @Override  
//	 public void onReceive(Context context, Intent intent) {  
//	    
//	  if (intent.getAction().equals(ACTION)){ 
//	   Log.i(TAG,"com.changhong.downloader.updateinfo");
//	   if(!Constant.isTaskRunning)
//	   {
//		   Constant.isTaskRunning=true;
//		   String datatype=intent.getStringExtra("datatype");
//		   String path=intent.getStringExtra("path");//�洢·��
//		   String url=intent.getStringExtra("url");//����ֱ�ӿ�ʼ����
//		   String view=intent.getStringExtra("view");//0�޽��棬1��2ϵͳ������3DTV������4APP������5��������
//		   if(datatype!=null&&!datatype.equals("")&&!datatype.equals("null"))
//		   {
//			   if(datatype.equals("1"))
//			   {
//				   String model=intent.getStringExtra("model");
//				   String androidsdk=intent.getStringExtra("androidsdk");
//				   String macadress=intent.getStringExtra("macadress");
//				   String signtype=intent.getStringExtra("signtype");
//				   String testmode=intent.getStringExtra("testmode");
//				   String firmwareversion=intent.getStringExtra("firmwareversion");
//
//				   object=new JSONObject();
//				   JSONObject object_sub=new JSONObject();
//				   try {
//					object.put("firmwareclient", object_sub);
//					object_sub.put("model", model);
//					object_sub.put("datatype", datatype);
//					object_sub.put("androidsdk", androidsdk);
//					object_sub.put("macadress", macadress);
//					object_sub.put("signtype", signtype);
//					object_sub.put("testmode", testmode);
//					object_sub.put("firmwareversion", firmwareversion);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					object=null;
//				}
//				  if(object!=null) Log.e(TAG,object.toString());
//			   }
//			   else if(datatype.equals("2"))
//			   {
//				   String model=intent.getStringExtra("model");
//				   String androidsdk=intent.getStringExtra("androidsdk");
//				   String macadress=intent.getStringExtra("macadress");
//				   String signtype=intent.getStringExtra("signtype");
//				   String testmode=intent.getStringExtra("testmode");
//				   String firmware_diffversion=intent.getStringExtra("firmware_diffversion");
//				   object=new JSONObject();
//				   JSONObject object_sub=new JSONObject();
//				   try {
//					object.put("firmware_diffclient", object_sub);
//					object_sub.put("model", model);
//					object_sub.put("datatype", datatype);
//					object_sub.put("androidsdk", androidsdk);
//					object_sub.put("macadress", macadress);
//					object_sub.put("signtype", signtype);
//					object_sub.put("testmode", testmode);
//					object_sub.put("firmware_diffversion", firmware_diffversion);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					object=null;
//				}
//				   if(object!=null) Log.e(TAG,object.toString());
//			   }
//			   else if(datatype.equals("3"))
//			   {
//				   String model=intent.getStringExtra("model");
//				   String androidsdk=intent.getStringExtra("androidsdk");
//				   String macadress=intent.getStringExtra("macadress");
//				   String signtype=intent.getStringExtra("signtype");
//				   String testmode=intent.getStringExtra("testmode");
//				   String firmwareversion=intent.getStringExtra("firmwareversion");
//				   String operatorcode=intent.getStringExtra("operatorcode");
//				   String catype=intent.getStringExtra("catype");
//				   String caversion=intent.getStringExtra("caversion");
//				   String refercoreversion=intent.getStringExtra("refercoreversion");
//				   object=new JSONObject();
//				   JSONObject object_sub=new JSONObject();
//				   try {
//					object.put("dtvapkclient", object_sub);
//					object_sub.put("model", model);
//					object_sub.put("datatype", datatype);
//					object_sub.put("androidsdk", androidsdk);
//					object_sub.put("macadress", macadress);
//					object_sub.put("signtype", signtype);
//					object_sub.put("testmode", testmode);
//					object_sub.put("firmwareversion", firmwareversion);
//					object_sub.put("operatorcode", operatorcode);
//					object_sub.put("catype", catype);
//					object_sub.put("caversion", caversion);
//					object_sub.put("refercoreversion", refercoreversion);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					object=null;
//				}
//				   if(object!=null) Log.e(TAG,object.toString());
//			   }
//			   else if(datatype.equals("4"))
//			   {
//				   String packagename=intent.getStringExtra("packagename");
//				   String sdkadapt=intent.getStringExtra("sdkadapt");
//				   String signtype=intent.getStringExtra("signtype");
//				   String appversioncode=intent.getStringExtra("appversioncode");
//				   String testmode=intent.getStringExtra("testmode");
//				   object=new JSONObject();
//				   JSONObject object_sub=new JSONObject();
//				   try {
//					object.put("appclient", object_sub);
//					object_sub.put("packagename", packagename);
//					object_sub.put("datatype", datatype);
//					object_sub.put("sdkadapt", sdkadapt);
//					object_sub.put("signtype", signtype);
//					object_sub.put("appversioncode", appversioncode);
//					object_sub.put("testmode", testmode);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					object=null;
//				}
//				   if(object!=null) Log.e(TAG,object.toString());
//			   }
//			   else if(datatype.equals("5"))
//			   {
//				   String model=intent.getStringExtra("model");
//				   String dataname=intent.getStringExtra("dataname");
//				   String signtype=intent.getStringExtra("signtype");
//				   String dataversioncode=intent.getStringExtra("dataversioncode");
//				   String testmode=intent.getStringExtra("testmode");
//				   object=new JSONObject();
//				   JSONObject object_sub=new JSONObject();
//				   try {
//					object.put("appclient", object_sub);
//					object_sub.put("model", model);
//					object_sub.put("datatype", datatype);
//					object_sub.put("dataname", dataname);
//					object_sub.put("signtype", signtype);
//					object_sub.put("dataversioncode", dataversioncode);
//					object_sub.put("testmode", testmode);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					object=null;
//				}
//				   if(object!=null) Log.e(TAG,object.toString());
//			   }
//		   }
//		   
//		   if(view!=null&&!view.equals("")&&!view.equals("null")){
//			   //��ʼ������ȫ�ֲ���
//			   Constant.view=view;
//			   Constant.downloadpath=url;
//			   Constant.path = path;
//			   if(view.equals("0"))
//			   {
//				   //�޽���������loaderService��ʵ�֣���ȡ������������������湦��һ��
//				   Intent mIntent=new Intent(context,loaderService.class);  
//				   mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				   context.startService(mIntent); 
////				   if(url!=null&&!url.equals("")&&!url.equals("null")){
////					   //ֱ������
////					   Constant.downloadpath=url;
////					   Constant.path = path;
////				   }else{
////					   //��������ϴ�json����ȡ����·��
////					   Constant.path = path;
////				   }
//			   }else{
//				   //��������������View��ֵ��ʾ��ͬ������
//				   Intent mIntent=new Intent(context,loaderActivity.class);  
//				   mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				   context.startActivity(mIntent); 
////				   if(url!=null&&!url.equals("")&&!url.equals("null")){
////					   //ֱ������
////					   Constant.downloadpath=url;
////					   Constant.path = path;
////				   }else{
////					   //��������ϴ�json����ȡ����·��
////					   Constant.path = path;
////				   }
//			   }
//		   }
//	   }else{
//		   Log.e(TAG,"��ǰ�����������񣡣���");
//	   }
//	  } 
//	 }  
//	  
//	}  
//
