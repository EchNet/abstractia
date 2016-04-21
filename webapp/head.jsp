<jsp:useBean id="model" scope="request" class="com.abstractia.display.HeadModel" />
<title><%= model.getTitle() %></title>
<style>
div.left {
    float: left;
    width: 140px;
    padding-right: 20px;
}
div.center {
    float: left;
}
</style>
