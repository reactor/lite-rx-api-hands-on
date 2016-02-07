package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveUserRepository;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.test.TestSubscriber;

/**
 * Learn how to transform values
 */
public class Part03Transform {

	ReactiveUserRepository repository = new ReactiveUserRepository();

//========================================================================================

	@Test
	public void transformMono() {
		Mono<User> mono = repository.findFirst();
		TestSubscriber<User> ts = new TestSubscriber<>();
		ts.bindTo(capitalizeOne(mono)).await().assertValues(new User("SWHITE", "SKYLER", "WHITE")).assertComplete();
	}

	// TODO Capitalize the user username, firstname and lastname
	Mono<User> capitalizeOne(Mono<User> mono) {
		return mono.map(u -> new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase())); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void transformFlux() {
		Flux<User> flux = repository.findAll();
		TestSubscriber<User> ts = new TestSubscriber<>();
		ts.bindTo(capitalizeMany(flux)).await().assertValues(
				new User("SWHITE", "SKYLER", "WHITE"),
				new User("JPINKMAN", "JESSE", "PINKMAN"),
				new User("WWHITE", "WALTER", "WHITE"),
				new User("SGOODMAN", "SAUL", "GOODMAN")
				).assertComplete();
	}

	// TODO Capitalize the users firstName and lastName
	Flux<User> capitalizeMany(Flux<User> flux) {
		return flux.map(u -> new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase())); // TO BE REMOVED
	}


}
