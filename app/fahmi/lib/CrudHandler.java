package fahmi.lib;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;

import controllers.ApplicationSiswa;
import models.Auth;
import models.Siswa;
import models.User;
import play.api.libs.iteratee.Cont;
import play.data.Form;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class CrudHandler<T extends Model> implements Constants{
	
	private boolean checkAuth = false;
	
	public CrudHandler(boolean checkAuth) {
		super();
		this.checkAuth = checkAuth;
	}

	
	public CrudHandler() {
		super();
	}
	
	/**
	 * generic create method 
	 * @param form
	 * @return
	 */
	public Result create(Form form){
		if(checkAuth){
			String authMessage = findAuth(form.data());
			if(!authMessage.equals(SUCCESS)){
				return Controller.badRequest(JsonHandler.getSuitableResponse(authMessage, false));
			}
		}
    	if(form.hasErrors()){
    		return Controller.badRequest(JsonHandler.getSuitableResponse(form, false));
    	}else {
    		T data = (T) form.get();
    		data.save();
    		return Controller.ok(JsonHandler.getSuitableResponse(data, true));
    	}
    }
	
	/**
	 * generic read method
	 * @param form
	 * @param finder
	 * @return
	 */
	public Result read(Form form, Finder finder){
		if(checkAuth){
			String authMessage = findAuth(form.data());
			if(!authMessage.equals(SUCCESS)){
				return Controller.badRequest(authMessage);
			}
		}
		List<T> list = finder.all();
    	return Controller.ok(JsonHandler.getSuitableResponse(list, true));
		
	}
	
	/**
	 * generic update method
	 * @param form
	 * @return
	 */
	public Result update(Form form){
		if(checkAuth){
			String authMessage = findAuth(form.data());
			if(!authMessage.equals(SUCCESS)){
				return Controller.badRequest(JsonHandler.getSuitableResponse(authMessage, false));
			}
		}
		if(form.hasErrors()){
    		return Controller.badRequest(JsonHandler.getSuitableResponse(form, false));
    	}else {
    		T data = (T) form.get();
    		data.update();
    		return Controller.ok(JsonHandler.getSuitableResponse(data, true));
    	}
	}
	
	/**
	 * generic delete method
	 * @param form
	 * @param id
	 * @param finder
	 * @return
	 */
	public Result delete(Form form, String id, Finder finder){
		if(checkAuth){
			String authMessage = findAuth(form.data());
			if(!authMessage.equals(SUCCESS)){
				return Controller.badRequest(JsonHandler.getSuitableResponse(authMessage, false));
			}
		}
		System.out.print(form.data());
    	String idValue = (String) form.data().get(id);
    	if(idValue == null){
    		return Controller.badRequest(JsonHandler.getSuitableResponse("require " + id, false));
    	}else {
    		T siswa = (T) finder.byId(idValue);
    		if(siswa == null){
    			return Controller.badRequest(JsonHandler.getSuitableResponse("data not found", false));
    		}
    		siswa.delete();
    		return Controller.ok(JsonHandler.getSuitableResponse("success delete data", true));
    	}
	}
	
	public String findAuth(Map map){
		String auth_key = (String) map.get(AUTH_KEY);
		if(auth_key == null) {
			return AUTH_NOT_FOUND;
		}
		Auth auth = Auth.findAuth(auth_key);
		if(auth == null){
			return USER_NOT_FOUND;
		}
		return SUCCESS;
	}
	
	public Map findKey(Form form, String [] listKey){
		Map<String, Object> requestMap = form.data();
		HashMap<String, Object> map = new HashMap<>();
		if(checkAuth){
			String authKeyStatus =  findAuth(requestMap);
			if(!authKeyStatus.equalsIgnoreCase(SUCCESS)){
				map.put(ERROR, authKeyStatus);
				return map;
			}
			else {
				map.put(AUTH_KEY, requestMap.get(AUTH_KEY));
			}
		}
		
		for (String string : listKey) {
			String key = (String) form.data().get(string);
			if(key == null){
				map.put(ERROR, "require " + string);
				break;
			}
			map.put(string, key);
		}
		return map;
	}
	
	public void setCheckAuth(boolean checkAuth) {
		this.checkAuth = checkAuth;
	}
	
}
