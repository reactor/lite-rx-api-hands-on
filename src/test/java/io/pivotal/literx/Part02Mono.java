package io.pivotal.literx;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Learn how to create Mono instances.
 *
 * @author Sebastien Deleuze
 * @see <a href="http://projectreactor.io/core/docs/api/reactor/core/publisher/Mono.html">Mono Javadoc</a>
 */
public class Part02Mono {

//========================================================================================

	@Test
	public void empty() {
		Mono<String> mono = emptyMono();
		StepVerifier.create(mono)
				.expectNextCount(0)
				.expectComplete()
				.verify();
	}

	// TODO Return an empty Mono
	Mono<String> emptyMono() {
		return null;
	}

//========================================================================================

	@Test
	public void fromValue() {
		Mono<String> mono = fooMono();
		StepVerifier.create(mono)
				.expectNext("foo")
				.expectComplete()
				.verify();
	}

	// TODO Return a Mono that contains a "foo" value
	Mono<String> fooMono() {
		return null;
	}

//========================================================================================

	@Test
	public void error() {
		Mono<String> mono = errorMono();
		StepVerifier.create(mono)
				.expectError(IllegalStateException.class)
				.verify();
	}

	// TODO Create a Mono that emits an IllegalStateException
	Mono<String> errorMono() {
		return null;
	}

}
