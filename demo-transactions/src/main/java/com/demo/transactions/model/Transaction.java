package com.demo.transactions.model;

import java.util.UUID;

public class Transaction {
	
	private UUID id;
	private Double amount;
	private long timestamp;
	
	public Transaction(){
		
	}
	
	public Transaction(UUID id, Double amount, long timestamp) {
		super();
		this.id = id;
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", amount=" + amount + ", timestamp=" + timestamp + "]";
	}
	
	
	
	
}
