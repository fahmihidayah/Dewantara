package controllers;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;

import fahmi.lib.CrudHandler;
import fahmi.lib.JsonHandler;
import models.User;
import models.Siswa;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.*;
public class ApplicationAccount extends Controller{
	public static Form<User> frmAccount = Form.form(User.class);
	public static CrudHandler<User> crudHandler = new CrudHandler<User>();
	public static Result insert(){
		return crudHandler.create(frmAccount.bindFromRequest());
	}
	
	public static Result delete(){
		return crudHandler.delete(frmAccount.bindFromRequest(), "id", User.finder);
	}
	
	public static Result edit(){
		return crudHandler.update(frmAccount.bindFromRequest());
	}
	
	public static Result list(){
		return crudHandler.read(frmAccount.bindFromRequest(), User.finder);
	}
	
	public static Result addAccountToGuru(){
		return TODO;
	}

}
