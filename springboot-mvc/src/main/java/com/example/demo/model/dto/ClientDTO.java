package com.example.demo.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO: Data Transfer Object
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClientDTO {
	
	
	@NotNull(message="客戶編號不可以是空值")
	private Integer num; // 客戶編號
	

	@NotEmpty(message="姓名不可以是空值")
	private String name; 
	
	@Size(min=10 ,max=10 ,message="身分證字號為十位數")
	private String id; // 身分證
	
	@NotEmpty(message="車牌號碼不可以是空值")
	private String car; // 車牌號碼
	
	@NotEmpty(message="到期日不可以是空值")
	private String day; 
	
	@NotEmpty(message="保險公司不可以是空值")
	private String company; 
	
	@NotNull(message="保費不可以是空值")
	private Integer money; 
}