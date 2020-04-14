package com.gyj.yingx.controller;

import com.gyj.yingx.util.ImageCodeUtil;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("code")
public class VerificationCode {

    /**
     * 验证码
     * */
    @RequestMapping("jsCode")
    public void createCode(String code,HttpServletResponse response){
        BufferedImage image = ImageCodeUtil.createImage(code);
        System.out.println("页面验证码"+code);
        try {
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
