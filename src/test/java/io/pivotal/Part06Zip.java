package io.pivotal;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.test.TestSubscriber;

public class Part06Zip {

	@Test
	public void zipFirstNameAndLastName() {
		Flux<String> firstNameFlux = Flux.just(skyler.getFirstName(), jesse.getFirstName(), walter.getFirstName(), saul.getFirstName());
		Flux<String> lastNameFlux = Flux.just(skyler.getLastName(), jesse.getLastName(), walter.getLastName(), saul.getLastName());
		Flux<Person> personFlux = Flux.zip(firstNameFlux, lastNameFlux, (firstName, lastName) -> new Person(firstName, lastName));
		TestSubscriber<Person> ts = new TestSubscriber<>();
		ts.bindTo(personFlux).assertValues(skyler, jesse, walter, saul);
	}


	Person skyler = new Person("Skyler", "White");
	Person jesse = new Person("Jesse", "Pinkman");
	Person walter = new Person("Walter", "Pinkman");
	Person saul = new Person("Saul", "Goodman");
}
