package com.other.mcTesty.entities;

public class APILogEntity {
	private String transactionId;
    private long totalTransTime;
    private String env;
    private String exceptionDetails;

    public APILogEntity() {
      // NOTE empty constructor
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public long getTotalTransTime() {
        return totalTransTime;
    }

    public void setTotalTransTime(long totalTransTime) {
        this.totalTransTime = totalTransTime;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getExceptionDetails() {
        return exceptionDetails;
    }

    public void setExceptionDetails(String exceptionDetails) {
        this.exceptionDetails = exceptionDetails;
    }
}
