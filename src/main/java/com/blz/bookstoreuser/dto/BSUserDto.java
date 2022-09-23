package com.blz.bookstoreuser.dto;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

/**
 *  
 * Purpose:DTO for the bookstore user data
 * 
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 **/ 

@Data
public class BSUserDto {
	@Pattern(regexp = "^[A-Z]{1,}[a-z\\s]{2,}$", message = "Name is invalid, first letter should be uppercase!")
	private String fullName;

	@Pattern(regexp = "([a-zA-Z0-9./.-])+.([a-zA-Z0-9./.-])?@([a-z]{2,7})+.([a-z]{2,4})+.([a-z]{2,4})?", message = "Valid format is: abc.xyz@gmail.com")
	private String emailId;

	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",message ="Enter valid password")
	private String password;
	
	@NotNull
	private String dateOfBirth;

}
