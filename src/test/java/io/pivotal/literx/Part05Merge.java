package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Learn how to merge flux.
 *
 * @author Sebastien Deleuze
 */
public class Part05Merge {

	final static User MARIE = new User("mschrader", "Marie", "Schrader");
	final static User MIKE = new User("mehrmantraut", "Mike", "Ehrmantraut");

	ReactiveRepository<User> repository1 = new ReactiveUserRepository(500);
	ReactiveRepository<User> repository2 = new ReactiveUserRepository(MARIE, MIKE);

//========================================================================================

	@Test
	public void mergeWithInterleave() {
		Flux<User> flux = mergeFluxWithInterleave(repository1.findAll(), repository2.findAll());
		StepVerifier.create(flux)
				.expectNext(MARIE, MIKE, User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
				.expectComplete()
				.verify();
	}

	// TODO Merge flux1 and flux2 values with interleave
	Flux<User> mergeFluxWithInterleave(Flux<User> flux1, Flux<User> flux2) {
		return null;
	}

//========================================================================================

	@Test
	public void mergeWithNoInterleave() {
		Flux<User> flux = mergeFluxWithNoInterleave(repository1.findAll(), repository2.findAll());
		StepVerifier.create(flux)
				.expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL, MARIE, MIKE)
				.expectComplete()
				.verify();
	}

	// TODO Merge flux1 and flux2 values with no interleave (flux1 values, and then flux2 values)
	Flux<User> mergeFluxWithNoInterleave(Flux<User> flux1, Flux<User> flux2) {
		return null;
	}

//========================================================================================

	@Test
	public void multipleMonoToFlux() {
		Mono<User> skylerMono = repository1.findFirst();
		Mono<User> marieMono = repository2.findFirst();
		Flux<User> flux = createFluxFromMultipleMono(skylerMono, marieMono);
		StepVerifier.create(flux)
				.expectNext(User.SKYLER, MARIE)
				.expectComplete()
				.verify();
	}

	// TODO Create a Flux containing the values of the 2 Mono
	Flux<User> createFluxFromMultipleMono(Mono<User> mono1, Mono<User> mono2) {
		return null;
	}

}
