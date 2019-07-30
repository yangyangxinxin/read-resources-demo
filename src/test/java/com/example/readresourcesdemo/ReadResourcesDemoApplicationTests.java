package com.example.readresourcesdemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ReadResourcesDemoApplicationTests {

    // ------------使用Resource的实现类----------------

    // 加载classpath下的资源
    @Test
    public void testClassPathResources() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("application.properties");
        InputStream inputStream = classPathResource.getInputStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        properties.forEach((k, v) -> {
            log.info("k:{} ,v:{}", k, v);
        });
    }

    // 通过文件系统中的资源
    @Test
    public void testFileSystemResources() throws IOException {
        FileSystemResource fileSystemResource = new FileSystemResource("Y:\\study\\code\\read-resources-demo\\src\\main\\resources\\application.properties");
        InputStream inputStream = fileSystemResource.getInputStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        properties.forEach((k, v) -> {
            log.info("k:{} ,v:{}", k, v);
        });
    }

    // -----------如果你业务中需要使用延迟加载，我们可以使用类ResourceLoader：-------

    @Test
    public void testResourceLoader() throws IOException {
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        String location = "application.properties";
        org.springframework.core.io.Resource resource = defaultResourceLoader.getResource(location);
        InputStream inputStream = resource.getInputStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        properties.forEach((k, v) -> {
            log.info("k:{} ,v:{}", k, v);
        });
    }

    @Resource
    private ApplicationContext webApplicationContext;

    @Test
    public void testContextLoad() throws IOException {
        org.springframework.core.io.Resource resource = webApplicationContext.getResource("application.properties");
        InputStream inputStream = resource.getInputStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        properties.forEach((k, v) -> {
            log.info("k:{} ,v:{}", k, v);
        });
    }

    // 使用value注解

    @Value("classpath:application.properties")
    private org.springframework.core.io.Resource valueResource;

    @Test
    public void testValueAnn() throws IOException {
        InputStream inputStream = valueResource.getInputStream();
        Properties properties = new Properties();
        properties.load(inputStream);
        properties.forEach((k, v) -> {
            log.info("k:{} ,v:{}", k, v);
        });
    }


    // 使用EL表达式

    @Value("#{new com.example.readresourcesdemo.ResourceLoader().readResource('application.properties')}")
    private Properties elProperties;

    @Test
    public void testElProperties(){
        elProperties.forEach((k, v) -> {
            log.info("k:{} ,v:{}", k, v);
        });
    }

}
