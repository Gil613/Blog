<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/auth/loginProc" method="POST">
		<div class="form-group">
			<label for="username">UserName:</label> 
			<input type="text" name="username" class="form-control" placeholder="Enter UserName" id="username">
		</div>

		<div class="form-group">
			<label for="password">Password:</label> 
			<input type="password" name="password" class="form-control" placeholder="Enter Password" id="password">
		</div>

		<div class="form-group form-check">
			<label class="form-check-label"> 
			<input class="form-check-input" type="checkbox" name="remember"> Remember me
			</label>
		</div>
		
		
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=d423f54ea1829b65ccf4895e27242fd3&redirect_uri=http://localhost:8088/auth/kakao/callback&response_type=code"><img width="74" height="38" src="/image/카카오로그인버튼.png"></a>
	</form>

</div>

<%@ include file="../layout/footer.jsp"%>