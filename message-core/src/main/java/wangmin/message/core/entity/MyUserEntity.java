package wangmin.message.core.entity;

import java.io.Serializable;

/**
 * @author Wang Min
 */
public class MyUserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String id;
	protected int revision;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String password;

	public MyUserEntity() {
	}

	public MyUserEntity(String id) {
		this.id = id;
	}

	public int getRevisionNext() {
		return revision + 1;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRevision() {
		return revision;
	}
	public void setRevision(int revision) {
		this.revision = revision;
	}

}
