package com.bnw.beta.config.post;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class GameConfig implements WebMvcConfigurer{

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry){

            String projectDir = System.getProperty("user.dir"); // 현재 프로젝트 디렉토리 가져오기
            Path uploadPath = Paths.get(projectDir, "src", "main", "resources", "static", "image", "guide", "game");

            registry.addResourceHandler("/image/game/**")
                    .addResourceLocations("file:"+uploadPath+"/");
        }
    }

