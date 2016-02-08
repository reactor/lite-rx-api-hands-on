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
import reactor.core.publisher.SchedulerGroup;
import reactor.core.test.TestSubscriber;

/**
 * Learn how to call blocking code for Reactive one.
 *
 * @author Sebastien Deleuze
 */
public class Part08BlockingToReactive {

//========================================================================================

	@Test
	public void slowPublisherFastSubscriber() {
		BlockingRepository<User> repository = new BlockingUserRepository();
		Flux<User> flux = blockingRepositoryToFlux(repository);
		TestSubscriber<User> ts = new TestSubscriber<>();
		ts.bindTo(flux).assertNotTerminated().await().assertValues(User.SKYLER, User.JESSE, User.WALTER, User.SAUL).assertComplete();
	}

	// TODO Create a Flux for all users from the blocking repository, and run it on a scheduler factory dedicated to IO to avoid blocking the calling thread
	Flux<User> blockingRepositoryToFlux(BlockingRepository<User> repository) {
		return Flux.fromIterable(repository.findAll()).publishOn(SchedulerGroup.io()); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void fastPublisherSlowSubscriber() {
		ReactiveRepository<User> reactiveRepository = new ReactiveUserRepository();
		BlockingRepository<User> blockingRepository = new BlockingUserRepository(new User[]{});
		Mono<Void> complete = fluxToBlockingRepository(reactiveRepository.findAll(), blockingRepository);
		TestSubscriber<Void> ts = new TestSubscriber<>();
		ts.bindTo(complete).assertNotTerminated().await().assertComplete();
		Iterator<User> it = blockingRepository.findAll().iterator();
		assertEquals(User.SKYLER, it.next());
		assertEquals(User.JESSE, it.next());
		assertEquals(User.WALTER, it.next());
		assertEquals(User.SAUL, it.next());
		assertFalse(it.hasNext());
	}

	// TODO Insert values in the blocking repository without blocking the calling thread using an scheduler factory suitable for async tasks
	Mono<Void> fluxToBlockingRepository(Flux<User> flux, BlockingRepository<User> repository) {
		return flux.dispatchOn(SchedulerGroup.async()).doOnNext(user -> repository.insert(user)).after(); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void nullHandling() {
		Mono<User> mono = nullAwareUserToMono(User.SKYLER);
		TestSubscriber<User> ts = new TestSubscriber<>();
		ts.bindTo(mono).assertValues(User.SKYLER).assertComplete();
		mono = nullAwareUserToMono(null);
		ts = new TestSubscriber<>();
		ts.bindTo(mono).assertNoValues().assertComplete();
	}

	// TODO Return a valid Mono of user for null and non null input user (hint: Reactive Streams does not accept null values)
	Mono<User> nullAwareUserToMono(User user) {
		return user == null ? Mono.empty() : Mono.just(user); // TO BE REMOVED
	}

}
