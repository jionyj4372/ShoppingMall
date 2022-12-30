package shoppingMall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ShoppingMallSQL {

	// DB접속을 위한 변수 con 선언
	Connection con = null;

	// 쿼리문 전송을 위한 변수 stmt, pstmt 선언
	Statement stmt = null;
	PreparedStatement pstmt = null;

	// 조회(select) 결과를 저장하기 위한 변수 rs선언
	ResultSet rs = null;

	// 입력을 받아주는 객체
	Scanner sc = new Scanner(System.in);

	// DB접속 메소드
	public void connect() {
		// DBConnection 클래스의 DBConnect() 메소드의 return값
		con = DBConnection.DBConnect();
	}

	// DB접속해제 메소드
	public void conClose() {
		try {
			// con이라는 접속객체가 접속한 경우
			con.close();
//				System.out.println("DB접속 해제");
		} catch (SQLException e) {
			// con이라는 접속객체가 접속하지 않은 경우
			e.printStackTrace();
		}
	}

	// 회원가입 메소드
	public void memberJoin(MemberDTO member) {

		String sql = "INSERT INTO MEMBERDTO(MID,MPW,MNAME,MBIRTH,MEMAIL" + ",MGENDER,MPHONE,MADDRESS,MPOINT)"
				+ " VALUES(?,?,?,?,?,?,?,?,5000)";

		try {
			pstmt = con.prepareStatement(sql);
			// sql문에 '?'가 없어도 pstmt 사용 가능

			pstmt.setString(1, member.getmId());
			// id값에 icia1111 이라는 값을 입력핼을 경우
			// 1번째 물음표(?)에 'icia1111' 이라는 값이 들어간다
			// 즉, 1번째 물음표(?)에 'member.getmId()'가 들어가는 것

			pstmt.setString(2, member.getmPw());
			pstmt.setString(3, member.getmName());
			pstmt.setString(4, member.getmBirth());
			pstmt.setString(5, member.getmEmail());
			pstmt.setString(6, member.getmGender());
			pstmt.setString(7, member.getmPhone());
			pstmt.setString(8, member.getmAddress());

			// int result는 결과 확인을 위한 변수, 실행과 전혀 관계x
			// if문과 result변수를 사용하지 않아도 상관 없음
			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("회원가입 성공");
			} else {
				System.out.println("회원가입 실패");
			}

		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("회원가입 실패");
		}

	}

	// MEMBERDTO 테이블의 아이디 중복 체크 메소드
	public String idOverlapCheck(String mId) {

		String sql = "SELECT MID FROM MEMBERDTO WHERE MID=?";

		// Q. stmt, pstmt 중에 무엇을 사용?
		// A. pstmt 사용 : cuz, '?'가 있기 때문

		String checkResult = null;
		// try문이 작동하지 않을 경우 checkResult에 아무런 값도 들어가지 않기 때문에
		// 초기값인 false를 대입한다.

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, mId);

			// rs는 SELECT문과 짝꿍 => executeQuery()사용
			rs = pstmt.executeQuery();
			// INSERT, UPDATE, DELETE는 int result와 짝꿍 => executeUpdate()사용

			// rs.next() : 데이터 존재할 때 true, 존재하지 않으면 false
			if (rs.next()) {
				checkResult = mId;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return checkResult;
	}
	
	
	// MEMBERDTO 테이블의 아이디, 비밀번호 체크 메소드
	public int idCheck(String mId, String mPw) {

		String sql = "SELECT MID FROM MEMBERDTO WHERE MID=? AND MPW=?";

		// Q. stmt, pstmt 중에 무엇을 사용?
		// A. pstmt 사용 : cuz, '?'가 있기 때문

		int checkResult = 0; // id, pw가 관리자 정보와 같다면 2, 아니라면 1을 저장하는 변수
		String part = "admin";

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, mId);
			pstmt.setString(2, mPw);

			rs = pstmt.executeQuery();
			
			// rs.next() : 데이터 존재할 때 true, 존재하지 않으면 false
			while (rs.next()) {
				if (part.equals(rs.getString(1))) {
					checkResult = 2;
				} else if (mId.equals(rs.getString(1))) {
					checkResult = 1;
				} else {
					checkResult = 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return checkResult;
	}
	
	// 회원 구매 항목에 대한 목록 출력 메소드
	public void showList(String cType) {
		String sql = "SELECT CNAME,CPRICE,CCOUNT,CSTORE,CSIZE,CCOLOR FROM CLOTHES WHERE CTYPE=?";

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, cType);

			rs = pstmt.executeQuery();

			int i=1;
			while (rs.next()) {
				// CLOTHES 테이블에서 타입이 상의인 레코드들을 출력
				System.out.println(i
				+ ".상품 이름 : " + rs.getString(1) 
				+ ",상품 가격 : " + rs.getString(2) 
				+ ",남은 물량 : " + rs.getInt(3) 
				+ ",상품 판매처 : "+ rs.getString(4) 
				+ ",상품 사이즈 : " + rs.getString(5) 
				+ ",상품 색상 : " + rs.getString(6));
				i++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 상품 카테고리의 레코드 갯수를 구하는 메소드
	public int rowCount(String cType) {

		int cnt = 0;

		String sql = "SELECT COUNT(*) FROM CLOTHES WHERE CTYPE=?";

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, cType);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cnt;
	}

	// 주문 테이블의 레코드 갯수를 구하는 메소드
	public int ordersRowCount() {

		int cnt = 0;

		String sql = "SELECT COUNT(*) FROM ORDERS";

		try {
			stmt = con.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				cnt = rs.getInt(1) + 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cnt;
	}
	

	// 선택한 상품의 정보와 수량 출력 메소드
	public void resultOutput(String productName, String color, String size, int cnt) {

		String sql = "SELECT DISTINCT CNAME,CSTORE,CCOLOR,CSIZE " 
				+ "FROM CLOTHES WHERE CNAME=? AND CCOLOR=? "
				+ "AND CSIZE=?";

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, productName);
			pstmt.setString(2, color);
			pstmt.setString(3, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println("상품 이름 : " + rs.getString(1) + " ,판매처 : " + rs.getString(2) + " ,색상 : "
						+ rs.getString(3) + " ,사이즈 : " + rs.getString(4) + " ,갯수 : " + cnt);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	// 입력한 값을 orders테이블에 저장하는 메소드
	public void orderAdd(OrdersDTO order) {
		
		String sql = "SELECT CID FROM CLOTHES WHERE CNAME=? AND CSIZE=? AND CCOLOR=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, order.getOcName());
			pstmt.setString(2, order.getoSize());
			pstmt.setString(3, order.getoColor());
	
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				int cId = rs.getInt(1);
				
				sql = "INSERT INTO ORDERS VALUES(O_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?)";
	//			(O_ID,ONAME,OCNAME,OPRICE,OCOUNT,OSIZE,OCOLOR,MID,CID)"
				pstmt = con.prepareStatement(sql);
			
				// sql문에 '?'가 없어도 pstmt 사용 가능
				pstmt.setString(1, order.getoName());
				pstmt.setString(2, order.getOcName());
				pstmt.setInt(3, order.getoPrice());
				pstmt.setInt(4, order.getoCount());
				pstmt.setString(5, order.getoSize());
				pstmt.setString(6, order.getoColor());
				pstmt.setString(7, order.getmId());
				pstmt.setString(8, order.getoAccount());
				pstmt.setInt(9, cId);
				
				// int result는 결과 확인을 위한 변수, 실행과 전혀 관계x
				// if문과 result변수를 사용하지 않아도 상관 없음
				int result = pstmt.executeUpdate();
				
				if(result > 0) {
					System.out.println("주문 완료");
				} else {
					System.out.println("주문 실패");
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}
	
	// 옷의 데이터값을 들고오는 메소드
	public int callValue(String colName, String cName, String cSize, String cColor) {

		String sql = "SELECT " + colName + " FROM CLOTHES WHERE CNAME=? AND CSIZE=? AND CCOLOR=?";

		int price = 0;

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, cName);
			pstmt.setString(2, cSize);
			pstmt.setString(3, cColor);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				price = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return price;
	}
	
	// 상품 물량-주문 물량 메소드
	public boolean cCountCheck(String ocName, String oSize, String oColor, int oCount) {
		
		String sql = "SELECT CCOUNT FROM CLOTHES WHERE CNAME=? AND"
				+ " CSIZE=? AND CCOLOR=?";
		
		int cnt = 0;
		boolean trueFalse = true;
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, ocName);
			pstmt.setString(2, oSize);
			pstmt.setString(3, oColor);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cnt = rs.getInt(1);
				cnt -= oCount; 
				if(cnt<0) {
					trueFalse = false;
					System.out.println("해당 상품이 품절되었습니다");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return trueFalse;
	}
	
	// admin 계정으로 CLOTHES 테이블에 레코드 추가하는 메소드
	public void clothesAdd(ClothesDTO clothes, String cType) {
		
		String sql = "INSERT INTO CLOTHES VALUES(C_SEQ.NEXTVAL,?,?,?,?,?,?,?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, clothes.getcName());
			pstmt.setString(2, cType);
			pstmt.setInt(3, clothes.getcPrice());
			pstmt.setString(4, clothes.getcStore());
			pstmt.setInt(5, clothes.getcCount());
			pstmt.setString(6, clothes.getcSize());
			pstmt.setString(7, clothes.getcColor());
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("상품 추가 성공");
			} else {
				System.out.println("상품 추가 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// 현재 로그인한 회원의 이름을 반환해주는 메소드
	public String getmName(String mId) {
		
		String sql = "SELECT MNAME FROM MEMBERDTO WHERE MID='"+mId+"'";
		String mName = null;
		
		
		try {
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				mName = rs.getString(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mName;
	}
	
	// 로그인한 회원의 주문 목록을 보여주는 메소드
	public void ordersOutput(String mId) {
		
		String sql = "SELECT ONAME,OCNAME,OPRICE,OCOUNT,OSIZE,OCOLOR "
				+ "FROM ORDERS WHERE MID='"+mId+"'";
		
		
		try {
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			int i=1;
			while(rs.next()) {
				System.out.println(i+".주문자 이름 : "+rs.getString(1)
					+ " ,상품 이름 : "+rs.getString(2)
					+ " ,가격 : "+rs.getString(3)
					+ " ,주문 수량 : "+rs.getString(4)
					+ " ,사이즈 : "+rs.getString(5)
					+ " ,색상 : "+rs.getString(6));
				i++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// admin 계정으로 CLOTHES 테이블의 상품 목록을 삭제하는 메소드
	public void clothesDel(ClothesDTO clothes, String cType) {
		
		String sql = "DELETE FROM CLOTHES WHERE "
			+ "CNAME=? AND CSTORE=? AND CSIZE=? AND CCOLOR=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, clothes.getcName());
			pstmt.setString(2, clothes.getcStore());
			pstmt.setString(3, clothes.getcSize());
			pstmt.setString(4, clothes.getcColor());
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("상품 삭제 성공");
			} else {
				System.out.println("입력한 값에 해당되는 상품이 존재하지 않습니다");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// admin 계정으로 ORDERS테이블의 주문 목록을 출력하는 메소드
	public void showOrdersList() {
		
		// 주문 목록 출력
		String sql = "SELECT O_ID,ONAME,o.MID,m.MPHONE,m.MADDRESS,"
				+ "o.CID,OCNAME,OPRICE,OCOUNT,OSIZE,OCOLOR "
				+ "FROM ORDERS o,MEMBERDTO m WHERE o.MID=m.MID";
		
		try {
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println("주문번호 : "+rs.getString(1)
					+ ", 주문자 이름 : "+rs.getString(2)
					+ ", 주문자 아이디 : "+rs.getString(3)
					+ ", 주문자 연락처 : "+rs.getString(4)
					+ ", 주문자 주소 : "+rs.getString(5)
					+ ", 상품 번호 : "+rs.getString(6)
					+ ", 상품 이름 : "+rs.getString(7)
					+ ", 가격 : "+rs.getString(8)
					+ ", 물량 : "+rs.getString(9)
					+ ", 사이즈 : "+rs.getString(10)
					+ ", 색상 : "+rs.getString(11)
					);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// 주문번호를 받아 해당하는 주문 목록을 삭제하는 메소드
	public void ordersComplete(int menu) {
		
		String sql = "DELETE FROM ORDERS WHERE O_ID='"+menu+"'";
		
		try {
			stmt = con.createStatement();
			
			int result = stmt.executeUpdate(sql);
			
			if(result > 0) {
				System.out.println("배송 완료");
			} else{
				System.out.println("주문 목록 삭제 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	// 은행 회원가입 메소드
	public void BankMemberJoin(BankMemberDTO bankMember) {
		
		String sql = "INSERT INTO BANKMEMBER(BID,BPW,BNAME,BBIRTH,BEMAIL" 
				+ ",BPHONE)"
				+ " VALUES(?,?,?,?,?,?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, bankMember.getbId());
			pstmt.setString(2, bankMember.getbPw());
			pstmt.setString(3, bankMember.getbName());
			pstmt.setString(4, bankMember.getbBirth());
			pstmt.setString(5, bankMember.getbEmail());
			pstmt.setString(6, bankMember.getbPhone());
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("회원가입 성공");
			} else {
				System.out.println("회원가입 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 은행 아이디 일치 여부 메소드
	public boolean idCheck2(String bId, String bPw) {
		
		String sql = "SELECT BID FROM BANKMEMBER WHERE BID=? AND BPW=?";
		
		boolean checkResult = false;

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, bId);
			pstmt.setString(2, bPw);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				checkResult = true;
			} else {
				System.out.println("회원 정보가 없습니다");
				checkResult = false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return checkResult;
	}
	
	// 은행 회원가입 생성시 아이디 중복 여부 확인 메소드
	public String idOverlapCheck2(String bId) {

		String sql = "SELECT BID FROM BANKMEMBER WHERE BID=?";

		// Q. stmt, pstmt 중에 무엇을 사용?
		// A. pstmt 사용 : cuz, '?'가 있기 때문

		String checkResult = null;
		// try문이 작동하지 않을 경우 checkResult에 아무런 값도 들어가지 않기 때문에
		// 초기값인 false를 대입한다.

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, bId);

			// rs는 SELECT문과 짝꿍 => executeQuery()사용
			rs = pstmt.executeQuery();
			// INSERT, UPDATE, DELETE는 int result와 짝꿍 => executeUpdate()사용

			// rs.next() : 데이터 존재할 때 true, 존재하지 않으면 false
			if (rs.next()) {
				checkResult = bId;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return checkResult;
	}
	
	// 계좌 갯수 세기 메소드
	public int AccountCount(String mId) {
		int count = 0;

		String sql = "SELECT COUNT(*) FROM BANK WHERE MID=?";

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, mId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = Integer.parseInt(rs.getString(1));
			} else {
				System.out.println("");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}
	
	// 계좌번호 생성 메소드
	public String accountNumber() {

		// 카카오뱅크 계좌 생성 규칙 : 3333-xx-xxxxxx
		String aNumber = "3333-";

		for (int i = 1; i <= 2; i++) {
			aNumber += (int) (Math.random() * 9);
		}

		aNumber += "-";

		for (int i = 1; i <= 6; i++) {
			aNumber += (int) (Math.random() * 9);
		}

		String sql = "SELECT ACCOUNT FROM BANK";

		boolean checkAccount = false;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				if (aNumber.equals(rs.getString(1))) {
					checkAccount = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (checkAccount) {
			accountNumber();
		} else {
			System.out.println("새로 생성된 계좌번호 : " + aNumber);
		}

		return aNumber;
	}
	
	// 계좌생성 메소드
	public void createAccount(BankDTO bank) {

		String sql = "INSERT INTO BANK VALUES(?,?,?)";

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, bank.getAccount());
			pstmt.setInt(2, bank.getBalance());
			pstmt.setString(3, bank.getb_mId());

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("계좌생성 성공");
			} else {
				System.out.println("계좌생성 실패");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	// 매개변수로 들어온 아이디의 생성된 계좌를 보여주는 메소드
	public String[] showAccount(String bId) {

		int cnt = AccountCount(bId);

		String sql = "SELECT ACCOUNT, BALANCE FROM BANK WHERE MID=?";

		// 계좌를 담는 배열 myAccount[] 생성
		String myAccount[] = new String[cnt];

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, bId);

			rs = pstmt.executeQuery();

			int i = 0;
			while (rs.next()) {
				String account = rs.getString(1);
				int balance = rs.getInt(2);
				myAccount[i] = account;
				System.out.println("[" + (i + 1) + "] " + account + " : 잔액 " + balance + "원");
				i++;
			}
			System.out.println("["+(i+1)+"]"+" 뒤로가기");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return myAccount;
	}
	
	// 로그인된 계좌의 계좌번호를 반환해주는 메소드
	public String accountCheck(String accountVar) {
		
		String sql = "SELECT ACCOUNT FROM BANK WHERE ACCOUNT='"+accountVar+"'";
		
		String result = null;
		
		try {
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				result = rs.getString(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	// 입금 메소드
	public void deposit(BankDTO bank) {
		// (1) sql문 작성
		String sql = "UPDATE BANK SET BALANCE = BALANCE+? WHERE ACCOUNT=?";

		// (2) stmt, pstmt 선언 후 try/catch문
		try {
			pstmt = con.prepareStatement(sql);

			// (3) pstmt로 선언했을 경우 ?에 데이터 삽입
			pstmt.setInt(1, bank.getBalance());
			pstmt.setString(2, bank.getAccount());

			// (4) 실행
			int result = pstmt.executeUpdate();

			// (5) 실행결과
			if (result > 0) {
				System.out.println("입금 성공!");
			} else {
				System.out.println("입금 실패!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	// 잔액조회 메소드
	public int checkBalance(String account) {
		String sql = "SELECT BALANCE FROM BANK WHERE ACCOUNT=?";
		int balance = 0;
		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, account);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				balance = rs.getInt(1);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;

	}
	
	// 출금 메소드
	public void withdraw(BankDTO bank) {
		// (1) sql문 작성
		String sql = "UPDATE BANK SET BALANCE = BALANCE-? WHERE ACCOUNT=?";

		// (2) stmt, pstmt 선언 후 try/catch문
		try {
			pstmt = con.prepareStatement(sql);

			// (3) pstmt로 선언했을 경우 ?에 데이터 삽입
			pstmt.setInt(1, bank.getBalance());
			pstmt.setString(2, bank.getAccount());

			// (4) 실행
			int result = pstmt.executeUpdate();

			// (5) 실행결과
			if (result > 0) {
				System.out.println("출금 성공!");
			} else {
				System.out.println("출금 실패!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 송금 메소드
	public void sendMoney(String account, String account1, int sBalance) {
		String sql1 = "UPDATE BANK SET BALANCE=BALANCE-? WHERE ACCOUNT=?";
		String sql2 = "UPDATE BANK SET BALANCE=BALANCE+? WHERE ACCOUNT=?";

		try {
			pstmt = con.prepareStatement(sql1);
			pstmt.setInt(1, sBalance);
			pstmt.setString(2, account);

			int result1 = pstmt.executeUpdate();

			pstmt = con.prepareStatement(sql2);
			pstmt.setInt(1, sBalance);
			pstmt.setString(2, account1);

			int result2 = pstmt.executeUpdate();

			if (result1 > 0 && result2 > 0) {
				System.out.println("송금 성공!");
			} else {
				System.out.println("송금 실패!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// 계좌 존재 유무 확인 메소드
	public boolean checkAccount(String getAccount) {

		boolean check1 = false;

		String sql = "SELECT ACCOUNT FROM BANK WHERE ACCOUNT=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, getAccount);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				check1 = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return check1;
	}
	
	// 주문취소시 취소된 주문목록을 삭제하는 메소드
	public void ordersCancle(int ordersNum) {
		
		String sql = "SELECT OCOUNT,CID,OPRICE,OACCOUNT FROM ORDERS WHERE O_ID="+ordersNum+"";
		String sql3 = "UPDATE CLOTHES SET CCOUNT=CCOUNT+? WHERE CID=?";
		String sql4 = "DELETE FROM ORDERS WHERE O_ID="+ordersNum;
		
		
		int oCount = 0 ;
		int cId = 0;
		int price = 0;
		String oAccount = null;
		
		try {
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				oCount = rs.getInt(1);
				cId = rs.getInt(2);
				price = rs.getInt(3);
				oAccount = rs.getString(4);
			}
			
			String sql2 = "UPDATE BANK SET BALANCE=BALANCE+"+price+" WHERE ACCOUNT='"+oAccount+"'";
			
			stmt = con.createStatement();
			
			int result1 = stmt.executeUpdate(sql2);
			
			
			pstmt = con.prepareStatement(sql3);
			
			pstmt.setInt(1, oCount);
			pstmt.setInt(2, cId);
			
			int result2 = pstmt.executeUpdate();
			
			
			stmt = con.createStatement();
			
			int result3 = stmt.executeUpdate(sql4);
			
			if(result1>0 && result2>0 && result3>0) {
				System.out.println("주문 취소목록 삭제 성공");
			} else {
				System.out.println("주문 취소 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	// 주문 시 CLOTHES 테이블에서 상품 갯수를 계산하는 메소드
	public boolean cCountMinus(String ocName, String oSize, String oColor, int oCount) {
		
		String sql = "SELECT CCOUNT FROM CLOTHES WHERE CNAME=? AND"
				+ " CSIZE=? AND CCOLOR=?";
		
		boolean check = true;
		int cnt = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, ocName);
			pstmt.setString(2, oSize);
			pstmt.setString(3, oColor);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cnt = rs.getInt(1);
				cnt -= oCount; 
				if(cnt>=0) {
					
					sql = "UPDATE CLOTHES SET CCOUNT=" + cnt 
							+" WHERE CNAME='"+ocName
							+"' AND CSIZE='" + oSize
							+ "' AND CCOLOR='"+oColor+"'";
					
					stmt = con.createStatement();
					
					int result = stmt.executeUpdate(sql);
				}else {
					check = false;
					System.out.println("결제 실패");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return check;
	}
	
	// 최종적으로 BANK테이블에서 해당하는 계좌의 잔고를 빼는 메소드
	public void pay(BankDTO bank, int price) {
		
		String sql = "SELECT BALANCE FROM BANK WHERE ACCOUNT=?";
		String sql2 = "UPDATE BANK SET BALANCE = BALANCE-? WHERE ACCOUNT=?";
		
		int balance = 0;
		
		try {
			
			// sql
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bank.getAccount());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				balance = rs.getInt(1);
			}
			
			// sql2
			pstmt = con.prepareStatement(sql2);
			pstmt.setInt(1, price);
			pstmt.setString(2, bank.getAccount());

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("결제 성공");
			} else {
				System.out.println("결제 실패");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// 결제하려는 옷의 가격을 반환해주는 메소드
	public int getPrice(String cName,String cSize, String cColor, int oCount) {
		
		String sql = "SELECT CPRICE FROM CLOTHES WHERE CNAME=? "
				+ "AND CSIZE=? AND CCOLOR=?";
		int price = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, cName);
			pstmt.setString(2, cSize);
			pstmt.setString(3, cColor);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				price = (rs.getInt(1)*oCount);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return price;
	}
	
	// 결제시스템에서 입력한 계좌번호의 잔고를 반환해주는 메소드
	public int getBalnce(String bAccount) {
		
		int balance = 0;
		
		String sql = "SELECT BALANCE FROM BANK WHERE ACCOUNT='"+bAccount+"'";
		
		try {
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				balance = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}
	
	// 결제할때 은행에 로그인한 아이디의 계좌번호와 결제할 계좌번호를 비교하는 메소드
	public boolean checkMyAccount(String mId,String bAccount) {
		
		boolean checkMyAccount = false;
		
		String sql = "SELECT ACCOUNT FROM BANK WHERE MID='"+mId+"'";
		
		try {
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				if(bAccount.equals(rs.getString(1))) {
					checkMyAccount = true;
					break;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return checkMyAccount;
	}

	
	
}