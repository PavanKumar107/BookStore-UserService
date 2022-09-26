package com.blz.bookstoreuser.config;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.blz.bookstoreuser.model.BSUserModel;
import com.blz.bookstoreuser.reposiitory.BSUserRepository;
import com.blz.bookstoreuser.service.MailService;

@Component
public class ShedulingConfig {

	@Autowired
	BSUserRepository userRepository;

	@Autowired
	MailService mailService;

	@Scheduled(fixedDelay = 60000)
	public void emailShedulingJob() {
		List<BSUserModel> usersList = userRepository.findAll();
		for(BSUserModel userModel : usersList) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(userModel.getPurchaseDate());
			calendar.add(Calendar.MONTH, 11);
			Date date = calendar.getTime();
			if(new Date().equals(date)) {
				String body = "your subscription expiring in one month :" +userModel.getUserId();
				String subject = "Please take subscription";
				mailService.send(userModel.getEmailId(), subject, body);
			}
			
			Calendar calendar2 = Calendar.getInstance();
			calendar.setTime(userModel.getPurchaseDate());
			calendar.add(Calendar.MONTH, 12);
			Date date2 = calendar2.getTime();
			if(userModel.getExpiryDate() == date2) {
				String body = "your subscription expired :" +userModel.getUserId();
				String subject = "Please take subscription";
				mailService.send(userModel.getEmailId(), subject, body);
			}	
		}
	}
}
