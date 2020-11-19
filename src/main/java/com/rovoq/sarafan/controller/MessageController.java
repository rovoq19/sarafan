package com.rovoq.sarafan.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.rovoq.sarafan.domain.Message;
import com.rovoq.sarafan.domain.Views;
import com.rovoq.sarafan.repo.MessageRepo;

@RestController
@RequestMapping("message")
public class MessageController {
	private final MessageRepo messageRepo;

	@Autowired
	public MessageController(MessageRepo messageRepo) {
		this.messageRepo = messageRepo;
	}

	@GetMapping
	@JsonView(Views.IdName.class)
	public List<Message> list() {
		return messageRepo.findAll();
	}

	@GetMapping("{id}")
	public Message getOne(@PathVariable("id") Message message) {
		return message;
	}
	
	@PostMapping
	public Message create(@RequestBody Message message) {
		message.setCreationDate(LocalDateTime.now());
		return messageRepo.save(message);
	}
	
	@PutMapping("{id}")
	public Message update(
			@PathVariable("id") Message messageFromDb, //Получение через id в url от пользователя
			@RequestBody Message message //Данные для message получены от пользователя в виде json
			){
		BeanUtils.copyProperties(message, messageFromDb, "id"); //Скопировать из message в messageFromDb все поля, кроме id
		
		return messageRepo.save(messageFromDb);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Message message) {
		messageRepo.delete(message);
	}
}
