<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>파일 목록</title></head>
<body>
    <h2>업로드된 파일 목록</h2>
    <ul>
		<c:forEach var="file" items="${files}">
		    <li>
		        <c:out value="${file}" /> |
		        <a href="/download?name=${file}">다운로드</a>
		    </li>
		</c:forEach>
    </ul>
    <a href="/upload">← 파일 업로드로 이동</a>
</body>
</html>