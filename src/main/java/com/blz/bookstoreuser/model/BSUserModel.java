package com.blz.bookstoreuser.model;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.blz.bookstoreuser.dto.BSUserDto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  
 * Purpose:Model for the bookstore User data Registration
 * 
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 **/ 

@Entity
@Table(name = "bs_user")
@Data
@NoArgsConstructor
public class BSUserModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	private String name;
	private String emailId;
	private String password;
	private String address;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private boolean isActive;
	private boolean isDeleted;
	private String dob;
	private String Phoneno;
	private String profilePic;
	
	public BSUserModel(BSUserDto bsUserDto) {
	this.name = bsUserDto.getName();
	this.emailId = bsUserDto.getEmailId();
	this.password = bsUserDto.getPassword();
	this.dob = bsUserDto.getDob();
	this.address = bsUserDto.getAddress();
	this.Phoneno = bsUserDto.getPhoneno();
	}
	
	

}
