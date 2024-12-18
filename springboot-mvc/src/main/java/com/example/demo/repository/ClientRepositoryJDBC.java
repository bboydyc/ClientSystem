package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.entity.Client;


public interface ClientRepositoryJDBC {
	List<Client> findAllClients(); //查找所有客戶(多筆)
	Optional<Client> findClientById(Integer clientNum); //查找指定客戶(單筆)
	int saveClient(Client client);
	int updateClient(Client client);
	int deleteById(Integer clientNum);
	
}
