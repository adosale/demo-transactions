package com.demo.transactions.manager;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.demo.transactions.model.Transaction;

public class TransactionManager {
	
	private ConcurrentHashMap<Integer, Transaction> transactions =
			new  ConcurrentHashMap<Integer, Transaction>();

	public TransactionManager() {
		super();
	}

	public Transaction getTransaction(Integer id){
		return transactions.get(id);
	}
	
	public void putTransaction(Integer id, Transaction transaction){
		transactions.put(id, transaction);
	}
	
	public List<Transaction> getTransactions(){
		return (List<Transaction>) transactions.values();
	}
	
}
