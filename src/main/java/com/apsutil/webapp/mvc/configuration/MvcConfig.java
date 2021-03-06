package com.apsutil.webapp.mvc.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
@Configuration
@EnableWebMvc
@ComponentScan({ "com.apsutil.*" })
public class MvcConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	registry.addViewController("/home").setViewName("welcome");
	//registry.addViewController("/").setViewName("welcome");
	registry.addViewController("/admin").setViewName("admin");
	registry.addViewController("/dba").setViewName("dba");
	registry.addViewController("/login").setViewName("login");
	registry.addViewController("/accessDenied").setViewName("accessDenied");
	}
	@Bean(name="viewResolver")
	public InternalResourceViewResolver viewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	resolver.setPrefix("/WEB-INF/jsp/");
	resolver.setSuffix(".jsp");
	 resolver.setViewClass(JstlView.class);
	return resolver;
	}
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
	}

	@Bean
	public LocaleResolver localeResolver() {

		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("en"));
		return cookieLocaleResolver;
	}
	@Bean
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages/messages", "classpath:messages/validation");
		// if true, the key of the message will be displayed if the key is not
		// found, instead of throwing a NoSuchMessageException
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		// # -1 : never reload, 0 always reload
		messageSource.setCacheSeconds(0);
		return messageSource;
	}
	/* @Bean
	    public List<HttpMessageConverter<?>> configureMessageConverters() {
	    	List<HttpMessageConverter<?>> httpMessageConverters=new ArrayList<HttpMessageConverter<?>>();
	        httpMessageConverters.add(new MappingJackson2HttpMessageConverter());
	        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
	        List<MediaType> supportedApplicationTypes = new ArrayList<MediaType>();
	        MediaType pdfApplication = new MediaType("application","pdf");
	        supportedApplicationTypes.add(pdfApplication);
	        supportedApplicationTypes.add(MediaType.TEXT_PLAIN);
	        byteArrayHttpMessageConverter.setSupportedMediaTypes(supportedApplicationTypes);
	        httpMessageConverters.add(byteArrayHttpMessageConverter);
	        //httpMessageConverters.add(new Jaxb2RootElementHttpMessageConverter());
	        
	        return httpMessageConverters;
	    }*/
}
