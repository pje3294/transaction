<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.bank.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="datas" class="java.util.ArrayList" scope="session" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>트랜잭션_계좌이체</title>
</head>
<body>

	<h2>OO은행</h2>
	<table border="1">
		<caption>현 계좌 상황</caption>
		<tr>
			<td>성명</td>
			<td>id</td>
			<td>금액</td>
		</tr>

		<!-- JSTL, EL로 DB의 고객 정보를 받아옴 -->
		<c:forEach var="v" items="${datas}">
			<tr>
				<td>${v.name}</td>
				<td>${v.bid}</td>
				<td>${v.balance }</td>
			</tr>
		</c:forEach>

	</table>

	<h3>이체자</h3>
	<form method="post" action="control.jsp" name="form1">
		<input type="hidden" name="action" value="tran"> 
		<select	name="sendName">
			<c:forEach var="v" items="${datas}">
				<option>${v.name }</option>
			</c:forEach>
		</select>

		<h3>입금자</h3>
		<select name="giveName">
			<c:forEach var="v" items="${datas}">
				<option>${v.name }</option>
			</c:forEach>
		</select> <input type="text" name="money"> 
		<input type="submit" value="진행">
	</form>

</body>
</html>