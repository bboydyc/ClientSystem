package com.example.demo.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

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

import com.example.demo.beans.Insurance;
import com.example.demo.beans.Customers;
import jakarta.servlet.http.HttpServletRequest;

//客戶資料處理控制器
//POJO
@Controller
@RequestMapping(path="/insurance")
@PropertySource("classpath:sql.properties")
public class InsuranceController {
	
	@Autowired
	private Environment env;
	//注入依賴物件DataSource(介面型別識別)
	@Autowired
	private DataSource datasource;
	//注入JdbcTemplate
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Value("${insurance.sql.qry}")
	private String insSql;
	
	
	//直接採用參數注入 Parameter Injection 注入封裝前端所有資訊Servlet api-HttpServuetRequest 
	@RequestMapping(path="/qry/byid",method= {RequestMethod.GET,RequestMethod.POST})
	public String customersQ(Model model,HttpServletRequest request,@RequestParam(name="insuranceId",required=false) String insuranceId) {
		Insurance insurance=null;
		
		String message=null;
		
		if(request.getMethod().equals("POST")) {
		
			//取輸入的保單號碼(Form Field)
			System.out.println("保單號碼:"+insuranceId);
			//進行查詢作業
			//跟DataSource要一個連接物件(具有開啟連接上資料庫)
			//客戶查詢結果JavaBean
			
			try {
				Connection connection=datasource.getConnection();
				//問看看是否連接上資料庫
				if(!connection.isClosed()) {
					//透過連接物件拖出來一個令命物件
					PreparedStatement st=connection.prepareStatement(insSql);
					//設定參數
					st.setString(1,insuranceId);
					//執行查詢--產生線上讀取結果集
					ResultSet rs=st.executeQuery();
					
					//線上讀取資料(保持連接開啟)
					if(rs.next()) {
						//封裝查詢結果 To JavaBean
						insurance=new Insurance();
						insurance.setCompanyName(rs.getString("companyName"));
						insurance.setInsuranceId(rs.getString("insuranceId"));
						insurance.setInsuranceName(rs.getString("insuranceName"));
						insurance.setInsurancePremium(rs.getString("insurancePremium"));
						insurance.setSumInsured(rs.getString("sumInsured"));
						insurance.setInsurancePayment(rs.getString("insurancePayment"));
						insurance.setBenefitPeriod(rs.getString("benefitPeriod"));
						
						
						message="";
					}else {
						System.out.println("查無保單資料");
						message=String.format("保單號碼:%s 查無該保單資料!!",insuranceId);
					
					}
					
					//關閉連接
					connection.close();
					}
				} catch (SQLException e) {
				
				System.out.println(e.getMessage());
			}
		}	
		//持續查詢結果狀態到View Page去
		model.addAttribute("message", message);
		model.addAttribute("result", insurance);
		//調用頁面
		return "insuranceqrybyid";
		}
}