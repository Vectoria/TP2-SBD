package usr;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class User {

	private BigDecimal userid = null; // Automático
	private LocalDateTime updated = null; // Automático
	private byte profile = 0; // Perfil de utilizador
	private String username = null;
	private String password = null; // md5 -> 32; SHA2('password', 256)->64
	private String firstname = null;
	private String lastname = null;
	private String email = null;
	private int nif; // Ajustado para minúsculas

	public BigDecimal getUserid() {
		return userid;
	}

	public void setUserid(BigDecimal userid) {
		this.userid = userid;
	}

	public LocalDateTime getUpdated() {
		if (updated == null)
			return LocalDateTime.now();
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}

	public byte getProfile() {
		return profile;
	}

	public void setProfile(byte profile) {
		this.profile = profile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNif() { // Ajustado para minúsculas
		return nif;
	}

	public void setNif(int nif) { // Ajustado para minúsculas
		this.nif = nif;
	}

	public String welcome() {
		if (userid == null)
			return "There is no valid user!";
		else
			return "Welcome " + firstname + " " + lastname + "!";
	}

	public void show() {
		if (userid == null)
			System.out.println("Nothing to do!");
		else {
			System.out.println("Show user data:");
			System.out.println("  userID:    " + userid);
			System.out.println("  updated:   " + updated);
			System.out.println("  profile:   " + profile);
			System.out.println("  username:  " + username);
			System.out.println("  password:  " + password);
			System.out.println("  firstname: " + firstname);
			System.out.println("  lastname:  " + lastname);
			System.out.println("  email:     " + email);
			System.out.println("  nif:       " + nif);
		}
	}
}
