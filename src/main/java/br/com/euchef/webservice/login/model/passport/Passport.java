package br.com.euchef.webservice.login.model.passport;

import br.com.euchef.webservice.login.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Passport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date insertedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date updatedDate;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(nullable = false)
	private User user;

	// constructor
	public Passport(User user) {
		super();
		this.user = user;
	}

	protected Passport() {
	}

	@PrePersist
	protected void onCreate() {
		insertedDate = updatedDate = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedDate = new Date();
	}

	@Override
	public String toString() {
		return "Passport [id=" + id + ", insertedDate=" + insertedDate + ", updatedDate=" + updatedDate + "]";
	}

	// set e getters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public User getUser() {
		return user;
	}

}
