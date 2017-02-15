package com.tunedoor.microservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tunedoor.microservice.model.Account;

/**
 * 
 * @author Mohamed Saeed
 *
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long>{

	public Account findOneByUserId(long userId);
}
