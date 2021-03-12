package br.com.euchef.webservice.login.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.euchef.webservice.login.model.passport.Passport;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date insertedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date updatedDate;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Passport> passports;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> roles;

	@PrePersist
	protected void onCreate() {
		insertedDate = updatedDate = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedDate = new Date();
	}

	public User(String email) {
		super();
		this.email = email;
	}

	public User() {
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", insertedDate=" + insertedDate + ", updatedDate=" + updatedDate
				+ ", passports=" + passports.toString() + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<Passport> getPassports() {
		return passports;
	}

	public List<Role> getRoles() {
		return roles;
	}

	@JsonIgnore
	public String getRolesCommaSepareted() {
		List<String> authorities = roles.stream().map(role -> role.getName()).collect(Collectors.toList());

		return StringUtils.arrayToDelimitedString(authorities.toArray(), ",");
	}

}
