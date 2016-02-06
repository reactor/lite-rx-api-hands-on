package io.pivotal;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.test.TestSubscriber;

/**
 * Learn how to transform values
 */
public class Part03TransformValues {

//========================================================================================

	@Test
	public void transformMono() {
		Mono<Person> mono = Mono.just(new Person("Skyler", "White"));
		TestSubscriber<Person> ts = new TestSubscriber<>();
		ts.bindTo(capitalizeOne(mono)).assertValues(new Person("SKYLER", "WHITE")).assertComplete();
	}

	// TODO Capitalize the person firstName and lastName
	Mono<Person> capitalizeOne(Mono<Person> mono) {
		return mono.map(person ->
				new Person(person.getFirstName().toUpperCase(), person.getLastName().toUpperCase())); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void transformFlux() {
		Flux<Person> flux = Flux.just(new Person("Skyler", "White"), new Person("Jesse", "Pinkman"));
		TestSubscriber<Person> ts = new TestSubscriber<>();
		ts.bindTo(capitalizeMany(flux)).assertValues(
				new Person("SKYLER", "WHITE"),
				new Person("JESSE", "PINKMAN")).assertComplete();
	}

	// TODO Capitalize the persons firstName and lastName
	Flux<Person> capitalizeMany(Flux<Person> flux) {
		return flux.map(person ->
				new Person(person.getFirstName().toUpperCase(), person.getLastName().toUpperCase())); // TO BE REMOVED
	}


}
