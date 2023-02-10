<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Demo Login</title>
</head>
<body>
<script>
document.addEventListener("DOMContentLoaded", function(){
	let msg = "<c:out value='${result}'/>";
	if (msg) {
		alert(msg);
	}
});
</script>
<div><p>LOGIN TYPE : <c:out value='${loginType}'/></p></div>
  <div>
    <form class="form-signin" method="post" action="/login">
      <h2 class="form-signin-heading">Please sign in for test</h2>
      <p>
        <label for="username">Unique Name</label>
        <input type="text" id="username" name="username" placeholder="uniqueName" required autofocus>
      </p>
      <p>
        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="Password" required>
      </p>
      <p>
        <label>Client Type</label>
        <input type="radio" name="clienttype" value="WEB" checked>WEB
        <input type="radio" name="clienttype" value="ANDROID">ANDROID
        <input type="radio" name="clienttype" value="IOS">IOS
      </p>
      <button type="submit">Sign in</button>
    </form>
  </div>
</body>
</html>
