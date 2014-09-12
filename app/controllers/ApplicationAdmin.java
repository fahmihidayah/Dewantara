package controllers;

import java.util.Map;

import models.Admin;
import models.User;
import fahmi.lib.Constants;
import fahmi.lib.CrudHandler;
import fahmi.lib.JsonHandler;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class ApplicationAdmin extends Controller implements Constants {
	private static CrudHandler<Admin> crudHandler = new CrudHandler<Admin>(true);
	private static Form<Admin> frmAdmin = Form.form(Admin.class);
	
	public static Result createAdmin(){
		return crudHandler.create(frmAdmin.bindFromRequest());
	}
	
	public static Result readAdmin(){
		return crudHandler.read(frmAdmin.bindFromRequest(), Admin.finder);
	}
	
	public static Result updateAdmin(){
		return crudHandler.update(frmAdmin.bindFromRequest());
	}
	
	public static Result deleteAdmin(){
		return crudHandler.delete(frmAdmin.bindFromRequest(), "id", Admin.finder);
	}
	
	public static Result addUserToAdmin(){
		Form<Admin> frmAdminBnd = frmAdmin.bindFromRequest();
		String arrayKey[] = {"id", "userName"};
		Map<String, Object> responseMap = crudHandler.findKey(frmAdminBnd, arrayKey);
		if(responseMap.containsKey(ERROR)){
			return badRequest(JsonHandler.getSuitableResponse(responseMap.get(ERROR), false));
		}
		Admin admin = Admin.finder.byId(Long.parseLong((String) responseMap.get("id")));
		if(admin == null){
			return badRequest(JsonHandler.getSuitableResponse("admin account not foound", false));
		}
		
		User user = User.findByUserName((String) responseMap.get("userName"));
		if(user == null){
			return badRequest(JsonHandler.getSuitableResponse("user account not found", false));
		}
		
		admin.user = user;
		admin.update();
		
		return ok(JsonHandler.getSuitableResponse("Success add user to admin", true));
	}

}
