package com.ottserver.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {  
	 
	 static final String ACTION = "android.intent.action.BOOT_COMPLETED";
	 private static final String TAG = "loader";
	  /*
	   * 开机广播接收
	   * */
	 @Override  
	 public void onReceive(Context context, Intent intent) {  
	    
	  if (intent.getAction().equals(ACTION)){
	   Log.e(TAG,"android.intent.action.BOOT_COMPLETED");
	   LoaderService.startByBroadcast=true;
	   Intent mIntent=new Intent(context,LoaderService.class);  
	   mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	   context.startService(mIntent);
	  }else{
		  Log.i(TAG,"com.action.private_BOOT_COMPLETED");
		  LoaderService.startByBroadcast=true;
		  Intent mIntent=new Intent(context,LoaderService.class);
		  mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		  context.startService(mIntent);
	  } 
	 }  
	  
	}  

