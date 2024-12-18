<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <div style="background-color:grey; height:60px; display: flex; align-items: center; padding: 0 20px;">
  <a href="http://localhost:5487">
    <img src="/image/homepage.png" alt="回到首頁" style="width:60px; height:60px;">
  </a>  
  <div style="display: flex; gap: 20px; justify-content: center; margin-left: 20px;">
    <button onclick="window.location.href='http://localhost:5487/client'" style="padding: 10px 20px; background-color: black; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 14px;">
      維護客戶資料
    </button>
    <button onclick="window.location.href='http://localhost:5487/client/qry/byid'" style="padding: 10px 20px; background-color: black; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 14px;">
      以身分證查詢客戶車籍資料
    </button>
    <button onclick="window.location.href='http://localhost:5487/client/qry/bycarid'" style="padding: 10px 20px; background-color: black; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 14px;">
      以車牌查詢客戶車籍資料
    </button>
    <button onclick="window.location.href='http://localhost:5487/exchange'" style="padding: 10px 20px; background-color: black; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 14px;">
      線上兌換貨幣計算
    </button>
  </div>
</div>
    