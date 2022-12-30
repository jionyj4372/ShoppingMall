package shoppingMall;

public class ClothesDTO {
	
	// Clothes 테이블의 필드 선언
	private int cId;
	private String cName;
	private String cType;
	private String cdType;
	private int cPrice;
	private String cStore;
	private int cCount;
	private String cSize;
	private String cColor;
	private String c_mid;
	
	// Clothes 테이블의 필드의 값을 전송받기위한 getter, setter 생성
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String getcType() {
		return cType;
	}
	public void setcType(String cType) {
		this.cType = cType;
	}
	public String getCdType() {
		return cdType;
	}
	public void setCdType(String cdType) {
		this.cdType = cdType;
	}
	public int getcPrice() {
		return cPrice;
	}
	public void setcPrice(int cPrice) {
		this.cPrice = cPrice;
	}
	public String getcStore() {
		return cStore;
	}
	public void setcStore(String cStore) {
		this.cStore = cStore;
	}
	public int getcCount() {
		return cCount;
	}
	public void setcCount(int cCount) {
		this.cCount = cCount;
	}
	public String getcSize() {
		return cSize;
	}
	public void setcSize(String cSize) {
		this.cSize = cSize;
	}
	public String getcColor() {
		return cColor;
	}
	public void setcColor(String cColor) {
		this.cColor = cColor;
	}
	public String getC_mid() {
		return c_mid;
	}
	public void setC_mid(String c_mid) {
		this.c_mid = c_mid;
	}
	
	@Override
	public String toString() {
		return "ClothesDTO [cId=" + cId + ", cName=" + cName + ", cType=" + cType + ", cdType=" + cdType + ", cPrice="
				+ cPrice + ", cStore=" + cStore + ", cCount=" + cCount + ", cSize=" + cSize + ", cColor=" + cColor
				+ ", c_mid=" + c_mid + "]";
	}
	
}