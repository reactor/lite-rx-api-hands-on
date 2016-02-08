package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.test.TestSubscriber;

public class Part05Request {

	ReactiveRepository<User> repository = new ReactiveUserRepository();

//========================================================================================

	@Test
	public void requestNoValue() {
		Flux<User> flux = repository.findAll();
		TestSubscriber<User> ts = createSubscriber();
		ts.bindTo(flux).assertValueCount(0);
	}

	// TODO Create a TestSubscriber that requests initially no value
	TestSubscriber<User> createSubscriber() {
		return new TestSubscriber<>(0);  // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void requestValueOneByOne() {
		Flux<User> flux = repository.findAll();
		TestSubscriber<User> ts = createSubscriber();
		ts.bindTo(flux).assertValueCount(0);
		requestOne(ts);
		ts.awaitAndAssertValues(User.SKYLER).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(User.JESSE).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(User.WALTER).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(User.SAUL).assertComplete();
	}

	// TODO Request one value
	void requestOne(TestSubscriber<User> ts) {
		ts.request(1);  // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void experimentWithLog() {
	Flux<User> flux = fluxWithLog();
		TestSubscriber<User> ts = createSubscriber();
		ts.bindTo(flux).assertValueCount(0);
		requestOne(ts);
		ts.awaitAndAssertValues(User.SKYLER).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(User.JESSE).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(User.WALTER).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(User.SAUL).assertComplete();
	}

	// TODO Return a Flux with skyler, jesse, walter and saul that print automatically logs for all Reactive Streams signals
	Flux<User> fluxWithLog() {
		return repository.findAll().log(); // TO BE REMOVED
	}


//========================================================================================

	@Test
	public void experimentWithDoOn() {
		Flux<User> flux = fluxWithDoOnPrintln();
		TestSubscriber<User> ts = createSubscriber();
		ts.bindTo(flux).assertValueCount(0);
		requestOne(ts);
		ts.awaitAndAssertValues(User.SKYLER).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(User.JESSE).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(User.WALTER).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(User.SAUL).assertComplete();
	}

	// TODO Return a Flux with skyler, jesse, walter and saul that prints "Starring:" on subscribe, "firstName lastName"
	Flux<User> fluxWithDoOnPrintln() {
		return repository.findAll()
				.doOnSubscribe(s -> System.out.println("Starring:"))
				.doOnNext(p -> System.out.println(p.getFirstname() + " " + p.getLastname()))
				.doOnComplete(() -> System.out.println("The end!"));  // TO BE REMOVED
	}

}
