package com.blz.bookstoreuser.service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.blz.bookstoreuser.dto.BSUserDto;
import com.blz.bookstoreuser.model.BSUserModel;
import com.blz.bookstoreuser.util.UserResponse;

/**
 *  
 * Purpose:BookStoreUser Service Interface
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 **/

public interface IBSUserService {
	
	BSUserModel addUser(BSUserDto bsUserDto);

	BSUserModel updateUser(BSUserDto bsUserDto, Long userId, String token);

	List<BSUserModel> getAllUsers(String token);

	UserResponse login(String emailId, String password);

	Optional<BSUserModel> getUserById(Long userId, String token);
	
	UserResponse addProfilePic(Long userId, MultipartFile profilePic) throws IOException;
	
//	BSUserModel Activation(Long userId);

	UserResponse forgotPassword(String emailId);

	BSUserModel changePassword(String token, String password);

	UserResponse deleteUser(Long userId, String token);

	UserResponse restoreUser(Long userId, String token);

	UserResponse deletePermanently(Long userId, String token);

	BSUserModel sendOtp(String token, Long userId);

	boolean verifyOtp(String token, Integer otp);

	UserResponse validateUser(String token);

	Boolean validateUserId(String token);


}
