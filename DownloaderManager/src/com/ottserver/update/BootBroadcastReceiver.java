package com.ottserver.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {  
	 
	 static final String ACTION = "android.intent.action.BOOT_COMPLETED";
	 private static final String TAG = "loader";
//	 private FileService fileService;
	  /*
	   * 开机广播接收
	   * */
	 @Override  
	 public void onReceive(Context context, Intent intent) {  
	    
	  if (intent.getAction().equals(ACTION)){
	   Log.e(TAG,"android.intent.action.BOOT_COMPLETED");
	   LoaderService.startByBroadcast=true;
	   Constant.isDownCompleted=false;
	   Intent mIntent=new Intent(context,LoaderService.class);  
	   mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	   context.startService(mIntent);
//	   fileService = new FileService(context);
//	   if(fileService.isExistTask()){
//		   Constant.isTaskRunning=true;
//		   //有未完成的下载任务,继续下载
//		   if(Constant.view.equals("0")){
//			   //无界面下载启动Service下载
//			   Intent mIntent=new Intent(context,loaderService.class);  
//			   mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			   context.startService(mIntent);  
//		   }else{
//			   //启动界面下载
//			   Intent mIntent=new Intent(context,loaderActivity.class);  
//			   mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			   context.startActivity(mIntent);
//		   }
//	   }else{
//		   //没有下载任务,可接收新的下载需求
//		   Constant.isTaskRunning=false;
//	   }
	  }else{
		  Log.i(TAG,"com.action.private_BOOT_COMPLETED");
		  LoaderService.startByBroadcast=true;
		  Constant.isDownCompleted=false;
		  Intent mIntent=new Intent(context,LoaderService.class);
		  mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		  context.startService(mIntent);
	  } 
	 }  
	  
	}  

