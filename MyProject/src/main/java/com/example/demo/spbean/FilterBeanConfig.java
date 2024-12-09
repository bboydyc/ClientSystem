package com.example.demo.spbean;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import com.example.demo.filter.UserValidFilter;

@Configuration
public class FilterBeanConfig {
	//注入
	@Autowired
	private Environment evn;
	
	@Bean
	@Scope("singleton")
	public UserValidFilter uerValidFilterBean() {
		System.out.println("User Valid Filter Bean佈署...");
		//包裹自訂Filter Instnace個體物件
		UserValidFilter filter=new UserValidFilter();
		return filter;
	}
	
	//Ioc 注入控制反轉
	@Bean
	@Scope("singleton") 
	public FilterRegistrationBean<UserValidFilter> registerUserValidFilter(UserValidFilter filter){
		System.out.println("User Valid Filter Cofing Bean佈署...");
		//取出安全目錄
		String[] paths=evn.getProperty("spring.application.secpath").split(";");	
		//建構Filter包裹註冊(佈署)物件
		FilterRegistrationBean<UserValidFilter> bean=new FilterRegistrationBean<UserValidFilter>();	
		bean.setFilter(filter); //屬性注入 Property Injection
		//設定攔截URL Patterns
		ArrayList<String> urls=new ArrayList<>();
		for(String path:paths) {
			urls.add(path); //可以多個列舉出來的安全目錄端點...
		}
		bean.setUrlPatterns(urls);
		//攔截器屬於Middleware 中介層元件
		//具有順序性(Pipe Line)
		bean.setOrder(1); 
		//賦於別名
		bean.setName("uservalid");
		return bean;
		
		
		
		
	}

}