package com.blz.bookstoreuser.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.blz.bookstoreuser.dto.BSUserDto;
import com.blz.bookstoreuser.model.BSUserModel;
import com.blz.bookstoreuser.service.IBSUserService;
import com.blz.bookstoreuser.util.UserResponse;

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
	public ResponseEntity<UserResponse> addUser(@Valid@RequestBody BSUserDto bsUserDto) {
		BSUserModel bsUserModel = userService.addUser(bsUserDto);
		UserResponse response = new UserResponse("User added successfully", 200, bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}


	/**
	 * Purpose: update User
	 * @Param: bsUserDto,id,token
	 * 
	 */
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserResponse> updateUser(@Valid@RequestBody BSUserDto bsUserDto,@PathVariable Long userId,@RequestHeader String token) {
		BSUserModel bsUserModel = userService.updateUser(bsUserDto,userId,token);
		UserResponse response = new UserResponse("User updated successfully", 200, bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: get all Users
	 * @Param: token
	 * 
	 */
	@GetMapping("/read")
	public ResponseEntity<UserResponse> getAllUsers(@RequestHeader String token) {
		List<BSUserModel> bsUserModel = userService.getAllUsers(token);
		UserResponse response = new UserResponse("Fetching all users successfully", 200, bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose: get User by id
	 * @Param: id,token
	 * 
	 */
	@GetMapping("/readbyid/{userId}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId,@RequestHeader String token) {
		Optional<BSUserModel> bsUserModel = userService.getUserById(userId,token);
		UserResponse response = new UserResponse("Fectching user by id successfully", 200, bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}

	/**
	 * Purpose: to login
	 * @Param: email and password
	 * 
	 */
	@PostMapping("/login")
	public ResponseEntity<UserResponse> login(String emailId,String password) {
		UserResponse bsUserModel = userService.login(emailId,password);
		UserResponse response = new UserResponse("User login successfull", 200, bsUserModel);
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
	public ResponseEntity<UserResponse> forgotPassword(@RequestParam String emailId) {
		UserResponse  bsUserModel = userService.forgotPassword(emailId);
		UserResponse response = new UserResponse("Reset password successfull", 200,  bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:To change the password
	 * @Param: token and password
	 */
	@PutMapping("/changepassword/{token}")
	public ResponseEntity<UserResponse> changePassword(@PathVariable String token, @RequestParam String password) {
		BSUserModel  bsUserModel = userService.changePassword(token,password);
		UserResponse response = new UserResponse("Password changed successfully", 200,  bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:Validate user
	 * @return 
	 * @Param: token
	 */
	@GetMapping("/validateuser/{token}")
	public UserResponse validateUser(@PathVariable String token) {
		return userService.validateUser(token);
	}
	
	
	@GetMapping("/validateuserid/{token}")
	public Boolean validateUserId(@PathVariable String token) {
		return userService.validateUserId(token);
	}


	
	/**
	 * Purpose:To delete the user
	 * @Param: token and id
	 */
	@DeleteMapping("delete/{userId}")
	public ResponseEntity<UserResponse> deleteUser(@PathVariable Long userId,@RequestHeader String token) {
		UserResponse  bsUserModel = userService.deleteUser(userId,token);
		UserResponse response = new UserResponse("User deleted successfully", 200,  bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/addprofilepic/{userId}")
	public ResponseEntity<UserResponse> addProfilePic(@PathVariable Long userId,@RequestParam MultipartFile profilePic) throws IOException {
		UserResponse bsUserModel = userService.addProfilePic(userId,profilePic);
		UserResponse response = new UserResponse("Profile pic uploaded sucessfully ", 200, bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose: To restore the user
	 * @Param: token and id
	 */
	@GetMapping("restore/{userId}")
	public ResponseEntity<UserResponse> restoreUser(@PathVariable Long userId,@RequestHeader String token) {
		UserResponse  bsUserModel = userService.restoreUser(userId,token);
		UserResponse response = new UserResponse("User restored successfully", 200,  bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:To delete the user permanently
	 * @Param: token and id
	 */
	@DeleteMapping("deletepermanently/{userId}")
	public ResponseEntity<UserResponse> deletePermanently(@PathVariable Long userId,@RequestHeader String token) {
		UserResponse bsUserModel = userService.deletePermanently(userId,token);
		UserResponse response = new UserResponse("User deleted permanently ", 200,  bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:To send otp to the user
	 * @Param: token and id
	 */
	
	@PutMapping("/sendotp/{userId}")
	public ResponseEntity<UserResponse> sendOtp(@RequestHeader String token,@PathVariable Long userId) {
		BSUserModel bsUserModel = userService.sendOtp(token,userId);
		UserResponse response = new UserResponse("Otp sent sucessfully ", 200, bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	
	/**
	 * Purpose:To verify the otp
	 * @Param: token and otp
	 */
	@PutMapping("/verifyotp/{otp}")
	public ResponseEntity<UserResponse> verifyOtp(@RequestHeader String token,@PathVariable Integer otp) {
		boolean bsUserModel = userService.verifyOtp(token,otp);
		UserResponse response = new UserResponse("Otp verified sucessfully ", 200, bsUserModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
