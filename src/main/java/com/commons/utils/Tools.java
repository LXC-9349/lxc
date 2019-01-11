package com.commons.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * 辅助方法
 * @author DoubleLi
 * @time 2018年12月10日
 * 
 */
public class Tools {


	// 文件允许格式
	public static String[] allowFiles = {".jpg", ".png",".jpeg",".bmp",".pdf",".xls",".xlsx",".doc",".docx",".wps",".zip",".rar" };
	// 文件允许格式
	public static String[] xlsFiles = {".xls",".xlsx" };

	/**
	 * 获取文件后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileSuffix(String fileName) {
		if (isEmpty(fileName)) {
			return "";
		}
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 判断文件后缀
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean formatCheck(String suffix) {
		if (Arrays.asList(allowFiles).contains(suffix)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断文件大小
	 * 
	 * @param size 文件大小
	 * @param size 限制大小
	 * @param unit 限制单位（B,K,M,G）
	 * @return
	 */
	public static boolean checkFileSize(long filesize,int size,String unit) {
		long len = filesize;
		double fileSize = 0;
		if("B".equals(unit.toUpperCase())){
			fileSize = (double)len;			
		}else if("K".equals(unit.toUpperCase())){
			fileSize = (double)len / 1024;
		}else if("M".equals(unit.toUpperCase())){
			fileSize = (double)len / 1048576;
		}else if("G".equals(unit.toUpperCase())){
			fileSize = (double)len / 1073741824;
		}
		if(fileSize > size){
			return false;
		}
		return true;
	}

	/**
	 * 随机生成六位数验证码 
	 * @return
	 */
	public static int getRandomNum(){
		Random r = new Random();
		return r.nextInt(900000)+100000;//(Math.random()*(999999-100000)+100000)
	}

	/**
	 * 获取四位随机数
	 * @return
	 */
	public static String getRandomNumForApplogin(){
		Random r = new Random();
		return String.valueOf(r.nextInt(9000)+1000);
	}
	
	/**
	 * 获取两位随机数
	 * @return
	 */
	public static String getRandomNumTwo(){
		Random r = new Random();
		return String.valueOf(r.nextInt(100));
	}

	/**
	 * 生成UUID
	 * 
	 * @return
	 */
	public static String generateUuid() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}

	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}



	/**
	 * 
	 * @param strs
	 * @return
	 */
	public static boolean hasEmpty(String... strs) {
		if (strs == null || strs.length == 0) {
			return true;
		}
		boolean result = false;
		for (String str : strs) {
			if (str == null || str.trim().equals("")) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}

	/**
	 * 字符串转换为字符串数组
	 * @param str 字符串
	 * @param splitRegex 分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str,String splitRegex){
		if(isEmpty(str)){
			return null;
		}
		return str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * @param str	字符串
	 * @return
	 */
	public static String[] str2StrArray(String str){
		return str2StrArray(str,",\\s*");
	}

	/**
	 * 写txt里的单行内容
	 * @param filePath  文件路径
	 * @param content  写入的内容
	 */
	public static void writeFile(String fileP,String content){
		String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
		filePath = (filePath.trim() + fileP.trim()).substring(6).trim();
		PrintWriter pw;
		try {
			pw = new PrintWriter( new FileWriter(filePath));
			pw.print(content);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证邮箱
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email){
		boolean flag = false;
		try{
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 * @param mobiles
	 * @return
	 */
	public static boolean checkMobileNumber(String mobileNumber){
		boolean flag = false;
		try{
			Pattern regex = Pattern.compile("^(1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|7[0-9])\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}

	/**
	 * 读取txt里的单行内容
	 * @param filePath  文件路径
	 */
	public static String readTxtFile(String fileP) {
		try {

			String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.trim() + fileP.trim();

			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { 		// 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);	// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					return lineTxt;
				}
				read.close();
			}else{
				System.out.println("找不到指定的文件,查看此路径是否正确:"+filePath);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		}
		return "";
	}


	/**
	 * 判断是否成人true 未成年 false
	 * @param 
	 * @return
	 */
	public static boolean ifGrown_up(String num) throws ParseException {
		int year = Integer.parseInt(num.substring(6, 10));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date update=null;
		try {
			update = sdf.parse(String.valueOf(year + 18) + num.substring(10, 14));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		Date today = new Date();
		if(update!=null){
			return today.after(update);
		}
		return false;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 * @return
	 */
	public static boolean createFolder(String path) {
		boolean flag = false;
		if (null == path || "".equals(path)) {
			return flag;
		} else {
			File f = new File(path);
			if (!f.exists()) {
				flag = f.mkdir();
			}
			return true;
		}
	}

	/**
	 * 压缩文件-由于out要在递归调用外,所以封装一个方法用来 调用ZipFiles(ZipOutputStream out,String
	 * path,File... srcFiles)
	 * 
	 * @param zip
	 *            压缩后的文件（带路径）
	 * @param path
	 *            压缩包最外层名称，如果为空，则为要压缩的最外层文件夹名称
	 * @param srcFiles
	 *            要压缩的目标源文件或文件夹
	 * @throws IOException
	 */
	public static void ZipFiles(File zip, String path, File... srcFiles)
			throws IOException {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
		ZipFiles(out, path, srcFiles);
		out.close();
	}

	/**
	 * 压缩文件-File
	 * 
	 * @param path
	 *            压缩包最外层名称，如果为空，则为要压缩的最外层文件夹名称
	 * @param srcFiles
	 *            被压缩源文件
	 */
	public static void ZipFiles(ZipOutputStream out, String path,
			File... srcFiles) {
		if (!StringUtils.isEmpty(path)) {
			path = path.replaceAll("\\*", "/");
			if (!path.endsWith("/")) {
				path += "/";
			}
		} else {
			path = "";
		}

		byte[] buf = new byte[1024];
		try {
			for (int i = 0; i < srcFiles.length; i++) {
				if (srcFiles[i].isDirectory()) {
					File[] files = srcFiles[i].listFiles();
					String srcPath = srcFiles[i].getName();
					srcPath = srcPath.replaceAll("\\*", "/");
					if (!srcPath.endsWith("/")) {
						srcPath += "/";
					}
					out.putNextEntry(new ZipEntry(path + srcPath));
					System.out.println(path + srcPath);
					ZipFiles(out, path + srcPath, files);
				} else {
					FileInputStream in = new FileInputStream(srcFiles[i]);
					System.out.println(path + srcFiles[i].getName());
					out.putNextEntry(new ZipEntry(path + srcFiles[i].getName()));
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					out.closeEntry();
					in.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹下面的所有文件
	 * 
	 * @param folderPath
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		if(tempList!=null)
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/***
	 * 根据路劲删除文件
	 * @param path
	 * @return
	 */
	public static boolean delFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		return true;
	}

	/**
	 * 验证证件是10-32位的数字
	 * @param creditNo
	 * @return
	 */
	public static boolean checkCredit(String creditNo){
		if(isEmpty(creditNo)){
			return false;
		}
		if(creditNo.length() < 10 || creditNo.length() > 32){
			return false;
		}
		if(!isNumber(creditNo)){
			return false; 
		}
		return true;
	}

	/**
	 * 验证数字
	 * @param number
	 * @return
	 */
	public static boolean isNumber(String number){
		return Pattern.compile("^(\\d+.?\\d{0,2})$").matcher(number).matches(); 
	}

	/**
	 * 验证多个数字
	 * @param strs
	 * @return
	 */
	public static boolean hasNumber(String... strs) {
		if (strs == null || strs.length == 0) {
			return false;
		}
		boolean result = true;
		for (String str : strs) {
			if (!Pattern.compile("^(\\d+.?\\d{0,2})$").matcher(str).matches()) {
				result = false;
				break;
			}
		}
		return result;
	}

	/**
	 * 导出中文文件时名称处理方法
	 *@author Limenghui
	 *@date 2015-7-10-上午10:27:18
	 * @param s
	 * @return
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 生成单号可自定义
	 * @param 
	 * @return
	 */
	public static String getApplyNo() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + (new Random().nextInt(90000000)+10000000);

	}
	
	/**
	 * 验证中文和英文
	 */
	public static boolean checkChinaEnglish(String ChinaEnglish){
		//	return Pattern.compile("^[a-zA-Z\\u4e00-\\u9fa5]{1,16}$").matcher(ChinaEnglish).matches(); 
		return Pattern.compile("^[a-zA-Z\\u4e00-\\u9fa5\\s]{1,16}$").matcher(ChinaEnglish).matches(); 
	}

	/**
	 * 验证中文
	 */
	public static boolean checkChina(String China){
		return Pattern.compile("^[\\u4e00-\\u9fa5]+$").matcher(China).matches(); 
	}

	/**
	 * 验证数字
	 */
	public static boolean checkNumber(String number){
		return Pattern.compile("^[0-9]+$").matcher(number).matches(); 
	}
		
	/**
	 * 简单验证 银行卡  数字和空格的组合
	 * @param bankNo
	 * @return
	 */
	public static boolean checkBankNo(String bankNo){
		return Pattern.compile("^[\\d\\s]+$").matcher(bankNo).matches();
	}

	/**
	 * 验证金钱
	 */
	public static boolean checkMoney(String money){
		return Pattern.compile("^(\\d+.?\\d{0,2})$").matcher(money).matches(); 
	}

	/**
	 * 验证固定电话或传真
	 */
	public static boolean checkTelephone(String telePhone){
		return Pattern.compile("^((0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$").matcher(telePhone).matches(); 
	}

	public static boolean checkSite(String site){
		boolean flag = false;
		try {
			String strRegex="^((https|http|ftp|rtsp|mms)?://)"
					+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
					+ "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
					+ "|" // 允许IP和DOMAIN（域名）
					+ "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
					+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
					+ "[a-z]{2,6})" // first level domain- .com or .museum
					+ "(:[0-9]{1,4})?" // 端口- :80
					+ "((/?)|" // a slash isn't required if there is no file name
					+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
			Pattern regex = Pattern.compile(strRegex);
			Matcher matcher = regex.matcher(site);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证传真号
	 * @param fax
	 * @return
	 */
	public static boolean checkFax(String fax){
		boolean flag = false;
		try {
			Pattern regex = Pattern.compile("^(\\d{3,4}-)?\\d{7,8}$");
			Matcher matcher = regex.matcher(fax);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证邮编
	 * @param zipCode
	 * @return
	 */
	public static boolean checkZIpCode(String zipCode){
		boolean flag = false;
		try {
			Pattern regex = Pattern.compile("^[1-9][0-9]{5}$");
			Matcher matcher = regex.matcher(zipCode);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}


	/**
	 * 转换字符串对像,如果是null，就转换为""，否则不做转换
	 * 
	 * @param obj
	 *            要转化的字符串
	 * @return 格式化之后的字符串
	 */
	public static String convertIsNull(Object obj) {
		if (obj instanceof String) {
			return ((obj == null) ? "" : ((String) obj).trim());
		} else {
			return ((obj == null) ? "" : (obj.toString()).trim());
		}
	}

	/**
	 * BigDecimal 保留两位小数点
	 */
	public static BigDecimal convertBigDecimal(BigDecimal bd){
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00"); 
		BigDecimal bdc = new BigDecimal(df.format(bd));
		return bdc;
	}

	/**
	 * 折扣率显示处理
	 */
	public static String convertDiscountRate(String cdr){
		if(Tools.isEmpty(cdr)){
			return "0";
		}
		if(cdr.indexOf(".")==0){
			cdr="0."+cdr.substring(cdr.indexOf(".")+1);
		}
		if(cdr.indexOf(".")==-1){
			cdr="0."+cdr;
		}
		System.out.println(cdr);
		return cdr;
	}

	/**
	 * 功能描述：传入正则和字符，检测是否匹配
	 */
	public static boolean checkZzToStr(String Zz,String str){
		return Pattern.compile(Zz).matcher(str).matches();
	}

	/**
	 * 功能描述：密码格式验证
	 */
	public static String checkPassword(String password,String userName){
		String message = null;
		boolean lowerBoolean = checkZzToStr(".*?[a-z]+.*?",password);
		boolean upperBoolean = checkZzToStr(".*?[A-Z]+.*?",password);
		boolean numberBoolean = checkZzToStr(".*?[\\d]+.*?",password);
		boolean charsBoolean = checkZzToStr(".*?[`=[{}];',./~!@#$%^&*()_+|\\-|:\"<>?]+.*?",password);
		if(password.length()<8){
			message = "密码长度不少于8位";
		}else if(!((lowerBoolean && upperBoolean && numberBoolean) || (lowerBoolean && upperBoolean && charsBoolean) || (lowerBoolean && numberBoolean && charsBoolean) || (upperBoolean && numberBoolean && charsBoolean))){
			message = "密码至少包含大小写字母、数字、特殊字符中的三种";
		}else if(password.indexOf(userName)!=-1){
			System.out.println(password.indexOf(userName));
			message = "用户名不得作为口令一部分";
		}else {
			message = "成功";
		}
		return message;
	}

	/**参数检查
	 * 
	 */
	public static String [] formParamCheck(String [] str) {
		String inj_str = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"  
				+ "(\\b(select|update|and|or|delete|insert|truncate|trancate|table|from|grant|char|into|substr|group_concat|column_name|information_schema.columns|table_schema|" +
				"union|order|by|like|net user|xp_cmdshell|union|where|ascii|declare|exec|execute|count|master|into|create|alter|drop|execute)\\b)";  
		String[] inj_stra = inj_str.split("\\|");
		for(int i = 0; i < str.length; i++){
			for(int j = 0; j < inj_stra.length; j++){
				if (str[i].indexOf(" " + inj_stra[j] + " ") >= 0) {
					str[i] = str[i].replace(" " + inj_stra[j] + " ", "");
				}
			}
		}
		return str;
	}

	/**
	 * 功能描述：过滤特殊字符
	 * 
	 * @param src
	 * @return
	 */
	public static String encoding(String src) {
		if (src == null)
			return "";
		StringBuilder result = new StringBuilder();

		src = src.trim();
		for (int pos = 0; pos < src.length(); pos++) {
			switch (src.charAt(pos)) {
			case '\"':
				result.append("&quot;");
				break;
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '\'':
				result.append("&apos;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '%':
				result.append("&pc;");
				break;
			case '#':
				result.append("&shap;");
				break;
			case '?':
				result.append("&ques;");
				break;
			default:
				result.append(src.charAt(pos));
				break;
			}
		}

		return result.toString();
	}
	
	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 根据浏览器的不同设置不同的编码格式  防止中文乱码
	 * @param fileName 下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletRequest request,HttpServletResponse response, String fileName) {
		try {
			//中文文件名支持
			String encodedfileName = null;
			String agent = request.getHeader("USER-AGENT");
			if(null != agent && -1 != agent.indexOf("MSIE")){//IE
				encodedfileName = java.net.URLEncoder.encode(fileName,"UTF-8");
			}else if(null != agent && -1 != agent.indexOf("Mozilla")){
				encodedfileName = new String (fileName.getBytes("UTF-8"),"iso-8859-1");
			}else{
				encodedfileName = java.net.URLEncoder.encode(fileName,"UTF-8");
			}
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * 金额格式化
	 * @param val
	 * @return
	 */
	public static String parseDecimalFormat(Double val){
		DecimalFormat df=new DecimalFormat("#,###.####"); 
		return df.format(val);
	}
	
	public static void main(String[] args) {
		System.out.println(generateUuid());
	}

}
