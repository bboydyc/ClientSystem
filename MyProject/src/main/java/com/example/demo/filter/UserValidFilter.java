package com.example.demo.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserValidFilter extends OncePerRequestFilter{
	//抽象方法實作 	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//透過封裝前端所有資訊的Request取出帶進來的Cookies
		Cookie[] cookies=request.getCookies();
		//判斷是否存在
		if(cookies!=null) {
			//判斷是否具有一個使用登入驗證發出的Cookie .cred逐一走訪
			boolean r=false;
			String credValue=null;
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals(".cred")) {
					r=true;
					System.out.println("前端有憑證...cred");
					credValue=cookie.getValue();
					//結束迴圈
					break;
				}
			}
			//判斷是否.cred Cookie
			if(r) {
				//是否合法(針對後端一個Session進行比對)
				//配合後端 Session管理的.cred進行雙重驗證
				HttpSession session=request.getSession(false); //只取配合前端瀏覽器session id cookie(沒有 不產生新的)
				if(session!=null) {
					//驗證合法的憑證
					Object serverCred=session.getAttribute(".cred");
					if(serverCred!=null) {
						//驗證內容
						if (((String)serverCred).equals(credValue)) {
							System.out.println("合法的Cookie...合法登入...");
							filterChain.doFilter(request, response);
						}else {
							System.out.println("偽造的Cookie...沒有登入...");
							response.sendError(401,"驗證不通過!!!");
						}
						
					}else {
						//前端是偽造的 Cookie
						System.out.println("偽造的Cookie...沒有登入...");
						response.sendError(401,"驗證不通過!!!");
					}
					
				}else {
					//偽造的Cookie
					System.out.println("偽造的Cookie...沒有登入....");
					response.sendError(401,"驗證不通過!!!");
				}
				
				
			}else {
				//來亂的 踢到login
				System.out.println("沒有憑證...");
				response.sendError(401,"驗證不通過!!!");
			}
		}else {
			System.out.println("沒有憑證...");
			response.sendError(401,"驗證不通過!!!");
		}
		
		
	}

}
