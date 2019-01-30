package com.kunlab.jpos.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @Title Util.java
 * @Description 通用方法工具类
 * @author gaozh@haodaibao.com
 * @date 2013.01.15
 *
 */
public final class Util {
	private static Logger logger = LogManager.getLogger(Util.class);
	
	/**
     * 判断字符串是否为空
     * 
     * @param value
     * @return true/false
     */
	public static boolean isEmpty(String value) {
		boolean result = false;
		
		value = Format.stringNull(value);
		if(value == null)
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * 判断字符串长度范围
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @param charset
	 * @return true/false
	 */
	public static boolean isLength(String value, int min, int max, String charset) {
        boolean result = false;
        
        try{
            int length = (charset == null ? value.getBytes() : value.getBytes(charset)).length;
            if(length >= min && length <= max)
            {
                result = true;
            }
        }
        catch(Exception e){
            logger.error(min + "~" + max + ":" + value.length(), e);
        }
        
        return result;
    }
	
	/**
	 * 判断字符串长度范围
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return true/false
	 */
	public static boolean isLength(String value, int min, int max) {
		return isLength(value, min, max, Constants.DEFAULT_CHARSET);
	}
	
	/**
	 * 正则判断字符串是否是数字
	 * 
	 * @param value
	 * @return true/false
	 */
	public static boolean isNumber(String value) {
	    boolean result = false;
	    
	    try{
	        result = value.matches("^\\d+$");
	    }
	    catch(Exception e){
	        logger.error(value, e);
	    }
	    
	    return result;
	}
	
	/**
	 * 正则判断字符串是否是金额
	 * 
	 * @param value
	 * @return true/false
	 */
	public static boolean isAmount(String value) {
        boolean result = false;
        
        try{
            result = value.matches("^(\\d+)(\\.\\d{1,2})?$");
        }
        catch(Exception e){
            logger.error(value, e);
        }
        
        return result;
    }
	
	/**
	 * 正则判断字符串是否是EMAIL
	 * 
	 * @param value
	 * @return true/false
	 */
	public static boolean isMail(String value) {
        boolean result = false;
        
        try{
            result = value.matches("(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)");
        }
        catch(Exception e){
            logger.error(value, e);
        }
        
        return result;
    }
    
	/**
	 * 正则判断字符串是否是英文字母
	 * 
	 * @param value
	 * @return true/false
	 */
    public static boolean isLetter(String value) {
        boolean result = false;
        
        try{
            result = value.matches("^[A-Za-z]+$");
        }
        catch(Exception e){
            logger.error(value, e);
        }
        
        return result;
    }
    
    /**
     * 正则判断字符串是否是中文字符
     * 
     * @param value
     * @return true/false
     */
    public static boolean isChar(String value) {
        boolean result = false;
        
        try{
            result = value.matches("^[\\x00-\\xff]+$");
        }
        catch(Exception e){
            logger.error(value, e);
        }
        
        return result;
    }
	
	/**
     * 解析unicode字符串
     * 
     * @param value
     * @return String
     */
	public static String decodeUnicode(String value) {
    	StringBuilder sb = new StringBuilder();
    	
    	try{
    		if(value.indexOf("%u") != -1)
    		{
        		int n = 0;
            	while((n = value.indexOf("%u")) != -1)
            	{
            		sb.append(value.substring(0, n));
            		String str = value.substring(n + 2, n + 6);
            		char c = (char)Integer.parseInt(str, 16);
                	sb.append(new Character(c).toString());
                	value = value.substring(n + 6);
            	}
            	sb.append(value);
    		}
    		else
    		{
    			sb = new StringBuilder(value);
    		}
    	}
    	catch(Exception e){
    		logger.error(value, e);
			sb = new StringBuilder(value);
		}

    	return Format.stringBlank(sb.toString());
    }
	
	/**
     * 对象拷贝
     * 针对set/get属性
     * 
     * @param source 源对象
     * @param target 目标对象
     * @return Object
     */
	public static Object copyObject(Object source, Object target) {
		try{
			Class<?> sourceClass = source.getClass();
			Class<?> targetClass = target.getClass();
			
			Field[] targetFields = targetClass.getDeclaredFields();
			for(int i = 0; i < targetFields.length; i ++)
			{
				String name = targetFields[i].getName();
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				
				try{
					Method getTargetMethod = targetClass.getMethod("get" + name, new Class[0]);
					Object targetValue = getTargetMethod.invoke(target, new Object[0]);
					
					Method getSourceMethod = sourceClass.getMethod("get" + name, new Class[0]);
					Object sourceValue = getSourceMethod.invoke(source, new Object[0]);
					
					if(targetValue != null && !targetValue.equals(sourceValue))
					{
						Method setMethod = sourceClass.getMethod("set" + name, new Class[]{targetValue.getClass()});
						setMethod.invoke(source, targetValue);
					}
				}
				catch(Exception e){
					logger.debug(name, e);
				}
			}
		}
		catch(Exception e){
			logger.error(source, e);
		}
		
		return source;
	}
	
    /**
     * 按字节长度截取字符串
     * 
     * @param value
     * @param charset
     * @param length
     * @param left
     * @return String
     */
    public static String truncate(String value, String charset, int length, boolean left) {
        try{
            value = Format.stringBlank(value);
            byte[] bytes = value.getBytes(charset);
            if(bytes.length > length)
            {
                byte[] temp = new byte[length];
                if(left)
                {
                    System.arraycopy(bytes, bytes.length - length, temp, 0, length);
                }
                else
                {
                    System.arraycopy(bytes, 0, temp, 0, length);
                }
                value = new String(temp, charset);
            }
        }
        catch(Exception e){
            logger.error(value + "|" + charset + "|" + length + "|" + left, e);
        }
        
        return value;
    }
    
    /**
     * 按字节长度截取字符串
     * 
     * @param value
     * @param length
     * @param left
     * @return String
     */
    public static String truncate(String value, int length, boolean left) {
    	return truncate(value, Constants.DEFAULT_CHARSET, length, left);
    }
    
    /**
     * 按字节长度对字符串补位
     * 
     * @param value
     * @param charset
     * @param length
     * @param c
     * @param left
     * @return String
     */
    public static String pad(String value, String charset, int length, char c, boolean left) {
        String result = value;
        
        try{
            value = Format.stringBlank(value);
            result = truncate(value, charset, length, left);
            byte[] bytes = result.getBytes(charset);
            if(bytes.length < length)
            {
                StringBuffer sb = new StringBuffer(length);
                int num = length - bytes.length;
                while(num-- > 0)
                {
                    sb.append(c);
                }
                if(left)
                {
                    sb.append(result);
                }
                else
                {
                    sb.insert(0, result);
                }
                result = sb.toString();
             }
        }
        catch(Exception e){
            logger.error(value + "|" + charset + "|" + length + "|" + c + "|" + left, e);
        }
        
        return result;
    }
    
    /**
     * 按字节长度对字符串补位
     * 
     * @param value
     * @param length
     * @param c
     * @param left
     * @return String
     */
    public static String pad(String value, int length, char c, boolean left) {
    	return pad(value, Constants.DEFAULT_CHARSET, length, c, left);
    }
    
    /**
     * 读取二维数组元素值
     * 每行第一列为key
     * 每行第二列为value
     * 
     * @param args
     * @param key
     * @param defaultValue
     * @return String
     */
    public static String getArrayElement(String[][] args, String key, String defaultValue) {
        String result = null;
        
        key = Format.stringBlank(key);
        for(int i = 0; i < args.length; i ++)
        {
            if(key.equals(args[i][0]))
            {
                result = args[i][1];
                break;
            }
        }
        result = isEmpty(result) ? defaultValue : result;
        
        return result;
    }
    
    /**
     * 判断一维数组元素
     * 
     * @param args
     * @param value
     * @return true/false
     */
    public static boolean isArrayElement(String[] args, String value) {
        boolean result = false;
        
        value = Format.stringBlank(value);
        for(int i = 0; i < args.length; i ++)
        {
            if(value.equals(args[i]))
            {
                result = true;
                break;
            }
        }
        
        return result;
    }
    
    /**
     * 判断二维数组元素
     * 
     * @param args
     * @param position
     * @param value
     * @return true/false
     */
    public static boolean isArrayElement(String[][] args, int position, String value) {
        boolean result = false;
        
        value = Format.stringBlank(value);
        for(int i = 0; i < args.length; i ++)
        {
            if(value.equals(args[i][position]))
            {
                result = true;
                break;
            }
        }
        
        return result;
    }
    
    /**
     * 生成随机码字符串
     * 
     * @param n 随机码位数
     * @return String
     */
    public static String getRandomNumber(int n) {
        String result = "";
        
        try{
            Random random = new Random();
            for(int i = 0; i < n; i ++)
            {
                result += random.nextInt(10);
            }
        }
        catch(Exception e){
            logger.error(n, e);
            result = "";
        }
        
        return result;
    }
    
    /**
     * 校验卡号校验位
     * 
     * @param card
     * @return true/false
     */
    public static boolean isCardNo(String card) {
        boolean result = false;
        
        result = isValidCRC(card);
        if(!result)
        {
            logger.info("card crc error : {}", card);
        }
        
        return result;
    }
    private static boolean isValidCRC (String p) {
        int i, crc;

        int odd = p.length() % 2;
        
        for (i=crc=0; i<p.length(); i++) {
            char c = p.charAt(i);
            if (!Character.isDigit (c))
                return false;
            c = (char) (c - '0');
            if (i % 2 == odd)
                crc+=(c*2) >= 10 ? ((c*2)-9) : (c*2);        
            else
                crc+=c;
        }
        return crc % 10 == 0;
    }
    
    /**
     * 主键生成
     * 25位：日期(8位:yyyyMMdd)+数据版本(1位:0)+系统编码(2位)+业务编码(2位)+分库位(1位:用户ID)+分表位(3位:000)+序列(8位)
     * 
     * @param code 系统编码(2位)+业务编码(2位)
     * @param shardingCode 分库位（用户ID）
     * @param sequence 序列(8位)
     * @return String
     */
    public static String id(String code, String shardingCode, String sequence) {
    	StringBuffer sb = new StringBuffer(25);
    	
    	sb.append(Format.date2String(new Date(), "yyyyMMdd"));
    	sb.append("0");
    	sb.append(pad(code, 4, '0', true));
    	sb.append(pad(shardingCode, 1, '0', true));
    	sb.append("000");
    	sb.append(pad(sequence, 8, '0', true));
    	
    	if(sb.length() != 25)
    	{
    		throw new IllegalArgumentException(sb.toString());
    	}
    	
        return sb.toString();
    }
    
    /**
     * 主键生成
     * 25位：日期(8位:yyyyMMdd)+数据版本(1位:0)+系统编码(2位)+业务编码(2位)+分库位(1位:用户ID)+分表位(3位:000)+序列(8位)
     * 
     * @param version	数据版本(1位/默认:0)
     * @param appCode	系统编码(2位)
     * @param bizCode	业务编码(2位)
     * @param shardingCode	分库位（用户ID）
     * @param extCode	分表位(3位/默认:000)
     * @param sequence	序列(8位)
     * @return	String
     */
    public static String id(String version, String sysCode, String bizCode, String shardingCode, String extCode, String sequence) {
    	StringBuffer sb = new StringBuffer(25);
    	
    	sb.append(Format.date2String(new Date(), "yyyyMMdd"));
    	sb.append(pad(version, 1, '0', true));
    	sb.append(pad(sysCode, 2, '0', true));
    	sb.append(pad(bizCode, 2, '0', true));
    	sb.append(pad(shardingCode, 1, '0', true));
    	sb.append(pad(extCode, 3, '0', true));
    	sb.append(pad(sequence, 8, '0', true));
    	
    	if(sb.length() != 25)
    	{
    		throw new IllegalArgumentException(sb.toString());
    	}
    	
        return sb.toString();
    }
    
    /**
     * 校验IP格式
     * @param ipAddress
     * @return
     */
    public static boolean checkIP(String ipAddress) {
        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."  
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";  
  
        if(isEmpty(ipAddress)) return false;
        
        Pattern pattern = Pattern.compile(ip);  
        Matcher matcher = pattern.matcher(ipAddress);  
        return matcher.matches();
    }
    
    /**
     * 生成UUID
     * @return UUID
     */
    public static String randomUUID() {
    	String uuid = UUID.randomUUID().toString();
    	uuid = uuid.replaceAll("-", "");
    	
    	return uuid.toUpperCase();
    }
}