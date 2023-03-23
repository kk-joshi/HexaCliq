package com.hegemony.arraymi.captcha.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hegemony.arraymi.captcha.service.AppException;
import com.hegemony.arraymi.captcha.service.ITransationService;
import com.hegemony.arraymi.captcha.dao.ICustomerDetailsDao;
import com.hegemony.arraymi.captcha.dao.ITransactionDao;
import com.hegemony.arraymi.captcha.dao.IWalletDao;
import com.hegemony.arraymi.captcha.form.WalletDetails;
import com.hegemony.arraymi.captcha.model.Customer;
import com.hegemony.arraymi.captcha.model.CustomerDetails;
import com.hegemony.arraymi.captcha.model.Transaction;
import com.hegemony.arraymi.captcha.model.Wallet;

@Service
public class TransationServiceImpl extends BaseService implements ITransationService {

	@Autowired
	private IWalletDao walletDao;

	@Autowired
	private ITransactionDao transctionDao;

	@Autowired
	private ICustomerDetailsDao customerDetailsDao;

	@Override
	public WalletDetails getWalletDetails() {
		Customer user = getLoggedInUser();
		Optional<Wallet> wallet = walletDao.findByUserId(user.getId());
		if (!wallet.isPresent()) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Please update your profile first!");
		}
		Optional<CustomerDetails> oldDetails = customerDetailsDao.findByUserIdAndActive(user.getId(), true);
		if (!oldDetails.isPresent()) {
			throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Something went wrong please contect customer care");
		}
		List<Transaction> allTransactions = transctionDao.findByUserId(user.getId());
		int transactionCount = allTransactions.size();
		Collections.reverse(allTransactions);
		allTransactions = allTransactions.stream().limit(10).collect(Collectors.toList());
		return new WalletDetails(transactionCount, oldDetails.get().getCountry(), wallet.get(), allTransactions);
	}

}
