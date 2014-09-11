package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class MataPelajaran extends Model{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long idMataPelajaran;
	@Required
	public String namaMataPelajaran;
	
	public static Finder<Long, MataPelajaran> finder = new Finder<Long, MataPelajaran>(Long.class, MataPelajaran.class);
}
