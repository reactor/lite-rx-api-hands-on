package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.subscriber.ScriptedSubscriber;

/**
 * Learn how to merge flux.
 *
 * @author Sebastien Deleuze
 * @see <a href="http://projectreactor.io/core/docs/api/reactor/core/publisher/Flux.html">Flux Javadoc</a>
 * @see <a href="http://projectreactor.io/core/docs/api/reactor/core/publisher/Mono.html">Mono Javadoc</a>
 * @see <a href="https://github.com/reactor/reactor-addons/blob/master/reactor-test/src/main/java/reactor/test/subscriber/ScriptedSubscriber.java>ScriptedSubscriber</a>
 */
public class Part04Merge {

	final static User MARIE = new User("mschrader", "Marie", "Schrader");
	final static User MIKE = new User("mehrmantraut", "Mike", "Ehrmantraut");

	ReactiveRepository<User> repository1 = new ReactiveUserRepository(500);
	ReactiveRepository<User> repository2 = new ReactiveUserRepository(MARIE, MIKE);

//========================================================================================

	@Test
	public void mergeWithInterleave() {
		Flux<User> flux = mergeFluxWithInterleave(repository1.findAll(), repository2.findAll());
		ScriptedSubscriber
				.create()
				.expectValues(MARIE, MIKE, User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
				.expectComplete()
				.verify(flux);
	}

	// TODO Merge flux1 and flux2 values with interleave
	Flux<User> mergeFluxWithInterleave(Flux<User> flux1, Flux<User> flux2) {
		return flux1.mergeWith(flux2); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void mergeWithNoInterleave() {
		Flux<User> flux = mergeFluxWithNoInterleave(repository1.findAll(), repository2.findAll());
		ScriptedSubscriber
				.create()
				.expectValues(User.SKYLER, User.JESSE, User.WALTER, User.SAUL, MARIE, MIKE)
				.expectComplete()
				.verify(flux);
	}

	// TODO Merge flux1 and flux2 values with no interleave (flux1 values, and then flux2 values)
	Flux<User> mergeFluxWithNoInterleave(Flux<User> flux1, Flux<User> flux2) {
		return flux1.concatWith(flux2); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void multipleMonoToFlux() {
		Mono<User> skylerMono = repository1.findFirst();
		Mono<User> marieMono = repository2.findFirst();
		Flux<User> flux = createFluxFromMultipleMono(skylerMono, marieMono);
		ScriptedSubscriber
				.create()
				.expectValues(User.SKYLER, MARIE)
				.expectComplete()
				.verify(flux);
	}

	// TODO Create a Flux containing the values of the 2 Mono
	Flux<User> createFluxFromMultipleMono(Mono<User> mono1, Mono<User> mono2) {
		return Flux.concat(mono1, mono2);  // TO BE REMOVED
	}

}
