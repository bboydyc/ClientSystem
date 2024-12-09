package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.beans.Member;
import com.example.demo.model.HashProcess;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//會員資料處理
@Controller
@RequestMapping(path="/member")
@PropertySource("classpath:sql.properties")
public class MemberController {
	//Data Field Field Injection
	@Autowired
	private JdbcTemplate jdbcTemplate;
	//注入網站環境物件
	@Autowired
	private Environment evn;
	
	
	@Value("${member.sql.add}")
	private String memberSql;
	@Value("${member.sql.log}")
	private String memberlogSql;
	
	//會員註冊
	@RequestMapping(path="/add",method={RequestMethod.GET,RequestMethod.POST})
	public String register(HttpServletRequest request,Member member,Model model) {
		//進行判斷 採用傳送方法 GET or POST
		if(request.getMethod().equals("POST")) {
			//訊息狀態
			String message=null;
			//進行註冊作業
			System.out.println(member.getUserName());
			//TODO 進行會員註冊(敏感性資料 userName and Password)
			String salt=evn.getProperty("spring.application.salt");
			//呼叫自訂模組進行Hash static member
			var userHash=HashProcess.getSecureHash(member.getUserName(),salt.getBytes());
			var passwordHash=HashProcess.getSecureHash(member.getPassword(),salt.getBytes());
			
			System.out.println(userHash);
			
			
			//String sql="Insert Into Membership(UserName,Password,RealName,Email) values(?,?,?,?)";
			
			try {
				int row=jdbcTemplate.update(memberSql,userHash,passwordHash,member.getRealName(),member.getEmail());
				message=member.getUserName()+" 註冊成功!!";
			}catch(DataAccessException ex) {
				message="註冊失敗!!帳戶可能被使用了!!"+ex.getMessage();
			}
			//調用頁面(Thymeleaf engine)
			model.addAttribute("message", message);
			
		}
		
		return "register";
		
	}

	//登入驗證作業
	@RequestMapping(path="/login",method= {RequestMethod.GET,RequestMethod.POST})
	public String logon(Member member,HttpServletRequest request,HttpServletResponse response,Model model) {
		//判斷是否post back
		if(request.getMethod().equals("POST")) {
			String message=null;
			//登入驗證作業
			//1.取得注入進來的username and password
			String userName=member.getUserName();
			String password=member.getPassword();
			//進行Hash 演算 呼叫自訂模組
			var hashUserName=HashProcess.getSecureHash(userName,evn.getProperty("spring.application.salt").getBytes());
			var hashPassword=HashProcess.getSecureHash(password,evn.getProperty("spring.application.salt").getBytes());
				
			//資料存取
			//String sql="Select count(*) as counter From  Membership Where UserName=? and password=?";
			//注入JdbcTemplate
			List<Integer> result=jdbcTemplate.query(memberlogSql,
					//實作RowMapper Interface Lambda Expression Functional 
					(rs,number)->{
						//TODO 取出查詢結果
						int re=rs.getInt("counter");
						return re;
					}
					,hashUserName,hashPassword);
			//取出第一筆 
			
			if(result.get(0)==1) {
				//驗證通過發出憑證(Client Cookie)--借助Response
				Cookie cookie=new Cookie(".cred",hashUserName);
				//設定這一個Cookie path整個網站
				cookie.setPath("/");
				cookie.setHttpOnly(true); //防止前端採用JavaScript偽造Cookie
				//讓回應物件參考Cookie
				response.addCookie(cookie);
				//後端有產生一個相對的狀態 (證件)??? 採用Session 具有Timeout
				//透過 Request來問 沒有既定產生一個 如果有 使用既定的
				HttpSession session=request.getSession();
				//管理一個後端狀態 後端憑證Session針對一個前端瀏覽器
				session.setAttribute(".cred", hashUserName); //產生一個Session id送至前端特定Cookie
	
				//訊息
				message="驗證通過!!!";
				
				
				
			}else {
				//訊息
				message="驗證失敗!!";
			}
			//持續狀態到頁面
			model.addAttribute("message", message);
		}
		
		//調用登入頁面
		return "login"; 
	}
}
