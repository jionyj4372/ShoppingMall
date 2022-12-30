package shoppingMall;

public class MemberDTO {
	
	// MEMBERM 테이블의 필드 선언
	private String mId;
	private String mPw;
	private String mName;
	private String mBirth;
	private String mEmail;
	private String mGender;
	private String mPhone;
	private String mAddress;
	private String mPart;
	private String mPoint;
	
	// MEMBER 테이블의 필드의 값을 전송받기위한 getter, setter 생성
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getmPw() {
		return mPw;
	}
	public void setmPw(String mPw) {
		this.mPw = mPw;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getmBirth() {
		return mBirth;
	}
	public void setmBirth(String mBirth) {
		this.mBirth = mBirth;
	}
	public String getmGender() {
		return mGender;
	}
	public void setmGender(String mGender) {
		this.mGender = mGender;
	}
	public String getmEmail() {
		return mEmail;
	}
	public void setmEmail(String mEmail) {
		this.mEmail = mEmail;
	}
	public String getmPhone() {
		return mPhone;
	}
	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}
	public String getmAddress() {
		return mAddress;
	}
	public void setmAddress(String mAddress) {
		this.mAddress = mAddress;
	}
	public String getmPart() {
		return mPart;
	}
	public void setmPart(String mPart) {
		this.mPart = mPart;
	}
	public String getmPoint() {
		return mPoint;
	}
	public void setmPoint(String mPoint) {
		this.mPoint = mPoint;
	}
	
	@Override
	public String toString() {
		return "MemberDTO [mId=" + mId + ", mPw=" + mPw + ", mName=" + mName + ", mBirth=" + mBirth + ", mGender="
				+ mGender + ", mEmail=" + mEmail + ", mPhone=" + mPhone + ", mAddress=" + mAddress + ", mPart=" + mPart
				+ ", mPoint=" + mPoint + "]";
	}
	
}