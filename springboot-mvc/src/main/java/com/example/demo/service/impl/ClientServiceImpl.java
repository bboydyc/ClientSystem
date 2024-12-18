package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ClientAlreadyExistException;
import com.example.demo.exception.ClientException;
import com.example.demo.exception.ClientNotFoundException;

import com.example.demo.mapper.ClientMapper;
import com.example.demo.model.dto.ClientDTO;
import com.example.demo.model.entity.Client;
import com.example.demo.repository.ClientRepositoryJDBC;
import com.example.demo.service.ClientService;


@Service
public class ClientServiceImpl implements ClientService{

	
	@Autowired
	private ClientRepositoryJDBC clientRepositoryJDBC;
	
	@Autowired
	private ClientMapper clientMapper;
	
	@Override
	public List<ClientDTO> getAllClients() {
		//很多entity
		
		return  clientRepositoryJDBC.findAllClients() 
								  .stream() 
								  .map(clientMapper::toDTO)
								  .collect(Collectors.toList()); //收集起來變成List<ClientDTO>
	}

	@Override
	public ClientDTO getClientById(Integer clientNum) {
		
		Client client= clientRepositoryJDBC.findClientById(clientNum)
								     .orElseThrow(() ->new ClientNotFoundException("無此客戶:"+ clientNum));
		return clientMapper.toDTO(client);
	}

	@Override
	public void addClient(ClientDTO clientDTO) {
		//判斷該客戶是否已經存在?
		Optional<Client>optClient=clientRepositoryJDBC.findClientById(clientDTO.getNum());
		if(optClient.isPresent()) {
			throw new ClientAlreadyExistException("此客戶已經存在:"+clientDTO.getNum());
			
		}
		Client client= clientMapper.toEntity(clientDTO);
		int rowcount=clientRepositoryJDBC.saveClient(client);
		if(rowcount==0) {
			throw new ClientException("無法新增");
		}
	}

	@Override
	public void addClient(Integer clientNum, String clientName, String clientId, String clientCar, String clientDay, String clientCompany, Integer clientMoney) {
		ClientDTO clientDTO=new ClientDTO(clientNum,clientName,clientId,clientCar,clientDay,clientCompany,clientMoney) ;
			addClient(clientDTO);
		
	}

	@Override
	public void updateClient(ClientDTO clientDTO) {
		
		Optional<Client> optClient=clientRepositoryJDBC.findClientById(clientDTO.getNum());
		if(optClient.isEmpty()) {
			throw new ClientNotFoundException("修改客戶失敗,客戶不存在"+clientDTO.getNum());
			
		}
		Client client=clientMapper.toEntity(clientDTO);
		int rowcount =clientRepositoryJDBC.updateClient(client);
		if(rowcount==0) {
			
			throw new ClientException("無法修改");
		}
	}

	@Override
	public void updateClient(Integer clientNum, ClientDTO clientDTO) {
		clientDTO.setNum(clientNum);
		updateClient(clientDTO);		
	}

	@Override
	public void updateClient(Integer clientNum, String clientName,String clientId,String clientCar,String clientDay,String clientCompany, Integer clientMoney) {
		ClientDTO clientDTO=new ClientDTO( clientNum, clientName, clientId, clientCar,clientDay, clientCompany, clientMoney);
		updateClient(clientDTO);
		
	}
 
	@Override
	public void deleteClient(Integer clientNum) {
		
		Optional<Client> optClient=clientRepositoryJDBC.findClientById(clientNum);
		if(optClient.isEmpty()) {
			
			throw new ClientNotFoundException("刪除失敗,客戶不存在: "+ clientNum);
		}
		//進行刪除
		int rowcount= clientRepositoryJDBC.deleteById(clientNum);
		if(rowcount==0) {
			throw new ClientException("無法刪除");
		}
		
	}

}
