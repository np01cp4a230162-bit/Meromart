<%-- Redirect root URL to the login servlet --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    response.sendRedirect(request.getContextPath() + "/login");
%>
