package com.example.demo.controller;


import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



import com.example.demo.model.dto.ClientDTO;

import com.example.demo.service.ClientService;


import jakarta.validation.Valid;

/**
 * Method URI                      	    功能
 * --------------------------------------------------------------------
 * GET    /client                   	查詢所有客戶(多筆)
 * GET    /client/{clientNum}       	查詢指定客戶(單筆)
 * POST   /client                 		新曾客戶資料
 * POST   /client/update/{clientNum} 	修改客戶資料
 * GET    /client/delete/{clientNum} 	刪除客戶資料
 * --------------------------------------------------------------------
 * */

@Controller
@RequestMapping("/client")
public class ClientController {
	
	
	@Autowired
	private ClientService clientService;
	
	
	
	@GetMapping // 查詢所有客戶(多筆) / 首頁
	public String getClients(Model model, ClientDTO clientDTO) {
		List<ClientDTO> clientDTOs = clientService.getAllClients(); 
		model.addAttribute("clientDTOs", clientDTOs); 
		return "client/client"; 
	}
	
	
	
	
	@GetMapping("/{clientNum}") // 查詢指定客戶(單筆) / 要修改哪一筆資料
	//@ResponseBody
	public String getClient(@PathVariable Integer clientNum,Model model) {
		// 得到要修改的資料
		ClientDTO clientDTO = clientService.getClientById(clientNum);
		//將要修改的資料傳給jsp
		model.addAttribute("clientDTO", clientDTO);
		return "client/client_update";
	}
	@PostMapping
	public String addClient(@Valid ClientDTO clientDTO,BindingResult result,Model model) { //新增客戶
		if(result.hasErrors()) {
			List<ClientDTO> clientDTOs=clientService.getAllClients();
			model.addAttribute("clientDTOs",clientDTOs);
			return "client/client";
		}
		clientService.addClient(clientDTO); //儲存
		
		return "redirect:/client";
	}
	@PostMapping("/update/{clientNum}")

	public String updateClient(@PathVariable Integer clientNum,@Valid ClientDTO clientDTO,BindingResult result) { //修改會議室
		  
		if(result.hasErrors()) { 
			
			return "client/client_update"; //回到jsp去顯示錯誤
		}
	clientService.updateClient(clientNum, clientDTO); //修改
		return "redirect:/client";
	}
	@GetMapping("/delete/{clientNum}") //刪除
	public String deleteClient(@PathVariable Integer clientNum) { 
		clientService.deleteClient(clientNum);
		return "redirect:/client";	
	
}
	}
