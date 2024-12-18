package com.example.demo.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Client;

import com.example.demo.repository.ClientRepositoryJDBC;


@Repository // 宣告一個 Repository 給 Spring 管理
@PropertySource("classpath:sql.properties") // 自動到 src/main/resources 找到 sql.properties
public class ClientRepositoryJDBCImpl implements ClientRepositoryJDBC {
	
	@Autowired // 自動綁定(會自動採用 application.properties 有關於 spring.datasource 的設定資訊)
	private JdbcTemplate jdbcTemplate;
	
	// 會透過 Spring DI 技術將資料注入給指定變數
	@Value("${client.sql.findAll}") // ${} SpringEL 語法 
	private String findAllSql;
	
	@Value("${client.sql.findById}")
	private String findByIdSql;
	
	@Value("${client.sql.save}")
	private String saveSql;
	
	@Value("${client.sql.update}")
	private String updateSql;
	
	@Value("${client.sql.deleteById}")
	private String deleteByIdSql;
	
	
	@Override
	public List<Client> findAllClients() {
		return jdbcTemplate.query(findAllSql, new BeanPropertyRowMapper<>(Client.class));
	}

	@Override
	public Optional<Client> findClientById(Integer clientNum) {
		try {	
			// queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
			Client client = jdbcTemplate.queryForObject(findByIdSql, new BeanPropertyRowMapper<>(Client.class), clientNum);
			return Optional.of(client);
		} catch (Exception e) {
			//空
		}
		return Optional.empty();
	}

	@Override
	public int saveClient(Client client) {
		return jdbcTemplate.update(saveSql, client.getClientNum(), client.getClientName(),client.getClientId(),client.getClientCar(),client.getClientDay(),client.getClientCompany(),client.getClientMoney());
	}

	@Override
	public int updateClient(Client client) {
		return jdbcTemplate.update(updateSql, client.getClientName(),client.getClientId(),client.getClientCar(),client.getClientDay(),client.getClientCompany(),client.getClientMoney(),client.getClientNum() );
	}

	@Override
	public int deleteById(Integer clientNum) {
		return jdbcTemplate.update(deleteByIdSql, clientNum);
	}

}