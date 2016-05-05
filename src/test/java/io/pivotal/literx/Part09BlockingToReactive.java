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
import reactor.core.publisher.Computations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.test.TestSubscriber;

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
 * @see Computations
 */
public class Part09BlockingToReactive {

//========================================================================================

	@Test
	public void slowPublisherFastSubscriber() {
		BlockingUserRepository repository = new BlockingUserRepository();
		Flux<User> flux = blockingRepositoryToFlux(repository);
		assertEquals(0, repository.getCallCount());
		TestSubscriber<User> testSubscriber = new TestSubscriber<>();
		testSubscriber
				.bindTo(flux)
				.assertNotTerminated()
				.await()
				.assertValues(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
				.assertComplete();
	}

	// TODO Create a Flux for reading all users from the blocking repository, and run it with a scheduler suitable for slow tasks without blocking the main thread
	Flux<User> blockingRepositoryToFlux(BlockingRepository<User> repository) {
		return null;
	}

//========================================================================================

	@Test
	public void fastPublisherSlowSubscriber() {
		ReactiveRepository<User> reactiveRepository = new ReactiveUserRepository();
		BlockingRepository<User> blockingRepository = new BlockingUserRepository(new User[]{});
		Mono<Void> complete = fluxToBlockingRepository(reactiveRepository.findAll(), blockingRepository);
		TestSubscriber<Void> testSubscriber = new TestSubscriber<>();
		testSubscriber
				.bindTo(complete)
				.assertNotTerminated()
				.await()
				.assertComplete();
		Iterator<User> it = blockingRepository.findAll().iterator();
		assertEquals(User.SKYLER, it.next());
		assertEquals(User.JESSE, it.next());
		assertEquals(User.WALTER, it.next());
		assertEquals(User.SAUL, it.next());
		assertFalse(it.hasNext());
	}

	// TODO Insert users contained in the Flux parameter in the blocking repository using an scheduler factory suitable for fast tasks
	Mono<Void> fluxToBlockingRepository(Flux<User> flux, BlockingRepository<User> repository) {
		return null;
	}

//========================================================================================

	@Test
	public void nullHandling() {
		Mono<User> mono = nullAwareUserToMono(User.SKYLER);
		TestSubscriber<User> testSubscriber = new TestSubscriber<>();
		testSubscriber
				.bindTo(mono)
				.assertValues(User.SKYLER)
				.assertComplete();
		mono = nullAwareUserToMono(null);
		testSubscriber = new TestSubscriber<>();
		testSubscriber
				.bindTo(mono)
				.assertNoValues()
				.assertComplete();
	}

	// TODO Return a valid Mono of user for null input and non null input user (hint: Reactive Streams does not accept null values)
	Mono<User> nullAwareUserToMono(User user) {
		return null;
	}

}
