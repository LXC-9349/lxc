package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.interceptor.SecurityInterceptor;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = { "com.*" })
@ServletComponentScan
@EnableSwagger2
@EnableScheduling
public class Application extends SpringBootServletInitializer implements WebMvcConfigurer {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Value("${server.port}")
	private String port;
	@Value("${spring.datasource.url}")
	private String dataSource;
	@Value("${spring.redis.host}")
	private String redishost;
	@Value("${spring.redis.port}")
	private String redisport;

	@Bean
	public SecurityInterceptor getSecurityInterceptor() {
		return new SecurityInterceptor();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 设置允许跨域的路径
		registry.addMapping("/**")
				// 设置允许跨域请求的域名
				.allowedOrigins("*")
				// 是否允许证书 不再默认开启
				.allowCredentials(true)
				// 设置允许的方法
				.allowedMethods("*")
				// 跨域允许时间
				.maxAge(3600);
	}

	/**
	 * 自定义拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getSecurityInterceptor()).addPathPatterns("/api/**")
				.excludePathPatterns("/api/user/login/**").excludePathPatterns("/api/user/info/**")
				.excludePathPatterns("/api/user/logout/**").excludePathPatterns("/api-docs/**")
				.excludePathPatterns("/views/**").excludePathPatterns("/swagger/**").excludePathPatterns("/static/**")
				.excludePathPatterns("/templates/**");
	}

	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {

		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

		FastJsonConfig fastJsonConfig = new FastJsonConfig();

		fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat);

		fastConverter.setFastJsonConfig(fastJsonConfig);

		HttpMessageConverter<?> converter = fastConverter;

		return new HttpMessageConverters(converter);

	}

	@Bean
	public String showLog() {
		log.info("项目启动端口：" + port);
		log.info("数据库：" + dataSource);
		log.info("Redis：" + redishost + ":" + redisport);
		log.info("JVM监控端口：1091");
		return "";
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
