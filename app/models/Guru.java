package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
@Entity
public class Guru extends Model{
	@Id
	@Required
	public String nik;
	@Required
	public String name;
	@Required
	public String address;
	@Required
	public String phone;
	@Required
	public String email;
	
	@OneToOne(cascade = CascadeType.ALL)
//	@JsonIgnore
	public User account;
	
	public static Finder<String, Guru> finder = new Finder<String, Guru>(String.class, Guru.class);
}
