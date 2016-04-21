<%@page import="com.abstractia.display.*" %>
<%@page import="com.abstractia.model.*" %>
<%@page import="com.abstractia.web.*" %>
<jsp:useBean id="model" scope="request" class="com.abstractia.display.NavLinksModel" />
<% UrlFormatter urlFormatter = new UrlFormatter(request); %>
<% 
    for (Dimension dimension : model.getDimRefinements().keySet())
    {
%>
    <p>Choose <%= dimension.getName() %>:</p>
    <ul>
<% 
        for (DimRefinement refinement : model.getDimRefinements().get(dimension)) {
%>
        <li><a href="<%= urlFormatter.format(model.getState().addDimFilter(new DimFilter(dimension, refinement.getDval()))) %>"><%= refinement.getDval().getName() %></a> (<%= refinement.getBinCount() %>)</li>
<%
        }
%>
    </ul>
<%
    }
%>
