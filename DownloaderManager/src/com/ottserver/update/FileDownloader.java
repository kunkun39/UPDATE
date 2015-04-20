package com.ottserver.update;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/***
 *  @author   TangChao
 *  <Type Create time>     2014��4��15��  ��    ����1:10:59
 *  TODO  >>  �ļ�������
 *****************************************************************************************
 */
public class FileDownloader {
	
	private final static String TAG = "FileDownloader";//���ô�ӡtag
	private Context context; //�����Ķ���
	private FileService fileService; // �������ݿ�ҵ��Bean
	private boolean isExited = false; //ֹͣ���ر�־
	private int downloadedSize; //�Ѿ����ص��ļ��ĳ���
	private int fileSize; //�ļ�����
	private  DownLoadThread[] threads; //���������߳����������������̳߳�
	private File saveFile; //���浽���ص��ļ�
	//��������̵߳����س���
	private Map<Integer, Integer> data = new ConcurrentHashMap<Integer, Integer>();
	private int block; //ÿ���߳����صĳ���
	private String downLoadUrl; //����·��
	public static long SqlTime=500;
	public static int Sqlfilesize=1024;
	private long M100=100*1024*1024;
	private long M500=500*1024*1024;

	/******
	 * < Constructor > �����ļ�������
	 * @param context �����Ļ���
	 * @param downloadUrl �ļ�����·��
	 * @param fileSaveDir  �ļ������Ŀ¼
	 * @param threadNum ���ص��߳���Ŀ
	 *********************************************************************************************
	 */
	public FileDownloader(Context context, String downloadUrl, File fileSaveDir, int threadNum) {
		try {
			//�Ա�����ֵ
			this.context = context;
			this.downLoadUrl = downloadUrl;
			fileService = new FileService(this.context);//ʵ�������ݿ�ҵ��Bean
			URL url = new URL(this.downLoadUrl);
			//���������߳���Ŀ�����̳߳�
			this.threads = new DownLoadThread[threadNum];
			//����һ�����ӣ���ʱ��δ�����Ŀ�ʼ����
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setConnectTimeout(5000);//���ӳ�ʱʱ��Ϊ5��
			httpConnection.setRequestMethod("GET");//����Ϊget����
			httpConnection.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, "
					+ "image/pjpeg, application/x-shockwave-flash,"
					+ " application/xaml+xml, application/vnd.ms-xpsdocument, "
					+ "application/x-ms-xbap, application/x-ms-application, "
					+ "application/vnd.ms-excel, application/vnd.ms-powerpoint,"
					+ " application/msword, */*");//���ÿͻ��˿��Խ��ܵ�ý������
			//���ÿͻ��˵�����
			httpConnection.setRequestProperty("Accept-language", "zh-CN");
			//����������Դ��ҳ�棬���ڷ���������
			httpConnection.setRequestProperty("Referer", downloadUrl);
			//���ÿͻ��˵ı��뷽ʽ
			httpConnection.setRequestProperty("Charset", "UTF-8");
			//�����û�����
			//			httpConnection.setRequestProperty("User-Agent", "Mozilla/4.0");
			//����connection�ķ�ʽ
			httpConnection.setRequestProperty("connection", "Keep-Alive");
			//�����û�����
			httpConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; "
					+ "MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727;"
					+ " .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			httpConnection.connect();//��Զ�̵���Դ�������ӣ���������������
			printHttpResponseHeader(httpConnection);//��ӡHTTPͷ�ֶ�
			if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				//�˴������������򿪷���������ȡ�����룬�����������HTTP_OK(200)ʱִ������Ĵ���
				Log.i(TAG, "����HTTP_OK������ɹ�!====================================");
				this.fileSize = httpConnection.getContentLength();//��ȡ�ļ���С
				if (this.fileSize  <= 0) {
					//���ļ�С�ڵ���0ʱ���׳�����ʱ�쳣
					throw new RuntimeException("δ֪���ļ���С");
				}
				if(fileSize<M100){
					SqlTime=500;
					Sqlfilesize=1024*50;
				}else if(fileSize>M100&&fileSize<M500){
					SqlTime=1000;
					Sqlfilesize=1024*500;
				}else{
					SqlTime=5000;
					Sqlfilesize=1024*1024;
				}
				//�Դ��������ļ�·�����д���
				if(fileSaveDir.isDirectory())//�ļ���·��
				{
					if (!fileSaveDir.exists())  fileSaveDir.mkdir();
					String fileName = getFileName(httpConnection);//��ȡ�ļ���
					Constant.filename=fileName;
					Log.i(TAG, "�ļ���С"+ fileSize+"B");
					Log.i(TAG, "��ȡ�����ļ��� "+ fileSaveDir+"/"+"download.tmp");
					Constant.filepath=fileSaveDir+"/"+"download.tmp";
					//�����ļ�Ŀ¼���ļ��������ļ�
					this.saveFile = new File(fileSaveDir, "download.tmp");
				}else if(fileSaveDir.isFile()){//�ļ�·��
					if (!fileSaveDir.exists())  fileSaveDir.createNewFile();
					Log.i(TAG, "�ļ���С"+ fileSize+"B");
					Log.i(TAG, "��ȡ�����ļ��� "+ fileSaveDir);
					this.saveFile = fileSaveDir;
				}
				//��ȡ���ݿ��е����ؼ�¼
				Map<Integer, Integer> logData = fileService.getData(downloadUrl);
				Log.i(TAG, "���ݿ��еĻ����߳�����"+ logData.size());
				if (logData.size()>0) { //������ݿ��д������ؼ�¼
					Log.i(TAG, "���ݿ��д������ؼ�¼����ʼ�ϵ����� ");
					for (Map.Entry<Integer, Integer> entry : logData.entrySet()) {//��������
						//�����ݿ��л���ĸ��̵߳��Ѿ����ص����ݷŵ�data��
						Log.i(TAG,"�߳� " +entry.getKey()+"�Ѿ����ش�С "+ entry.getValue()+"B");
						data.put(entry.getKey(), entry.getValue());
					}
				}
				if (this.data.size() == this.threads.length) {
					//����Ѿ����ص����ݵ��߳������������õ��߳�����ͬ��������е��߳����س��ȵĺ�
					for (int i = 0; i <  this.threads.length; i++) {//����ÿ���߳��Ѿ����ص�����
						//�����Ѿ����ص�����֮��
						this.downloadedSize += this.data.get(i+1);
					}
					Log.i(TAG, "�Ѿ����ص�����֮���� "+ this.downloadedSize+"B");
				}
			}
			//����ÿ���̵߳����س���
			this.block = (this.fileSize%this.threads.length) == 0 ? 
					this.fileSize/this.threads.length  :
						this.fileSize/this.threads.length+1;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*****
	 *  < Method >  ��ȡ�ļ���
	 *********************************************************************************************
	 */
	private String getFileName(HttpURLConnection httpConnection) {
		//������·���ַ�������ȡ�ļ���
		String fileName = this.downLoadUrl.substring(this.downLoadUrl.lastIndexOf('/')+1);
		if (fileName == null || "".equals(fileName.trim())) {
			//�����ȡ�����ļ���
			for (int i = 0; ; i++) { //���߱���ѭ��
				String mine = httpConnection.getHeaderField(i);
				if (mine == null) break;//���ļ�ĩβ�˳�ѭ��
				if ("content-disposition".equals(httpConnection.getHeaderFieldKey(i)
						.toLowerCase())) {//��ȡcontent-disposition �ֶΣ�������ܰ����ļ���
					//ʹ��������ʽ��ѯ�ļ���
					Matcher matcher = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
					if (matcher.find()) return matcher.group(1);//���Ͼͷ���
				}
			}
			return UUID.randomUUID()+".tmp";
		}
		//�������ϵı�ʶ����(ÿ����������Ψһ�ı�ʾ)
		//�Լ�CPUʱ�ӵ�Ψһ�������ɵ�һ��ʮ�����Ƶ�������Ϊ�ļ���
		return fileName;
	}

	/*****
	 *  < Method >  ��ӡHTTPͷ�ֶ�
	 *********************************************************************************************
	 */
	private static void printHttpResponseHeader(HttpURLConnection httpConnection) {
		
		//ʹ��LinkedHashMap���Ա�֤�����ȡ����˳�� ��ͬ
		Map<String, String> header = new LinkedHashMap<String, String>();
		for (int i = 0;true; i++) {//�˴�������ѭ������Ϊ���Ȳ�֪��ͷ�ֶε�����
			//��ȡ��i��ͷ�ֶε�ֵ
			String fieldValue = httpConnection.getHeaderField(i);
			if (fieldValue == null)  break;//���û��ֵ�˾ͱ�ʾͷ�ֶ�ѭ����ϣ��˳�ѭ��
			header.put(httpConnection.getHeaderFieldKey(i), fieldValue);
			//httpConnection.getHeaderFieldKey(i)���ص�i��ͷ�ֶε�key
		}
		for (Map.Entry<String, String> entry : header.entrySet()) {
			//ʹ��for-eachѭ��������ȡ�ֶ�ֵ����ʱ�ı���˳��������˳����ͬ
			//��Ŀ���㣬���м�ֵ��ʱ��ͻ�ȡ��ֵ������Ϊ��
			String key = entry.getKey()!=null ? entry.getKey() + ":  " : "";
			Log.v(TAG, key+entry.getValue());//���ֵ�����
		}
	}

	/*****
	 *  < Method >  ��ȡ���ص��߳���Ŀ
	 *********************************************************************************************
	 */
	public int getThreadSize(){
		return threads.length;
	}

	/*****
	 *  < Method > �����˳���־Ϊtrue,�˳����� 
	 *********************************************************************************************
	 */
	public void exit(){
		this.isExited = true;
	}

	/*****
	 *  < Method >  ��ȡ�Ƿ��Ѿ��˳�������
	 *********************************************************************************************
	 */
	public boolean getExited(){
		return this.isExited;
	}

	/*****
	 *  < Method >  ��ȡ�ļ��Ĵ�С
	 *********************************************************************************************
	 */
	public int getFileSize(){
		return fileSize; // �ӳ�Ա�����л�ȡ�����ļ��Ĵ�С
	}

	/*****
	 *  < Method >  �ۼ��Ѿ����ص��ļ��Ĵ�С
	 *  ʹ��synchronized�ؼ��ֽ���������ʵ�����
     *****************************************************************************************
	 */
	protected synchronized void appendDownloadSize(int size){
		downloadedSize += size; //��ʵʱ���ص��ļ����ȼӵ������ص��ļ��ܳ����У��ۼ��Ѿ����ص��ļ��Ĵ�С
	}

	/*****
	 *  < Method >  ����ָ���߳�������ص��ļ�λ�ã����̲߳�����ʹ��synchronized�ؼ��ֽ���������ʵ�����
	 *  @param threadId �߳�ID
	 *  @param downPosition �߳��Ѿ����ص����ļ���λ��
	 *********************************************************************************************
	 */
	protected synchronized void updateDownloadSize(int threadId , int downPosition){
		//��ָ�����̵߳�ID�������µ����س��ȣ�ԭ�ȵ�ֵ�ᱻ���ǵ�
		this.data.put(threadId, downPosition);
		//�������ݿ��е��߳����س���
//		this.fileService.update(this.downLoadUrl, threadId, downPosition);
	}
	protected synchronized void updateDownloadSizeSql(int threadId , int downPosition){
		//��ָ�����̵߳�ID�������µ����س��ȣ�ԭ�ȵ�ֵ�ᱻ���ǵ�
//		this.data.put(threadId, downPosition);
		//�������ݿ��е��߳����س���
		this.fileService.update(this.downLoadUrl, threadId, downPosition);
	}

	/*****
	 *  < Method >  ��ʼ��������
	 *  @param downloadProgressListener �������ص��ļ����ȵı仯��
	 *   �������Ҫʵʱ�˽����ؽ��ȵı仯���Դ���null
	 *********************************************************************************************
	 */
	public int downLoad(DownloadProgressListener downloadProgressListener )throws Exception{
			try {
				/*
				 * �ڱ��ش���һ������Ҫ���ص��ļ���ȴ�С���ļ�
				 * ʹ��RandomAccessFile�������Ա㲻ͬ�̴߳Ӳ�ͬ��λ��д���ļ�
				 */
				RandomAccessFile	raf = new RandomAccessFile(saveFile, "rw");
				if (this.fileSize>0) raf.setLength(this.fileSize);//�����ļ��Ĵ�С
				raf.close();//�ر��ļ�ʹ������Ч���ɹ����������ļ�
				URL url = new URL(this.downLoadUrl);
				if (this.data.size() != this.threads.length) { 
					//���ԭ��δ���ػ�ԭ�ȵ������߳��������ڵĲ�һ��
					this.data.clear();//�������Ԫ��
					for (int i = 0; i <this.threads.length; i++) {//�����̳߳�
						data.put(i+1, 0);//��ʼ��ÿ���̵߳�������Ϊ0
					}
					this.downloadedSize = 0; //�����Ѿ����صĳ���Ϊ0 
				}
				for (int i = 0; i < this.threads.length; i++) {//�����߳̽�������
					int downloadedLength = this.data.get(i+1);//ͨ���ض����߳�id��ȡ���̵߳��Ѿ����ص��ļ�����
					if (downloadedLength < this.block &&this.downloadedSize<this.fileSize) {//�ж��Ƿ��������
						this.threads[i] = new DownLoadThread(this,url,this.saveFile,this.block,this.data.get(i+1),i+1);
						this.threads[i].setPriority(7);//�����߳����ȼ�
						this.threads[i].start();//��ʼ����
						Log.i(TAG, "�߳� "+(i+1)+" ����");
					}else {
						this.threads[i]  = null;//�����߳��Ѿ���������ˣ�����Ϊnull
					}
				}
				fileService.delete(this.downLoadUrl);//����������ؼ�¼��ɾ������Ȼ�������
				fileService.save(this.downLoadUrl,Constant.view,Constant.path, this.data);//���Ѿ����ص�д�����ݿ�
				//Log.i(TAG, "data����Ŀ�� "+data.size());
				boolean notFinished  =  true;//����δ���
				while (notFinished) {//ѭ�������߳��Ƕ��������
					try {
						Thread.sleep(400);//����400����
						notFinished = false;//�ٶ������̶߳����������
						for (int i = 0; i <this.threads.length; i++) {
							if ((this.threads[i] != null) && !(this.threads[i].isFinished())) {//������߳�δ�������
								notFinished = true;//���ñ�־λΪ�������
								if (this.threads[i].getDownlodedSize() == -1) {
									//�������ʧ�ܣ���ԭ���Ѿ����ص����ݻ�������������
									//���¿������߳�����
									this.threads[i] = new DownLoadThread(this,url,this.saveFile,this.block,this.data.get(i+1),i+1);
									this.threads[i].setPriority(7);//�����߳����ȼ�
									this.threads[i].start();//��ʼ����
								}
							}
							if (downloadProgressListener != null) //�������˼�������֪ͨĿǰ�Ѿ����ص��ļ��ĳ���
								downloadProgressListener.onDownloadSize(this.downloadedSize);
							if (downloadedSize == this.fileSize)//�������ɾ����¼
							{
								fileService.delete(this.downLoadUrl);
//								fileService.deleteAll();
							}
							
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("�ļ������ڣ�����");
			} catch (IOException e) {
				e.printStackTrace();
				Intent intent=new Intent("SYSTEMUPDATE_DOWNLOAD_CLEARSDCARD");
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
				context.sendBroadcast(intent);
				throw new RuntimeException("�ռ䲻��������");
			}
		return this.downloadedSize;
	}
}
