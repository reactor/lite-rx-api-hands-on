package io.pivotal.literx.domain;

public class User {

	public static final User SKYLER = new User("swhite", "Skyler", "White");
	public static final User JESSE = new User("jpinkman", "Jesse", "Pinkman");
	public static final User WALTER = new User("wwhite", "Walter", "White");
	public static final User SAUL = new User("sgoodman", "Saul", "Goodman");

	private final String username;

	private final String firstname;

	private final String lastname;

	public User(String username, String firstname, String lastname) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		User user = (User) o;

		if (!username.equals(user.username)) {
			return false;
		}
		if (!firstname.equals(user.firstname)) {
			return false;
		}
		return lastname.equals(user.lastname);

	}

	@Override
	public int hashCode() {
		int result = username.hashCode();
		result = 31 * result + firstname.hashCode();
		result = 31 * result + lastname.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Person{" +
				"username='" + username + '\'' +
				", firstname='" + firstname + '\'' +
				", lastname='" + lastname + '\'' +
				'}';
	}
}
