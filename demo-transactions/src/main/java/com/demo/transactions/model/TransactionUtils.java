package com.demo.transactions.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.demo.transactions.exception.ExceededTimeException;
import com.google.gson.JsonObject;

public class TransactionUtils {
	
	private static final Logger _logger = Logger.getLogger(TransactionUtils.class);

	public static void validateTime(Transaction transaction){
		Calendar currentTime = Calendar.getInstance();
		long now = currentTime.getTimeInMillis();
		long passed = now - transaction.getTimestamp();
		int secondsPassed = (int) (passed / 1000);
		
		if(secondsPassed > 60){
			_logger.info("Throwing exceeded time exception");
			throw new ExceededTimeException("The transaction is older than 60 seconds");
		}
	}
	
	public static void validateTime(Calendar statisticTime, Transaction transaction){
		long statisticMillis = statisticTime.getTimeInMillis();
		long passed = statisticMillis - transaction.getTimestamp();
		int secondsPassed = (int) (passed / 1000);
		
		if(secondsPassed > 60){
			_logger.info("Throwing exceeded time exception");
			throw new ExceededTimeException("The transaction is older than 60 seconds");
		}
	}
	
	public static JsonObject buildJsonResponse(Statistic statistic){
		JsonObject response =  new JsonObject();
		response.addProperty(Constants.STATISTIC_SUM_FIELD, statistic.getSum());
		response.addProperty(Constants.STATISTIC_AVG_FIELD, statistic.getAvg());
		response.addProperty(Constants.STATISTIC_MAX_FIELD, statistic.getMax());
		response.addProperty(Constants.STATISTIC_MIN_FIELD, statistic.getMin());
		response.addProperty(Constants.STATISTIC_COUNT_FIELD, statistic.getCount());
		
		return response;
	}
	
	public static Statistic calculateStatistics(List<Transaction> transactions){
		Statistic statistic = new Statistic();
		List<Transaction> validTransactions = new ArrayList<Transaction>();
		Double sum = new Double(Constants.ZERO_VALUE);
		Double max = new Double(Constants.ZERO_VALUE);
		Double min = new Double(Constants.MAX_VALUE);
		Calendar statisticTime = Calendar.getInstance();

		for (Transaction transaction : transactions) {
			try{
				validateTime(statisticTime,transaction);
				sum = sum + transaction.getAmount();
				
				if(max < transaction.getAmount()){
					max = transaction.getAmount();
				}
				
				if(min > transaction.getAmount()){
					min = transaction.getAmount();
				}
				
				validTransactions.add(transaction);
				
			} catch (ExceededTimeException e) {
				//Transaction was not made in the last 60 seconds
			}
		}
		
		statistic.setCount(validTransactions.size());
		statistic.setAvg(((Double) sum)/statistic.getCount());
		statistic.setSum(sum);
		statistic.setMax(max);
		
		if(validTransactions.size() == 0){
			statistic.setMin(new Double(Constants.ZERO_VALUE));
		} else {
			statistic.setMin(min);
		}
		
		return statistic;
	
	}
}
