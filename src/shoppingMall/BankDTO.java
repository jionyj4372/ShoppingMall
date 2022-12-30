package shoppingMall;

public class BankDTO {
	
	// BANK 테이블의 필드 선언
	private String account;
	private int balance;
	private String b_mId;
	
	// MEMBER 테이블의 필드의 값을 전송받기위한 getter, setter 생성
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getb_mId() {
		return b_mId;
	}
	public void setb_mId(String b_mId) {
		this.b_mId = b_mId;
	}
	
	@Override
	public String toString() {
		return "BankDTO [account=" + account + ", balance=" + balance + ", b_mId=" + b_mId + "]";
	}
	
}