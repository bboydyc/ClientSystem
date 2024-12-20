package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.ClientDTO;
import com.example.demo.model.entity.Client;

@Component
public class ClientMapper {
	@Autowired
	private ModelMapper modelMapper;
	
	public ClientDTO toDTO(Client client) {
		//entity轉dto
		return modelMapper.map(client, ClientDTO.class);
	}
	public Client toEntity(ClientDTO clientDTO) {
		//dto轉entity
		return modelMapper.map(clientDTO, Client.class);
	}
}
