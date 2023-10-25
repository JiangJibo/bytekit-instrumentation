package com.shulie.jjb.bytekit.agent;

import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClientDemo {

    public static void main(String[] args) throws Exception {
        Test1();
    }

    private static void Test1() throws MalformedURLException, IOException, InterruptedException {
        URL url = new URL("http://localhost:8092/demo/httpclient/get");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        InputStream inputStrea = con.getInputStream();//字节流
        InputStreamReader inputStreamReader = new InputStreamReader(inputStrea);//转为字符流
        //通过bufferReader 读取
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String content = bufferedReader.readLine();

        int responseCode = con.getResponseCode();//获得状态码
        String headerField = con.getHeaderField("Server");//获取消息头 名字为 Server的头


        System.out.println(content);
        System.out.println(responseCode);
        System.out.println(headerField);
    }
}
