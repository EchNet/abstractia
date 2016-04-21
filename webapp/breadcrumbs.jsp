<%@page import="com.abstractia.model.DimFilter" %>
<%@page import="com.abstractia.web.UrlFormatter" %>
<jsp:useBean id="model" scope="request" class="com.abstractia.display.BreadcrumbsModel" />
<% UrlFormatter urlFormatter = new UrlFormatter(request); %>
<p> YOU ARE HERE:
<% for (DimFilter df : model.getDimFilters()) { %>
<b><%= df.getDimension().getName() %>:</b> <%= df.getDval().getName() %> <a href="<%= urlFormatter.format(model.getState().removeDimFilter(df)) %>">X</a>|
<% } %>
</p>
