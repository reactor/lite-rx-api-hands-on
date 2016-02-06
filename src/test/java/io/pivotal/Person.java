package io.pivotal;

public class Person {



	private final String firstName;

	private final String lastName;

	public Person(String firstname, String lastname) {
		this.firstName = firstname;
		this.lastName = lastname;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Person person = (Person) o;
		if (!firstName.equals(person.firstName)) {
			return false;
		}
		return lastName.equals(person.lastName);
	}

	@Override
	public int hashCode() {
		int result = firstName.hashCode();
		result = 31 * result + lastName.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Person{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}

}
