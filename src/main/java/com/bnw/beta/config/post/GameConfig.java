package com.bnw.beta.config.post;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class GameConfig implements WebMvcConfigurer{

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry){
            registry.addResourceHandler("/image/game/**")
                    .addResourceLocations("file:C:/Users/user/Desktop/BetaPro/beta/src/main/resources/static/image/guide/game/");
        }
    }

