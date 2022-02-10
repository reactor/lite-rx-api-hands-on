package io.pivotal.literx;

import reactor.core.publisher.Mono;

/**
 * Learn how to create Mono instances.
 *
 * @author Sebastien Deleuze
 * @see <a href="https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html">Mono Javadoc</a>
 */
public class Part02Mono {

//========================================================================================

	Mono<String> emptyMono() {
		return Mono.empty();
	}

//========================================================================================

	Mono<String> monoWithNoSignal() {
		return Mono.never();
	}

//========================================================================================

	Mono<String> fooMono() {
		return Mono.just("foo");
	}

//========================================================================================

	Mono<String> errorMono() {
		return Mono.error(IllegalStateException::new);
	}

}
