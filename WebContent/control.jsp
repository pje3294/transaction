<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.bank.*, java.util.*"%>
<%
	request.setCharacterEncoding("UTF-8");
%>

<jsp:useBean id="bankDAO" class="model.bank.BankDAO" />
<jsp:useBean id="bankVO" class="model.bank.BankVO" />
<jsp:setProperty property="*" name="bankVO" />
<%
	String action = request.getParameter("action");

	if (action.equals("view")) {
		System.out.println("view 실행 ");
		ArrayList<BankVO> datas = bankDAO.selectAll();
		request.setAttribute("datas", datas);
		pageContext.forward("view.jsp");

	} else if (action.equals("tran")) {

		int money = Integer.parseInt(request.getParameter("money"));

		String sendName = (String) request.getParameter("sendName");
		String giveName = (String) request.getParameter("giveName");
		if (bankDAO.trans(money, sendName, giveName)) {
			out.println("<script>alert('이체성공');location.href='control.jsp?action=view';</script>");
		} else {
			out.println("<script>alert('이체실패');location.href='control.jsp?action=view';</script>");
		}
	}
%>