<!-- This page simply removes session attributes and returns the the home page, ie logs out the user -->
<%
session.invalidate();
response.sendRedirect("/Bookstore/Start");
%>