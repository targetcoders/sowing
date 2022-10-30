package com.targetcoders.sowing.authentication;

public class LoginConstants {

    public static final String REQUEST_URI = "https://accounts.google.com/o/oauth2/v2/auth";
    public static final String CLIENT_ID = "341020820476-isdn8vj1e925suj59io78sabdhtukmq0.apps.googleusercontent.com";
    public static final String REDIRECT_URI = "http://localhost:8080/login/google/callback";
    public static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile+openid";
    public static final String RESPONSE_TYPE = "code";

}
