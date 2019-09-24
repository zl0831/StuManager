package zl.app.utility;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by lotus on 2015/6/25.
 */
public class CString {

    public static String CutStringLength(String strSource, int length)
    {
        String Result ="";
        if (strSource != null)
        {

            if (strSource.length()>0)
            {
                if (strSource.length() > length)
                {
                    Result = strSource.substring(0,length)+"...";
                }
                else
                {
                    Result = strSource;
                }

            }
        }
        return Result;
    }

    
    public static  boolean  IsNullOrEmpty(Object data)
    {
    	
        boolean result=false;
        
        if(data==null || data.toString().length()<=0)
        {
           result = true;
        }
        
        return result;
        
    }


    /**
     * 瀵筓RL杩涜瑙ｇ爜
     * @param value
     * @param code
     * @return
     */
     public static String getURLDecodeString(String value )
     {
   	  	if(value==null)
   	  		return "";
   	  	String re="";
   	  	try
   	  	{
   	  	    value=new String(value.getBytes("iso-8859-1"),"utf-8");
		 
   	  		re = URLDecoder.decode(value,"utf-8");
   	  		re = re.trim();
   	  	}
   	  	catch(Exception e)
   	  	{
   	  		System.out.println("error at decode:"+e.getMessage());
   	  	}
   	  	return re;
     }
    
     /**
      * 瀵筓RL杩涜鍔犵爜
      * @param value
      * @param code
      * @return
      */
     public static String getURLEncodeString(String value)
     {
   	  	if(value==null)
   	  		return "";
   	  	String re="";
   	  	try
   	  	{
   	  		re = URLEncoder.encode(value, "utf-8");
   	  		re = re.trim();
   	  	}
   	  	catch(Exception e)
   	  	{
   	  		System.out.println("error at decode:"+e.getMessage());
   	  	}
   	  	return re;
     }
     /**
      * 根据key值查询list<map>中值
      * @param List<Map<String, Object>> LMap
      * @param String key
      * @return string
      */
    public static String ListMapFind(List<Map<String, Object>> LMap,String key,String val) {
    	String retStr = "";
    	for (Map<String,Object> map:LMap) {
    		for (String s:map.keySet()) {
    			if(key.equals(s)){
    				retStr = map.get(s).toString();
        			if(retStr.equals(val)){
        				System.out.println(retStr);
        				break;
        			}
    			}
    			
    			
    		}
    	}

		return retStr;
	}
    /**
     * 根据key值查询list<map>中值
     * @param List<Map<String, Object>> LMap
     * @param String key
     * @return key2
     */
	public static String ListMapFind(List<Map<String, Object>> LMap,String key,String val,String key2) {
	   	String retStr = "0";
	   	for (Map<String,Object> map:LMap) {
	   		if(map.get(key).equals(val)){
	   			retStr = map.get(key2).toString();
	   			break;
	   		}
	   	}
	   	return retStr;
	}
   
	
	/**
     * 	判断字符串变量是否为空，为空则返回默认值，否则返回原值
     * @param strName
     * @param defaultV
     * @return String
     */
	public static String checkNull(String strName,String defaultV){
		if(!IsNullOrEmpty(strName)){
			return String.valueOf(strName);
		} else {
			return defaultV;
		}
	}
	
	/**
     * 判断字符串变量是否为空，为空则返回默认值，否则返回原值
     * @param strName
     * @param defaultV
     * @return String
     */
	public static Double isNullNumber(Double num,Double defaultV){
		if(!IsNullOrEmpty(num)){
			return num;
		} else {
			return defaultV;
		}
	}
	
	/**
	 * 组织Where中in语句（带 “（）”）
	 * @param params
	 * @return
	 */
	public static String sqlIn(String params) {
		if(IsNullOrEmpty(params)) return "";
		return String.format("'%s'", params.replace(" ","").replace(",","','"));
	}
}
