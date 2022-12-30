package shoppingMall;

public class BankMemberDTO {
	
	// BankMember 테이블의 필드 생성
	private String bId;
	private String bPw;
	private String bName;
	private String bBirth;
	private String bEmail;
	private String bPhone;
	
	
	// BankMember 테이블의 getter, setter 생성
	public String getbId() {
		return bId;
	}
	public void setbId(String bId) {
		this.bId = bId;
	}
	public String getbPw() {
		return bPw;
	}
	public void setbPw(String bPw) {
		this.bPw = bPw;
	}
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	public String getbBirth() {
		return bBirth;
	}
	public void setbBirth(String bBirth) {
		this.bBirth = bBirth;
	}
	public String getbEmail() {
		return bEmail;
	}
	public void setbEmail(String bEmail) {
		this.bEmail = bEmail;
	}
	public String getbPhone() {
		return bPhone;
	}
	public void setbPhone(String bPhone) {
		this.bPhone = bPhone;
	}
	
	@Override
	public String toString() {
		return "BankMemberDTO [bId=" + bId + ", bPw=" + bPw + ", bName=" + bName + ", bBirth=" + bBirth + ", bEmail="
				+ bEmail + ", bPhone=" + bPhone + "]";
	}
	
	
	
	
	
	
	
}
