package io.pivotal.literx;

import java.time.Duration;
import java.util.function.Supplier;

import io.pivotal.literx.domain.User;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.assertEquals;

/**
 * Learn how to use StepVerifier to test Mono, Flux or any other kind of Reactive Streams Publisher.
 *
 * @author Sebastien Deleuze
 * @see <a href="http://projectreactor.io/docs/test/release/api/reactor/test/StepVerifier.html">StepVerifier Javadoc</a>
 */
public class Part03StepVerifier {

//========================================================================================

	@Test
	public void expectElementsThenComplete() {
		expectFooBarComplete(Flux.just("foo", "bar"));
	}

	// TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then completes successfully.
	void expectFooBarComplete(Flux<String> flux) {
		StepVerifier.create(flux)
				.expectNext("foo", "bar")
				.verifyComplete(); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void expect2ElementsThenError() {
		expectFooBarError(Flux.just("foo", "bar").concatWith(Mono.error(new RuntimeException())));
	}

	// TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then a RuntimeException error.
	void expectFooBarError(Flux<String> flux) {
		StepVerifier.create(flux)
				.expectNext("foo", "bar")
				.verifyError(RuntimeException.class); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void expectElementsWithThenComplete() {
		expectSkylerJesseComplete(Flux.just(new User("swhite", null, null), new User("jpinkman", null, null)));
	}

	// TODO Use StepVerifier to check that the flux parameter emits a User with "swhite" username and another one with "jpinkman" then completes successfully.
	void expectSkylerJesseComplete(Flux<User> flux) {
		StepVerifier.create(flux)
				.expectNextMatches(user -> user.getUsername().equals("swhite"))
				.consumeNextWith(user -> assertEquals("jpinkman", user.getUsername()))
				.verifyComplete(); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void count() {
		expect10Elements(Flux.interval(Duration.ofSeconds(1)).take(10));
	}

	// TODO Expect 10 elements then complete and notice how long the test takes
	void expect10Elements(Flux<Long> flux) {
		StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete(); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void countWithVirtualTime() {
		expect3600Elements(() -> Flux.interval(Duration.ofSeconds(1)).take(3600));
	}

	// TODO Expect 3600 elements then complete using the virtual time capabilities provided via StepVerifier#withVirtualTime and notice how long the test takes
	void expect3600Elements(Supplier<Flux<Long>> supplier) {
		StepVerifier.withVirtualTime(supplier)
                .thenAwait(Duration.ofHours(1))
                .expectNextCount(3600)
                .verifyComplete(); // TO BE REMOVED
	}

}
