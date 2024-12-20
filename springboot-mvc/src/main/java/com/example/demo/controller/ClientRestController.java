package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ClientAlreadyExistException;
import com.example.demo.exception.ClientException;
import com.example.demo.exception.ClientNotFoundException;
import com.example.demo.model.dto.ClientDTO;
import com.example.demo.model.response.ApiResponse;
import com.example.demo.service.ClientService;

/**
請求方法 URL 路徑                 	 功能說明           請求參數                       	                回應
-----------------------------------------------------------------------------------------------------------------------------------------
GET    /rest/clients       	     取得所有客戶列表      無                                  		        成功時返回所有客戶的列表及成功訊息。
GET    /rest/client/{clientNum}  取得指定客戶資料    clientNum (路徑參數，客戶編號)            	        成功時返回指定客戶資料及成功訊息。
POST   /rest/client              新增客戶資料        請求體包含 clientDto                   		    成功時返回成功訊息，無回傳資料。
PUT    /rest/client/{clientNum}  更新指定客戶資料    clientNum(路徑參數，客戶編號)，請求體包含clientDto     成功時返回成功訊息，無回傳資料。
DELETE /rest/client/{clientNum}  刪除指定客戶        clientNum(路徑參數，客戶編號)                  	    成功時返回成功訊息，無回傳資料。
*/   
@RestController
@RequestMapping("/rest")
@CrossOrigin(origins="http://localhost:8194", allowCredentials="true")
public class ClientRestController {
	@Autowired
	private ClientService clientService;
	
	//取得所有客戶資料
	
	@GetMapping("/clients")
	public ResponseEntity<ApiResponse<List<ClientDTO>>> getClients(){
		List<ClientDTO> clientDTOs=clientService.getAllClients();
		return ResponseEntity.ok(ApiResponse.success("查詢成功", clientDTOs));
		}
	//取得單一客戶資料
	
	@GetMapping("/client/{clientNum}")
	public ResponseEntity<ApiResponse<ClientDTO>> getClient(@PathVariable Integer clientNum) {
		ClientDTO clientDTO=clientService.getClientById(clientNum);
		return ResponseEntity.ok(ApiResponse.success("查詢客戶編號["+clientNum+"]成功", clientDTO));
	}
	//新增客戶

	@PostMapping("/client")
	public ResponseEntity<ApiResponse<Boolean>> addClient(@RequestBody ClientDTO clientDTO) {
		clientService.addClient(clientDTO);
		return  ResponseEntity.ok(ApiResponse.success("新增客戶資料成功", true));
	}
	//修改客戶資料
	
	@PutMapping("/client/{clientNum}")
	public ResponseEntity<ApiResponse<Boolean>> updateClient(@PathVariable Integer clientNum,@RequestBody ClientDTO clientDTO) {
		clientService.updateClient(clientNum, clientDTO);
		return ResponseEntity.ok(ApiResponse.success("修改客戶資料成功", true));
	}
	//刪除客戶資料
	
	@DeleteMapping("/client/{clientNum}")
	public ResponseEntity<ApiResponse<Boolean>> deleteClient(@PathVariable Integer clientNum){
		clientService.deleteClient(clientNum);
		return ResponseEntity.ok( ApiResponse.success("刪除客戶資料成功", true));
	}
	// 例外處理
		@ExceptionHandler({ClientException.class})
		public ResponseEntity<ApiResponse<String>> handleRoomException(ClientException re) {
			int status=500;
			if(re instanceof ClientNotFoundException) {
				status=404;
			}else if(re instanceof ClientAlreadyExistException) {
				status=409;
			}
			return ResponseEntity.status(status).body(ApiResponse.error(status, re.getMessage())) ;
		}
}
