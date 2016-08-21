<%@ page language="java" import="org.json.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./twitterAPIs.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Here are your followers</title>
</head>
<body>
	<h4>Here are your followers </h4>
	<table>
		<thead>
			<tr>
				<th>Name</th>
				<th>Screen Name</th>
			</tr>
		</thead>
		<tbody>
	<%
		Object respBody = null;
		String usersStr = null;
		JSONArray users = null;
		if (request.getAttribute("followers") != null) {
			respBody = request.getAttribute("followers");
		} else if (request.getAttribute("friends") != null) {
			respBody = request.getAttribute("friends");
		}
		usersStr = respBody.toString();
		users = new JSONArray(new JSONObject(usersStr).getJSONArray("users").toString());
		for (int i = 0; i < users.length(); i++)
		{
			%><tr>
				<td> 
				<%=users.getJSONObject(i).getString("name")%>
				</td>
				<td>
				<%=users.getJSONObject(i).getString("screen_name")%>
				</td>
			 </tr>
	   <%}
	%>
	</tbody>
	</table>
</body>
</html>