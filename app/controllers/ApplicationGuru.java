package controllers;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;

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
public class ApplicationGuru extends Controller{
	public static Form<Guru> frmGuru = Form.form(Guru.class);
	public static CrudHandler<Guru> crudHandler = new CrudHandler<Guru>(false);
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
		String authMessage = crudHandler.findAuth(frmGuruBnd.data());
		if(!authMessage.equals(CrudHandler.SUCCESS)){
			return badRequest(JsonHandler.getSuitableResponse("require auth key", false));
		}
		String userName = frmGuruBnd.data().get("userName");
		if(userName == null){
			return badRequest(JsonHandler.getSuitableResponse("error add teacher", false));
		}
		
		User user = User.findByUserName(userName);
		
		if(user == null){
			return badRequest(JsonHandler.getSuitableResponse("user not found", false));
		}
		
		String nik = frmGuruBnd.data().get("nik");
		if(nik == null){
			return badRequest(JsonHandler.getSuitableResponse("require nik", false));
		}
		
		Guru guru = Guru.finder.byId(nik);
		
		if(guru == null){
			return badRequest(JsonHandler.getSuitableResponse("teacher not found", false));
		}
		
		guru.account = user;
		guru.update();
		
		return ok(JsonHandler.getSuitableResponse(guru, true));
		
	}
	
	public static Result setGuruAccount(){
		return TODO;
	}

}
