<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>mailForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  
  <script>
  'use strict';
  function allCheck() {
    let email = document.getElementsByName("emailcheck");
     for(let i=0; i<email.length; i++) {
         email[i].checked = !email[i].checked;
     }
   }
  
  function emailSelect() {
    let emails = "";
    let email = document.getElementById("emailcheck");
    for(let i=0; i<emailcheck.length; i++) {
        if(document.getElementsByName("emailcheck")[i].checked == true) {
          emails += document.getElementsByName("emailcheck")[i].value + ";";
      }
    }
    $("#toMail").val(emails);
  }
  
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>메 일 보 내 기</h2>
  <p>(받는 사람의 메일 주소를 정확히 입력하셔야 합니다.)</p>
  <form name="myform" method="post">
      <table class="table table-bordered">
          <tr>
              <th style="width:30%">
                  받는 사람&nbsp;&nbsp;&nbsp;
                  <button type="button" class="btn btn-outline-dark mr-0" data-toggle="modal" data-target="#myModal">주소록</button>
              </th>
              <td style="width:70%">
                  <input type="text" name="toMail" id="toMail" value="${email}" placeholder="받는 사람의 주소를 입력하세요" class="form-control" required autofocus />
              </td>
          </tr>
          <tr>
              <th>메일제목</th>
              <td>
                  <input type="text" name="title" placeholder="메일 제목을 입력하세요" class="form-control" required />
              </td>
          </tr>
          <tr>
              <th>메일 내용</th>
              <td><textarea rows = "7" name="content" class="form-control" required></textarea></td>
          </tr>
          <tr>
              <td colspan="2" class="text-center">
                  <input type="submit" value="메일보내기" class="btn btn-outline-dark">
                  <input type="reset" value="다시쓰기" class="btn btn-outline-dark">
                  <input type="button" value="돌아가기" onclick="location.href='${ctp}/';" class="btn btn-outline-dark">
              </td>
          </tr>
      </table>
  </form>
  <!-- The Modal -->
  <div class="modal fade" id="myModal">
    <div class="modal-dialog">
      <div class="modal-content">
      
  <!-- Modal Header -->
  <div class="modal-header">
    <h4 class="modal-title text-center">주소록</h4>
    <button type="button" class="close" data-dismiss="modal">&times;</button>
  </div>
  
  <!-- Modal body -->
  <div class="modal-body">
    <form name="emailsForm">
      <input type="checkbox" id="emailCheck" name="emailCheck" onclick="xxxxjavascript:allCheck()" class="ml-2" />
      <table class="table table-bordered text-center">
        <tr>
            <td>번호</td>
            <td>이름</td>
            <td>이메일</td>
        </tr>
        <c:forEach var="vo" items="${vos}" varStatus="st">
          <tr>
            <td><input type="checkbox" id="emailcheck" name="emailcheck" value="${vo.email}"/></td>
            <td>${st.count}</td>
            <td>${vo.name}</td>
            <td>${vo.email}</td>
          </tr>
        </c:forEach>
      </table>
    </form>         
  </div>
        
  <!-- Modal footer -->
  <div class="modal-footer">
      <button type="button" class="btn btn-outline-dark" onclick="emailSelect()" data-dismiss="modal">선택</button>
    <button type="button" class="btn btn-outline-dark" data-dismiss="modal">닫기</button>
  </div>
        
      </div>
    </div>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
