<%@page import="com.abstractia.display.*" %>
<%@page import="com.abstractia.model.*" %>
<%@page import="com.abstractia.web.*" %>
<jsp:useBean id="model" scope="request" class="com.abstractia.display.ResultsModel" />
<%
    final int CELLS_PER_ROW = 5;
    UrlFormatter urlFormatter = new UrlFormatter(request);
%>
<%
    if (model.getRecords().size() == 0) {
%>
<h2>No results.</h2>
<%
    } else {
%>
<table cellspacing="5" cellpadding="5">
<%
        int count = 0;
        for (Record rec : model.getRecords()) {
            if (count % CELLS_PER_ROW == 0) {
%>
    <tr>
<%
            }
%>
    <td style="width: 110px; height: 180px; 
               text-align: center;
               border-style: solid;
               border-width: 10px;
               border-color: <%= rec.get("bgcolor") %>;
               background-color: white;">
        <!-- <%= rec.getRecordNumber() %> -->

        <div style="width: 64px; height: 54px; margin-left: 21px;
                    text-align:center;
                    padding-top: 10px;
                    background-color: white;
                    background-image: url(/abstractia/img/<%= rec.get("shape") %>.jpg);"
                >
            <span style="font-size: 42px; color: <%= rec.get("color") %>;"><%= rec.get("symbol") %></span>

        </div>
        <p><%= rec.get("name") %></p>
    </td>
<%
            if (count % CELLS_PER_ROW == (CELLS_PER_ROW - 1)) {
%>
    </tr>
<%
            }
            ++count;
        }
%>
</table>
<%
    }
%>
<span>
<% 
    int foo = 0;
%>
<%
    if (model.getState().getRecordStart() > 0) { 
        ++foo;
%>
    <a href="<%= urlFormatter.format(model.getState().page(0)) %>">Top</a>
<%
    }
%>
<%
    if (!model.isAtRecordEnd()) { 
%>
    <%= foo > 0 ? " ~ " : "" %><a href="<%= urlFormatter.format(model.getState().page(model.getState().getRecordStart() + model.getRecords().size())) %>">More...</a>
<%
        ++foo;
    }
%>
</span>
