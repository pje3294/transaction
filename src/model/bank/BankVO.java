package model.bank;

public class BankVO {
	private int bid;
	private String name;
	private int balance;
	
	public int getBid() {
		return bid;
	}
	public String getName() {
		return name;
	}
	public int getBalance() {
		return balance;
	}
	
	public void setBid(int bid) {
		this.bid = bid;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "BankVO [bid=" + bid + ", name=" + name + ", balance=" + balance + "]";
	}
	
	
	
}
