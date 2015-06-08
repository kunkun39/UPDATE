package com.ottserver.update;

public class Constant {
	//全局参数
	public static boolean isTaskRunning=false;//当前是否有下载任务
	public static String downloadpath=null;//下载url
	public static String view=null;//界面选项
	public static String force=null;//界面选项
	public static String path="/mnt/sda/sda1";//默认下载存储路径
	public static String filepath=null;//文件地址
	public static String filename=null;//文件名字
//	public static String[] updatePaths={
//							"http://www.changhongotv.scmcc.com.cn:8080/update/main.html",
//							"http://www.chmobileott.cn:8080/update/main.html",
//							"http://www.ottserver.com:8080/update/main.html"};
	public static String[] updatePaths={
		"http://www.ottserver.com:8099/update/main.html"
	};
	public static String updateReportPaths="http://www.ottserver.com:8099/update/report.html";
}
