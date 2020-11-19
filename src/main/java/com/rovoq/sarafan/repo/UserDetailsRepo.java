package com.rovoq.sarafan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovoq.sarafan.domain.User;

public interface UserDetailsRepo extends JpaRepository<User, String>{

}
