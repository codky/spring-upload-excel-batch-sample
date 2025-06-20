<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head><title>파일 업로드</title></head>
<body>
	<h2>파일 업로드</h2>
	<form method="post" action="/upload" enctype="multipart/form-data">
		<input type="file" name="file" />
		<button type="submit">업로드</button>
	</form>
</body>
</html>