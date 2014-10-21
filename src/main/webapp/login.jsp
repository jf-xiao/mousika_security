<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html><head><title>Login Page</title></head><body onload='document.f.j_username.focus();'>
<h3>Login with Username and Password</h3><form name='f' action='j_spring_security_check' method='POST'>
 <table>
    <tr><td>User:</td><td><input type='text' name='j_username' value=''></td></tr>
    <tr><td>Password:</td><td><input type='password' name='j_password'/></td></tr>
    <tr><td><input id="_remember_me" name="_remember_me" type="checkbox" value="true"/></td></tr>
    <tr><td colspan='2'><input name="submit" type="submit" value="Login"/></td></tr>
  </table>
</form></body></html>
