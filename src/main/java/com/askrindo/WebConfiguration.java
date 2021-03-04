package com.askrindo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration  implements WebMvcConfigurer {

    @Value("${document-task}")
    String documentTask;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/document-task/**")
                .addResourceLocations("file:"+documentTask);
//        super.addResourceHandlers(registry);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
