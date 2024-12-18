package com.example.demo.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
	
	
	private Integer clientNum; //客戶編號
	
	private String clientName;	//客戶姓名
	
	private String clientId;	//客戶身分證字號
	
	private String clientCar;  //車牌號碼
	
	private String clientDay;	//強制險到日
	
	private String clientCompany;	//保險公司
	
	private Integer clientMoney;	//保費
}
