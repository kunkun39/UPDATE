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
	   * �����㲥����
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
//		   //��δ��ɵ���������,��������
//		   if(Constant.view.equals("0")){
//			   //�޽�����������Service����
//			   Intent mIntent=new Intent(context,loaderService.class);  
//			   mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			   context.startService(mIntent);  
//		   }else{
//			   //������������
//			   Intent mIntent=new Intent(context,loaderActivity.class);  
//			   mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			   context.startActivity(mIntent);
//		   }
//	   }else{
//		   //û����������,�ɽ����µ���������
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

