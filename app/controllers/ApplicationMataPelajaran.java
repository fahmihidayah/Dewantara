package controllers;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;

import fahmi.lib.CrudHandler;
import fahmi.lib.JsonHandler;
import models.MataPelajaran;
import models.Siswa;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.*;
public class ApplicationMataPelajaran extends Controller{
	public static Form<MataPelajaran> frmMataPelajaran = Form.form(MataPelajaran.class);
	public static CrudHandler<MataPelajaran> crudHandler = new CrudHandler<MataPelajaran>(true);
	public static Result insert(){
		return crudHandler.create(frmMataPelajaran.bindFromRequest());
	}
	
	public static Result delete(){
		return crudHandler.delete(frmMataPelajaran.bindFromRequest(), "idMataPelajaran", MataPelajaran.finder);
	}
	
	public static Result edit(){
		return crudHandler.update(frmMataPelajaran.bindFromRequest());
	}
	
	public static Result list(){
		return crudHandler.read(frmMataPelajaran.bindFromRequest(), MataPelajaran.finder);
	}

}
