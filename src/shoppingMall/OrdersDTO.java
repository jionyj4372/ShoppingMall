package shoppingMall;

public class OrdersDTO {
	
	// 주문 테이블의 필드 선언
	private int o_id; 
	private String oName;
	private String ocName;
	private int oPrice;
	private int oCount;
	private String oSize;
	private String oColor;
	private String mId;
	private String oAccount;
	private int cId;
	
	// 데이터 전송을 위한 getter, setter 생성
	public int getO_id() {
		return o_id;
	}
	public void setO_id(int o_id) {
		this.o_id = o_id;
	}
	public String getoName() {
		return oName;
	}
	public void setoName(String oName) {
		this.oName = oName;
	}
	public String getOcName() {
		return ocName;
	}
	public void setOcName(String ocName) {
		this.ocName = ocName;
	}
	public int getoPrice() {
		return oPrice;
	}
	public void setoPrice(int oPrice) {
		this.oPrice = oPrice;
	}
	public int getoCount() {
		return oCount;
	}
	public void setoCount(int oCount) {
		this.oCount = oCount;
	}
	public String getoSize() {
		return oSize;
	}
	public void setoSize(String oSize) {
		this.oSize = oSize;
	}
	public String getoColor() {
		return oColor;
	}
	public void setoColor(String oColor) {
		this.oColor = oColor;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getoAccount() {
		return oAccount;
	}
	public void setoAccount(String oAccount) {
		this.oAccount = oAccount;
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	
	@Override
	public String toString() {
		return "OrdersDTO [o_id=" + o_id + ", oName=" + oName + ", ocName=" + ocName + ", oPrice=" + oPrice
				+ ", oCount=" + oCount + ", oSize=" + oSize + ", oColor=" + oColor + ", mId=" + mId + ", oAccount="
				+ oAccount + ", cId=" + cId + "]";
	}
	
	
	
	
	
	
}