package com.sonvh.makebabies.service;

import com.sonvh.makebabies.config.ApplicationProperties;
import com.sonvh.makebabies.domain.BabyHistory;
import com.sonvh.makebabies.repository.BabyHistoryRepository;
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
        this.saveResult(generateDTO.getImg1(), generateDTO.getImg2(), content, generateDTO.getBabyname(), generateDTO.getGender(), generateDTO.getEthnicity());
        return content;
    }

    public void saveResult(String img1, String img2, String res, String babyname, String gender, String ethnicity) throws JSONException {
        System.out.println("img1: " + img1);
        System.out.println("img2: " + img2);
        System.out.println("res: " + res);
//        JSONObject object = new JSONObject(res);
//        String imgRes = object.getString("result_url");
//        System.out.println("*********** img res**********" + imgRes);
//        BufferedImage image1 = null;
//        BufferedImage image2 = null;
//        BufferedImage imageRes = null;
//        try{
//            URL url1 = new URL(img1);
//            URL url2 = new URL(img2);
//            URL urlRes =new URL(imgRes);
//            // read the url
//            image1 = ImageIO.read(url1);
//            image2 = ImageIO.read(url2);
//            imageRes = ImageIO.read(urlRes);
//            // create timeStamp
//            Long timeStamp = Long.valueOf(String.valueOf(System.currentTimeMillis()));
//
////            ImageIO.write(image1, "jpg",new File("src/main/webapp/content/images/img1" + timeStamp +".jpg"));
////            ImageIO.write(image2, "jpg",new File("src/main/webapp/content/images/img2" + timeStamp +".jpg"));
////            ImageIO.write(imageRes, "jpg",new File("src/main/webapp/content/images/img3" + timeStamp +".jpg"));
//            System.out.println(rootLocation + "\\img1" + timeStamp +".jpg");
//
//            ImageIO.write(image1, "jpg",new File(rootLocation + "\\img1" + timeStamp +".jpg"));
//            ImageIO.write(image2, "jpg",new File( rootLocation + "\\img2" + timeStamp +".jpg"));
//            ImageIO.write(imageRes, "jpg",new File( rootLocation + "\\img3" + timeStamp +".jpg"));
//
//            BabyHistory babyHistory = new BabyHistory();
//            babyHistory.setImg1("img1" + timeStamp + ".jpg");
//            babyHistory.setImg2("img2" + timeStamp + ".jpg");
//            babyHistory.setImgRes("img3" + timeStamp + ".jpg");
//            babyHistory.setBabyname(babyname);
//            babyHistory.setGender(gender);
//            babyHistory.setEthnicity(ethnicity);
//            this.babyHistoryRepository.save(babyHistory);
//        }catch(IOException e){
//            e.printStackTrace();
//        }
    }

    public List<BabyHistory> getAll() {
        return babyHistoryRepository.findAll(new Sort(Sort.Direction.DESC, "createdDate"));
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
