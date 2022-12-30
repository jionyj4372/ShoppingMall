package shoppingMall;

import java.util.Scanner;

public class ShoppingMallMain {

	public static void main(String[] args) {

		// MemberDTO 객체
		MemberDTO member = new MemberDTO();

		// TopDTO 객체
		ClothesDTO clothes = new ClothesDTO();

		// BankDTO 객체
		BankDTO bank = new BankDTO();

		// BankMemberDTO 객체
		BankMemberDTO bankMember = new BankMemberDTO();

		// Orders 객체
		OrdersDTO order = new OrdersDTO();

		// shoppingMallSQL 객체
		ShoppingMallSQL sql = new ShoppingMallSQL();

		// 입력을 받기 위한 sc 객체
		Scanner sc = new Scanner(System.in);

		// 처음 프로그램 실행시 DB에 접속되도록 설정
		sql.connect();

		boolean run = true; // 프로그램 구동 변수
		int menu = 0; // 프로그램 메뉴 선택 변수
		String productName = null;
		int oid = 1;

		while (run) {
			System.out.println("==================== 시스템 ==================");
			System.out.println("1.쇼핑몰\t\t 2.은행\t\t 3.시스템종료");
			System.out.println("============================================");
			System.out.print("메뉴 선택 : ");
			menu = sc.nextInt(); // 어떠한 업무를 볼것인지 선택하는 변수 초기화

			boolean work = true; // 쇼핑몰 구동 변수
			boolean bankRun = true; // 은행 구동 변수

			switch (menu) {
			// 쇼핑몰
			case 1:
				while (work) {
					System.out.println("================= 쇼핑몰 =================");
					System.out.println("1.로그인\t\t 2.회원가입\t 3.뒤로가기");
					System.out.println("========================================");
					System.out.print("메뉴 선택 : ");
					menu = sc.nextInt(); // 쇼핑몰의 메뉴를 선택하기 위한 변수 초기화

					switch (menu) {
					// 로그인
					case 1:

						System.out.println("로그인 정보");
						System.out.print("로그인할 아이디 입력 : ");
						String mId = sc.next();
						System.out.print("로그인할 패스워드 입력 : ");
						String mPw = sc.next();

						int check = sql.idCheck(mId, mPw); // id, pw 일치 여부 변수 id,pw가 맞다면 1, 틀리면 0

						// 회원 로그인시 실행할 작업
						if (check == 1) {
							System.out.println("회원 로그인 성공");

							boolean memLogin = true; // 로그인 성공시 작업할 반복문 실행에 대한 변수

							System.out.println("구매하실 의류의 항목을 선택하세요");

							while (memLogin) {

								System.out.println("========================================================");
								System.out.println("1.상의\t 2.하의\t 3.신발\t 4.모자\t 5.주문목록\t 6.로그아웃");
								System.out.println("========================================================");
								System.out.print("구매할 카테고리 선택 : ");
								menu = sc.nextInt(); // 구매할 제품 목록을 선택하기 위한 변수 초기화

								switch (menu) {
								case 1:
									String cType = "상의";

									boolean buyOrExit = true;
									// 상품 목록을 본 후 구매 or 뒤로가기
									while (buyOrExit) {

										sql.showList(cType);

										System.out.println("메뉴를 선택하세요 :\t 1.구매\t 2.뒤로가기");
										menu = sc.nextInt();

										switch (menu) {
										// 구매 케이스
										case 1:

											System.out.println("구매를 원하시는 상품의 정보를 입력하세요");

											String mName = sql.getmName(mId); // 현재 로그인한 아이디를 저장하는 변수
											System.out.print("상품 이름 : ");
											sc.nextLine();
											String ocName = sc.nextLine();
											System.out.print("주문 수량 : ");
											int oCount = sc.nextInt();
											System.out.print("사이즈 : ");
											String oSize = sc.next();
											oSize = oSize.toUpperCase();
											System.out.print("색상 : ");
											String oColor = sc.next();
											oColor = oColor.toUpperCase();

											// 구매자가 입력한 값에 따라 해당 상품의 정보를 출력해주는 메소드 구현
											System.out.println("주문자 이름 : " + mName + ", 상품 이름 : " + ocName
													+ ", 주문 수량 : " + oCount + ", 사이즈 : " + oSize + ", 색상 : " + oColor);

											String yesNo = "n";
											System.out.println("입력하신 정보가 일치합니까?(y/n)");
											yesNo = sc.next();
											yesNo.toLowerCase();

											boolean trueFalse = sql.cCountCheck(ocName, oSize, oColor, oCount);

											if (yesNo.equals("y") && trueFalse) {

												boolean payOrCancle = true;

												while (payOrCancle) {
													// 결제 시스템
													System.out.println("========은행 결제 대행 서비스 입니다========");
													System.out.println("\t1.결제\t\t 2.취소");
													System.out.println("====================================");
													menu = sc.nextInt();

													switch (menu) {
													// 결제
													case 1:

														int price = 0;
														// 결제할 옷의 금액
														price = sql.getPrice(ocName, oSize, oColor, oCount);

														if (price > 0) {
															System.out.println("결제하실 금액 : " + price);

															System.out.println("xx은행의 회원정보로 로그인 하세요");
															System.out.print("아이디 입력 : ");
															String bId = sc.next(); // xx은행의 xx회원의 아이디
															System.out.print("패스워드 입력 : ");
															String bPw = sc.next();

															int cnt = sql.AccountCount(bId);

//															String[] payAccount = new String[cnt];
//															payAccount = sql.showAccount(bId);
//															String payAccountVar;
															
															if (sql.idCheck2(bId, bPw)) {

																sql.showAccount(bId);
																
																System.out.print("결제를 진행하실 계좌번호를 입력하세요 : ");
																String bAccount = sc.next();

																boolean checkMyAccount = sql.checkMyAccount(bId,
																		bAccount);

																// 계좌번호가 존재시 실행
																if (checkMyAccount) {

																	int AccountBalance = sql.getBalnce(bAccount);

																	// 입력한 계좌번호의 잔고가 상품가격보다 많이 있을때만 결제
																	if (AccountBalance >= price) {

																		// 입력받은 정보를 order 객체에 저장
																		order.setoName(mName);
																		order.setOcName(ocName);
																		order.setoPrice(price);
																		order.setoCount(oCount);
																		order.setoSize(oSize);
																		order.setoColor(oColor);
																		order.setmId(mId);
																		order.setoAccount(bAccount);
																		order.setcId(0);

																		bank.setAccount(bAccount);
																		// CLOTHES 테이블의 상품에 있는 물량을 주문 수 만큼 빼주는 메소드
																		sql.cCountMinus(ocName, oSize, oColor, oCount);
																		// 데이터를 DTO로 담아서 shoppingMallSQl 클래스로 전송
																		sql.orderAdd(order);
																		// 최종적으로 BANK테이블에서 해당하는 계좌의 잔고를 빼는 메소드
																		sql.pay(bank, price);
																	} else {
																		System.out.println("계좌잔액이 부족합니다");
																	}
																	payOrCancle = false;
																	buyOrExit = false;
																} else {
																	System.out.println("회원정보가 없습니다.");
																}
															}
														} else {
															System.out.println("해당하는 상품정보가 없습니다.");
															payOrCancle = false;
														}
														break;
													case 2:
														payOrCancle = false;
														System.out.println("결제를 취소합니다.");
														break;
													default:
														System.out.println("잘못 입력하셨습니다.");
														break;
													}
												}
											} else {
												System.out.println("구매 실패");
											}
											break;
										// 뒤로가기 케이스
										case 2:
											buyOrExit = false;
											System.out.println("한 단계 상위 메뉴로 이동합니다.");
											break;
										default:
											System.out.println("잘못 누르셨습니다. 1번 또는 2번을 선택하여주십시오.");
											break;
										}
									}
									break;
								case 2:
									cType = "하의";

									buyOrExit = true;
									// 상품 목록을 본 후 구매 or 뒤로가기
									while (buyOrExit) {

										sql.showList(cType);

										System.out.println("메뉴를 선택하세요 :\t 1.구매\t 2.뒤로가기");
										menu = sc.nextInt();

										switch (menu) {
										// 구매 케이스
										case 1:

											System.out.println("구매를 원하시는 상품의 정보를 입력하세요");

											String mName = sql.getmName(mId); // 현재 로그인한 아이디를 저장하는 변수
											System.out.print("상품 이름 : ");
											sc.nextLine();
											String ocName = sc.nextLine();
											System.out.print("주문 수량 : ");
											int oCount = sc.nextInt();
											System.out.print("사이즈 : ");
											String oSize = sc.next();
											oSize = oSize.toUpperCase();
											System.out.print("색상 : ");
											String oColor = sc.next();
											oColor = oColor.toUpperCase();

											// 구매자가 입력한 값에 따라 해당 상품의 정보를 출력해주는 메소드 구현
											System.out.println("주문자 이름 : " + mName + ", 상품 이름 : " + ocName
													+ ", 주문 수량 : " + oCount + ", 사이즈 : " + oSize + ", 색상 : " + oColor);

											String yesNo = "n";
											System.out.println("입력하신 정보가 일치합니까?(y/n)");
											yesNo = sc.next();
											yesNo.toLowerCase();

											boolean trueFalse = sql.cCountCheck(ocName, oSize, oColor, oCount);

											if (yesNo.equals("y") && trueFalse) {

												boolean payOrCancle = true;

												while (payOrCancle) {
													// 결제 시스템
													System.out.println("========은행 결제 대행 서비스 입니다========");
													System.out.println("\t1.결제\t\t 2.취소");
													System.out.println("====================================");
													menu = sc.nextInt();

													switch (menu) {
													// 결제
													case 1:

														int price = 0;
														// 결제할 옷의 금액
														price = sql.getPrice(ocName, oSize, oColor, oCount);

														if (price > 0) {
															System.out.println("결제하실 금액 : " + price);

															System.out.println("xx은행의 회원정보로 로그인 하세요");
															System.out.print("아이디 입력 : ");
															String bId = sc.next(); // xx은행의 xx회원의 아이디
															System.out.print("패스워드 입력 : ");
															String bPw = sc.next();

															int cnt = sql.AccountCount(bId);

//															String[] payAccount = new String[cnt];
//															payAccount = sql.showAccount(bId);
//															String payAccountVar;
															
															if (sql.idCheck2(bId, bPw)) {
																
																sql.showAccount(bId);

																System.out.print("결제를 진행하실 계좌번호를 입력하세요 : ");
																String bAccount = sc.next();

																boolean checkMyAccount = sql.checkMyAccount(bId,
																		bAccount);

																// 계좌번호가 존재시 실행
																if (checkMyAccount) {

																	int AccountBalance = sql.getBalnce(bAccount);

																	// 입력한 계좌번호의 잔고가 상품가격보다 많이 있을때만 결제
																	if (AccountBalance >= price) {

																		// 입력받은 정보를 order 객체에 저장
																		order.setoName(mName);
																		order.setOcName(ocName);
																		order.setoPrice(price);
																		order.setoCount(oCount);
																		order.setoSize(oSize);
																		order.setoColor(oColor);
																		order.setmId(mId);
																		order.setoAccount(bAccount);
																		order.setcId(0);

																		bank.setAccount(bAccount);
																		// CLOTHES 테이블의 상품에 있는 물량을 주문 수 만큼 빼주는 메소드
																		sql.cCountMinus(ocName, oSize, oColor, oCount);
																		// 데이터를 DTO로 담아서 shoppingMallSQl 클래스로 전송
																		sql.orderAdd(order);
																		// 최종적으로 BANK테이블에서 해당하는 계좌의 잔고를 빼는 메소드
																		sql.pay(bank, price);
																	} else {
																		System.out.println("계좌잔액이 부족합니다");
																	}
																	payOrCancle = false;
																	buyOrExit = false;
																} else {
																	System.out.println("회원정보가 없습니다.");
																}
															}
														} else {
															System.out.println("해당하는 상품정보가 없습니다.");
															payOrCancle = false;
														}
														break;
													case 2:
														payOrCancle = false;
														System.out.println("결제를 취소합니다.");
														break;
													default:
														System.out.println("잘못 입력하셨습니다.");
														break;
													}
												}
											} else {
												System.out.println("구매 실패");
											}
											break;
										// 뒤로가기 케이스
										case 2:
											buyOrExit = false;
											System.out.println("한 단계 상위 메뉴로 이동합니다.");
											break;
										default:
											System.out.println("잘못 누르셨습니다. 1번 또는 2번을 선택하여주십시오.");
											break;
										}
									}
									break;
								// 신발 케이스
								case 3:
									cType = "신발";

									buyOrExit = true;
									// 상품 목록을 본 후 구매 or 뒤로가기
									while (buyOrExit) {

										sql.showList(cType);

										System.out.println("메뉴를 선택하세요 :\t 1.구매\t 2.뒤로가기");
										menu = sc.nextInt();

										switch (menu) {
										// 구매 케이스
										case 1:

											System.out.println("구매를 원하시는 상품의 정보를 입력하세요");

											String mName = sql.getmName(mId); // 현재 로그인한 아이디를 저장하는 변수
											System.out.print("상품 이름 : ");
											sc.nextLine();
											String ocName = sc.nextLine();
											System.out.print("주문 수량 : ");
											int oCount = sc.nextInt();
											System.out.print("사이즈(ex.240,250...) : ");
											String oSize = sc.next();
											System.out.print("색상 : ");
											String oColor = sc.next();
											oColor = oColor.toUpperCase();

											// 구매자가 입력한 값에 따라 해당 상품의 정보를 출력해주는 메소드 구현
											System.out.println("주문자 이름 : " + mName + ", 상품 이름 : " + ocName
													+ ", 주문 수량 : " + oCount + ", 사이즈 : " + oSize + ", 색상 : " + oColor);

											String yesNo = "n";
											System.out.println("입력하신 정보가 일치합니까?(y/n)");
											yesNo = sc.next();
											yesNo.toLowerCase();

											boolean trueFalse = sql.cCountCheck(ocName, oSize, oColor, oCount);

											if (yesNo.equals("y") && trueFalse) {

												boolean payOrCancle = true;

												while (payOrCancle) {
													// 결제 시스템
													System.out.println("========은행 결제 대행 서비스 입니다========");
													System.out.println("\t1.결제\t\t 2.취소");
													System.out.println("====================================");
													menu = sc.nextInt();

													switch (menu) {
													// 결제
													case 1:

														int price = 0;
														// 결제할 옷의 금액
														price = sql.getPrice(ocName, oSize, oColor, oCount);

														if (price > 0) {
															System.out.println("결제하실 금액 : " + price);

															System.out.println("xx은행의 회원정보로 로그인 하세요");
															System.out.print("아이디 입력 : ");
															String bId = sc.next(); // xx은행의 xx회원의 아이디
															System.out.print("패스워드 입력 : ");
															String bPw = sc.next();

															int cnt = sql.AccountCount(bId);

//															String[] payAccount = new String[cnt];
//															payAccount = sql.showAccount(bId);
//															String payAccountVar;

															if (sql.idCheck2(bId, bPw)) {
																
																sql.showAccount(bId);

																System.out.print("결제를 진행하실 계좌번호를 입력하세요 : ");
																String bAccount = sc.next();

																boolean checkMyAccount = sql.checkMyAccount(bId,
																		bAccount);

																// 계좌번호가 존재시 실행
																if (checkMyAccount) {

																	int AccountBalance = sql.getBalnce(bAccount);

																	// 입력한 계좌번호의 잔고가 상품가격보다 많이 있을때만 결제
																	if (AccountBalance >= price) {

																		// 입력받은 정보를 order 객체에 저장
																		order.setoName(mName);
																		order.setOcName(ocName);
																		order.setoPrice(price);
																		order.setoCount(oCount);
																		order.setoSize(oSize);
																		order.setoColor(oColor);
																		order.setmId(mId);
																		order.setoAccount(bAccount);
																		order.setcId(0);

																		bank.setAccount(bAccount);
																		// CLOTHES 테이블의 상품에 있는 물량을 주문 수 만큼 빼주는 메소드
																		sql.cCountMinus(ocName, oSize, oColor, oCount);
																		// 데이터를 DTO로 담아서 shoppingMallSQl 클래스로 전송
																		sql.orderAdd(order);
																		// 최종적으로 BANK테이블에서 해당하는 계좌의 잔고를 빼는 메소드
																		sql.pay(bank, price);
																	} else {
																		System.out.println("계좌잔액이 부족합니다");
																	}

																	payOrCancle = false;
																	buyOrExit = false;
																} else {
																	System.out.println("회원정보가 없습니다.");
																}
															}
														} else {
															System.out.println("해당하는 상품정보가 없습니다.");
															payOrCancle = false;
														}
														break;
													case 2:
														payOrCancle = false;
														System.out.println("결제를 취소합니다.");
														break;
													default:
														System.out.println("잘못 입력하셨습니다.");
														break;
													}
												}
											} else {
												System.out.println("구매 실패");
											}
											break;
										// 뒤로가기 케이스
										case 2:
											buyOrExit = false;
											System.out.println("한 단계 상위 메뉴로 이동합니다.");
											break;
										default:
											System.out.println("잘못 누르셨습니다. 1번 또는 2번을 선택하여주십시오.");
											break;
										}
									}
									break;
								case 4:
									cType = "모자";

									buyOrExit = true;
									// 상품 목록을 본 후 구매 or 뒤로가기
									while (buyOrExit) {

										sql.showList(cType);

										System.out.println("메뉴를 선택하세요 :\t 1.구매\t 2.뒤로가기");
										menu = sc.nextInt();

										switch (menu) {
										// 구매 케이스
										case 1:

											System.out.println("구매를 원하시는 상품의 정보를 입력하세요");

											String mName = sql.getmName(mId); // 현재 로그인한 아이디를 저장하는 변수
											System.out.print("상품 이름 : ");
											sc.nextLine();
											String ocName = sc.nextLine();
											System.out.print("주문 수량 : ");
											int oCount = sc.nextInt();
											System.out.print("사이즈 : ");
											String oSize = sc.next();
											oSize = oSize.toUpperCase();
											System.out.print("색상 : ");
											String oColor = sc.next();
											oColor = oColor.toUpperCase();

											// 구매자가 입력한 값에 따라 해당 상품의 정보를 출력해주는 메소드 구현
											System.out.println("주문자 이름 : " + mName + ", 상품 이름 : " + ocName
													+ ", 주문 수량 : " + oCount + ", 사이즈 : " + oSize + ", 색상 : " + oColor);

											String yesNo = "n";
											System.out.println("입력하신 정보가 일치합니까?(y/n)");
											yesNo = sc.next();
											yesNo.toLowerCase();

											boolean trueFalse = sql.cCountCheck(ocName, oSize, oColor, oCount);

											if (yesNo.equals("y") && trueFalse) {

												boolean payOrCancle = true;

												while (payOrCancle) {
													// 결제 시스템
													System.out.println("========은행 결제 대행 서비스 입니다========");
													System.out.println("\t1.결제\t\t 2.취소");
													System.out.println("====================================");
													menu = sc.nextInt();

													switch (menu) {
													// 결제
													case 1:

														int price = 0;
														// 결제할 옷의 금액
														price = sql.getPrice(ocName, oSize, oColor, oCount);

														if (price > 0) {
															System.out.println("결제하실 금액 : " + price);

															System.out.println("xx은행의 회원정보로 로그인 하세요");
															System.out.print("아이디 입력 : ");
															String bId = sc.next(); // xx은행의 xx회원의 아이디
															System.out.print("패스워드 입력 : ");
															String bPw = sc.next();

															int cnt = sql.AccountCount(bId);

//															String[] payAccount = new String[cnt];
//															payAccount = sql.showAccount(bId);
//															String payAccountVar;
															
															if (sql.idCheck2(bId, bPw)) {
																
																sql.showAccount(bId);

																System.out.print("결제를 진행하실 계좌번호를 입력하세요 : ");
																String bAccount = sc.next();

																boolean checkMyAccount = sql.checkMyAccount(bId,
																		bAccount);
																
																// 계좌번호가 존재시 실행
																if (checkMyAccount) {

																	int AccountBalance = sql.getBalnce(bAccount);

																	// 입력한 계좌번호의 잔고가 상품가격보다 많이 있을때만 결제
																	if (AccountBalance >= price) {

																		// 입력받은 정보를 order 객체에 저장
																		order.setoName(mName);
																		order.setOcName(ocName);
																		order.setoPrice(price);
																		order.setoCount(oCount);
																		order.setoSize(oSize);
																		order.setoColor(oColor);
																		order.setmId(mId);
																		order.setoAccount(bAccount);
																		order.setcId(0);

																		bank.setAccount(bAccount);
																		// CLOTHES 테이블의 상품에 있는 물량을 주문 수 만큼 빼주는 메소드
																		sql.cCountMinus(ocName, oSize, oColor, oCount);
																		// 데이터를 DTO로 담아서 shoppingMallSQl 클래스로 전송
																		sql.orderAdd(order);
																		// 최종적으로 BANK테이블에서 해당하는 계좌의 잔고를 빼는 메소드
																		sql.pay(bank, price);
																	} else {
																		System.out.println("계좌잔액이 부족합니다");
																	}
																	payOrCancle = false;
																	buyOrExit = false;
																} else {
																	System.out.println("회원정보가 없습니다.");
																}
															}
														} else {
															System.out.println("해당하는 상품정보가 없습니다.");
															payOrCancle = false;
														}
														break;
													case 2:
														payOrCancle = false;
														System.out.println("결제를 취소합니다.");
														break;
													default:
														System.out.println("잘못 입력하셨습니다.");
														break;
													}
												}
											} else {
												System.out.println("구매 실패");
											}
											break;
										// 뒤로가기 케이스
										case 2:
											buyOrExit = false;
											System.out.println("한 단계 상위 메뉴로 이동합니다.");
											break;
										default:
											System.out.println("잘못 누르셨습니다. 1번 또는 2번을 선택하여주십시오.");
											break;
										}
									}
									break;
								// 상품 주문 목록 출력 케이스
								case 5:
									System.out.println("주문하신 상품 목록입니다");
									sql.ordersOutput(mId);

									break;
								// 로그아웃 케이스
								case 6:
									memLogin = false; // 회원 로그아웃 종료 변수
									System.out.println("로그아웃 합니다. 이용해주셔서 감사합니다");
									break;
								default:
									System.out.println("잘못 입력하셨습니다.(1~5번 메뉴 중에 입력 해주십시오)");
									break;
								}

							}

							// 관리자 로그인시 실행할 작업
						} else if (check == 2) {
							System.out.println("관리자 로그인 성공");

							boolean adminLogin = true;

							System.out.println("관리하실 항목을 선택하시오.");

							while (adminLogin) {

								System.out.println(
										"===============================================================");
								System.out.println("1.상품 추가\t 2.상품 삭제\t 3.주문 관리\t 4.로그아웃");
								System.out.println(
										"===============================================================");
								System.out.print("관리할 카테고리 선택 : ");
								menu = sc.nextInt(); // 관리할 목록을 선택하기 위한 변수 초기화

								switch (menu) {
								// 상품 추가 케이스
								case 1:

									boolean addWork = true;

									while (addWork) {
										System.out.println("=============================================");
										System.out.println("1.상의\t 2.하의\t 3.신발\t 4.모자\t 5.뒤로가기");
										System.out.println("=============================================");
										System.out.print("추가하실 상품의 카테고리 선택 : ");
										menu = sc.nextInt(); // 추가할 제품 목록을 선택하기 위한 변수 초기화

										switch (menu) {
										case 1:
											String cType = "상의";

											System.out.println("추가하실 상품 정보를 입력해주세요");

											System.out.print("상품 이름 : ");
											sc.nextLine();
											String cName = sc.nextLine();
											System.out.print("상품 가격 : ");
											int cPrice = sc.nextInt();
											System.out.print("판매처 : ");
											String cStore = sc.next();
											System.out.print("추가 상품 개수 : ");
											int cCount = sc.nextInt();
											System.out.print("사이즈(ex.S,M,L) : ");
											String cSize = sc.next();
											cSize = cSize.toUpperCase();
											System.out.print("색상 : ");
											String cColor = sc.next();
											cColor = cColor.toUpperCase();

											// 입력받은 정보를 member 객체에 저장
											clothes.setcName(cName);
											clothes.setcPrice(cPrice);
											clothes.setcStore(cStore);
											clothes.setcCount(cCount);
											clothes.setcSize(cSize);
											clothes.setcColor(cColor);

											// 데이터를 DTO로 담아서 MemberSQl 클래스로 전송
											sql.clothesAdd(clothes, cType);
											break;
										case 2:
											cType = "하의";
											System.out.println("추가하실 상품 정보를 입력해주세요");

											System.out.print("상품 이름 : ");
											sc.nextLine();
											cName = sc.nextLine();
											System.out.print("상품 가격 : ");
											cPrice = sc.nextInt();
											System.out.print("판매처 : ");
											cStore = sc.next();
											System.out.print("추가 상품 개수 : ");
											cCount = sc.nextInt();
											System.out.print("사이즈(ex.S,M,L) : ");
											cSize = sc.next();
											cSize = cSize.toUpperCase();
											System.out.print("색상 : ");
											cColor = sc.next();
											cColor = cColor.toUpperCase();

											// 입력받은 정보를 member 객체에 저장
											clothes.setcName(cName);
											clothes.setcPrice(cPrice);
											clothes.setcStore(cStore);
											clothes.setcCount(cCount);
											clothes.setcSize(cSize);
											clothes.setcColor(cColor);

											// 데이터를 DTO로 담아서 MemberSQl 클래스로 전송
											sql.clothesAdd(clothes, cType);
											break;
										case 3:
											cType = "신발";
											System.out.println("추가하실 상품 정보를 입력해주세요");

											System.out.print("상품 이름 : ");
											sc.nextLine();
											cName = sc.nextLine();
											System.out.print("상품 가격 : ");
											cPrice = sc.nextInt();
											System.out.print("판매처 : ");
											cStore = sc.next();
											System.out.print("추가 상품 개수 : ");
											cCount = sc.nextInt();
											System.out.print("사이즈(ex.250,260...) : ");
											cSize = sc.next();
											System.out.print("색상 : ");
											cColor = sc.next();
											cColor = cColor.toUpperCase();

											// 입력받은 정보를 member 객체에 저장
											clothes.setcName(cName);
											clothes.setcPrice(cPrice);
											clothes.setcStore(cStore);
											clothes.setcCount(cCount);
											clothes.setcSize(cSize);
											clothes.setcColor(cColor);

											// 데이터를 DTO로 담아서 MemberSQl 클래스로 전송
											sql.clothesAdd(clothes, cType);
											break;
										case 4:
											cType = "모자";
											System.out.println("추가하실 상품 정보를 입력해주세요");

											System.out.print("상품 이름 : ");
											sc.nextLine();
											cName = sc.nextLine();
											System.out.print("상품 가격 : ");
											cPrice = sc.nextInt();
											System.out.print("판매처 : ");
											cStore = sc.next();
											System.out.print("추가 상품 개수 : ");
											cCount = sc.nextInt();
											System.out.print("사이즈(ex.S,M,L) : ");
											cSize = sc.next();
											cSize = cSize.toUpperCase();
											System.out.print("색상 : ");
											cColor = sc.next();
											cColor = cColor.toUpperCase();

											// 입력받은 정보를 member 객체에 저장
											clothes.setcName(cName);
											clothes.setcPrice(cPrice);
											clothes.setcStore(cStore);
											clothes.setcCount(cCount);
											clothes.setcSize(cSize);
											clothes.setcColor(cColor);

											// 데이터를 DTO로 담아서 MemberSQl 클래스로 전송
											sql.clothesAdd(clothes, cType);
											break;
										case 5:
											addWork = false;
											break;
										default:
											System.out.println("잘못 입력하셨습니다(1~5번 중에 입력해주십시오)");
											break;
										}
									}
									break;
								// 상품 삭제 케이스
								case 2:

									boolean deleteWork = true;

									while (deleteWork) {
										System.out.println("=================================================");
										System.out.println("1.상의\t 2.하의\t 3.신발\t 4.모자\t 5.뒤로가기");
										System.out.println("=================================================");
										System.out.print("삭제하실 상품의 카테고리 선택 : ");
										menu = sc.nextInt(); // 추가할 제품 목록을 선택하기 위한 변수 초기화

										switch (menu) {
										case 1:
											String cType = "상의";

											System.out.println("삭제하실 상품 정보를 입력해주세요");
											sql.showList(cType);

											System.out.print("상품 이름 : ");
											sc.nextLine();
											String cName = sc.nextLine();
											System.out.print("판매처 : ");
											String cStore = sc.next();
											System.out.print("사이즈 : ");
											String cSize = sc.next();
											cSize = cSize.toUpperCase();
											System.out.print("색상 : ");
											String cColor = sc.next();
											cColor = cColor.toUpperCase();

											// 입력받은 정보를 clothes 객체에 저장
											clothes.setcName(cName);
											clothes.setcStore(cStore);
											clothes.setcSize(cSize);
											clothes.setcColor(cColor);

											// 데이터를 DTO로 담아서 CLOTHES 클래스로 전송
//											sql.clothesAdd(clothes, cType);
											sql.clothesDel(clothes, cType);
											break;
										case 2:
											cType = "하의";

											System.out.println("삭제하실 상품 정보를 입력해주세요");
											sql.showList(cType);

											System.out.print("상품 이름 : ");
											sc.nextLine();
											cName = sc.nextLine();
											System.out.print("판매처 : ");
											cStore = sc.next();
											System.out.print("사이즈 : ");
											cSize = sc.next();
											cSize = cSize.toUpperCase();
											System.out.print("색상 : ");
											cColor = sc.next();
											cColor = cColor.toUpperCase();

											// 입력받은 정보를 clothes 객체에 저장
											clothes.setcName(cName);
											clothes.setcStore(cStore);
											clothes.setcSize(cSize);
											clothes.setcColor(cColor);

											// 데이터를 DTO로 담아서 CLOTHES 클래스로 전송
											sql.clothesDel(clothes, cType);
											break;
										case 3:
											cType = "신발";

											System.out.println("삭제하실 상품 정보를 입력해주세요");
											sql.showList(cType);

											System.out.print("상품 이름 : ");
											sc.nextLine();
											cName = sc.nextLine();
											System.out.print("판매처 : ");
											cStore = sc.next();
											System.out.print("사이즈 : ");
											cSize = sc.next();
											System.out.print("색상 : ");
											cColor = sc.next();
											cColor = cColor.toUpperCase();

											// 입력받은 정보를 clothes 객체에 저장
											clothes.setcName(cName);
											clothes.setcStore(cStore);
											clothes.setcSize(cSize);
											clothes.setcColor(cColor);

											// 데이터를 DTO로 담아서 CLOTHES 클래스로 전송
											sql.clothesDel(clothes, cType);
											break;
										case 4:
											cType = "모자";

											System.out.println("삭제하실 상품 정보를 입력해주세요");
											sql.showList(cType);

											System.out.print("상품 이름 : ");
											sc.nextLine();
											cName = sc.nextLine();
											System.out.print("판매처 : ");
											cStore = sc.next();
											System.out.print("사이즈 : ");
											cSize = sc.next();
											cSize = cSize.toUpperCase();
											System.out.print("색상 : ");
											cColor = sc.next();
											cColor = cColor.toUpperCase();

											// 입력받은 정보를 clothes 객체에 저장
											clothes.setcName(cName);
											clothes.setcStore(cStore);
											clothes.setcSize(cSize);
											clothes.setcColor(cColor);

											// 데이터를 DTO로 담아서 CLOTHES 클래스로 전송
											sql.clothesDel(clothes, cType);
											break;
										case 5:
											deleteWork = false;
											break;
										default:
											System.out.println("잘못 입력하셨습니다(1~5번 중에 입력해주십시오)");
											break;
										}
									}
									break;
								// 주문관리 케이스
								case 3:

									boolean ordersWork = true;

									while (ordersWork) {

										System.out.println("현재 주문 목록입니다.");
										sql.showOrdersList();

										System.out.println("1.배송 완료 주문목록 삭제\t 2. 주문 취소 목록 삭제\t 3.뒤로가기");
										System.out.print("메뉴를 입력하세요 :");
										menu = sc.nextInt();

										switch (menu) {
										case 1:
											System.out.print("배송 완료된 주문목록의 주문번호를 입력하세요 : ");
											menu = sc.nextInt();

											// 배송 완료된 주문번호를 받아 해당하는 주문 목록을 삭제
											sql.ordersComplete(menu);

											break;
										case 2:
											System.out.print("주문 취소된 주문목록의 주문번호를 입력하세요 : ");
											menu = sc.nextInt();

											// 주문 취소된 주문번호를 받아 해당하는 주문 목록을 삭제
											sql.ordersCancle(menu);

											break;
										case 3:
											ordersWork = false;
											System.out.println("한 단계 상위메뉴로 돌아갑니다.");
											break;
										default:
											System.out.println("잘못 입력하셨습니다.");
											break;
										}
									}
									break;
								case 4:
									adminLogin = false; // 관리자 로그아웃 종료 변수
									System.out.println("로그아웃 합니다.");
									break;
								default:
									System.out.println("잘못 입력하셨습니다.(1~4번 메뉴 중에 입력 해주십시오)");
									break;
								}
							}
						} else {
							System.out.println("회원 정보가 존재하지 않습니다");
						}

						break;
					// 회원가입
					case 2:
						System.out.println("회원 가입을 위한 정보를 입력해주세요");

						System.out.print("아이디(5자 이상 입력) : ");
						mId = sc.next();

						if (mId.equals(sql.idOverlapCheck(mId))) {
							System.out.println("아이디가 중복 되었습니다");
						} else {
							System.out.print("패스워드(8자 이상 입력) : ");
							mPw = sc.next();
							System.out.print("이름 : ");
							String mName = sc.next();
							System.out.print("생년월일(형식 : yyyy-mm-dd) : ");
							String mBirth = sc.next();
							System.out.print("이메일 : ");
							String mEmail = sc.next();
							System.out.print("성별 : ");
							String mGender = sc.next();
							System.out.print("연락처(형식 : 010-xxxx-xxxx): ");
							String mPhone = sc.next();
							sc.nextLine();
							System.out.print("주소 : ");
							String mAddress = sc.nextLine();

							// 입력받은 정보를 member 객체에 저장
							member.setmId(mId);
							member.setmPw(mPw);
							member.setmName(mName);
							member.setmBirth(mBirth);
							member.setmEmail(mEmail);
							member.setmGender(mGender);
							member.setmPhone(mPhone);
							member.setmAddress(mAddress);

							// 데이터를 DTO로 담아서 MemberSQl 클래스로 전송
							sql.memberJoin(member);
						}
						break;
					// 종료
					case 3:
						work = false; // 쇼핑몰 종료 변수
						System.out.println("쇼핑몰을 종료합니다.");
						break;
					// 그외 경우
					default:
						System.out.println("잘못 입력하셨습니다.(1,2,3 중에 입력 해주십시오)");
						break;
					}
				}
				break;
			// 은행
			case 2:
				while (bankRun) {
					System.out.println("================= 은행 업무 ================");
					System.out.println("1.회원가입\t\t 2.로그인\t\t 3.뒤로가기");
					System.out.println("==========================================");
					System.out.print("메뉴 선택 : ");
					menu = sc.nextInt(); // 어떠한 업무를 볼것인지 선택하는 변수 초기화

					switch (menu) {
					// 회원가입 케이스
					case 1:

						System.out.println("회원정보를 입력하세요");

						System.out.print("아이디(5자 이상 입력) : ");
						String bId = sc.next();

						if (bId.equals(sql.idOverlapCheck2(bId))) {
							System.out.println("아이디가 중복 되었습니다");
						} else {
							System.out.print("패스워드(8자 이상 입력) : ");
							String bPw = sc.next();
							System.out.print("이름 : ");
							String bName = sc.next();
							System.out.print("생년월일(형식: yyyy-mm-dd) : ");
							String bBirth = sc.next();
							System.out.print("이메일 : ");
							String bEmail = sc.next();
							System.out.print("연락처(형식:010-xxxx-xxxx) : ");
							String bPhone = sc.next();

							bankMember.setbId(bId);
							bankMember.setbPw(bPw);
							bankMember.setbName(bName);
							bankMember.setbBirth(bBirth);
							bankMember.setbEmail(bEmail);
							bankMember.setbPhone(bPhone);

							sql.BankMemberJoin(bankMember);
						}
						break;
					case 2:
						System.out.println("로그인 정보");
						System.out.print("로그인할 아이디 입력 : ");
						bId = sc.next();
						System.out.print("로그인할 패스워드 입력 : ");
						String bPw = sc.next();

						boolean check = sql.idCheck2(bId, bPw);

						if (check) {
							System.out.println("로그인 성공");

							boolean login = true;

							System.out.println("작업하실 항목을 선택하세요");

							while (login) {

								System.out.println("=========================================================");
								System.out.println("1.계좌생성\t\t 2.계좌선택\t 3.로그아웃");
								System.out.println("=========================================================");
								System.out.print("작업할 메뉴 선택 : ");
								menu = sc.nextInt();

								switch (menu) {
								// 계좌생성 케이스
								case 1:
									int accountCnt = sql.AccountCount(bId);
									int balance = 0;

									if (accountCnt >= 0 && accountCnt < 3) {
										String newAccount = sql.accountNumber();

										bank.setAccount(newAccount);
										bank.setBalance(balance);
										bank.setb_mId(bId);

										sql.createAccount(bank);
									} else {
										System.out.println("계좌 생성 한도가 초과하였습니다(계정당 최대 3개)");
									}
									break;
								// 계좌선택 케이스
								case 2:

									String[] account = sql.showAccount(bId);
									int cnt = sql.AccountCount(bId);

									System.out.print("업무를 보실 계좌를 선택하시오 : ");
									menu = sc.nextInt();

									String accountVar = null;

									if (cnt != (menu - 1)) {
										accountVar = account[menu - 1];

//										String x = sql.accountCheck(accountVar);

										boolean accountWork = true;

										while (accountWork) {

											System.out.println("현재 선택하신 계좌 : " + accountVar);
											if (accountVar.equals(sql.accountCheck(accountVar))) {
												System.out.println(
														"=======================================================");
												System.out.println("1.입금\t 2.출금\t 3.송금\t 4.잔액 조회\t 5.뒤로가기");
												System.out.println(
														"=======================================================");
												System.out.print("업무를 보실 메뉴를 선택하세요 : ");
												menu = sc.nextInt();

												switch (menu) {
												// 입금 케이스
												case 1:
													System.out.print("입금액 : ");
													balance = sc.nextInt();

													bank.setAccount(accountVar);
													bank.setBalance(balance);

													sql.deposit(bank);
													break;
												// 출금 케이스
												case 2:
													System.out.print("출금액 : ");
													int wBalance = sc.nextInt();

													balance = sql.checkBalance(accountVar);

													if (balance >= wBalance) {

														bank.setAccount(accountVar);
														bank.setBalance(wBalance);

														sql.withdraw(bank);
													} else {
														System.out.println(
																"출금 실패!! 잔액이 " + (wBalance - balance) + "원 부족합니다");
													}
													break;
												// 송금 케이스
												case 3:
													System.out.print("받는분 계좌번호 >> ");
													String getAccount = sc.next();

													boolean accountCheck = sql.checkAccount(getAccount);

													if (accountCheck) {
														System.out.print("송금액 >> ");
														int sBalance = sc.nextInt();

														balance = sql.checkBalance(accountVar);
														if (balance >= sBalance) {

															sql.sendMoney(accountVar, getAccount, sBalance);

														} else {
															System.out
																	.println("잔액이 " + (sBalance - balance) + "원 부족합니다");
														}
													} else {
														System.out.println("해당 계좌가 없습니다.");
													}
													break;
												// 잔액조회 케이스
												case 4:
													balance = sql.checkBalance(accountVar);
													System.out.println("현재잔액 : " + balance + "원");
													break;
												case 5:
													accountWork = false;
													break;
												default:
													System.out.println("잘못 입력하셨습니다(1~5번 메뉴 중에 선택하여주십시오)");
													break;
												}

											} else if (menu == (cnt + 1)) {
												accountWork = false;
											}
										}
									}
									break;
								case 3:
									login = false;
									System.out.println("로그아웃 합니다");
									break;
								default:
									System.out.println("잘못 입력하셨습니다.(1~3번 메뉴 중에 선택해주십시오)");
									break;
								}
							}

						}
						break;
					case 3:
						bankRun = false;
						System.out.println("은행 시스템을 종료합니다");
						break;
					default:
						System.out.println("잘못 입력하셨습니다.(1~5번 메뉴 중 선택해주십시오)");
						break;
					}
				}
				break;
			// 시스템 종료
			case 3:
				run = false; // 시스템 종료 변수
				System.out.println("시스템을 종료합니다");
				break;
			// 그 외 입력
			default:
				System.out.println("잘못 입력하셨습니다");
				break;
			}
		}
	}

}