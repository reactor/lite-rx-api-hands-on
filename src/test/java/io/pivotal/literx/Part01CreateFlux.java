package io.pivotal.literx;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.test.TestSubscriber;

/**
 * Learn how to create Flux instances.
 *
 * @author Sebastien Deleuze
 * @see <a href="http://next.projectreactor.io/core/docs/api/reactor/core/publisher/Flux.html>Flux Javadoc</a>
 * @see <a href="http://next.projectreactor.io/core/docs/api/reactor/core/test/TestSubscriber.html>TestSubscriber Javadoc</a>
 */
public class Part01CreateFlux {

//========================================================================================

	@Test
	public void empty() {
		Flux<String> flux = emptyFlux();
		TestSubscriber<String> ts = new TestSubscriber<>();
		ts.bindTo(flux).assertValueCount(0).assertComplete();
	}

	// TODO Return an empty Flux
	Flux<String> emptyFlux() {
		return Flux.empty(); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void fromValues() {
		Flux<String> flux = fooBarFluxFromValues();
		TestSubscriber<String> ts = new TestSubscriber<>();
		ts.bindTo(flux).assertValues("foo", "bar").assertComplete();
	}

	// TODO Return a Flux that contains 2 values "foo" and "bar" without using an array or a collection
	Flux<String> fooBarFluxFromValues() {
		return Flux.just("foo", "bar"); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void fromList() {
		Flux<String> flux = fooBarFluxFromList();
		TestSubscriber<String> ts = new TestSubscriber<>();
		ts.bindTo(flux).assertValues("foo", "bar").assertComplete();
	}

	// TODO Create a Flux from anList that contains 2 values "foo" and "bar"
	Flux<String> fooBarFluxFromList() {
		return Flux.fromIterable(Arrays.asList("foo", "bar")); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void error() {
		Flux<String> flux = errorFlux();
		TestSubscriber<String> ts = new TestSubscriber<>();
		ts.bindTo(flux).assertError(IllegalStateException.class).assertNotComplete();
	}

	// TODO Create a Flux that emits an IllegalStateException
	Flux<String> errorFlux() {
		return Flux.error(new IllegalStateException()); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void neverTerminates() {
		Flux<String> flux = neverTerminatedFlux();
		TestSubscriber<String> ts = new TestSubscriber<>();
		ts.bindTo(flux).assertNotTerminated();
	}

	// TODO Create a Flux that never terminates
	Flux<String> neverTerminatedFlux() {
		return Flux.never(); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void countEachSecond() {
		Flux<Long> flux = counter();
		TestSubscriber<Long> ts = new TestSubscriber<>();
		ts.bindTo(flux).awaitAndAssertValues(0L, 1L, 2L);
	}

	// TODO Create a Flux that emits an increasing value each 100ms
	Flux<Long> counter() {
		return Flux.interval(100, TimeUnit.MILLISECONDS);  // TO BE REMOVED
	}

}
