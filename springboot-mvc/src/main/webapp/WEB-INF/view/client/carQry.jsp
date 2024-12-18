<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    
    <title>客戶車險資料查詢</title>
</head>
<body>

<%@include file="/WEB-INF/view/header.jsp" %>
    <fieldset>
        <legend>客戶車險查詢</legend>
        <div>
            <!-- 設計表單 -->
            <form method="post" action="/client/qry/bycarid">
                <div>
                    <label>請輸入車牌號碼：</label>
                    <input type="text" required maxlength="10" name="clientCar"/>
                    
                    <input type="submit" value="查詢"/>
                </div>
            </form>
        </div>
    </fieldset>
    <!-- 客戶資料查詢結果 -->
   
    <c:if test="${result!= null}">
        <fieldset>
            <legend>車牌查詢結果</legend>
            <div>
                <table>
                    <tr>
                        <td>客戶姓名:</td>
                        <td>${result.clientName}</td>
                    </tr>
                       <tr>
                        <td>客戶身分證:</td>
                        <td>${result.clientId}</td>
                    </tr>
                  <tr>
                        <td>車牌號碼:</td>
                        <td>${result.clientCar} </td>
                    </tr>
                    <tr>
                        <td>到期日:</td>
                        <td>${result.clientDay} </td>
                    </tr>
                      <tr>
                        <td>保險公司:</td>
                        <td>${result.clientCompany}</td>
                    </tr>
                      <tr>
                        <td>保費:</td>
                        <td>${result.clientMoney} </td>
                    </tr>
             
                </table>
            </div>
        </fieldset>
        <!-- 顯示查詢成功時間 -->
        <div>
            <strong>查詢時間：</strong>
            <fmt:formatDate value="${queryTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </div>
    </c:if>
    <div>${message}</div>
</body>
</html>
