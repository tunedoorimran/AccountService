package com.tunedoor.microservice.api;

import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tunedoor.microservice.exception.InsufficientBalanceException;
import com.tunedoor.microservice.exception.UserNotFoundException;
import com.tunedoor.microservice.model.Account;
import com.tunedoor.microservice.model.TransferRequest;
import com.tunedoor.microservice.repository.AccountRepository;

/**
 * 
 * @author Mohamed Saeed
 *
 */
@RestController
public class AccountController {

	private Logger logger = Logger.getLogger(AccountController.class.getName());

	private static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient balance to complete your transaction";
	private static final String NOT_FOUND_MESSAGE = "User with Id: %s not found";

	@Autowired
	private AccountRepository accountRepository;

	/**
	 * Check user balance
	 * @param userId
	 * @return balance
	 */
	@RequestMapping("/account/balance/{id}")
	public int getBalanceByUserId(@PathVariable("id") long userId) {
		Account account = getAccountRepository().findOneByUserId(userId);
		if (account == null) {
			logger.info(String.format("User with ID: %s not exist",userId));
			throw new UserNotFoundException(String.format(NOT_FOUND_MESSAGE, userId));
		}
		return account.getBalance();
	}

	/**
	 * Do Transfer from account to another account.
	 * @param transferRequest
	 * @return transaction id
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/account/transfer")
	public String transfer(@RequestBody TransferRequest transferRequest) {
		synchronized (transferRequest) {
			Account senderAccount = getAccountRepository().findOneByUserId(transferRequest.getSenderId());
			if (senderAccount == null) {
				logger.info(String.format("User with ID: %s not exist",transferRequest.getSenderId()));
				throw new UserNotFoundException(String.format(NOT_FOUND_MESSAGE, transferRequest.getSenderId()));
			}
			Account receiverAccount = getAccountRepository().findOneByUserId(transferRequest.getBeneficiaryId());
			if (receiverAccount == null) {
				logger.info(String.format("User with ID: %s not exist",transferRequest.getBeneficiaryId()));
				throw new UserNotFoundException(String.format(NOT_FOUND_MESSAGE, transferRequest.getBeneficiaryId()));
			}
			if (transferRequest.getAmount() > senderAccount.getBalance()) {
				logger.info(String.format("INSUFFICIENT BALANCE TO COMPLETE YOUR TRANSACTION WITH AMOUNT %s",transferRequest.getAmount()));
				throw new InsufficientBalanceException(INSUFFICIENT_BALANCE_MESSAGE);
			}
			int newSenderBalance = senderAccount.getBalance() - transferRequest.getAmount();
			senderAccount.setBalance(newSenderBalance);
			int newReceiverBalance = receiverAccount.getBalance() + transferRequest.getAmount();
			receiverAccount.setBalance(newReceiverBalance);
			getAccountRepository().save(senderAccount);
			getAccountRepository().save(receiverAccount);
		}
		String transactionId = UUID.randomUUID().toString();
		logger.info(String.format("Transaction is completed with ID: %s",transactionId));
		return transactionId;
	}

	public AccountRepository getAccountRepository() {
		return accountRepository;
	}
}
