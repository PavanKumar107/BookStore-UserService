package com.blz.bookstoreuser.reposiitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blz.bookstoreuser.model.BSUserModel;

/**
 *  
 * Purpose:Repository for book service
 * 
 * @author: Pavan Kumar G V 
 * @version: 4.15.1.RELEASE
 * 
 **/ 


@Repository
public interface BSUserRepository extends JpaRepository<BSUserModel, Long> {

	Optional<BSUserModel> findByEmailId(String emailId);

}
