package com.blz.bookstoreuser.reposiitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blz.bookstoreuser.model.BSUserModel;

@Repository
public interface BSUserRepository extends JpaRepository<BSUserModel, Long> {

	Optional<BSUserModel> findByEmailId(String emailId);

}
