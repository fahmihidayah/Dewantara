package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import models.Auth;
import models.Guru;
import models.User;
import fahmi.lib.JsonHandler;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class SecurityController extends Controller{
	public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";
    public static String ACCOUNT  = "accountType";
    /**
     * never used
     * @return
     */
    @Deprecated
    public static User getUser(){
    	return (User) Http.Context.current().args.get("user");
    }
    
    public static Result login(){
    	Form<Login> formLogin = Form.form(Login.class).bindFromRequest();
    	if(formLogin.hasErrors()){
    		return badRequest(JsonHandler.getSuitableResponse(formLogin, false));
    	}
    	
    	Login login = formLogin.get();
    	User user = Auth.findUser(login.userName, login.password);
    	if(user == null){
    		return badRequest(JsonHandler.getSuitableResponse("user not found", false));
    	}
    	else {
    		Auth auth = new Auth();
    		auth.createToken();
    		auth.save();
    		response().setCookie(AUTH_TOKEN, auth.authToken);
    		ObjectNode data = Json.newObject();
    		
    		data.put(AUTH_TOKEN, auth.authToken);
//    		data.put("userName", login.userName);
//    		data.put("password", login.password);
//    		data.put(ACCOUNT, user.type);
    		
    		return ok(JsonHandler.getSuitableResponse(data, true));
    	}
    }
    
    public static Result logout(){
    	Form<Login> formLogin = Form.form(Login.class).bindFromRequest();
    	String authToken = formLogin.data().get("authToken");
    	if(authToken == null){
    		return badRequest(JsonHandler.getSuitableResponse("data not found", false));
    	}
    	
    	Auth auth = Auth.findAuth(authToken);
    	if(auth == null){
    		return badRequest(JsonHandler.getSuitableResponse("auth not found", false));
    	}
    	auth.delete();
    	return ok(JsonHandler.getSuitableResponse("log out", true));
    }
    
    public static class Login {
    	@Required
    	public String userName;
    	@Required
    	public String password;
    	
    	public String authToken;
    	
    }
}
