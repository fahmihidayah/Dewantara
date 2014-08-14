package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Siswa;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.insert_siswa;
import views.html.helper.form;

public class ApplicationSiswa extends Controller{
	
	static Form<Siswa> formSiswa = Form.form(Siswa.class);
	
	public static Result insert(){
		Form<Siswa> siswaBind = formSiswa.bindFromRequest();
		ObjectNode result = Json.newObject();
		if(siswaBind.hasErrors()){
			result.put("status", 400);			
			result.put("message", siswaBind.errorsAsJson().toString());
			return badRequest(result);
		}else{
			Siswa siswa = siswaBind.get();
			siswa.save();
			result.put("status", "200");
			result.put("message", "success insert data");
			result.put("data", Json.toJson(siswa));
			return ok(result);
		}
	}
	
	public static Result edit(){
		Form<Siswa> siswaFrm = Form.form(Siswa.class).bindFromRequest();
		ObjectNode result = Json.newObject();
		if(siswaFrm.hasErrors()){
			result.put("status", 400);
			result.put("message", siswaFrm.errorsAsJson().toString());
			return badRequest(result);
		}
		else {
			Siswa siswa = siswaFrm.get();
			siswa.update();
			result.put("status", "200");
			result.put("message", "success edit data");
			result.put("data", Json.toJson(siswa));
			return ok(result);
		}
	
	}
	
	public static Result delete(Long id){
		ObjectNode result = Json.newObject();
		if(id == null){
			result.put("status", 400);
			result.put("message", "require nim");
			return badRequest(result);
		}
		else {
			Siswa siswa = Siswa.find.byId(id);
			siswa.delete();
			result.put("status", "200");
			result.put("message", "success delete data");
			result.put("data", Json.toJson(siswa));
			return ok(result);
		}
	}
	
	public static Result getList(){
		List<Siswa> listSiswa =Siswa.find.all();
		ObjectNode result= Json.newObject();
		result.put("status", 200);
		result.put("message", "success");
		result.put("data", Json.toJson(listSiswa));
    	return ok(result)	;
	}
	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result insertSiswaApi(){
		JsonNode json = Controller.request().body().asJson();
		if(json == null){
			return ok("null");
		}
		System.out.println(json.toString());
//		String nim = json.findPath("name").textValue();
//		if(nim == null){
//			ObjectNode objectNode = Json.newObject();
//			objectNode.put("status", "404");
//			objectNode.put("message", "error");
//			return ok(objectNode);
//		}
//		else {
//			ObjectNode objectNode = Json.newObject();
//			objectNode.put("status", "200");
//			objectNode.put("message", "success insert");
//			return ok(objectNode);
//		}
		return ok("ok");
	}
}
