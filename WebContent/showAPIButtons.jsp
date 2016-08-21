<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./twitterAPIs.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Congratulations ! Spartans App can now use your data</h1>
	<h3>Let us know what you want to do</h3>
	<form action="TwitterRestAPIServlet" method="GET">
		<input type="hidden" name="oauth_verifier" id="oauth_verifier" value="<%=request.getParameter("oauth_verifier")%>" />
		<input type="hidden" name="oauth_token" id="oauth_token" value="<%=request.getParameter("oauth_token")%>" />
		<input type="submit" class="btn" name="getTweets" value="Get My Last 10 Tweets" />
		<input type="submit" class="btn" name="getFollowers" value="Get My Followers" />
		<input type="submit" class="btn" name="getFriends" value="Get My Friends" />
		<input type="submit" class="btn" name="verifyCredentials" value="Verify My Credentials"/>
		<input type="submit" class="btn" name="searchTweets" value="Search Tweets"/>
		<input type="submit" class="btn" name="favoriteTweets" value="Favorite Tweets"/>
		<input type="submit" class="btn" name="timelineTweets" value="Timeline Tweets"/>
	</form>
	<form action="TwitterRestAPIServlet" method="POST">
		<input type="hidden" name="oauth_verifier" id="oauth_verifier" value="<%=request.getParameter("oauth_verifier")%>" />
		<input type="hidden" name="oauth_token" id="oauth_token" value="<%=request.getParameter("oauth_token")%>" />
		<input type="submit" class="btn" name="tweet" value="Post Tweet"/><br>
	</form>
</body>
</html>