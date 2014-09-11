package controllers;

import java.util.Map;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;

import models.MataPelajaran;
import models.OrangTua;
import models.Siswa;
import fahmi.lib.Constants;
import fahmi.lib.CrudHandler;
import fahmi.lib.JsonHandler;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import scala.annotation.meta.param;

public class ApplicationOrangTua extends Controller implements Constants {
	public static Form<OrangTua> frmOrangTua = Form.form(OrangTua.class);
	public static CrudHandler<OrangTua> crudHandler = new CrudHandler<OrangTua>(
			true);

	public static Result createOrangTua() {
		return crudHandler.create(frmOrangTua.bindFromRequest());
	}

	public static Result readOrangTua() {
		return crudHandler.read(frmOrangTua.bindFromRequest(), OrangTua.finder);
	}

	public static Result updateOrangTua() {
		return crudHandler.update(frmOrangTua.bindFromRequest());
	}

	public static Result deleteOrangTua() {
		return crudHandler.delete(frmOrangTua.bindFromRequest(), "idOrangTua",
				OrangTua.finder);
	}

	public static Result addSiswaToOrangTua() {
		Form<OrangTua> bindOrangTua = frmOrangTua.bindFromRequest();
		String arrayKey[] = { "auth_key", "nim", "idOrangTua" };
		Map<String, Object> keyPair = crudHandler.findKey(bindOrangTua,
				arrayKey);
		if (keyPair.containsKey(ERROR)) {
			return badRequest(JsonHandler.getSuitableResponse(
					keyPair.get(ERROR), false));
		}
		String message = crudHandler.findAuth(keyPair);
		if (!message.equals(SUCCESS)) {
			return badRequest(JsonHandler.getSuitableResponse(message, false));
		}

		Siswa siswa = Siswa.finder.byId((String) keyPair.get("nim"));
		if (siswa == null) {
			return badRequest(JsonHandler.getSuitableResponse(
					"Siswa not found", false));
		}

		OrangTua orangTua = OrangTua.finder.byId(Long
				.parseLong((String) keyPair.get("idOrangTua")));
		if (orangTua == null) {
			return badRequest(JsonHandler.getSuitableResponse(
					"Orang tua not found", false));
		}
		siswa.orangTua = orangTua;
		orangTua.listSiswa.add(siswa);
		Ebean.save(orangTua);
		return ok(JsonHandler.getSuitableResponse(
				"Success add siswa to Orang tua", true));
	}

	public static Result removeSiswaFromOrangTua() {
		Form<OrangTua> bindOrangTua = frmOrangTua.bindFromRequest();
		String arrayKey[] = { "auth_key", "nim", "idOrangTua" };
		Map<String, Object> keyPair = crudHandler.findKey(bindOrangTua,
				arrayKey);
		if (keyPair.containsKey(ERROR)) {
			return badRequest(JsonHandler.getSuitableResponse(
					keyPair.get(ERROR), false));
		}
		String message = crudHandler.findAuth(keyPair);
		if (!message.equals(SUCCESS)) {
			return badRequest(JsonHandler.getSuitableResponse(message, false));
		}

		Siswa siswa = Siswa.finder.byId((String) keyPair.get("nim"));
		if (siswa == null) {
			return badRequest(JsonHandler.getSuitableResponse(
					"Siswa not found", false));
		}

		OrangTua orangTua = OrangTua.finder.byId(Long
				.parseLong((String) keyPair.get("idOrangTua")));
		if (orangTua == null) {
			return badRequest(JsonHandler.getSuitableResponse(
					"Orang tua not found", false));
		}

		siswa.orangTua = null;
		orangTua.listSiswa.remove(siswa);
		Ebean.save(orangTua);
		return ok(JsonHandler.getSuitableResponse(
				"Success add siswa to Orang tua", true));
	}
}
