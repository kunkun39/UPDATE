package com.ottserver.update;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.SystemClock;
import android.util.Log;

/**********
 *  @author   tangchao
 *  <Type Create time>     2014��4��21��  
 *  TODO  >>  �����̣߳����ݾ�������ص�ַ�����ݱ��浽�ļ������ؿ�Ĵ�С���Ѿ����ص����ݴ�С����Ϣ��������
 * ****************************************************************************************
 */
public class DownLoadThread extends Thread{

    private final static String TAG = "DownLoadThread";
	private File saveFile;  //���ص����ݱ��浽�ļ�
	private URL downUrl;   //���ص�Url
	private int block; //ÿ���߳����ص����ݴ�С
	private int threadId = -1;//��ʼ���߳�id����
	private int downloadedSize; //�Ѿ����ص���λ��
	private boolean finished;//���߳��Ƿ�������ɵı�־
	private FileDownloader downloader;//�ļ�������

	/******
	 * < Constructor > ��ʼ������
	 * @param fileDownloader �ļ�������
	 * @param downUrl ���ص�ַ
	 * @param saveFile ������ļ�
	 * @param block �����߳����صĳ���
	 * @param downloadedSize �Ѿ����صĳ���
	 * @param threadId �߳�ID
	 * ********************************************************************************************
	 */
	public DownLoadThread(FileDownloader fileDownloader, URL downUrl, 
					File saveFile, int block, int downloadedSize, int threadId) {
		this.downUrl = downUrl;
		this.saveFile = saveFile;
		this.block = block;
		this.downloader = fileDownloader;
		this.threadId = threadId;
		this.downloadedSize = downloadedSize;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 *********************************************************************************************
	 */
	@Override
	public void run() {
		if (downloadedSize<block) {//���û���������
			//������
			try {
				//����һ�����ӣ���ʱ��δ�����Ŀ�ʼ����
				HttpURLConnection httpConnection = (HttpURLConnection) downUrl.openConnection();
				httpConnection.setConnectTimeout(5 * 1000);
				//ʹ��Get��ʽ����
				httpConnection.setRequestMethod("GET");
				httpConnection.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, "
												+ "image/pjpeg, application/x-shockwave-flash,"
												+ " application/xaml+xml, application/vnd.ms-xpsdocument, "
												+ "application/x-ms-xbap, application/x-ms-application, "
												+ "application/vnd.ms-excel, application/vnd.ms-powerpoint,"
												+ " application/msword, */*");
				httpConnection.setRequestProperty("Accept-Language", "zh-CN");
				httpConnection.setRequestProperty("Referer", downUrl.toString());
				httpConnection.setRequestProperty("Charset", "UTF-8");
				int startPos = block * (threadId - 1) + downloadedSize;//��ʼλ��
				int endPos = block * threadId -1;//����λ��
				httpConnection.setRequestProperty("Range", "bytes=" + startPos + "-"+ endPos);//���û�ȡʵ�����ݵķ�Χ
				httpConnection.setRequestProperty("User-Agent", "Mozilla/4.0 "
						+ "(compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; "
						+ ".NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; "
						+ ".NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
				httpConnection.setRequestProperty("Connection", "Keep-Alive");
				InputStream inStream = httpConnection.getInputStream();//��ȡԶ�̵�������
				byte[] buffer = new byte[1024*512];//���ñ��ػ�������λ1MB
				int offset = 0;//����ÿ�ζ�ȡ��������
				long curTime=SystemClock.currentThreadTimeMillis();
				int curSize=downloadedSize;
				Log.i(TAG, "�߳� "+this.threadId+"  �Ŀ�ʼ����λ����:"+ startPos+" B"+"  ���ݿ����ʱ������"+downloader.SqlTime+" ms"+"  ���ȼ����"+downloader.Sqlfilesize+"  B");//��ӡ�߳̿�ʼ��λ��
				RandomAccessFile threadFile = new RandomAccessFile(this.saveFile, "rwd");
				threadFile.seek(startPos);//�ļ�ָ��ָ���ļ���ʼ�ĵط�
				while (!downloader.getExited() && (offset = inStream.read(buffer, 0, 1024*512)) !=-1) {
					//���û�û��Ҫֹͣ���أ�ͬʱû�дﵽ�ļ���ĩβ��ʱ���һֱѭ����ȡ����
					threadFile.write(buffer, 0, offset);//ֱ�Ӱ�����д���ļ���
					downloadedSize += offset;//�����صĳ��ȼӵ������صĳ�����
					downloader.updateDownloadSize(this.threadId,  downloadedSize);//�������ݳ���
					downloader.appendDownloadSize(offset);//�������ص����ݳ��ȼӵ��Ѿ����ص������ܳ�����
					if((SystemClock.currentThreadTimeMillis()-curTime)>downloader.SqlTime||((downloadedSize-curSize)>downloader.Sqlfilesize)){
						curTime=SystemClock.currentThreadTimeMillis();
						curSize=downloadedSize;
						threadFile.close();
						threadFile=null;
						Log.i(TAG,"�߳� "+this.threadId+"  ���ݿ����");
						downloader.updateDownloadSizeSql(this.threadId,  downloadedSize);//д�����ݿ�
						threadFile = new RandomAccessFile(this.saveFile, "rwd");
						threadFile.seek(block * (threadId - 1) + downloadedSize);
					}
				}
				//���߳�����������ϻ����û�ֹͣ������
				threadFile.close();
				inStream.close();
				if (downloader.getExited()) {
					Log.i(TAG, "Thread " + this.threadId+ "ֹͣ����");
				}else {
					Log.i(TAG, "Thread " + this.threadId+ "�������");
				}
				//����������ɱ�־λtrue��������������ɻ����û�ֹͣ����
				this.finished = true;
			} catch (IOException e) {
				this.downloadedSize = -1;//�����߳��Ѿ����صĳ���Ϊ-1
				e.printStackTrace();
			}
		}
	}
	/*****
	 *  < Method >  �Ƿ��������
	 *********************************************************************************************
	 */
	public boolean isFinished(){
		return this.finished;
	}
	/*****
	 *  < Method >  �Ѿ����ص����ݵĴ�С
	 *  @return �������-1����ʾ����ʧ��
	 *********************************************************************************************
	 */
	public  int  getDownlodedSize() {
		return this.downloadedSize;
	}
}
