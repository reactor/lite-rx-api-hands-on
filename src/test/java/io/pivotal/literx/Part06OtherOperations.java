package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.test.TestSubscriber;

public class Part06OtherOperations {

//========================================================================================

	@Test
	public void zipFirstNameAndLastName() {
		Flux<String> usernameFlux = Flux.just(User.SKYLER.getUsername(), User.JESSE.getUsername(), User.WALTER.getUsername(), User.SAUL.getUsername());
		Flux<String> firstnameFlux = Flux.just(User.SKYLER.getFirstname(), User.JESSE.getFirstname(), User.WALTER.getFirstname(), User.SAUL.getFirstname());
		Flux<String> lastnameFlux = Flux.just(User.SKYLER.getLastname(), User.JESSE.getLastname(), User.WALTER.getLastname(), User.SAUL.getLastname());
		Flux<User> userFlux = userFluxFromStringFlux(usernameFlux, firstnameFlux, lastnameFlux);
		TestSubscriber<User> ts = new TestSubscriber<>();
		ts.bindTo(userFlux).assertValues(User.SKYLER, User.JESSE, User.WALTER, User.SAUL);
	}

	// TODO Create a Flux of user from a Flux of user name, first name and last name.
	Flux<User> userFluxFromStringFlux(Flux<String> usernameFlux, Flux<String> firstnameFlux, Flux<String> lastnameFlux) {
		return Flux.zip(usernameFlux, firstnameFlux, lastnameFlux).map(tuple -> new User(tuple.t1, tuple.t2, tuple.t3));
	}

//========================================================================================

	@Test
	public void or() {

	}

//========================================================================================

	@Test
	public void amb() {

	}

//========================================================================================

	@Test
	public void otherwise() {

	}

//========================================================================================

	@Test
	public void onErrorResumeWith() {

	}

//========================================================================================

	@Test
	public void exceptionWrapping() {
		// Exceptions.fail()
	}

//========================================================================================

	@Test
	public void end() {
		// After and Mono<Void>
	}

}
