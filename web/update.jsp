<%-- 
    Document   : test
    Created on : Aug 13, 2018, 9:32:42 AM
    Author     : Metalheadfk
--%>

<%@page import="com.google.gson.Gson"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="cls.clsQuery"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <%

            String custid = request.getParameter("id");

            clsQuery cls = new clsQuery();
            String result = cls.getlogin("");
            List listData = null;
            // String resultx = cls.callpartner(Integer.parseInt(result));
            List custdata = cls.updatecust(Integer.parseInt(result), Integer.parseInt(custid));
            Gson gson = new Gson();
            String jsonString = gson.toJson(custdata);

        %>
        cust data: <%=jsonString%>
    </body>
</html>
