package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.ClientDTO;


public interface ClientService {
	public List<ClientDTO> getAllClients();
	public ClientDTO getClientById(Integer clientNum);
	
	public void addClient(ClientDTO clientDTO);
	public void addClient(Integer clientNum, String clientName,String clientId,String clientCar,String clientDay,String clientCompany, Integer clientMoney);
	
	public void updateClient(ClientDTO clientDTO);
	public void updateClient(Integer clientNum, ClientDTO clientDTO);
	public void updateClient(Integer clientNum,  String clientName,String clientId,String clientCar,String clientDay,String clientCompany, Integer clientMoney);
	
	public void deleteClient(Integer clientNum);
	
}
