<%@page import="com.abstractia.display.*" %>
<jsp:useBean id="model" scope="request" class="com.abstractia.display.SlotModel" />
<div class="<%= model.getName() %>">
<% request.setAttribute("model", model.getComponent()); %>
<jsp:include page="default.jsp"/>
</div>
