package com.rovoq.sarafan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rovoq.sarafan.domain.Message;

public interface MessageRepo extends JpaRepository<Message, Long>{

}
