<%@ page language="java" import="org.json.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./twitterAPIs.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search Results</title>
</head>
<body>
	<h3>Here are the tweets matching the search query : San Jose </h3>
	<table>
		<thead>
	 		<tr>
	 			<th>By User</th>
	 			<th>Tweet</th>
			</tr>
	   	</thead>
	   	<tbody>
	<%
	String tweetsStr = request.getAttribute("searchTweets").toString();
	JSONArray tweets = new JSONObject(tweetsStr).getJSONArray("statuses");
	JSONObject tweet;
		for (int i = 0; i < tweets.length(); i++)
		{
			tweet = tweets.getJSONObject(i);
			%><tr>
				<td>
					<%=tweet.getJSONObject("user").getString("name")%>
				</td>
				<td>
					<%=tweet.getString("text")%>
				</td>
			</tr>
		<%}
	%>
	</tbody>
	</table>
</body>
</html>