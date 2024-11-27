package com.icycoke.httpclient;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icycoke.bean.User;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: JianJian
 * @date: 2023-11-19 12:21
 * @description:
 */

public class HttpClientDemo {

    @Test
    public void testGetDemo() {
        //1.创建http工具（理解为：浏览器  发送请求   解析响应）
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2.确定请求路径
        try {
            URIBuilder uriBuilder = new URIBuilder("http://localhost:8080/demo");
            uriBuilder.addParameter("param","jianjian");
            //3.创建http get请求对象
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            //4.创建响应对象
            CloseableHttpResponse response = httpClient.execute(httpGet);
            //5.获取结果    返回的类型是httpEntity类型   进行类型转换
            String result = EntityUtils.toString(response.getEntity(), "utf-8");

            System.out.println(result);
            //6.释放资源
            response.close();
            httpClient.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testPostDemo()
    {

        try {
            //1.创建Http工具（理解成 ： 浏览器） 发送请求 ， 解析响应
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //2.创建HttpPost请求对象
            HttpPost post = new HttpPost("http://localhost:8080/demo");
            //2.1. 创建请求参数
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("param" , "JIANJIAN"));
            //2.2创建HttpEntity接口的文本实现类对象， 放入参数并设置编码
            HttpEntity httpEntity = new UrlEncodedFormEntity(params , "utf-8");
            //2.3 放入到HttpPost对象中
            post.setEntity(httpEntity);
            //3. 创建响应对象
            CloseableHttpResponse response = httpClient.execute(post);
            //4.由于响应体是字符串， 因此把HttpEntity类型转换为字符串类型
            String result = EntityUtils.toString(response.getEntity());
            //5.输出结果
            System.out.println(result);
            //6.释放资源
            response.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    响应对象转换为json格式的字符串
 */
    @Test
    public void testObjectPostDemo()
    {
        try {
            //1.创建Http工具(理解为 浏览器)
            CloseableHttpClient httpClient = HttpClients.createDefault();

            //2.创建httpPost请求对象
            HttpPost httpPost = new HttpPost("http://localhost:8080/demo2");
            //3.创建参数
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id" , "1"));
            params.add(new BasicNameValuePair("name" , "张三丰"));

            HttpEntity httpEntity = new UrlEncodedFormEntity(params , "utf-8");

            httpPost.setEntity(httpEntity);

            CloseableHttpResponse response = httpClient.execute(httpPost);

            String content = EntityUtils.toString(response.getEntity());
            System.out.println(content);

            //jackson 把字符串转换为对象
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(content , User.class);

            System.out.println(user);

            //使用jackson 把 对象转换为Json
            String userJson = objectMapper.writeValueAsString(user);

            System.out.println(userJson);

            response.close();
            httpClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testListPostDemo()
    {

        try {
            //1.创建http工具
            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost("http://localhost:8080/demo3");

            CloseableHttpResponse response = httpClient.execute(httpPost);

            //3.创建参数
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id" , "1"));
            params.add(new BasicNameValuePair("name" , "张三丰"));

            HttpEntity httpEntity = new UrlEncodedFormEntity(params , "utf-8");


            String content = EntityUtils.toString(response.getEntity());
            System.out.println(content);

            //jackson
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class , User.class);

            List<User> list = objectMapper.readValue(content , javaType);

            System.out.println(list);

            response.close();
            httpClient.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testInputStream()
    {
        try {
            //1.创建Http工具
            CloseableHttpClient httpClient = HttpClients.createDefault();

            //2.创建HttpPost对象
            HttpPost httpPost = new HttpPost("http://localhost:8080/demo4");

            //3. 创建集合对象
            List<User> listParam = new ArrayList<User>();
            listParam.add(new User(1 , "张三丰"));
            listParam.add(new User(2 , "张无忌"));

            ObjectMapper objectMapper = new ObjectMapper();
            //4. 将集合对象转换为json类型的字符串
            String jsonParam = objectMapper.writeValueAsString(listParam);
            HttpEntity httpEntity = new StringEntity(jsonParam , ContentType.APPLICATION_JSON);

            httpPost.setEntity(httpEntity);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            String content = EntityUtils.toString(response.getEntity());
            System.out.println(content);

            response.close();
            httpClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
