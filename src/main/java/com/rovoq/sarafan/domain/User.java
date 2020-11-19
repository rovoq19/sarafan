package com.rovoq.sarafan.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name = "usr")
@Data
public class User implements Serializable{
	@Id
	private String id;
	private String name;
	private String userpic;
	private String email;
	private String gender;
	private String locale;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastVisit;
	
	
}
