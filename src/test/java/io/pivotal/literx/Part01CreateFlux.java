package io.pivotal.literx;

import java.time.Duration;
import java.util.Arrays;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.subscriber.ScriptedSubscriber;

/**
 * Learn how to create Flux instances.
 *
 * @author Sebastien Deleuze
 * @see <a href="http://projectreactor.io/core/docs/api/reactor/core/publisher/Flux.html">Flux Javadoc</a>
 * @see <a href="https://github.com/reactor/reactor-addons/blob/master/reactor-test/src/main/java/reactor/test/subscriber/ScriptedSubscriber.java>ScriptedSubscriber</a>
 */
public class Part01CreateFlux {

//========================================================================================

	@Test
	public void empty() {
		Flux<String> flux = emptyFlux();

		ScriptedSubscriber
				.expectValueCount(0)
				.expectComplete()
				.verify(flux);
	}

	// TODO Return an empty Flux
	Flux<String> emptyFlux() {
		return null;
	}

//========================================================================================

	@Test
	public void fromValues() {
		Flux<String> flux = fooBarFluxFromValues();
		ScriptedSubscriber
				.create()
				.expectValues("foo", "bar")
				.expectComplete()
				.verify(flux);
	}

	// TODO Return a Flux that contains 2 values "foo" and "bar" without using an array or a collection
	Flux<String> fooBarFluxFromValues() {
		return null;
	}

//========================================================================================

	@Test
	public void fromList() {
		Flux<String> flux = fooBarFluxFromList();
		ScriptedSubscriber
				.create()
				.expectValues("foo", "bar")
				.expectComplete()
				.verify(flux);
	}

	// TODO Create a Flux from a List that contains 2 values "foo" and "bar"
	Flux<String> fooBarFluxFromList() {
		return null;
	}

//========================================================================================

	@Test
	public void error() {
		Flux<String> flux = errorFlux();
		ScriptedSubscriber
				.create()
				.expectError(IllegalStateException.class)
				.verify(flux);
	}
	// TODO Create a Flux that emits an IllegalStateException
	Flux<String> errorFlux() {
		return null;
	}

//========================================================================================

	@Test
	public void countEachSecond() {
		Flux<Long> flux = counter();
		ScriptedSubscriber
				.create()
				.expectValues(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L)
				.expectComplete()
				.verify(flux);
	}

	// TODO Create a Flux that emits increasing values from 0 to 9 each 100ms
	Flux<Long> counter() {
		return null;
	}

}
