package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.test.TestSubscriber;

/**
 * Learn how to control the demand.
 *
 * @author Sebastien Deleuze
 */
public class Part05Request {

	ReactiveRepository<User> repository = new ReactiveUserRepository();

//========================================================================================

	@Test
	public void requestNoValue() {
		Flux<User> flux = repository.findAll();
		TestSubscriber<User> testSubscriber = createSubscriber();
		testSubscriber
				.bindTo(flux)
				.await()
				.assertNoValues();
	}

	// TODO Create a TestSubscriber that requests initially no value
	TestSubscriber<User> createSubscriber() {
		return null;
	}

//========================================================================================

	@Test
	public void requestValueOneByOne() {
		Flux<User> flux = repository.findAll();
		TestSubscriber<User> testSubscriber = createSubscriber();
		testSubscriber
				.bindTo(flux)
				.assertValueCount(0);
		requestOne(testSubscriber);
		testSubscriber
				.awaitAndAssertNextValues(User.SKYLER)
				.assertNotTerminated();
		requestOne(testSubscriber);
		testSubscriber
				.awaitAndAssertNextValues(User.JESSE)
				.assertNotTerminated();
		requestOne(testSubscriber);
		testSubscriber
				.awaitAndAssertNextValues(User.WALTER)
				.assertNotTerminated();
		requestOne(testSubscriber);
		testSubscriber
				.awaitAndAssertNextValues(User.SAUL)
				.assertComplete();
	}

	// TODO Request one value
	void requestOne(TestSubscriber<User> testSubscriber) {

	}

//========================================================================================

	@Test
	public void experimentWithLog() {
	Flux<User> flux = fluxWithLog();
		TestSubscriber<User> testSubscriber = createSubscriber();
		testSubscriber
				.bindTo(flux)
				.assertValueCount(0);
		requestOne(testSubscriber);
		testSubscriber
				.awaitAndAssertNextValues(User.SKYLER)
				.assertNotTerminated();
		requestOne(testSubscriber);
		testSubscriber
				.awaitAndAssertNextValues(User.JESSE)
				.assertNotTerminated();
		requestOne(testSubscriber);
		testSubscriber
				.awaitAndAssertNextValues(User.WALTER)
				.assertNotTerminated();
		requestOne(testSubscriber);
		testSubscriber
				.awaitAndAssertNextValues(User.SAUL)
				.assertComplete();
	}

	// TODO Return a Flux with all users stored in the repository that prints automatically logs for all Reactive Streams signals
	Flux<User> fluxWithLog() {
		return null;
	}


//========================================================================================

	@Test
	public void experimentWithDoOn() {
		Flux<User> flux = fluxWithDoOnPrintln();
		TestSubscriber<User> testSubscriber = createSubscriber();
		testSubscriber
				.bindTo(flux)
				.assertValueCount(0);
		requestOne(testSubscriber);
		testSubscriber
				.awaitAndAssertNextValues(User.SKYLER)
				.assertNotTerminated();
		requestOne(testSubscriber);
		testSubscriber
				.awaitAndAssertNextValues(User.JESSE)
				.assertNotTerminated();
		requestOne(testSubscriber);
		testSubscriber
				.awaitAndAssertNextValues(User.WALTER)
				.assertNotTerminated();
		requestOne(testSubscriber);
		testSubscriber
				.awaitAndAssertNextValues(User.SAUL)
				.assertComplete();
	}

	// TODO Return a Flux with all users stored in the repository that prints "Starring:" on subscribe, "firstname lastname" for all values and "The end!" on complete
	Flux<User> fluxWithDoOnPrintln() {
		return null;
	}

}
