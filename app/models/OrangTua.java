package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class OrangTua extends Model {
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	public Long idOrangTua;
	@Column(nullable = false)
	public String name;
	@Column(nullable = false)
	public String address;
	@Column(nullable = false)
	public String phoneNumber;
	
	@OneToMany(cascade = CascadeType.ALL)
	public List<Siswa> listSiswa = new ArrayList<>();
	
	
	public static Finder<Long, OrangTua> finder = new Finder<Long, OrangTua>(
			Long.class, OrangTua.class);

}
