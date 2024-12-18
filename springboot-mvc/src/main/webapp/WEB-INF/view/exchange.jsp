<%@ page import="java.util.List, java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>貨幣兌換</title>
</head>
<body>

<jsp:include page="header.jsp" />

<form action="/calculate" method="post" style="max-width: 600px; text-align: left;">
    <div style="margin-bottom: 1em;">
        <label for="amount">金額：</label>
        <input type="number" id="amount" name="amount" required style="width: 100%; padding: 0.5em; font-size: 1em;" 
               pattern="\d*" title="金額不能為負數">
        <div id="amount-error" style="color: red; display: none;">金額不能為負數</div>
    </div>
    
    <div style="margin-bottom: 1em;">
        <label for="fromCurrency">來源貨幣：</label>
        <select id="fromCurrency" name="fromCurrency" style="width: 100%; padding: 0.5em; font-size: 1em;">
            <% 
                List<String> currencies = (List<String>) request.getAttribute("currencies");
                if (currencies != null) {
                    for (String currency : currencies) {
            %>
                <option value="<%= currency %>"><%= currency %></option>
            <% 
                }
                } else {
            %>
                <option value="">無法獲取幣別</option>
            <% 
                }
            %>
        </select>
    </div>

    <div style="margin-bottom: 1em;">
        <label for="toCurrency">目標貨幣：</label>
        <select id="toCurrency" name="toCurrency" style="width: 100%; padding: 0.5em; font-size: 1em;">
            <% 
                for (String currency : currencies) {
            %>
                <option value="<%= currency %>"><%= currency %></option>
            <% 
                }
            %>
        </select>
    </div>

    <button type="submit" style="padding: 0.7em 1.5em; font-size: 1.2em; background-color: black; color: white; border: none; border-radius: 4px; cursor: pointer;">兌換</button>
</form>


    


<script src="/js/exchange.js"></script>

</body>
</html>