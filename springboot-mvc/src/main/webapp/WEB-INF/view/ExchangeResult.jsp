<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>兌換結果</title>
</head>
<body>
<jsp:include page="header.jsp" />
  <h1>匯率計算結果</h1>
<p>金額：<%= request.getAttribute("amount") %></p>
<p>來源貨幣：<%= request.getAttribute("fromCurrency") %></p>
<p>目標貨幣：<%= request.getAttribute("toCurrency") %></p>
<p>結果：<%= request.getAttribute("result") %></p>
<form action="http://localhost:5487/exchange" method="get" style="max-width: 600px; text-align: left;">
    <button type="submit" style="padding: 0.7em 1.5em; font-size: 1.2em; background-color: black; color: white; border: none; border-radius: 4px; cursor: pointer;">
        繼續兌換
    </button>
</form>


</body>
</html>
