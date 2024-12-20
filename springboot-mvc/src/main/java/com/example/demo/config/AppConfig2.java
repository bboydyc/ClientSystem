package com.example.demo.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.dto.ClientDTO;

import com.example.demo.model.entity.Client;


@Configuration 
public class AppConfig2 {
	
	@Bean
	ModelMapper modelMapper2() {
		ModelMapper modelMapper2 = new ModelMapper();

		// DTO -> Entity 映射規則
		PropertyMap<ClientDTO, Client> clientMapRule1 = new PropertyMap<ClientDTO, Client>() { // 規則一
			@Override
			protected void configure() {
				map(source.getNum(),destination.getClientNum());
				map(source.getName(), destination.getClientName());
				map(source.getId(), destination.getClientId());
				map(source.getCar(),destination.getClientCar()); //new
				map(source.getDay(), destination.getClientDay());
				map(source.getCompany(), destination.getClientCompany());
				map(source.getMoney(), destination.getClientMoney());
			}
		}; 	
		modelMapper2.addMappings(clientMapRule1); // 加入新規則
		
		// Entity -> DTO 映射規則
		PropertyMap<Client, ClientDTO> clientMapRule2 = new PropertyMap<Client, ClientDTO>() { // 規則二
			@Override
			protected void configure() {
				map(source.getClientNum(), destination.getNum());
				map(source.getClientName(), destination.getName());
				map(source.getClientId(), destination.getId());
				map(source.getClientCar(),destination.getCar()); 
				map(source.getClientDay(), destination.getDay());
				map(source.getClientCompany(), destination.getCompany());
				map(source.getClientMoney(), destination.getMoney());
			}
		}; 	
		modelMapper2.addMappings(clientMapRule2); // 加入新規則
		
		return modelMapper2;
	}
	
}