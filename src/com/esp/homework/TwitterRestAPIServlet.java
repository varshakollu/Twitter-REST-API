package com.esp.homework;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class TwitterRestAPIServlet
 */
@WebServlet("/TwitterRestAPIServlet")
public class TwitterRestAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1;
	private static final String FOLLOWING_URL = "https://api.twitter.com/1.1/friends/list.json";
	private static final String FOLLOWERS_URL = "https://api.twitter.com/1.1/followers/list.json";
	private static final String ALL_TWEETS_URL = "https://api.twitter.com/1.1/statuses/user_timeline.json";
	private static final String CREDENTIALS_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";
	private static final String SEARCH_TWEETS_URL = "https://api.twitter.com/1.1/search/tweets.json?q=San%20Jose";
	private static final String FAVORITE_TWEETS = "https://api.twitter.com/1.1/favorites/list.json";
	private static final String POST_STATUS = "https://api.twitter.com/1.1/statuses/update.json";
	private static final String TIMELINE_TWEETS = "https://api.twitter.com/1.1/statuses/home_timeline.json";
	private OAuthService service = null;
	private Token requestToken = null;
	private Verifier verifier = null;
	private Token accessToken = null;
	private String newTweet = null;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("login") != null) {
			setOAuthService();
			requestToken = getOAuthRequestToken(service);
			String authorizationUrl = service.getAuthorizationUrl(requestToken);
			response.sendRedirect(response.encodeRedirectURL(authorizationUrl));
		} else if (request.getParameter("getTweets") != null) {
			setAccessToken(request);
			request.setAttribute("tweets", getMyTweets());
			request.getRequestDispatcher("/tweets.jsp").forward(request, response);
		} else if (request.getParameter("getFollowers") != null) {
			setAccessToken(request);
			request.setAttribute("followers", getMyFollowers());
			request.getRequestDispatcher("/followers-friends.jsp").forward(request, response);
		} else if (request.getParameter("getFriends") != null) {
			setAccessToken(request);
			request.setAttribute("friends", getMyFriends());
			request.getRequestDispatcher("/followers-friends.jsp").forward(request, response);
		} else if (request.getParameter("verifyCredentials") != null) {
			setAccessToken(request);
			request.setAttribute("credentials", verifyCredentials());
			request.getRequestDispatcher("/credentials.jsp").forward(request, response);
		} else if (request.getParameter("searchTweets") != null) {
			setAccessToken(request);
			request.setAttribute("searchTweets", getSearchTweets());
			request.getRequestDispatcher("/searchTweets.jsp").forward(request, response);
		} else if (request.getParameter("favoriteTweets") != null) {
			setAccessToken(request);
			request.setAttribute("favoriteTweets", getFavoriteTweets());
			request.getRequestDispatcher("/favoriteTweets.jsp").forward(request, response);
		} else if (request.getParameter("timelineTweets") != null) {
			setAccessToken(request);
			request.setAttribute("timelineTweets", getTimelineTweets());
			request.getRequestDispatcher("/timelineTweets.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("tweet") != null) {
			setAccessToken(request);
			request.getRequestDispatcher("/postStatus.jsp").forward(request, response);
		} else if (request.getParameter("postStatus") != null && request.getParameter("status") != null) {
			setAccessToken(request);
			newTweet = request.getParameter("status");
			postStatus();
			request.setAttribute("status", newTweet);
			request.getRequestDispatcher("/postStatus.jsp").forward(request, response);
		}
	}

	public void setAccessToken(HttpServletRequest request) {
		if (request.getParameter("oauth_verifier") != null) {
			if (verifier == null) {
				verifier = new Verifier(request.getParameter("oauth_verifier"));
			}
			if (accessToken == null) {
				accessToken = service.getAccessToken(requestToken, verifier);
			}
		}
	}

	public void setOAuthService() {
		service = new ServiceBuilder().provider(TwitterApi.class).apiKey("7nL275BHBYIFYjfpwzM9coVwd")
				.apiSecret("xa90vqoN9xyiVdseLx1NhY5VhWvujMYi8Zio7xdUSLeTfVXVE1")
				.callback("http://127.0.0.1:8080/TwitterRestAPIs/showAPIButtons.jsp").build();
	}

	private Token getOAuthRequestToken(OAuthService service) {
		Token requestToken = service.getRequestToken();
		return requestToken;
	}

	protected String getMyTweets() {
		System.out.println("getting my tweets");
		return callTwitterAPI(Verb.GET, ALL_TWEETS_URL);
	}

	protected String getMyFollowers() {
		System.out.println("getting my followers");
		return callTwitterAPI(Verb.GET, FOLLOWERS_URL);
	}

	protected String getMyFriends() {
		System.out.println("getting my friends");
		return callTwitterAPI(Verb.GET, FOLLOWING_URL);
	}

	protected String verifyCredentials() {
		System.out.println("verify my credentials");
		return callTwitterAPI(Verb.GET, CREDENTIALS_URL);
	}

	protected String getSearchTweets() {
		System.out.println("search tweets");
		return callTwitterAPI(Verb.GET, SEARCH_TWEETS_URL);
	}

	protected String getFavoriteTweets() {
		System.out.println("favorite tweets");
		return callTwitterAPI(Verb.GET, FAVORITE_TWEETS);
	}

	protected String postStatus() {
		System.out.println("post tweet");
		return callTwitterAPI(Verb.POST, POST_STATUS);
	}
	protected String getTimelineTweets() {
		System.out.println("timeline tweets");
		return callTwitterAPI(Verb.GET, TIMELINE_TWEETS);
	}

	protected String callTwitterAPI(Verb httpMethod, String url) {
		String responseBody = "";
		try {
			OAuthRequest request = new OAuthRequest(httpMethod, url);
			if (newTweet != null) {
				request.addBodyParameter("status", newTweet);
			}
			service.signRequest(accessToken, request);
			Response response = request.send();
			System.out.println(response.getBody());
			responseBody = response.getBody();

		} catch (Exception e) {
			System.out.println("exception in parsing twitter response " + e.getMessage());
		}
		return responseBody;

	}

}
