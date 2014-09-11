package fahmi.lib;

import models.Kelas;
import models.Siswa;
import play.data.Form;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
/**
 * deprecated api. To dificult to use.
 * @author fahmi
 *
 * @param <O>
 * @param <I>
 */
@Deprecated
public abstract class FormHandler<O, I>{
	private Finder modelOwner;
	private Finder modelInverseOwner;
	
	public FormHandler(Finder modelOwner, Finder modelInverseOwner) {
		super();
		this.modelOwner = modelOwner;
		this.modelInverseOwner = modelInverseOwner;
	}

	public Result checkAndExecute(Form form, String idOwner, String idInverseOwner){
		Form<Kelas> frmKelas = Form.form(Kelas.class).bindFromRequest();
		String idOwnerValue = frmKelas.data().get(idOwner);
		String idInverseOwnerValue = frmKelas.data().get(idInverseOwner);
		if(idOwnerValue == null || idOwnerValue == null){
			return Controller.badRequest(JsonHandler.getSuitableResponse("require data", false));
		}
		else {
			O owner = (O) modelOwner.byId(idOwnerValue);
			I inverseOwner = (I) modelInverseOwner.byId(idInverseOwnerValue);
			if(owner == null || inverseOwner == null){
				return Controller.badRequest(JsonHandler.getSuitableResponse("data not found", false));
			}
			String message = execute((O)owner, (I) inverseOwner);
			return Controller.ok(JsonHandler.getSuitableResponse(message, true));
		}
	}
	
	public abstract String execute(O owner, I inverseOwner);
}
