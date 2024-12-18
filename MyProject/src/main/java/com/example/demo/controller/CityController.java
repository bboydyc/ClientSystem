package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.beans.Customers;

import jakarta.servlet.http.HttpServletRequest;
@Controller
@RequestMapping(path="/city") //快速路徑   http://localhost:8080/city/qry/city
@PropertySource("classpath:sql.properties")
public class CityController {
	
		@Autowired
		private Environment env;
		
		//注入JdbcTemplate
		@Autowired
		private JdbcTemplate jdbcTemplate;
		
		@Value("${client.sql.qry}")
		private String clientSql;
		
		//參數注入Model 持續狀態到View Page去
		@RequestMapping(path="/qry/city",method= {RequestMethod.GET,RequestMethod.POST})
		public String customersQryBCountry(HttpServletRequest request,Model model,@RequestParam(name="city",required=false)String city) {
			//透過環境物件 取出application.properties屬性內容
			String citys;
			try {
				//注入清單並且改變編碼
				citys = new String(env.getProperty("spring.data.city").getBytes("ISO-8859-1"), "UTF-8");
				String[] items=citys.split(",");
	
				List<Customers> result=null;
				//判斷是否post back (進行客戶資料查詢 )
				if(request.getMethod().equals("POST")) {				
					//查詢客戶資料				
					result=jdbcTemplate.query(clientSql,
							//使用Lambda functional 實作介面RowMapper 查詢結果逐筆傳入 客製化處理
							(r,n)->
							{
								Customers clients=new Customers();
								//相對記錄封裝成JavaBean
								clients.setClientId(r.getString("clientID"));
								clients.setClientName(r.getString("clientName"));
								clients.setPhone(r.getString("phone"));
								clients.setEmail(r.getString("email"));
								clients.setAddress(r.getString("address"));		
								return clients;	
							}
							, city);
					System.out.println("記錄數:"+result.size());	
				}
				//調用網頁 持續這一個國家別清單到頁面去 進行SSR(Server side rendering) 渲染成下拉式功能表
				model.addAttribute("city", items);
				model.addAttribute("result", result);			
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "cityqry"; 
		}
}
