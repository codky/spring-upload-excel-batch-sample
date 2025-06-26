<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>PDF 이미지 변환</title></head>
<body>
    <h2>PDF 업로드 후 이미지로 변환</h2>
    <form method="post" enctype="multipart/form-data" action="/pdf/convert">
        <input type="file" name="file" accept="application/pdf" />
        <input type="submit" value="변환하기" />
    </form>
</body>
</html>
