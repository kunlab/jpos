package com.kunlab.jpos.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Format {
	private static Logger logger = LogManager.getLogger(Format.class);
	
	/**
	 * 字符串trim
	 * @param value
	 * @return
	 */
	public static String stringTrim(String value) {
		if(value != null)
		{
			value = value.trim();
		}
		
		return value;
	}
	
	/**
     * null "" 格式化为null
     * 
     * @param value
     * @return null
     */
	public static String stringNull(String value) {
		if(value != null)
		{
			value = value.trim();
			if(value.equals(""))
			{
				value = null;
			}
		}
		
		return value;
	}
	
	/**
     * null "" 格式化为""
     * 
     * @param value
     * @return ""
     */
	public static String stringBlank(String value) {
		value = stringNull(value);
		
		if(value == null)
		{
			value = "";
		}
		
		return value;
	}
	public static String stringBlank(Object value) {
		String result = "";
		result = stringNull((String)value);
		
		if(result == null)
		{
			result = "";
		}
		
		return result;
	}
	
	/**
     * 日期字符串格式化为日期类型
     * e.g. yyyy-MM-dd HH:mm:ss
     * 
     * @param value
     * @param format
     * @return Date
     */
	public static Date string2Date(String value, String format) {
		Date result = null;
		
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(stringNull(format));
			result = sdf.parse(stringNull(value));
		}
		catch(Exception e){
			logger.error(value + " | " + format, e);
		}
		
		return result;
	}
	
	/**
     * 日期类型格式化为字符串
     * e.g. yyyy-MM-dd HH:mm:ss
     * 
     * @param value
     * @param format
     * @return String
     */
	public static String date2String(Date value, String format) {
		String result = null;
		
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(stringNull(format));
			result = sdf.format(value);
		}
		catch(Exception e){
			logger.error(value + " | " + format, e);
		}
		
		return result;
	}
	
	/**
     * 数字类型格式化为字符串
     * e.g. ########0.00
     * 
     * @param value
     * @param format
     * @return String
     */
	public static String number2String(BigDecimal value, String format) {
		String result = null;
		
		try{
			DecimalFormat df = new DecimalFormat(stringNull(format));
			result = df.format(value);
		}
		catch(Exception e){
			logger.error(value + " | " + format, e);
		}
		
		return result;
	}
	
	/**
     * 数组格式化为字符串
     * 
     * @param object
     * @return String
     */
	public static String array2String(Object[] object) {
		StringBuilder result = new StringBuilder();
		
		result.append("{");
		
		int n = 0;
		for(Object obj : object)
		{
			result.append(obj.toString());
			if(++ n < object.length)
			{
				result.append(", ");
			}
		}
		
		result.append("}");
		
		return result.toString();
	}
	
	/**
     * 异常格式化为字符串
     * 
     * @param t
     * @return String
     */
	public static String exception2String(Throwable t) {
		StringBuilder result = new StringBuilder();
		
		result.append(t.toString());
		result.append("\n");
		
		StackTraceElement[] stes = t.getStackTrace();
		for(StackTraceElement ste : stes)
		{
			result.append(ste.toString());
			result.append("\n");
		}
		
		return result.toString();
	}
	
	/**
	 * 字符串占位符转换
	 * 
	 * @param pattern
	 * @param arguments
	 * @return
	 */
	public static String format(String pattern, Object... arguments) {
    	return MessageFormat.format(pattern, arguments);
    }
	
	 /**
     * 把金额从元转换成分
     * eg:12.34 to 1234
     * @param amountStr
     * @return
     */
    public static String amountYuanTOamountFen(String amountStr) {
        BigDecimal bigDecimalYuan = new BigDecimal(amountStr);
        BigDecimal bigDecimalFen = bigDecimalYuan.multiply(new BigDecimal(100));
        return formatBigDecimalToStr(bigDecimalFen);
    }
    
    /**
     * 把金额从分转换成元
     * eg:1234 to 12.34
     * @param amountStr
     * @return
     */
    public static String amountFenTOamountYuanStr(String amountStr) {
        if(amountStr == null || "".equals(amountStr)) {
            return "0";
        }
        BigDecimal bigDecimalFen = new BigDecimal(amountStr);
        BigDecimal bigDecimalYuan  = bigDecimalFen.divide(new BigDecimal(100));
        DecimalFormat df = new DecimalFormat("0.00"); // 保留几位小数
        return df.format(bigDecimalYuan.doubleValue());
    }
    
    /**
     * 格式化金额
     * @param amount
     * @return
     */
    public static String formatBigDecimalToStr(BigDecimal amount) {
        DecimalFormat format = (DecimalFormat)NumberFormat.getPercentInstance();
        format.applyPattern("##########0");
        if(amount!= null)
            return format.format(amount);
        else
            return "";
    }
    
    /**
     * 格式化HTTP请求
     * @param request
     * @return
     */
    public static Map<String, Object> formatRequest(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try{
			Map<?, ?> map = request.getParameterMap();
			Enumeration<?> names = request.getParameterNames();
			while(names.hasMoreElements())
			{
				StringBuilder sb = new StringBuilder();
				Object key = names.nextElement();
				Object[] values = (Object[])map.get(key);
				
				String _key = (String)key;
				Object _value = values.length > 1 ? values : values[0];
				
				sb.append(_key);
				sb.append(" = |");
				for(Object value : values)
				{
					sb.append((String)value);
					sb.append("|");
				}
				logger.info(sb.toString());
				
				result.put(_key, _value);
			}
		}
		catch(Exception e){
			logger.error(request, e);
		}
		
		return result;
	}
}