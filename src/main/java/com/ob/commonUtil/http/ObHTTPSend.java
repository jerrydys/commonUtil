package com.ob.commonUtil.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;



/**
 * 
 * @author dys
 * HTTP请求工具类
 *
 */
public class ObHTTPSend {
	
	private static final Logger logger = Logger.getLogger(ObHTTPSend.class);  
	
	/**
	 * post请求
	 * @param url
	 * @param m
	 * @return
	 */

	public static String post(String url,Map<String,String> m) { 
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);  
        // 创建参数队列    
        UrlEncodedFormEntity uefEntity;  
        try {          	
        	uefEntity = new UrlEncodedFormEntity(transformToNameValuePair(m),"UTF-8");
            httppost.setEntity(uefEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {  
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "UTF-8");
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
        	logger.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {  
        	logger.error(e.getMessage());
        } catch (IOException e) {  
        	logger.error(e.getMessage());
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
            	logger.error(e.getMessage());
            }  
        }  
        return null;
    } 
	
	/**
	 * get请求
	 * @param url
	 * @return
	 */
	public static String get(String url,Map<String,String> m) {  
        CloseableHttpClient httpclient = HttpClients.createDefault(); 
        try {  
            // 创建httpget.    
            HttpGet httpget = new HttpGet(url+transformToParamURI(m)); 
            //设置请求头,dys添加
            httpget.setHeader("content-type", "text/html;charset=utf-8");
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget); 
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    return EntityUtils.toString(entity);
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
        	logger.error(e.getMessage());
        } catch (ParseException e) {  
        	logger.error(e.getMessage());
        } catch (IOException e) {  
        	logger.error(e.getMessage());
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
            	logger.error(e.getMessage());
            }  
        }  
        return null;
    }  
	
	/*public static String upload(String url,File file,Map<String,String> m){
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(url);  
			FileBody bin = new FileBody(file);
			HttpEntity reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addPart("uploadPic", bin).build();
			httppost.setEntity(reqEntity); 
			CloseableHttpResponse response = httpclient.execute(httppost); 			
			HttpEntity entity = response.getEntity();  
			return EntityUtils.toString(entity, "UTF-8");
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	*/
	

	
	/**
	 * 将Map集合转换成 List<NameValuePair>
	 * @param m
	 * @return
	 */
	private static List<NameValuePair> transformToNameValuePair(Map<String,String> m){
		List<NameValuePair> qparams = new ArrayList<NameValuePair>(0);
		if(m!=null&&m.size()>0){
			for (Map.Entry<String, String> entry : m.entrySet()) { 
				qparams.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
			}
		}else{
			qparams.add(new BasicNameValuePair("_a","b"));//由于apache服务器在没有参数时报400错误解决方案（很可能是httpClient在没有参数时附带了一些其他的参数造成的）
		}
		return qparams;
	}
	
	/**
	 * 将请求参数转成 ?a=b&c=d 形式
	 * @param m
	 * @return
	 */
	private static String transformToParamURI(Map<String,String> m){
		String s = "";
		if(m!=null){
			for (Map.Entry<String, String> entry : m.entrySet()) {
				s += entry.getKey()+"="+entry.getValue()+"&";
			}
		}
		if(s.length()>2){
			s = "?" + s.substring(0,s.length()-1);
		}
		return s;
	}
    
    public static void main(String args[]){
    	
       System.out.println(get("",null));
    }

}
