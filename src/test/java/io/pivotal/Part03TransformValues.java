package io.pivotal;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.test.TestSubscriber;

/**
 * Learn how to transform values
 */
public class Part03TransformValues {

	@Test
	public void transformMono() {
		Mono<String> mono = Mono.just("foo");
		TestSubscriber<String> ts = new TestSubscriber<>();
		ts.bindTo(capitalizeMono(mono)).assertValues("FOO").assertComplete();
	}

	// TODO Capitalize the Mono value
	private Mono<String> capitalizeMono(Mono<String> mono) {
		return mono.map(value -> value.toUpperCase()); // TO BE REMOVED
	}


	@Test
	public void transformFlux() {
		Flux<String> flux = Flux.just("foo", "bar");
		TestSubscriber<String> ts = new TestSubscriber<>();
		ts.bindTo(capitalizeFlux(flux)).assertValues("FOO", "BAR").assertComplete();
	}

	// TODO Capitalize the values contained in the Flux
	private Flux<String> capitalizeFlux(Flux<String> flux) {
		return flux.map(value -> value.toUpperCase()); // TO BE REMOVED
	}


}
