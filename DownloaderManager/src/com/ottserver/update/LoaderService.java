package com.ottserver.update;

import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.binary.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.ots.deviceinfoprovide.DeviceInfo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class LoaderService extends Service {

	private final static String TAG = "loaderService";
	private final static int Processing = 1;//正在下载标志
	private final static int Failure = -1;//下载失败标志、
	public static MyHandler mHandler = new MyHandler();
	static File saveDir=null;
	static long filesize=0;
	private static String DeviceModel=null;
	private static String AndroidSDK=null;
	private static String DeviceLastMacBin=null;
	private static String DeviceFirmWareVer=null;
	private static String hardwareversion=null;
	private static String username=null;
	private static String UpgradeDefaultSavePath=null;
	public static String testmode="false";
	static String jsonString=null;
	static String jsonReportString=null;
	public static JSONObject object=null;
	static String stringtmp=null;
	private static Context mContext=null;
	public static boolean startByBroadcast=false;
	private static int tmpsize=0;
	private static long tmptime=0;
	private static float speed=0;
	public static Downloadask task=null;
	Runnable mRunnable=null;
	private static String currentServerAddress=null;
	public static String serverAddress=null;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		if(mContext==null)
		{
			mContext=LoaderService.this;
		}
		if(mRunnable==null){
			mRunnable=new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(!Constant.isTaskRunning){
						mHandler.sendEmptyMessage(3);
						mHandler.postDelayed(mRunnable, 3600000);
					}
				}
			};
		}
		
		SharedPreferences preferences=getSharedPreferences("testmode", Context.MODE_PRIVATE);
		testmode=preferences.getString("tag", "false");
		preferences=null;
		preferences=getSharedPreferences("address", Context.MODE_PRIVATE);
		serverAddress=preferences.getString("contentpath", "");
		
		Intent noKillMe = new Intent("com.smartcenter.HOME_DONT_KILL_ME");
		noKillMe.putExtra("packageName", "com.changhong.downloadermanager");
		sendBroadcast(noKillMe);
		
		if(startByBroadcast&&!Constant.isTaskRunning){
			Log.i(TAG,"startBybroadcast!!!");
			mHandler.postDelayed(mRunnable, 10000);
			startByBroadcast=false;
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(task!=null){
			task.exit();
		}
		Constant.isTaskRunning=false;
		android.os.Process.killProcess(android.os.Process.myPid());
		Log.e(TAG,"killProcess>>>loaderService");
	}



	/**********
	 *  @author   唐超
	 *  <Type Create time>     2014年4月22日  、    11:32:06
	 *  TODO  >>   内部类实现Handler,
	 *此对象的作用是向创建Handler的对象所在的线程所绑定的消息队列发送消息并处理这些消息
	 *****************************************************************************************
	 */
	 public static class MyHandler extends Handler{
		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
	     ***************************************************************************************
		 *系统会自动回调方法，用于处理消息事件
		 *Message 一般会包含消息的标志和消息的内容以及消息的处理器Handler
		 */
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Processing:   //下载时
				//从消息中获取已经下载的数据长度
				if(LoaderActivity.progressBar!=null)
				{
					LoaderActivity.progressBar.setMax((int)filesize);//设置进度条最大值为文件的大小
				}
				int size = msg.getData().getInt("size");
				
				if(tmpsize==0)
				{
					tmpsize=size;
					tmptime=System.currentTimeMillis();
				}else{
					//更新速度
					if(size-tmpsize>512000&&((long)(System.currentTimeMillis()-tmptime)/1000)>5)
					{
						speed=(float) ((size-tmpsize)/(1024*((long)(System.currentTimeMillis()-tmptime)/1000)));
						Log.e(TAG,"speed  "+speed+" KB");
						tmpsize=size;
						tmptime=System.currentTimeMillis();
					}
				}
				 //计算已经下载的百分比，要使用浮点值计算
				 float num =(float)size/(float)filesize;
				 int result = (int) (num*100);//把获得的浮点数换成整数
				 if(LoaderActivity.tv_progress!=null){
					 LoaderActivity.tv_progress.setText(mContext.getResources().getString(R.string.downloadprogress)+result+"%");
					 if(speed!=0){
						 LoaderActivity.tv_speed.setText(mContext.getResources().getString(R.string.downloadspeed)+speed+" KB/S");
					 }else{
						 LoaderActivity.tv_speed.setText(mContext.getResources().getString(R.string.downloadspeed));
					 }
				 }
				 if(LoaderActivity.progressBar!=null){
					 LoaderActivity.progressBar.setProgress(size);
				 }
				 Log.i(TAG, "下载中Processing,"+"result百分比是："+result+"%");
				 if (size == filesize) {
					//当下载完成时,汇报信息
					 initReportInfo(true);
					 mHandler.sendEmptyMessage(10);
					 if(LoaderActivity.txt_prompt!=null){
						 LoaderActivity.txt_prompt.setText(mContext.getResources().getString(R.string.downloadsuccess));
						 LoaderActivity.tv_speed.setText(mContext.getResources().getString(R.string.downloadspeed));
					 }
					 if(Constant.isTaskRunning){
						 Constant.isTaskRunning=false;
							 File file1=new File(Constant.path+File.separator+"update.zip");
							 if(file1.exists())file1.delete();
							 File file2=new File(Constant.filepath);
							 boolean renameSuccess=file2.renameTo(file1);
							 if(renameSuccess)
							 {
								 Intent intent=new Intent("SYSTEMUPDATE_DOWNLOAD_COMPLETE");
								 intent.putExtra("path", Constant.path+File.separator+"update.zip");
								 if(Constant.force!=null&&Constant.force.equals("1"))
								 {
									 intent.putExtra("Force reboot", true);
								 }else{
									 intent.putExtra("Force reboot", false);
								 }
								 mContext.sendBroadcast(intent);
								 Log.e(TAG, "广播发送成功>>>"+Constant.path+File.separator+"update.zip");
							 }
					 }
					 if(task!=null){
						 task.exit();
					 }
					 Toast.makeText(mContext, mContext.getResources().getString(R.string.downloadsuccessed), Toast.LENGTH_SHORT).show();
				}
				break;
				
			case Failure :    //下载失败时
				Log.i(TAG, "收到Failure");
				initReportInfo(false);
				mHandler.sendEmptyMessage(10);
				if(task!=null){
					task.exit();
				}
				Constant.isTaskRunning=false;
				if(LoaderActivity.txt_prompt!=null){
					 LoaderActivity.txt_prompt.setText(mContext.getResources().getString(R.string.downloadfailed));
					 LoaderActivity.tv_progress.setVisibility(View.INVISIBLE);
					 LoaderActivity.tv_speed.setVisibility(View.INVISIBLE);
					 LoaderActivity.progressBar.setVisibility(View.INVISIBLE);
					 LoaderActivity.btn.setVisibility(View.VISIBLE);
				 }
				break;
			case 3:
				initSystemInfo();
				if(Constant.isTaskRunning){
					
				}else{
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Log.e("hhh",">>>>>>>>>>>>>");
							boolean isOK=false;
							if(serverAddress!=null&&!serverAddress.equals("")){
								currentServerAddress=serverAddress;
								mHandler.sendEmptyMessage(9);
								stringtmp=HttpClientRequestImpl.posttoserver(serverAddress,jsonString);
								if(stringtmp!=null&&!stringtmp.equals("needless")){
									isOK=true;
								}
							}
							//如果添加地址不行再轮询服务器地址
							if(!isOK){
								for(int i=0;i<Constant.updatePaths.length;i++){
									//提示检测哪一个服务器地址
									currentServerAddress=Constant.updatePaths[i];
									mHandler.sendEmptyMessage(9);
									stringtmp=HttpClientRequestImpl.posttoserver(Constant.updatePaths[i],jsonString);
									if(stringtmp!=null&&!stringtmp.equals("needless")){
										break;
									}
								}
							}
							mHandler.sendEmptyMessage(6);
						}
					}).start();
				}
				break;
			case 8:
				initSystemInfo();
				if(Constant.isTaskRunning){
					
				}else{
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Log.e("hhh",">>>>>>>>>>>>>");
							boolean isOK=false;
							if(serverAddress!=null&&!serverAddress.equals("")){
								currentServerAddress=serverAddress;
								mHandler.sendEmptyMessage(9);
								stringtmp=HttpClientRequestImpl.posttoserver(serverAddress,jsonString);
								if(stringtmp!=null&&!stringtmp.equals("needless")){
									isOK=true;
								}
							}
							//如果添加地址不行再轮询服务器地址
							if(!isOK){
								for(int i=0;i<Constant.updatePaths.length;i++){
									//提示检测哪一个服务器地址
									currentServerAddress=Constant.updatePaths[i];
									mHandler.sendEmptyMessage(9);
									stringtmp=HttpClientRequestImpl.posttoserver(Constant.updatePaths[i],jsonString);
									if(stringtmp!=null&&!stringtmp.equals("needless")){
										break;
									}
								}
							}
							mHandler.sendEmptyMessage(7);
						}
					}).start();
				}
				break;
			case 4:
				if(LoaderActivity.txt_prompt!=null){
					LoaderActivity.txt_prompt.setText(mContext.getResources().getString(R.string.systemupdatingprompt));
					LoaderActivity.tv_progress.setVisibility(View.VISIBLE);
					LoaderActivity.tv_speed.setVisibility(View.VISIBLE);
					LoaderActivity.progressBar.setVisibility(View.VISIBLE);
					LoaderActivity.btn.setVisibility(View.INVISIBLE);
				}
				if(Constant.downloadpath!=null&&!Constant.downloadpath.equals("")&&!Constant.isTaskRunning){
					Log.e(TAG,"开始地址下载！");
					task = new Downloadask(Constant.downloadpath,saveDir,1);//实例化下载任务,开始下载
					task.start();
					Toast.makeText(mContext, mContext.getResources().getString(R.string.systemupdating), Toast.LENGTH_SHORT).show();
				}else{
//					Toast.makeText(getApplicationContext(), "下载地址获取失败！！！", Toast.LENGTH_SHORT).show();
				}
				break;
			case 6:
				if(stringtmp!=null&&!stringtmp.equals("needless")){
					stringtmp.trim();
					Constant.view=String.valueOf(stringtmp.charAt(0));
					Constant.force=String.valueOf(stringtmp.charAt(1));
					Constant.downloadpath=stringtmp.substring(2, stringtmp.length());
					Log.e("hhh",Constant.view);
					Log.e("hhh",Constant.force);
					Log.e("hhh",Constant.downloadpath);
					
					if(Constant.view.equals("0")){
						mHandler.sendEmptyMessage(4);
					}else{
						Intent mIntent=new Intent(mContext,LoaderActivity.class);  
						mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						mContext.startActivity(mIntent);
						mHandler.sendEmptyMessage(4);
					}
					
					Log.e(TAG,Constant.view+"   "+Constant.downloadpath);
				}else{
					Constant.downloadpath=null;
					Constant.view=null;
				}
				break;
			case 7:
				if(LoaderActivity.btn!=null){
					LoaderActivity.btn.setEnabled(true);
				}
				if(stringtmp!=null&&!stringtmp.equals("needless")){
					stringtmp.trim();
					Constant.view=String.valueOf(stringtmp.charAt(0));
					Constant.force=String.valueOf(stringtmp.charAt(1));
					Constant.downloadpath=stringtmp.substring(2, stringtmp.length());
					Log.e("hhh",Constant.view);
					Log.e("hhh",Constant.force);
					Log.e("hhh",Constant.downloadpath);
					
					mHandler.sendEmptyMessage(4);
					
					Log.e(TAG,Constant.view+"   "+Constant.downloadpath);
				}else{
					Constant.downloadpath=null;
					Constant.view=null;
					if(LoaderActivity.txt_prompt!=null){
						 LoaderActivity.txt_prompt.setText(mContext.getResources().getString(R.string.systemnoupdating));
						 LoaderActivity.tv_progress.setVisibility(View.INVISIBLE);
						 LoaderActivity.tv_speed.setVisibility(View.INVISIBLE);
						 LoaderActivity.progressBar.setVisibility(View.INVISIBLE);
						 LoaderActivity.btn.setVisibility(View.VISIBLE);
					 }
				}
				break;
			case 9:
				if(LoaderActivity.txt_serveraddress!=null){
					LoaderActivity.txt_serveraddress.setText("服务器地址："+currentServerAddress);
				}
				break;
			case 10:
				//汇报升级结果
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(jsonReportString!=null&&!jsonReportString.equals("")){
							HttpClientRequestImpl.posttoserver(Constant.updateReportPaths,jsonReportString);
						}
					}
				}).start();
				break;
			default:
				break;
			}
		}
	}

	
	 /**********
		 *  @author   唐超
		 *  <Type Create time>     2014年4月22日  
		 *  TODO  >>  子线程用于想主线程发送消息更新UI
		 *****************************************************************************************
		 */
		public static class Downloadask extends Thread{
			private int threadnum=2;
			private String path;//下载路径
			private File saveDir; //下载的保存文件
			private FileDownloader loader; //文件下载器
			/******
			 * < Constructor > 初始化变量
			 * @param path 文件下载路径
			 * @param saveDir 文件保存路径
		     *****************************************************************************************
			 */
			public Downloadask(String path, File saveDir,int num) {
				this.path = path;
				this.saveDir = saveDir;
				this.threadnum=num;
			}

			/*****
			 *  < Method >  如果下载器存在，就退出下载
		     *****************************************************************************************
			 */
			public void exit(){
				if (loader != null) {
					loader.exit();//如果下载器存在，就退出下载
					Constant.isTaskRunning=false;
				}
			}
			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
		     *****************************************************************************************
			 */
			@Override
			public void run() {
				Constant.isTaskRunning=true;
				if(saveDir!=null&&!saveDir.exists()){
					Intent intent=new Intent("SYSTEMUPDATE_DOWNLOAD_NOSAVEDIR");
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
					mContext.sendBroadcast(intent);
					Log.e(TAG,"存储路径不存在！！！广播已发送");
				}
				try {
					loader = new FileDownloader(mContext, path, saveDir, threadnum);//初始化下载
					Log.i(TAG, "进度条最大值(文件大小)："+loader.getFileSize());
					filesize=loader.getFileSize();
					loader.downLoad(new DownloadProgressListener() {
						@Override
						public void onDownloadSize(int downloadSize) {
							Message msg = new Message();//用于向主线程发送下载进度的Messageduixia
							msg.what = Processing;//设置为1
							msg.getData().putInt("size", downloadSize);//把文件下载的长度放到Message对象
							mHandler.sendMessage(msg);//发送消息到消息队列
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message msg = new Message();//用于向主线程发送下载进度的Messageduixia
					msg.what = Failure;//设置为1
					Constant.isTaskRunning=false;
					mHandler.sendMessage(msg);
				}
			}
		}
	
//	private static class checkCAUpdateThread extends Thread
//	  {
//	      public String updatePath = null;
//
//	      public checkCAUpdateThread(String path)
//	      {
//	          this.updatePath = path;
//	      }
//	      public void run()
//	      {
//	          checkCAUpdateFile(updatePath);
//	      }
//	  }
//		
//		
//		public static void checkCAUpdateFile(String updatePath){
//
//	      File file = new File(updatePath+"/update.zip");
//
//	      SocketClient socketClient = null;
//	      socketClient = new SocketClient();
//	      Log.i(TAG,"CaAndroidUpdateFile "+file.getAbsolutePath()+" "+file.getAbsolutePath());
//	      socketClient.writeMess("CaAndroidUpdateFile "+file.getAbsolutePath()+" "+file.getAbsolutePath());
//	      int result = socketClient.readNetResponseSync();
//	      if(result == 0)
//	      {
//	          Log.i(TAG,"CAUpdateFile Check Success!");
//	          Message msg = mHandler.obtainMessage(10);
//	          msg.arg1= 0;
//	          msg.obj = updatePath;
//	          mHandler.sendMessage(msg);
//	      }
//	      else if(result == -1)
//	      {
//	          Log.i(TAG,"CAUpdateFile Check Failed!");
//	          Message msg = mHandler.obtainMessage(10);
//	          msg.arg1 = -1;
//	          msg.obj = updatePath;
//	          mHandler.sendMessage(msg);
//	      }
//	  }
		
		private static void initSystemInfo()
		{
			DeviceInfo.CollectInfo();
			DeviceModel=DeviceInfo.DeviceModel.replace(" ", "");
			Log.e(TAG,">>>>>>>>>>>>>>>>>DeviceModel "+DeviceModel);
			AndroidSDK=DeviceInfo.AndroidSDK;
			DeviceLastMacBin=DeviceInfo.DeviceLastMacBin;
			DeviceFirmWareVer=""+DeviceInfo.DeviceFirmWareVer;
			hardwareversion=""+DeviceInfo.DeviceHardwareVer;
			username=DeviceInfo.DeviceMac;
			UpgradeDefaultSavePath=DeviceInfo.UpgradeDefaultSavePath;
			object=new JSONObject();
			JSONObject object_sub=new JSONObject();
			try {
				object.put("client", object_sub);
				object_sub.put("signtype", "test");
				object_sub.put("testmode", testmode);
				object_sub.put("username", username);
				object_sub.put("datatype", "1");
				object_sub.put("model", DeviceModel);
				object_sub.put("androidsdk", AndroidSDK);
				object_sub.put("macadress", DeviceLastMacBin);
				object_sub.put("firmwareversion", DeviceFirmWareVer);
				object_sub.put("hardwareversion", hardwareversion);
//				object_sub.put("model", "hi3716cv200");
//				object_sub.put("androidsdk", "18");
//				object_sub.put("macadress", "0000");
//				object_sub.put("firmwareversion", "0.8");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				object=null;
			}
			if(object!=null) 
			{
				jsonString=object.toString();
				Log.e(TAG,jsonString);
			}
			if(UpgradeDefaultSavePath!=null&&!UpgradeDefaultSavePath.equals(""))
			{
				if(String.valueOf(UpgradeDefaultSavePath.charAt(UpgradeDefaultSavePath.length()-1)).equals("/")){
					//去除最后的  '/'
					Log.e(TAG,">>>"+String.valueOf(UpgradeDefaultSavePath.charAt(UpgradeDefaultSavePath.length()-1)));
					Constant.path=UpgradeDefaultSavePath.substring(0, UpgradeDefaultSavePath.length()-1);
				}else{
					Constant.path=UpgradeDefaultSavePath;
				}
				Log.i(TAG,"savedir>>>>"+Constant.path);
			}
			saveDir=new File(Constant.path);
		}
		private static void initReportInfo(boolean isSuccessed)
		{
			JSONObject object_report=new JSONObject();
			try {
				object_report.put("username", username);
				object_report.put("model", DeviceModel);
				object_report.put("versionBefore", DeviceFirmWareVer);
				object_report.put("versionAfter", DeviceFirmWareVer);
				if(isSuccessed){
					object_report.put("success", "1");
				}else{
					object_report.put("success", "0");
				}
				jsonReportString=object_report.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jsonReportString=null;
			}
		}
}
