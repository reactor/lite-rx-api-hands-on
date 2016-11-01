package io.pivotal.literx;

import java.time.Duration;
import java.util.Arrays;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.subscriber.Verifier;

/**
 * Learn how to create Flux instances.
 *
 * @author Sebastien Deleuze
 * @see <a href="http://projectreactor.io/core/docs/api/reactor/core/publisher/Flux.html">Flux Javadoc</a>
 * @see <a href="https://github.com/reactor/reactor-addons/blob/master/reactor-test/src/main/java/reactor/test/subscriber/Verifier.java>Verifier</a>
 */
public class Part01CreateFlux {

//========================================================================================

	@Test
	public void empty() {
		Flux<String> flux = emptyFlux();

		Verifier.create(flux)
				.expectNextCount(0)
				.expectComplete()
				.verify();
	}

	// TODO Return an empty Flux
	Flux<String> emptyFlux() {
		return Flux.empty(); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void fromValues() {
		Flux<String> flux = fooBarFluxFromValues();
		Verifier.create(flux)
				.expectNext("foo", "bar")
				.expectComplete()
				.verify();
	}

	// TODO Return a Flux that contains 2 values "foo" and "bar" without using an array or a collection
	Flux<String> fooBarFluxFromValues() {
		return Flux.just("foo", "bar"); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void fromList() {
		Flux<String> flux = fooBarFluxFromList();
		Verifier.create(flux)
				.expectNext("foo", "bar")
				.expectComplete()
				.verify();
	}

	// TODO Create a Flux from a List that contains 2 values "foo" and "bar"
	Flux<String> fooBarFluxFromList() {
		return Flux.fromIterable(Arrays.asList("foo", "bar")); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void error() {
		Flux<String> flux = errorFlux();
		Verifier.create(flux)
				.expectError(IllegalStateException.class)
				.verify();
	}
	// TODO Create a Flux that emits an IllegalStateException
	Flux<String> errorFlux() {
		return Flux.error(new IllegalStateException()); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void countEachSecond() {
		Flux<Long> flux = counter();
		Verifier.create(flux)
				.expectNext(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L)
				.expectComplete()
				.verify();
	}

	// TODO Create a Flux that emits increasing values from 0 to 9 each 100ms
	Flux<Long> counter() {
		return Flux
				.interval(Duration.ofMillis(100))
				.take(10);  // TO BE REMOVED
	}

}
