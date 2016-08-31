package io.pivotal.literx;

import org.junit.Test;
import reactor.core.publisher.Mono;
import io.pivotal.literx.test.TestSubscriber;

/**
 * Learn how to create Mono instances.
 *
 * @author Sebastien Deleuze
 * @see <a href="http://projectreactor.io/core/docs/api/reactor/core/publisher/Mono.html">Mono Javadoc</a>
 *
 */
public class Part02CreateMono {

//========================================================================================

	@Test
	public void empty() {
		Mono<String> mono = emptyMono();
		TestSubscriber
				.subscribe(mono)
				.assertValueCount(0)
				.assertComplete();
	}

	// TODO Return an empty Mono
	Mono<String> emptyMono() {
		return null;
	}

//========================================================================================

	@Test
	public void fromValue() {
		Mono<String> mono = fooMono();
		TestSubscriber
				.subscribe(mono)
				.assertValues("foo")
				.assertComplete();
	}

	// TODO Return a Mono that contains a "foo" value
	Mono<String> fooMono() {
		return null;
	}

//========================================================================================

	@Test
	public void error() {
		Mono<String> mono = errorMono();
		TestSubscriber
				.subscribe(mono)
				.assertError(IllegalStateException.class)
				.assertNotComplete();
	}

	// TODO Create a Mono that emits an IllegalStateException
	Mono<String> errorMono() {
		return null;
	}

}
