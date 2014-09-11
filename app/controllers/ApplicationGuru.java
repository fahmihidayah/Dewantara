package controllers;

import java.util.List;
import java.util.Map;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;

import fahmi.lib.Constants;
import fahmi.lib.CrudHandler;
import fahmi.lib.JsonHandler;
import models.Guru;
import models.Siswa;
import models.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.*;
/**
 * not fix yet
 * @author fahmi
 *
 */
public class ApplicationGuru extends Controller implements Constants{
	public static Form<Guru> frmGuru = Form.form(Guru.class);
	public static CrudHandler<Guru> crudHandler = new CrudHandler<Guru>(true);
	public static Result insert(){
		return crudHandler.create(frmGuru.bindFromRequest());
	}
	
	public static Result delete(){
		return crudHandler.delete(frmGuru.bindFromRequest(), "nik", Guru.finder);
	}
	
	public static Result edit(){
		return crudHandler.update(frmGuru.bindFromRequest());
	}
	
	public static Result list(){
		return crudHandler.read(frmGuru.bindFromRequest(), Guru.finder);
	}
	
	public static Result addGuruAccount(){
		Form<Guru> frmGuruBnd = frmGuru.bindFromRequest();
		String arrayKey[] = {"userName","nik" };
		
		Map<String, Object> resultData = crudHandler.findKey(frmGuruBnd, arrayKey);
		if(resultData.containsKey(ERROR)){
			return badRequest(JsonHandler.getSuitableResponse(resultData.get(ERROR),false));
		}
		
		User user = User.findByUserName((String) resultData.get("userName"));
		
		if(user == null){
			return badRequest(JsonHandler.getSuitableResponse("user not found", false));
		}
		
		Guru guru = Guru.finder.byId((String) resultData.get("nik"));
		
		if(guru == null){
			return badRequest(JsonHandler.getSuitableResponse("teacher not found", false));
		}
		user.type = "GURU";
		user.update();
		guru.account = user;
		guru.update();
		
		return ok(JsonHandler.getSuitableResponse(guru, true));
		
	}

}
