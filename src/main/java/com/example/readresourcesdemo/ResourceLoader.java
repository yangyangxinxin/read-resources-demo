package com.example.readresourcesdemo;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

/**
 * @author yangxin
 * @date 2019/7/30
 */
public class ResourceLoader {

    public Properties readResource(String path){
        return loadProperties(path);
    }

    private static Properties loadProperties(String path){
        ClassPathResource classPathResource = new ClassPathResource(path);
        Properties properties = new Properties();
        try {
            properties.load(classPathResource.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
        return properties;
    }

}