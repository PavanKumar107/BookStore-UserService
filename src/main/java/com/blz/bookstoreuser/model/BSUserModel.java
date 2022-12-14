package com.blz.bookstoreuser.model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
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
 * Purpose:Model class for the user
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
	private String fullName;
	private String emailId;
	private String password;
	private LocalDateTime registeredDate;
	private LocalDateTime updatedDate;
	private String dateOfBirth;
	@Column(length = 1000)
	private String profilePic;
	private boolean verify;
	private  boolean isDeleted;		
	private int otp;
	private Date purchaseDate;
	private Date expiryDate;
	
	public BSUserModel(BSUserDto bsUserDto) {
	this.fullName = bsUserDto.getFullName();
	this.emailId = bsUserDto.getEmailId();
	this.password = bsUserDto.getPassword();
	this.dateOfBirth = bsUserDto.getDateOfBirth();
	}
}
