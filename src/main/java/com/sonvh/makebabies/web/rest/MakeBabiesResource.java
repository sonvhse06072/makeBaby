package com.sonvh.makebabies.web.rest;

import com.sonvh.makebabies.domain.BabyHistory;
import com.sonvh.makebabies.service.MakeBabiesService;
import com.sonvh.makebabies.service.dto.BabyHistoryDTO;
import com.sonvh.makebabies.service.dto.GenerateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MakeBabiesResource {
    private final Logger log = LoggerFactory.getLogger(MakeBabiesResource.class);

    @Autowired
    private MakeBabiesService makeBabiesService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        log.debug("REST request to upload file : {}", file.getOriginalFilename());
        String res = makeBabiesService.postImage1(file);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<String> getBabies(@RequestBody GenerateDTO generateDTO) throws IOException, JSONException {
        String res = makeBabiesService.generate(generateDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<BabyHistoryDTO>> getAll() {
        List<BabyHistory> babyHistories = makeBabiesService.getAll();
        List<BabyHistoryDTO> dtos = babyHistories.stream().map(BabyHistoryDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
