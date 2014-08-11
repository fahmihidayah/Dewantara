package controllers;

import models.Siswa;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.insert_siswa;

public class ApplicationSiswa extends Controller{
	
	static Form<Siswa> formSiswa = Form.form(Siswa.class);
	
	public static Result insert(){
		Form<Siswa> siswaBind = formSiswa.bindFromRequest();
		if(siswaBind.hasErrors()){
			
		}else{
			Siswa siswa = siswaBind.get();
			siswa.save();
		}
		return ok(insert_siswa.render(siswaBind));
	}
	
	public static Result edit(){
		return TODO;
	}
	
	public static Result delete(){
		return TODO;
	}
	
	public static Result getList(){
		return TODO;
	}
}
