package com.demo.transactions.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.transactions.exception.ExceededTimeException;
import com.demo.transactions.exception.InternalErrorException;
import com.demo.transactions.exception.SuccessResponse;
import com.demo.transactions.model.Constants;
import com.demo.transactions.model.Statistic;
import com.demo.transactions.model.Transaction;
import com.demo.transactions.model.TransactionUtils;
import com.google.gson.Gson;

/**
 * Handles requests for the application home page.
 */
@Controller
public class TransactionController {
	
	private static final Logger _logger = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ServletContext servletContext;
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 */
	@RequestMapping(value = Constants.MAPPING_TRANSACTIONS_PATH, method = RequestMethod.POST)
	public String transactionsPost(Locale locale, Model model) throws IOException {
		try{
			BufferedReader reader = request.getReader();
			Gson gson = new Gson();
			Transaction transaction = gson.fromJson(reader, Transaction.class);
			transaction.setId(UUID.randomUUID());
			
			List<Transaction> transactions;
			if(servletContext.getAttribute(Constants.SERVLET_TRANSACITON_LIST_ATTRIBUTE) == null){
				transactions = new ArrayList<Transaction>();
				transactions.add(transaction);
				servletContext.setAttribute(Constants.SERVLET_TRANSACITON_LIST_ATTRIBUTE, transactions);
			} else {
				transactions = (List<Transaction>) servletContext.getAttribute(
						Constants.SERVLET_TRANSACITON_LIST_ATTRIBUTE);
				transactions.add(transaction);
			}
			
			TransactionUtils.validateTime(transaction);
			
		} catch(ExceededTimeException e){
			throw new ExceededTimeException("The transaction is older than 60 seconds");
		} catch(Exception e){
			throw new InternalErrorException("There was occurred an internal error");
		}
		
		throw new SuccessResponse("Success");
	}
	
	@RequestMapping(value = Constants.MAPPING_STATISTICS_PATH, method = RequestMethod.GET)
	public String statiticsGet(Locale locale, Model model) throws IOException {
		Statistic statistic = new Statistic();
		try{
			
			List<Transaction> transactions = new ArrayList<Transaction>();
			if(servletContext.getAttribute(Constants.SERVLET_TRANSACITON_LIST_ATTRIBUTE) == null){
			} else {
				transactions = (List<Transaction>) servletContext.getAttribute(
						Constants.SERVLET_TRANSACITON_LIST_ATTRIBUTE);
			}
			
			statistic = TransactionUtils.calculateStatistics(transactions);
			
		} catch(ExceededTimeException e){
			throw new ExceededTimeException("The transaction is older than 60 seconds");
		} catch(Exception e){
			throw new InternalErrorException("There was occurred an internal error");
		}
		
		model.addAttribute(Constants.VIEW_JSON_FIELD,TransactionUtils.buildJsonResponse(statistic));
		return Constants.VIEW_TRANSACTION_NAME;
	}
	
}
