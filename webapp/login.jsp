<%@page import="com.abstractia.web.*" %>
<jsp:useBean id="model" scope="request" class="com.abstractia.display.LoginModel" />
<% UrlFormatter urlFormatter = new UrlFormatter(request); %>
<div style="text-align: right; width: 100%: margin-right: 20px;">
    <a href="<%= urlFormatter.format(model.getState().page(0)) %>" onclick="document.cookie='user=joe<%= model.isLoggedIn() ? ";expires=Thu, 01-Jan-70 00:00:01 GMT;" : "" %>'; return true;"><button><%= model.isLoggedIn() ? "LOG OUT" : "LOG IN" %></button></a>
</div>
