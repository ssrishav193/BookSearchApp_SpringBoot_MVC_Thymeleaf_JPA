package com.rishav.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rishav.entity.Login;

@Repository
public interface LoginRepo extends JpaRepository<Login, String>{
	

}
