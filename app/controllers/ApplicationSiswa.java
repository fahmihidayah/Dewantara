package controllers;

import java.util.List;

import models.Siswa;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fahmi.lib.CrudHandler;
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

	public static Result insert() {
		return crudHandler.create(frmSiswa.bindFromRequest());
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
