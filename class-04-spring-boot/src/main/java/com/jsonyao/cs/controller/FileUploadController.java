package com.jsonyao.cs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fileUploadController")
public class FileUploadController {

    @RequestMapping("/fileUpload")
    public Map<String, Object> fileUpload(MultipartFile filename) throws IOException {
        System.out.println(filename.getOriginalFilename());
        System.out.println(filename.getName());

        String root_path = "D:/DevProgram/chg/";

        filename.transferTo(new File(root_path + filename.getOriginalFilename()));
        Map<String, Object> map = new HashMap<>();
        map.put("Msg", "OK");
        return map;
    }
}
