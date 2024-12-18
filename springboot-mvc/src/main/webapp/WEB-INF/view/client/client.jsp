<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>客戶系統</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
	<link rel="stylesheet" href="/css/my_css.css">
	</head>
	<body>
		<!-- menu bar include -->
		<%@include file="/WEB-INF/view/client/menu.jspf" %>
	
		<!-- body content -->
		<div style="padding: 15px">
			<table>
				<tr>
					<!-- 新增client表單 -->
					<td valign="top">
						<%@include file="/WEB-INF/view/client/client_form.jspf" %>
					</td>
					
					<!-- 列表client資訊 -->
					<td valign="top">
						<%@include file="/WEB-INF/view/client/client_list.jspf" %>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>