<%@page import="com.abstractia.display.*" %>
<jsp:useBean id="model" scope="request" class="com.abstractia.display.ContainerModel" />
<%
    for (ComponentModel comp : model.getComponents())
    {
        request.setAttribute("model", comp);
%>
<jsp:include page="default.jsp"/>
<%
    }
%>
