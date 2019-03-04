package com.companyname.springbootcrudrest.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/***
 * @Desciption: 拦截器
 * @Date: 13:53 2018/1/15
 * @return
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ParamInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/swagger-ui.html/**"); //匹配不过滤的路径。密码还要修改呢，所以这个路径不能拦截;

        }
}

