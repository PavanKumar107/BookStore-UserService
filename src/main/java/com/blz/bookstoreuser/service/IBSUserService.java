package com.blz.bookstoreuser.service;
import java.util.List;
import java.util.Optional;
import com.blz.bookstoreuser.dto.BSUserDto;
import com.blz.bookstoreuser.model.BSUserModel;
import com.blz.bookstoreuser.util.Response;

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

	Response login(String emailId, String password);

	Optional<BSUserModel> getUserById(Long userId, String token);
	
	BSUserModel Activation(Long userId);

	Response resetPassword(String emailId);

	BSUserModel changePassword(String token, String password);

	Response deleteUser(Long userId, String token);

	Response restoreUser(Long userId, String token);

	Response deletePermanently(Long userId, String token);
}
