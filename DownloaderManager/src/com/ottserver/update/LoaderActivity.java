package com.ottserver.update;

import com.ots.deviceinfoprovide.DeviceInfo;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 *   @author   tangchao
 *  <����ʱ��>     2014��4��22��  ��    ����4:00:36
 *  TODO  < ���� >  ʵ�ֶ��̶߳ϵ�����
 *****************************************************************************************
 */
public class LoaderActivity extends Activity{

	private final static String TAG = "loaderActivity";
	public static TextView txt_prompt;//��ʾ��
	public static TextView tv_progress;//��ʾ���Ȱٷֱ�
	public static TextView tv_speed;//��ʾ�����ٶ�
	public static ProgressBar progressBar;//���ؽ�������ʵʱͼ�λ���ʾ����
	public static Button btn=null;
	private String DeviceFirmWareVer=null;
	private int mKeyCodeIndex = 0;
	private final int[] mKeyCode = {KeyEvent.KEYCODE_2, KeyEvent.KEYCODE_5, KeyEvent.KEYCODE_8, KeyEvent.KEYCODE_8, KeyEvent.KEYCODE_8};
	private Handler mHandler =null;
	private Runnable mRunnable =null;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 *****************************************************************************************
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clayout_main);
		//��ʼ���������
		init();
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//����Service������ظ���
				LoaderService.mHandler.sendEmptyMessage(8);
				btn.setEnabled(false);
			}
		});
		
		mHandler =new Handler();
		mRunnable = new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				mKeyCodeIndex = 0;
			}
		};

	}
	
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(Constant.isTaskRunning){
			//1.�㲥ֱ�Ӹ��������ص�ַ2.δ������ɵ������������
			txt_prompt.setText(getResources().getString(R.string.systemupdatingprompt));
			tv_progress.setVisibility(View.VISIBLE);
			tv_speed.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.VISIBLE);
			btn.setVisibility(View.INVISIBLE);
		}else{
			txt_prompt.setText(getResources().getString(R.string.systemversion)+DeviceFirmWareVer);
			tv_progress.setVisibility(View.INVISIBLE);
			tv_speed.setVisibility(View.INVISIBLE);
			progressBar.setVisibility(View.INVISIBLE);
			btn.setVisibility(View.VISIBLE);
		}
	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.e(TAG,"onkeydown : "+keyCode);
		if(isAllowSetOP(keyCode)){
			//2��5��8�������ضԻ����趨����ģʽ
//			loaderService.testmode="true";
//			
			AlertDialog.Builder builder = new Builder(LoaderActivity.this);
			builder.setMessage(getResources().getString(R.string.chosemode));
			builder.setTitle(getResources().getString(R.string.prompt));
			builder.setPositiveButton(getResources().getString(R.string.test),new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					LoaderService.testmode="true";
					SharedPreferences preferences=getSharedPreferences("testmode",Context.MODE_PRIVATE);
					Editor editor=preferences.edit();
					editor.putString("tag", "true");
					editor.commit();
					dialog.dismiss();
				}
			});
			builder.setNegativeButton(getResources().getString(R.string.formal),new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					LoaderService.testmode="false";
					SharedPreferences preferences=getSharedPreferences("testmode",Context.MODE_PRIVATE);
					Editor editor=preferences.edit();
					editor.putString("tag", "false");
					editor.commit();
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_BACK:
			Log.e(TAG,"onkeydown back!!!");
			Constant.isTaskRunning=false;
			if(LoaderService.task!=null){
				LoaderService.task.exit();
			}
			Intent intent=new Intent("com.ottserver.update.loaderService");
			LoaderActivity.this.stopService(intent);
			break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Constant.isTaskRunning=false;
		if(LoaderService.task!=null){
			LoaderService.task.exit();
		}
		Log.e(TAG,"kill service!!!!");
//		ActivityManager manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);       
//		manager.killBackgroundProcesses(getPackageName());
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 *  < Method > 
	 *  ʵ�����������  ��ȡϵͳ��Ϣ
	 *****************************************************************************************
	 */
	private void init() {
		txt_prompt = (TextView)this.findViewById(R.id.txt1);
		tv_progress = (TextView)this.findViewById(R.id.tv_showprogress);
		tv_speed = (TextView)this.findViewById(R.id.tv_showspeed);
		progressBar = (ProgressBar)this.findViewById(R.id.pb_showdownload);
		btn = (Button)this.findViewById(R.id.btn);
		
		DeviceInfo.CollectInfo();
		DeviceFirmWareVer=""+DeviceInfo.DeviceFirmWareVer;
		txt_prompt.setText(getResources().getString(R.string.systemversion)+DeviceFirmWareVer);
		
		//����Service
		LoaderService.startByBroadcast=false;
		Intent intent=new Intent(LoaderActivity.this,LoaderService.class);
		startService(intent);
	}
	
	private boolean isAllowSetOP(int keyCode) {
		mHandler.removeCallbacks(mRunnable);
		mHandler.postDelayed(mRunnable, 1000);
		switch (mKeyCodeIndex) {
		case 0:
			if (keyCode == mKeyCode[mKeyCodeIndex]) {
				mKeyCodeIndex++;
			} else {
				mKeyCodeIndex = 0;
			}
			break;
		case 1:
			if (keyCode == mKeyCode[mKeyCodeIndex]) {
				mKeyCodeIndex++;
			} else {
				mKeyCodeIndex = 0;
			}
			break;
		case 2:
			if (keyCode == mKeyCode[mKeyCodeIndex]) {
				return true;
			} else {
				mKeyCodeIndex = 0;
			}
			break;
		case 3:
			break;
		default:
			break;
		}
		return false;
	}



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	
}
