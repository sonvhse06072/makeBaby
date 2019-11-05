package com.sonvh.makebabies.service;

import com.sonvh.makebabies.service.dto.GenerateDTO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;

@Service
public class MakeBabiesService {

    public String postImage1(MultipartFile file) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://www.makemebabies.com/image");
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+ Long.valueOf(String.valueOf(System.currentTimeMillis() / 1000)));
        file.transferTo(convFile);

        FileBody uploadFilePart = new FileBody(convFile);
        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("image", uploadFilePart);
        httpPost.setEntity(reqEntity);

        HttpResponse response = httpclient.execute(httpPost);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
        String body;
        String content = "";
        while ((body = bufferedReader.readLine()) != null) {
            content += body + "\n";
        }
        return content;
    }

    public String generate(GenerateDTO generateDTO) throws IOException, JSONException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://www.makemebabies.com/generate");
        JSONObject json = new JSONObject();

        json.put("img1", generateDTO.getImg1());
        json.put("img2", generateDTO.getImg2());
        json.put("frame", "img/frames/thumbnail/Frame_2_.png");
        json.put("gender", "either");
        json.put("ethnicity", "auto");

        StringEntity params = new StringEntity(json.toString());
        httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
        httpPost.setEntity(params);
        HttpResponse response = httpclient.execute(httpPost);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
        String body;
        String content = "";
        while ((body = bufferedReader.readLine()) != null) {
            content += body + "\n";
        }
        System.out.println(content);
        return content;
    }
}
