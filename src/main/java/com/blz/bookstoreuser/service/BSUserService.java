package com.blz.bookstoreuser.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.blz.bookstoreuser.dto.BSUserDto;
import com.blz.bookstoreuser.exception.CustomNotFoundException;
import com.blz.bookstoreuser.model.BSUserModel;
import com.blz.bookstoreuser.reposiitory.BSUserRepository;
import com.blz.bookstoreuser.util.Response;
import com.blz.bookstoreuser.util.TokenUtil;

/**
 *  
 * Purpose:Service implementation of the Bookstore User
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 */

@Service
public class BSUserService implements IBSUserService {

	@Autowired
	BSUserRepository  bsUserRepository;

	@Autowired
	MailService mailService;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	PasswordEncoder passwordEncoder;

	//Purpose:To add user details to the database
	@Override
	public BSUserModel addUser(BSUserDto bsUserDto) {
		BSUserModel userModel = new BSUserModel(bsUserDto);
		userModel.setRegisteredDate(LocalDateTime.now());
		userModel.setDeleted(false);
		bsUserRepository.save(userModel);
		String body = "User added successfully with userId " + userModel.getUserId();
		String subject = "User added Successfull";
		mailService.send(userModel.getEmailId(), subject, body);;
		return userModel;
	}	

	//Purpose:To update user details
	@Override
	public BSUserModel updateUser(BSUserDto bsUserDto, Long userId,String token) {
		Long decode = tokenUtil.decodeToken(token);
		Optional<BSUserModel> isTokenPresent = bsUserRepository.findById(decode);
		if (isTokenPresent.isPresent()) {
			Optional<BSUserModel>isUserPresent = bsUserRepository.findById(userId);
			if(isUserPresent.isPresent()) {
				isUserPresent.get().setFullName(bsUserDto.getFullName());
				isUserPresent.get().setEmailId(bsUserDto.getEmailId());
				isUserPresent.get().setPassword(bsUserDto.getPassword());
				isUserPresent.get().setDateOfBirth(bsUserDto.getDateOfBirth());
				isUserPresent.get().setUpdatedDate(LocalDateTime.now());
				bsUserRepository.save(isUserPresent.get());
				String body = "User updated successfully with userId "+isUserPresent.get().getUserId();
				String subject = "User updated Successfull";
				mailService.send(isUserPresent.get().getEmailId(), subject, body);
				return isUserPresent.get();
			}
		}
		throw new CustomNotFoundException(400,"Token is Invalid");
	}

	//Purpose:To fetch all the user details
	@Override
	public List<BSUserModel> getAllUsers(String token) {
		Long decode = tokenUtil.decodeToken(token);
		Optional<BSUserModel> isTokenPresent = bsUserRepository.findById(decode);
		if (isTokenPresent.isPresent()) {
			List<BSUserModel> getAllUsers = bsUserRepository.findAll();
			if(getAllUsers.size()>0) {
				return getAllUsers;
			}
		}
		throw new CustomNotFoundException(400,"Token is Invalid");
	}


	//Purpose:Ability to fetch user details 
	@Override
	public Optional<BSUserModel> getUserById(Long userId,String token) {
		Long decode = tokenUtil.decodeToken(token);
		Optional<BSUserModel> isTokenPresent = bsUserRepository.findById(decode);
		if (isTokenPresent.isPresent()) {
			return bsUserRepository.findById(userId);
		}
		throw new CustomNotFoundException(400,"Token is Invalid");
	}


	//Purpose:Login service
	@Override
	public Response login(String emailId, String password) {
		Optional<BSUserModel> isEmailPresent = bsUserRepository.findByEmailId(emailId);
		if(isEmailPresent.isPresent()){
			if(isEmailPresent.get().getPassword().equals(password)){
				String token = tokenUtil.createToken(isEmailPresent.get().getUserId());
				String body = "User login successfull with userid " + isEmailPresent.get().getUserId();
				String subject =  "mail sent Successfully";
				mailService.send(isEmailPresent.get().getEmailId(), subject, body);
				return new Response("User login succesfull",200,token);
			}
			throw new CustomNotFoundException(400,"Invald credentials");
		}
		throw new CustomNotFoundException(400,"invalid email id");
	}

	//	//Purpose:Method to activate the user
	//	@Override
	//	public BSUserModel Activation(Long userId) {
	//		Optional<BSUserModel>isUserPresent = bsUserRepository.findById(userId);
	//		if(isUserPresent.isPresent()) {
	//				isUserPresent.get().setActive(true);
	//				bsUserRepository.save(isUserPresent.get());
	//				return isUserPresent.get();
	//			}
	//		throw new CustomNotFoundException(400,"Token is Invalid");
	//	}
	//

	//Purpose:Service for forgot password
	@Override
	public Response forgotPassword(String emailId) {
		Optional<BSUserModel> isMailPresent = bsUserRepository.findByEmailId(emailId);
		if (isMailPresent.isPresent()){
			String token = tokenUtil.createToken(isMailPresent.get().getUserId());
			String url = "http://localhost:8068/userservice/resetpassword "+token;
			String subject = "Reset password Successfully";
			mailService.send(isMailPresent.get().getEmailId(), subject, url);
			return new Response("Reset password",200,token);
		}
		throw new CustomNotFoundException(400, "Email Not Found");
	}

	//Purpose:Service for changing password
	@Override
	public BSUserModel changePassword(String token, String password) {
		Long decode = tokenUtil.decodeToken(token);
		Optional<BSUserModel> isTokenPresent = bsUserRepository.findById(decode);
		if (isTokenPresent.isPresent()) {
			isTokenPresent.get().setPassword(passwordEncoder.encode(password));
			bsUserRepository.save(isTokenPresent.get());
			String body = "Password changed successfully with userId"+isTokenPresent.get().getUserId();
			String subject = "Password changed Successfully";
			mailService.send(isTokenPresent.get().getEmailId(), subject, body);
			return isTokenPresent.get();
		}
		throw new CustomNotFoundException(400,"Token not find");
	}

	//Purpose:Service for validate user
		@Override
		public Boolean validateUser(String token) {
			Long decode = tokenUtil.decodeToken(token);
			Optional<BSUserModel> isTokenPresent = bsUserRepository.findById(decode);
			if (isTokenPresent.isPresent())
				return true;
			throw new CustomNotFoundException(400, "Token not found");
		}
		
	//Purpose:Service for deleting user
	@Override
	public Response deleteUser(Long userId,String token) {
		Long decode = tokenUtil.decodeToken(token);
		Optional<BSUserModel> isTokenPresent = bsUserRepository.findById(decode);
		if (isTokenPresent.isPresent()) {
			Optional<BSUserModel> isIdPresent = bsUserRepository.findById(userId);
			if(isIdPresent.isPresent()) {
				isIdPresent.get().setDeleted(true);
				bsUserRepository.save(isIdPresent.get());
				return new Response("success",200,isIdPresent.get());
			}else {
				throw new  CustomNotFoundException(400,"User not present");
			}
		}
		throw new  CustomNotFoundException(400, "Token is Invalid");	
	}

	//Purpose:Service to restore users
	@Override
	public Response restoreUser(Long userId,String token) {
		Long decode = tokenUtil.decodeToken(token);
		Optional<BSUserModel> isTokenPresent = bsUserRepository.findById(decode);
		if (isTokenPresent.isPresent()) {
			Optional<BSUserModel> isIdPresent = bsUserRepository.findById(userId);
			if(isIdPresent.isPresent()) {
				isIdPresent.get().setDeleted(false);
				bsUserRepository.save(isIdPresent.get());
				return new Response("success",200,isIdPresent.get());
			}else {
				throw new  CustomNotFoundException(400,"User not present");
			}
		}
		throw new  CustomNotFoundException(400, "Token is Invalid");	
	}

	//Purpose:Service to delete user permanently
	@Override
	public Response deletePermanently(Long userId, String token) {
		Long userId1 = tokenUtil.decodeToken(token);
		Optional<BSUserModel> isUserPresent = bsUserRepository.findById(userId1);
		if(isUserPresent.isPresent()) {
			Optional<BSUserModel> isIdPresent = bsUserRepository.findById(userId);
			if(isIdPresent.isPresent()) {
				if(isIdPresent.get().isDeleted() == true ) {
					bsUserRepository.delete(isIdPresent.get());
					String body = "User deleted permanently with userId"+isUserPresent.get().getUserId();
					String subject = "User deleted Successfully";
					mailService.send(isUserPresent.get().getEmailId(), subject, body);
					return new Response("Success", 200, isIdPresent.get());
				}
			}
			throw new  CustomNotFoundException(400,"User not present");
		} 		
		throw new CustomNotFoundException(400, "Token is Invalid");
	}

	@Override
	public BSUserModel sendOtp(String token,Long userId) {
		Long userId1 = tokenUtil.decodeToken(token);
		Optional<BSUserModel> isUserPresent = bsUserRepository.findById(userId1);
		if(isUserPresent.isPresent()) {
//			Random random = new Random();
//			isUserPresent.get().setOtp(random.nextInt(1000000));
			isUserPresent.get().setOtp((int) (Math.random()*(999999-100000+1)+100000));
			bsUserRepository.save(isUserPresent.get());
			String body = "your Otp is : "+isUserPresent.get().getOtp();
			String subject = "Otp sent sucessfully";
			mailService.send(isUserPresent.get().getEmailId(), subject, body);
			return isUserPresent.get();
		}
		throw new  CustomNotFoundException(400,"Token is Invalid");
	}

	@Override
	public boolean verifyOtp(String token,Integer otp) {
		Long userId1 = tokenUtil.decodeToken(token);
		Optional<BSUserModel> isUserPresent = bsUserRepository.findById(userId1);
		if(isUserPresent.isPresent()) {
			if(isUserPresent.get().getOtp() == otp) {
				isUserPresent.get().setVerify(true);
				bsUserRepository.save(isUserPresent.get());
				String body = "Account verified sucessfully with userId : " +isUserPresent.get().getUserId();;
				String subject = "Account verified sucessfully";
				mailService.send(isUserPresent.get().getEmailId(), subject, body);
				return isUserPresent.get().isVerify();
			}else {
				throw new  CustomNotFoundException(400,"Invalid otp");
			}
		}
		throw new  CustomNotFoundException(400,"Token is Invalid");
	}
}


