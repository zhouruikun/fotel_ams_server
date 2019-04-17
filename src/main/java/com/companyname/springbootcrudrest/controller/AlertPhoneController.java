package com.companyname.springbootcrudrest.controller;

import com.companyname.springbootcrudrest.beans.AlertPhone;
import com.companyname.springbootcrudrest.beans.ResponseCommon;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

@RestController
@RequestMapping("/")
public class AlertPhoneController {


    @GetMapping("/phones")
    public ResponseCommon getPhones() {
        Properties prop = new Properties();
        ResponseCommon res1 = new ResponseCommon();
        ArrayList<AlertPhone> phones = new ArrayList<>();
        try {
            //读取属性文件a.properties
            InputStream in = new BufferedInputStream(new FileInputStream("phones.properties"));
            prop.load(in);     ///加载属性列表
            Iterator<String> it = prop.stringPropertyNames().iterator();

            while (it.hasNext()) {
                AlertPhone alertPhone = new AlertPhone();
                String key = it.next();
                alertPhone.setName(key);
                alertPhone.setPhone(prop.getProperty(key));
                phones.add(alertPhone);
            }
            res1.setData(phones);
        } catch (Exception e) {
            System.out.println("catch" + e);
        }
        res1.setMessage("success");
        res1.setCode(20000);
        return res1;
    }

    @PutMapping("/phones")
    public ResponseCommon setPhones(@RequestBody ArrayList<AlertPhone> phones) {
        Properties prop = new Properties();


        try {

            ///保存属性到phones.properties文件
            FileOutputStream oFile = new FileOutputStream("phones.properties", false);//true表示追加打开
            for (AlertPhone phone : phones
            ) {
                prop.setProperty(phone.getName(), phone.getPhone());
                prop.store(oFile, "The New properties file");

            }
            oFile.close();
            InputStream in = new BufferedInputStream(new FileInputStream("phones.properties"));
            prop.load(in);     ///加载属性列表

        } catch (Exception e) {
            System.out.println(e);
        }

        return getPhones();
    }
}
