package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn how to merge flux.
 *
 * @author Sebastien Deleuze
 */
public class Part05Merge {

//========================================================================================

	// TODO Merge flux1 and flux2 values with interleave
	Flux<User> mergeFluxWithInterleave(Flux<User> flux1, Flux<User> flux2) {
		return null;
	}

//========================================================================================

	// TODO Merge flux1 and flux2 values with no interleave (flux1 values and then flux2 values)
	Flux<User> mergeFluxWithNoInterleave(Flux<User> flux1, Flux<User> flux2) {
		return null;
	}

//========================================================================================

	// TODO Create a Flux containing the value of mono1 then the value of mono2
	Flux<User> createFluxFromMultipleMono(Mono<User> mono1, Mono<User> mono2) {
		return null;
	}

}
