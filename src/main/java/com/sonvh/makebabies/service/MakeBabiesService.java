package com.sonvh.makebabies.service;

import com.sonvh.makebabies.config.ApplicationProperties;
import com.sonvh.makebabies.domain.BabyHistory;
import com.sonvh.makebabies.repository.BabyHistoryRepository;
import com.sonvh.makebabies.service.dto.BabyHistoryDTO;
import com.sonvh.makebabies.service.dto.GenerateDTO;
import com.sonvh.makebabies.web.rest.errors.StorageFileNotFoundException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class MakeBabiesService {
    private final Path rootLocation;
    public MakeBabiesService(ApplicationProperties properties) {
        this.rootLocation = Paths.get(properties.getBaseLocation());
    }

    @Autowired
    private BabyHistoryRepository babyHistoryRepository;

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

    public BabyHistory saveHistory(BabyHistoryDTO babyHistoryDTO) {
        BabyHistory babyHistory = new BabyHistory();
        babyHistory.setBabyname(babyHistoryDTO.getBabyname());
        babyHistory.setEthnicity(babyHistoryDTO.getEthnicity());
        babyHistory.setGender(babyHistoryDTO.getGender());
        babyHistory.setImgMom(babyHistoryDTO.getImgMom());
        babyHistory.setDadAndSons(babyHistoryDTO.getDadAndSons());
        return babyHistoryRepository.save(babyHistory);
    }

    public List<BabyHistory> getAll() {
        return babyHistoryRepository.findAll(new Sort(Sort.Direction.DESC, "createdDate"));
    }

    public void delete(Long id) {
        this.babyHistoryRepository.findById(id).ifPresent(
            history -> babyHistoryRepository.delete(history)
        );
    }

    public Resource loadAsResource(String filename) {
        System.out.println("**************" + filename);
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                    "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }
}
