package com.blz.bookstoreuser.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.blz.bookstoreuser.dto.BSUserDto;
import com.blz.bookstoreuser.model.BSUserModel;
import com.blz.bookstoreuser.service.IBSUserService;
import com.blz.bookstoreuser.util.Response;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

/**
 * Purpose: BookStoreUser Controller to process User Data APIs.
 * @version: 4.15.1.RELEASE
 * @author: Pavan Kumar G V
 *   
 */

@RestController
@RequestMapping("/userservice")
public class BSUserController {

	@Autowired
	IBSUserService userService;

	/**
	 * Purpose: Create User
	 * @Param: bsUserDto
	 * 
	 */
	@PostMapping("/add")
	public ResponseEntity<Response> addUser(@Valid@RequestBody BSUserDto bsUserDto) {
		BSUserModel bsUserModel = userService.addUser(bsUserDto);
		Response response = new Response("User inserted successfully, Check mail to activate the user", 200, bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}


	/**
	 * Purpose: update User
	 * @Param: bsUserDto,id,token
	 * 
	 */
	@PutMapping("/update/{userId}")
	public ResponseEntity<Response> updateUser(@Valid@RequestBody BSUserDto bsUserDto,@PathVariable Long userId,@RequestHeader String token) {
		BSUserModel bsUserModel = userService.updateUser(bsUserDto,userId,token);
		Response response = new Response("User updated successfully", 200, bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: get all Users
	 * @Param: token
	 * 
	 */
	@GetMapping("/read")
	public ResponseEntity<Response> getAllUsers(@RequestHeader String token) {
		List<BSUserModel> bsUserModel = userService.getAllUsers(token);
		Response response = new Response("Fetching all users successfully", 200, bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: get User by id
	 * @Param: id,token
	 * 
	 */
	@GetMapping("/readbyid/{userId}")
	public ResponseEntity<Response> getUserById(@PathVariable Long userId,@RequestHeader String token) {
		Optional<BSUserModel> bsUserModel = userService.getUserById(userId,token);
		Response response = new Response("Fectching user by id successfully", 200, bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}

	/**
	 * Purpose: to login
	 * @Param: email and password
	 * 
	 */
	@PostMapping("/login")
	public ResponseEntity<Response> login(String emailId,String password) {
		Response bsUserModel = userService.login(emailId,password);
		Response response = new Response("User login successfull", 200, bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
//
//	/**
//	 * Purpose: to activate the user
//	 * @Param: id
//	 * 
//	 */
//	@GetMapping("activateuser/{userId}")
//	public ResponseEntity<Response> Activation(@PathVariable Long userId){
//		BSUserModel  bsUserModel = userService.Activation(userId);
//		Response response = new Response("Account Activated successfully ", 200,  bsUserModel);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}

	/**
	 * Purpose:TO reset the password
	 * @Param: emailid
	 */
	@PostMapping("/resetpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam String emailId) {
		Response  bsUserModel = userService.forgotPassword(emailId);
		Response response = new Response("Reset password successfull", 200,  bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:To change the password
	 * @Param: token and password
	 */
	@PutMapping("/changepassword/{token}")
	public ResponseEntity<Response> changePassword(@PathVariable String token, @RequestParam String password) {
		BSUserModel  bsUserModel = userService.changePassword(token,password);
		Response response = new Response("Password changed successfully", 200,  bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:Validate user
	 * @Param: token
	 */
	@GetMapping("/validateuser/{token}")
	public Boolean validateUser(@PathVariable String token) {
		return userService.validateUser(token);
	}

	/**
	 * Purpose:To delete the user
	 * @Param: token and id
	 */
	@DeleteMapping("delete/{userId}")
	public ResponseEntity<Response> deleteUser(@PathVariable Long userId,@RequestHeader String token) {
		Response  bsUserModel = userService.deleteUser(userId,token);
		Response response = new Response("User deleted successfully", 200,  bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: To restore the user
	 * @Param: token and id
	 */
	@GetMapping("restore/{userId}")
	public ResponseEntity<Response> restoreUser(@PathVariable Long userId,@RequestHeader String token) {
		Response  bsUserModel = userService.restoreUser(userId,token);
		Response response = new Response("User restored successfully", 200,  bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:To delete the user permanently
	 * @Param: token and id
	 */
	@DeleteMapping("deletepermanently/{userId}")
	public ResponseEntity<Response> deletePermanently(@PathVariable Long userId,@RequestHeader String token) {
		Response bsUserModel = userService.deletePermanently(userId,token);
		Response response = new Response("User deleted permanently ", 200,  bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/sendotp/{userId}")
	public ResponseEntity<Response> sendOtp(@RequestHeader String token,@PathVariable Long userId) {
		BSUserModel bsUserModel = userService.sendOtp(token,userId);
		Response response = new Response("User deleted permanently ", 200, bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	
	
	@PutMapping("/verifyotp/{otp}")
	public ResponseEntity<Response> verifyOtp(@RequestHeader String token,@PathVariable Integer otp) {
		boolean bsUserModel = userService.verifyOtp(token,otp);
		Response response = new Response("Otp verified sucessfully ", 200, bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
