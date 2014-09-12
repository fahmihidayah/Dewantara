package controllers;

import java.util.List;
import java.util.Map;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;

import fahmi.lib.Constants;
import fahmi.lib.CrudHandler;
import fahmi.lib.JsonHandler;
import models.Kelas;
import models.Siswa;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.*;
/**
 * fix
 * @author fahmi
 *
 */
public class ApplicationKelas extends Controller implements Constants{
	public static Form<Kelas> frmKelas = Form.form(Kelas.class);
	public static CrudHandler<Kelas> crudHandler = new CrudHandler<Kelas>(true);

	public static Result insert() {
		return crudHandler.create(frmKelas.bindFromRequest());
	}

	public static Result delete() {
		return crudHandler.delete(frmKelas.bindFromRequest(), "idKelas",
				Kelas.finder);
	}

	public static Result edit() {
		return crudHandler.update(frmKelas.bindFromRequest());
	}

	public static Result list() {
		return crudHandler.read(frmKelas.bindFromRequest(), Kelas.finder);
	}

	public static Result addSiswa() {
		Form<Kelas> frmKelas = Form.form(Kelas.class).bindFromRequest();
		String listKey[] = {"idKelas", "nim"};
		Map<String, Object> resultData = crudHandler.findKey(frmKelas, listKey);
		if(resultData.containsKey(ERROR)){
			return badRequest(JsonHandler.getSuitableResponse(resultData.get(ERROR), false));
		}
		String message = crudHandler.findAuth(resultData);
		if(!message.equals(SUCCESS)){
			return badRequest(JsonHandler.getSuitableResponse(message, false));
		}
		Kelas kelas =Kelas.finder.byId(Long.parseLong((String) resultData.get("idKelas")));
		if(kelas == null){
			return badRequest(JsonHandler.getSuitableResponse("kelas not found", false));
		}
		
		Siswa siswa = Siswa.finder.byId((String) resultData.get("nim"));
		if(siswa == null){
			return badRequest(JsonHandler.getSuitableResponse("siswa not found", false));
		}
		
		siswa.kelas = kelas;
		Ebean.save(siswa);
		kelas.listSiswa.add(siswa);
		Ebean.save(kelas);
		return ok(JsonHandler.getSuitableResponse("success insert data", false));
	}

	public static Result removeSiswa() {
		Form<Kelas> frmKelas = Form.form(Kelas.class).bindFromRequest();
		String listKey[] = {"idKelas", "nim"};
		Map<String, Object> resultData = crudHandler.findKey(frmKelas, listKey);
		if(resultData.containsKey(ERROR)){
			return badRequest(JsonHandler.getSuitableResponse(resultData.get(ERROR), false));
		}
		String message = crudHandler.findAuth(resultData);
		if(!message.equals(SUCCESS)){
			return badRequest(JsonHandler.getSuitableResponse(message, false));
		}
		Kelas kelas =Kelas.finder.byId(Long.parseLong((String) resultData.get("idKelas")));
		if(kelas == null){
			return badRequest(JsonHandler.getSuitableResponse("kelas not found", false));
		}
		
		Siswa siswa = Siswa.finder.byId((String) resultData.get("nim"));
		if(siswa == null){
			return badRequest(JsonHandler.getSuitableResponse("siswa not found", false));
		}
		
		siswa.kelas = null;
		Ebean.save(siswa);
		kelas.listSiswa.remove(siswa);
		Ebean.save(kelas);
		return ok(JsonHandler.getSuitableResponse("success insert data", false));
	}
}
