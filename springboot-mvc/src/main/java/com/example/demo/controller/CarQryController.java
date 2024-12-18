package com.example.demo.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.entity.Client;

import jakarta.servlet.http.HttpServletRequest;

//客戶資料處理控制器
//POJO
@Controller
@RequestMapping(path="/client")
@PropertySource("classpath:sql.properties")
public class CarQryController {
	
	//注入依賴物件DataSource(介面型別識別)
	@Autowired
	private DataSource datasource;
	
	
	@Value("${client.sql.carid}")
	private String carSql;
	
	
	//直接採用參數注入 Parameter Injection 注入封裝前端所有資訊Servlet api-HttpServuetRequest 
	@RequestMapping(path="/qry/bycarid",method= {RequestMethod.GET,RequestMethod.POST})
	public String customersQ(Model model,HttpServletRequest request,@RequestParam(required=false) String clientCar) {
		Client client=null;
		
		String message=null;
		
		Date queryTime = null; // 用於記錄查詢成功的時間
		if(request.getMethod().equals("POST")) {
		
			//取輸入的車牌號碼(Form Field)
			System.out.println("車牌號碼:"+clientCar);
			
			try {
				Connection connection=datasource.getConnection();
				//問看看是否連接上資料庫
				if(!connection.isClosed()) {
					
					PreparedStatement st=connection.prepareStatement(carSql);
					//設定參數
					st.setString(1,clientCar);
					//執行查詢--產生線上讀取結果集
					ResultSet rs=st.executeQuery();
					
					//線上讀取資料(保持連接開啟)
					if(rs.next()) {
						//封裝查詢結果到JavaBean中
						client=new Client();
						client.setClientName(rs.getString("clientName"));
						client.setClientId(rs.getString("clientId"));
						client.setClientCar(rs.getString("clientCar"));
						client.setClientDay(rs.getString("clientDay"));
						client.setClientCompany(rs.getString("clientCompany"));
						client.setClientMoney(rs.getInt("clientMoney"));
											
						message="";
						queryTime = new Date(); // 記錄查詢成功的時間
						
						
					}else {
						System.out.println("查無車籍資料");
						message=String.format("車牌號碼:%s 查無車籍資料!!",clientCar);
					
					}
					connection.close();
					}
				} catch (SQLException e) {
				
				System.out.println(e.getMessage());
			}
		}	
		
		model.addAttribute("message", message);
		model.addAttribute("result", client);
		model.addAttribute("queryTime", queryTime); //將查詢時間傳遞到前端
		return "client/carQry";
		}
	}