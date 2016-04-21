<jsp:useBean id="model" scope="request" class="com.abstractia.display.PageModel" />
<html>
<head>
<% request.setAttribute("model", model.getHead()); %>
<jsp:include page="default.jsp"/>
</head>
<body>
<% request.setAttribute("model", model.getBody()); %>
<jsp:include page="default.jsp"/>
</body>
</html>

