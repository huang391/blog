package com.hrl.blog.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class Webcig  extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterCeptor()).addPathPatterns("/admin/**").excludePathPatterns("/admin").excludePathPatterns("/admin/login");
        super.addInterceptors(registry);
    }
}
