<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<input type="hidden" id="id" value="${principal.user.id}">
		<div class="form-group">
			<label for="username">UserName:</label> <input value="${principal.user.username}" type="text" class="form-control" placeholder="Enter UserName" id="username" readonly="readonly">
		</div>


		<c:if test="${empty principal.user.oauth}">
			<div class="form-group">
				<label for="password">Password:</label> 
				<input type="password" class="form-control" placeholder="Enter Password" id="password">
			</div>
		</c:if>


		<div class="form-group">
			<label for="email">Email address:</label> 
			<input value="${principal.user.email}" type="email" class="form-control" placeholder="Enter Email" id="email" readonly="readonly">
		</div>
	</form>
	<button id="btn-update" class="btn btn-primary">수정완료</button>
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>