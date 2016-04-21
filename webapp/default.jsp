<%@page import="com.abstractia.display.*"%>
<%!
    /**
     * Heheh.
     */
    public String getRendererFor(ComponentModel model)
    {
        String name = model.getClass().toString();
        name = name.substring(name.lastIndexOf('.') + 1);
        if (name.endsWith("Model"))
        {
            name = name.substring(0, name.length() - 5);
        }

        if (model.getVariant() != null)
        {
            name += "-" + model.getVariant();
        }

        return "/" + name.toLowerCase() + ".jsp";
    }
%>
<jsp:include page="<%= getRendererFor((ComponentModel) request.getAttribute("model")) %>"/>
