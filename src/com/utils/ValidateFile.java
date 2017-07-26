package com.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateFile {
	/**
	 * 验证是否为数字
	 * 数字为true
	 * 非数字为false
	 * @param str
	 * @return
	 */
	public static boolean isNumer(String str){ 
	    if(str.matches("\\d+")){ 
	      return true; 
	    }else{ 
	      return false; 
	    } 
	} 
	/**
	 * 判断是否为正浮点数
	 * 浮点数为true
	 * 非浮点数为false
	 * @param num
	 * @param type
	 * @return
	 */
    public static boolean checkFloat(String num){   
        String eL = "^((\\d+\\.\\d{1,2})|(\\d+))$";//正浮点数   ^((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*))$
        Pattern p = Pattern.compile(eL);   
        Matcher m = p.matcher(num);   
        boolean b = m.matches();   
        return b;   
    }
    
    
    /**
     * 判断手机号码是否正确
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile){
    	String REGEX_MOBILE = "(\\d{11})|(\\+\\d{3,})";
    	return Pattern.matches(REGEX_MOBILE, mobile);
    }
    
    /**
     * 验证身份证
     * @param idCard
     * @return
     */
//    public static boolean isIDCard(String idCard) {
//    	String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
//        return Pattern.matches(REGEX_ID_CARD, idCard);
//    }
    public static boolean isIDCard(String certificateno){
    	boolean flag = false;
    	if(certificateno.length()<18){
    		return false;
    	}
    	int[] intArr = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };  
    	int sum = 0;  
    	for (int i = 0; i < intArr.length; i++) {  
    		// 2.将这17位数字和系数相乘的结果相加。  
    		sum += Character.digit(certificateno.charAt(i), 10) * intArr[i];  
    		// System.out.println((cardNo.charAt(i) - '0') + " x " + intArr[i] + " = " + (cardNo.charAt(i) - '0') *  
    		// intArr[i]);  
    	}  
    	// 3.用加出来和除以11，看余数是多少？  
    	int mod = sum % 11;  
    	// 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2。  
    	int[] intArr2 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };  
    	int[] intArr3 = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };  
    	String matchDigit = "";  
    	for (int i = 0; i < intArr2.length; i++) {  
    		int j = intArr2[i];  
    		if (j == mod) {  
    			matchDigit = String.valueOf(intArr3[i]);  
    			if (intArr3[i] > 57) {  
    				matchDigit = String.valueOf((char) intArr3[i]);  
    			}  
    		}  
    	}  
    	
    	if (matchDigit.equals(certificateno.substring(certificateno.length() - 1))) { 
    		flag = true;
    		
    	} 
    	return flag;
	}
    
    /**
     * 验证日期格式
     * 格式正确为true,格式错误为false
     * @param str
     * @return
     */
    public boolean isValidDate(String str) {  
    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
    	try{  
    		Date date = (Date)formatter.parse(str);  
    		return str.equals(formatter.format(date));  
    	}catch(Exception e){  
    		return false;  
    	}  
    }  
    
    
//    public static void main(String[] args){
//    	ValidateFile v = new ValidateFile();
//    	System.out.println(v.isIDCard("330521199110101011"));
//    }
 
	
}
