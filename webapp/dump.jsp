<%@page import="com.abstractia.model.*" %>
<%@page import="com.abstractia.mockdex.NavigationResult" %>
<jsp:useBean id="model" scope="request" class="com.abstractia.mockdex.NavigationResult" />
<html>
<%
    if (model.getRecords().size() == 0) {
%>
    <h2>No results.</h2>
<%
    } else {
%>
<dl>
<%
    for (Record rec : model.getRecords()) {
%>
    <dt>Record <%= rec.getRecordNumber() %>:</dt>
<%
        for (String key : rec.keySet()) {
%>
    <dd><b><%= key %>:</b> <%= rec.get(key).getValue() %></dd>
<%
    } }
%>
</dl>
<%
    }
%>
</html>
