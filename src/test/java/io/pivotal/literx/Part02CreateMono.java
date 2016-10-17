package io.pivotal.literx;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.subscriber.ScriptedSubscriber;

/**
 * Learn how to create Mono instances.
 *
 * @author Sebastien Deleuze
 * @see <a href="http://projectreactor.io/core/docs/api/reactor/core/publisher/Mono.html">Mono Javadoc</a>
 * @see <a href="https://github.com/reactor/reactor-addons/blob/master/reactor-test/src/main/java/reactor/test/subscriber/ScriptedSubscriber.java>ScriptedSubscriber</a>
 */
public class Part02CreateMono {

//========================================================================================

	@Test
	public void empty() {
		Mono<String> mono = emptyMono();
		ScriptedSubscriber
				.expectValueCount(0)
				.expectComplete()
				.verify(mono);
	}

	// TODO Return an empty Mono
	Mono<String> emptyMono() {
		return Mono.empty(); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void fromValue() {
		Mono<String> mono = fooMono();
		ScriptedSubscriber
				.create()
				.expectValues("foo")
				.expectComplete()
				.verify(mono);
	}

	// TODO Return a Mono that contains a "foo" value
	Mono<String> fooMono() {
		return Mono.just("foo"); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void error() {
		Mono<String> mono = errorMono();
		ScriptedSubscriber
				.create()
				.expectError(IllegalStateException.class)
				.verify(mono);
	}

	// TODO Create a Mono that emits an IllegalStateException
	Mono<String> errorMono() {
		return Mono.error(new IllegalStateException()); // TO BE REMOVED
	}

}
