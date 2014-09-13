package controllers;

import java.util.List;

import models.Kelas;
import models.OrangTua;
import models.Siswa;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fahmi.lib.CrudHandler;
import fahmi.lib.JsonHandler;
import fahmi.lib.RequestHandler;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.helper.form;
/**
 * fix
 * @author fahmi
 *
 */
public class ApplicationSiswa extends Controller {
	public static Form<Siswa> frmSiswa = Form.form(Siswa.class);
	public static CrudHandler<Siswa> crudHandler = new CrudHandler<Siswa>(true);
	public static RequestHandler requestHandler = new RequestHandler(frmSiswa);
	
	public static Result insert() {
		String arrayKey [] = {"nim", "name", "address", "id_orang_tua", "id_kelas"};
		requestHandler.setArrayKey(arrayKey);
		requestHandler.checkError();
		if(requestHandler.isContainError()){
			return badRequest(JsonHandler.getSuitableResponse(requestHandler.getErrorMessage(), false));
		}
		
		String nim = requestHandler.getStringValue("nim");
		String name = requestHandler.getStringValue("name");
		String address = requestHandler.getStringValue("address");
		Long idOrangTua = requestHandler.getLongValue("id_orang_tua");
		Long idKelas = requestHandler.getLongValue("id_kelas");
		
		OrangTua orangTua = OrangTua.finder.byId(idOrangTua);
		Kelas kelas = Kelas.finder.byId(idKelas);
		
		Siswa siswa = new Siswa();
		siswa.nim = nim;
		siswa.name = name;
		siswa.address = address;
		siswa.orangTua = orangTua;
		orangTua.listSiswa.add(siswa);
		siswa.kelas = kelas;
		kelas.listSiswa.add(siswa);
		Ebean.save(kelas);
		Ebean.save(orangTua);
		
		return ok(JsonHandler.getSuitableResponse(siswa, true));
	}

	public static Result list() {
		return crudHandler.read(frmSiswa.bindFromRequest(), Siswa.finder);
	}

	public static Result edit() {
		return crudHandler.update(frmSiswa.bindFromRequest());
	}

	public static Result delete() {
		return crudHandler.delete(frmSiswa.bindFromRequest(), "nim",
				Siswa.finder);
	}
}
