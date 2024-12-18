<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="cl" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>客戶編輯</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/css/my_css.css">
	</head>
	<body>
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/client/menu.jspf" %>
		
		<!-- body content -->
		<div style="padding: 15px">
			<cl:form modelAttribute="clientDTO" class="pure-form" method="post" action="/client/update/${ clientDTO.num }">
				<fieldset>
					<legend>客戶編輯</legend>
					客戶編號: <cl:input type="number" path="num" readonly="true" />
							  <cl:errors path="num" cssStyle="color: red"/>
					<p />
					客戶姓名: <cl:input type="text"   path="name" />
					          <cl:errors path="name" cssStyle="color: red"/>
					<p />
					身分證字號: <cl:input type="text"   path="id" />
					          <cl:errors path="id" cssStyle="color: red"/>
					<p />
					身分證字號: <cl:input type="text"   path="car" />
					          <cl:errors path="car" cssStyle="color: red"/>
					<p />
					保險到期日: <cl:input type="text"   path="day" />
					          <cl:errors path="day" cssStyle="color: red"/>
					<p />
					保險公司: <cl:input type="text"   path="company" />
					          <cl:errors path="company" cssStyle="color: red"/>
					<p />
					保費: <cl:input type="number" path="money" />
							  <cl:errors path="money" cssStyle="color: red"/>
					<p />
					<button type="submit" class="pure-button pure-button-primary" style="background-color: black; color: white;">
						修改
					</button>
				</fieldset>
			</cl:form>
		</div>
	</body>
</html>