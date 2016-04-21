<jsp:useBean id="model" scope="request" class="com.abstractia.display.FeaturedModel" />

<div style="width: 700px; height: 120px;
            margin-left: 5px;
            padding: 10px;
            background-color: <%= model.getRecord().get("bgcolor") %>;" >

    <h1>Category <%= model.getRecord().get("bgcolor").getName() %></h1>
    <h2>Featuring: the <%= model.getRecord().get("color").getName()%> <%= model.getRecord().get("shape") %> <%= model.getRecord().get("symbol")%> ("<%= model.getRecord().get("name") %>")!!</h2>

</div>
