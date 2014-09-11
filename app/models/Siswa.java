package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Siswa extends Model {
	
	@Id
	@Required
	public String nim;
	
	@Required
	public String name;
	
	@Required
	public String address;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	public Kelas kelas;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	public OrangTua orangTua;
	
	public static Finder<String, Siswa> finder = new Finder<String, Siswa>(String.class, Siswa.class);
}
