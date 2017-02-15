package com.tunedoor.microservice;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tunedoor.microservice.api.AccountController;
import com.tunedoor.microservice.exception.InsufficientBalanceException;
import com.tunedoor.microservice.exception.UserNotFoundException;
import com.tunedoor.microservice.model.TransferRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceApplicationTests {

	@BeforeClass
	public static void setConfiguration(){
		System.setProperty("spring.config.name", "account-server-test");
	}

	@Autowired
	AccountController accountController;
	
	@Test
	public void checkExistingUserBalance() {
		int balance = accountController.getBalanceByUserId(1);
		Assert.assertNotEquals(0, balance);
	}
	
	@Test
	public void checkNotExistingUserBalance() {
		try {
			accountController.getBalanceByUserId(11);
			Assert.fail("Expected an UserNotFoundException");
		} catch (UserNotFoundException e) {
		}
	}
	
	@Test
	public void doTransferForExistingUsers() {
		int senderBalanceBefore = accountController.getBalanceByUserId(1);
		int receiverBalanceBefore = accountController.getBalanceByUserId(5);
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setSenderId(1);
		transferRequest.setBeneficiaryId(5);
		transferRequest.setAmount(50);
		String txId = accountController.transfer(transferRequest);
		int senderBalanceAfter = accountController.getBalanceByUserId(1);
		int receiverBalanceAfter = accountController.getBalanceByUserId(5);
		Assert.assertEquals(senderBalanceBefore-50, senderBalanceAfter);
		Assert.assertEquals(receiverBalanceBefore+50, receiverBalanceAfter);
		Assert.assertNotNull(txId);
	}
	
	@Test
	public void doTransferForNotExistingUsers() {
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setSenderId(12);
		transferRequest.setBeneficiaryId(5);
		transferRequest.setAmount(50);
		try {
			accountController.transfer(transferRequest);
			Assert.fail("Expected an UserNotFoundException");
		} catch (UserNotFoundException e) {
		}
	}
	
	@Test
	public void doTransferWithInsufficientBalance() {
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setSenderId(1);
		transferRequest.setBeneficiaryId(5);
		transferRequest.setAmount(40000);
		try {
			accountController.transfer(transferRequest);
			Assert.fail("Expected an InsufficientBalanceException");
		} catch (InsufficientBalanceException e) {
		}
	}

}
