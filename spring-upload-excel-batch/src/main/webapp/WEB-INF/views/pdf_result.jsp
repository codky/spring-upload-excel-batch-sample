<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>변환 결과</title></head>
<body>
    <h2>변환된 이미지 목록</h2>
    <c:forEach var="img" items="${images}">
        <div>
            <img src="${img}" width="300px" /><br/>
        </div>
    </c:forEach>
    <a href="/pdf/convert">다시 업로드</a>
</body>
</html>
