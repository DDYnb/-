package com.szy.config;

import com.szy.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    //设置静态资源映射
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始静态资源映射");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }
    //扩展MVC框架的消息转换器, 将服务端响应的数据转换为字段类型是String类型的json对象
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置转换器，底层使用jackson将Java转换成json（引用common包下的转换器）
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将转换器对象追加到MVC的转换器集合中
        converters.add(0, messageConverter);
    }
}
