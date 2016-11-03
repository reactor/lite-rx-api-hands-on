package io.pivotal.literx;

import java.time.Duration;

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
				.expectComplete()
				.verify();
	}

	// TODO Return an empty Mono
	Mono<String> emptyMono() {
		return Mono.empty(); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void noSignal() {
		Mono<String> mono = monoWithNoSignal();
		StepVerifier
				.create(mono)
				.expectSubscription()
				.expectNoEvent(Duration.ofSeconds(1))
				.thenCancel()
				.verify();
	}

	// TODO Return an Mono that never emit any signal
	Mono<String> monoWithNoSignal() {
		return Mono.never(); // TO BE REMOVED
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
		return Mono.just("foo"); // TO BE REMOVED
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
		return Mono.error(new IllegalStateException()); // TO BE REMOVED
	}

}
