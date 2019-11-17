package com.sonvh.makebabies.service;

import com.sonvh.makebabies.config.ApplicationProperties;
import com.sonvh.makebabies.service.dto.GenerateDTO;
import com.sonvh.makebabies.service.dto.Share;
import com.squareup.okhttp.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class MakeBabiesService {
    private final Path rootLocation;
    public MakeBabiesService(ApplicationProperties properties) {
        this.rootLocation = Paths.get(properties.getBaseLocation());
    }

    public String postImage1(MultipartFile file) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://www.makemebabies.com/image");
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+ Long.valueOf(String.valueOf(System.currentTimeMillis() / 1000)) + file.getOriginalFilename());
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
        if (generateDTO.getGender()!=null) {
            json.put("gender", generateDTO.getGender());
        } else {
            json.put("gender", "either");
        }
        if (generateDTO.getEthnicity()!=null) {
            json.put("ethnicity", generateDTO.getEthnicity());
        } else {
            json.put("ethnicity", "auto");
        }
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

    public String shareForVote(List<Share> shares) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String temp = "";
        for (Share s: shares) {
            String tmp = s.getOption() + " <img src=\"" + s.getImgLink() + "\" />\n";
            temp += tmp;
        }
        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
        RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " +
            "name=\"page_id\"\r\n\r\n106397477490862\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " +
            "name=\"uid\"\r\n\r\n1669835436485576\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " +
            "name=\"question\"\r\n\r\n" + "Bé nào xinh hơn" + "\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " +
            "name=\"opts\"\r\n\r\n" + temp + "\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"vote\"\r\n\r\nVote\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
        Request request = new Request.Builder()
            .url("https://fans.vote/fb/create/save")
            .post(body)
            .addHeader("Cookie", "polls20678178440=u6t149to2n38j9mih15h67lre6")
            .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
