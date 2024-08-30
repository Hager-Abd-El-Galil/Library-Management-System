package com.system.library.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.library.security.entity.UserCredentials;

@Repository
public interface UserRepository extends JpaRepository<UserCredentials, Integer> {

	UserCredentials findByUsername(String username);
}
