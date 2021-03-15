<!DOCTYPE html>
<html>
<head>
    <title>空白</title>
</head>
<body>
    <h1>hello</h1>
    <%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%>
    <%
        Response.Write(Date.Today.ToString())
        callback = Request.QueryString("callback")
        Response.Write(callback)
        Response.End()
    %>
</body>
</html>