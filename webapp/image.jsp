<jsp:useBean id="model" scope="request" class="com.abstractia.display.ImageModel" />
<img src="<%= model.getSource() %>" width="120" height="240">
