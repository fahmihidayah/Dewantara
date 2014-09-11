package controllers;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;

import models.Guru;
import models.User;
import fahmi.lib.Constants;
import fahmi.lib.CrudHandler;
import fahmi.lib.JsonHandler;
import play.*;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

public class Application extends Controller implements Constants{

	public static CrudHandler crudHandler  = new CrudHandler<>(true);
	
    public static Result index() {
        return ok(index.render("Your new application is ready."));
        
    }
    
    public static Result getMetadata(){
    	Form form = Form.form().bindFromRequest();
    	String arrayKey[] = {"userName"};
    	Map<String, Object> map = crudHandler.findKey(form, arrayKey);
    	if(map.containsKey(ERROR)){
    		return badRequest(JsonHandler.getSuitableResponse(map.get(ERROR), false));
    	}
    	User user = User.findByUserName((String) map.get("userName"));
    	if(user == null){
    		return badRequest(JsonHandler.getSuitableResponse("user not found", false));
    	}
    	
    	String accountType = user.type;
    	ObjectNode jsonObject =Json.newObject();
    	if(accountType.equalsIgnoreCase("guru")){
    		Guru guru = Guru.finder.where().eq("account_id", user.id).findUnique();
    		jsonObject.put("guru", Json.toJson(guru));
    	}
    	return ok(JsonHandler.getSuitableResponse(jsonObject, true));
    }

}
