package com.tcompany.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcompany.model.Users;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<Users, Integer> {
	@Query("select u from Users u where trim(upper(u.firs_name)) like %?1%")
	List<Users> findUserByFirsName(String name);
	@Query("select u from Users u where u.email = ?1 and u.enabled ='1'")
	Users findUserByEmail(String email);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update users set token=?1 where email=?2")
	void updateTokenUser(String token, String email);
}