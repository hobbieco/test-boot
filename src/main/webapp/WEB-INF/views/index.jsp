<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<sec:authorize access="isAuthenticated()">
	<sec:authentication var="user" property="principal" />
</sec:authorize>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>Demo</title>
</head>
<body>
	<div>
		<h1>index</h1>
	</div>
	<div>
		<p>익명 전부 보임</p>
		<br />
		<p>
			before login name : <c:out value='${user}' />
		</p>
		<sec:authorize access="isAuthenticated()">
			<p>로그인 하면 보임</p>
			<p>
				after login name : <c:out value='${user}' /><br />
				after login type : <c:out value='${user}' />
			</p>
			<br />
		</sec:authorize>

		<sec:authorize access="hasRole('MANAGER')">
			<p>로그인 하고 ROLE_MANAGER면 보임</p>
			<br />
		</sec:authorize>

		<sec:authorize access="hasRole('ADMIN')">
			<p>로그인 하고 ROLE_ADMIN면 보임</p>
			<br />
		</sec:authorize>

		<sec:authorize access="hasAnyRole('MANAGER','ADMIN')">
			<p>로그인 하고 ROLE_MANAGER 이거나 ROLE_ADMIN면 보임</p>
			<br />
			<p>
				login name : <c:out value='${user}' /><br />
				login type : <c:out value='${user}' />
			</p>
		</sec:authorize>

		<sec:authorize access="hasRole('MANAGER') and hasRole('ADMIN')">
			<p>로그인 하고 ROLE_MANAGER이고 ROLE_ADMIN면 보임</p>
			<br />
		</sec:authorize>
	</div>
</body>
</html>
