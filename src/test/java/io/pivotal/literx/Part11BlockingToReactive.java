package io.pivotal.literx;

import java.util.Iterator;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.BlockingRepository;
import io.pivotal.literx.repository.BlockingUserRepository;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

/**
 * Learn how to call blocking code from Reactive one with adapted concurrency strategy for
 * blocking code that produces or receives data.
 *
 * For those who know RxJava:
 *  - RxJava subscribeOn = Reactor subscribeOn
 *  - RxJava observeOn = Reactor publishOn
 *
 * @author Sebastien Deleuze
 * @see Flux#subscribeOn(Scheduler)
 * @see Flux#publishOn(Scheduler)
 * @see Schedulers
 */
public class Part11BlockingToReactive {

//========================================================================================

	@Test
	public void slowPublisherFastSubscriber() {
		BlockingUserRepository repository = new BlockingUserRepository();
		Flux<User> flux = blockingRepositoryToFlux(repository);
		assertEquals(0, repository.getCallCount());
		StepVerifier.create(flux)
				.expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
				.expectComplete()
				.verify();
	}

	// TODO Create a Flux for reading all users from the blocking repository, and run it with an elastic scheduler
	Flux<User> blockingRepositoryToFlux(BlockingRepository<User> repository) {
		return null;
	}

//========================================================================================

	@Test
	public void fastPublisherSlowSubscriber() {
		ReactiveRepository<User> reactiveRepository = new ReactiveUserRepository();
		BlockingUserRepository blockingRepository = new BlockingUserRepository(new User[]{});
		Mono<Void> complete = fluxToBlockingRepository(reactiveRepository.findAll(), blockingRepository);
		assertEquals(0, blockingRepository.getCallCount());
		StepVerifier.create(complete)
				.expectComplete()
				.verify();
		Iterator<User> it = blockingRepository.findAll().iterator();
		assertEquals(User.SKYLER, it.next());
		assertEquals(User.JESSE, it.next());
		assertEquals(User.WALTER, it.next());
		assertEquals(User.SAUL, it.next());
		assertFalse(it.hasNext());
	}

	// TODO Insert users contained in the Flux parameter in the blocking repository using an parallel scheduler
	Mono<Void> fluxToBlockingRepository(Flux<User> flux, BlockingRepository<User> repository) {
		return null;
	}

}
