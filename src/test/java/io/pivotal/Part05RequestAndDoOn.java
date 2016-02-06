package io.pivotal;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.test.TestSubscriber;

public class Part05RequestAndDoOn {

	@Test
	public void requestNoValue() {
		Flux<Person> flux = Flux.just(skyler, jesse, walter, saul);
		TestSubscriber<Person> ts = createSubscriber();
		ts.bindTo(flux).assertValueCount(0);
	}

	// TODO Create a TestSubscriber that requests initially no value
	TestSubscriber<Person> createSubscriber() {
		return new TestSubscriber<>(0);  // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void requestValueOneByOne() {
		Flux<Person> flux = Flux.just(skyler, jesse, walter, saul);
		TestSubscriber<Person> ts = createSubscriber();
		ts.bindTo(flux).assertValueCount(0);
		requestOne(ts);
		ts.awaitAndAssertValues(skyler).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(jesse).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(walter).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(saul).assertComplete();
	}

	// TODO Request one value
	void requestOne(TestSubscriber<Person> ts) {
		ts.request(1);  // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void experimentWithLogging() {
		Flux<Person> flux = fluxWithLogs();
		TestSubscriber<Person> ts = createSubscriber();
		ts.bindTo(flux).assertValueCount(0);
		requestOne(ts);
		ts.awaitAndAssertValues(skyler).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(jesse).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(walter).assertNotTerminated();
		requestOne(ts);
		ts.awaitAndAssertValues(saul).assertComplete();
	}

	// TODO Return a Flux with skyler, jesse, walter and saul with log enabled
	Flux<Person> fluxWithLogs() {
		return Flux.just(skyler, jesse, walter, saul)
				.doOnSubscribe(s -> System.out.println("Starring:"))
				.doOnNext(p -> System.out.println(p.getFirstName() + " " + p.getLastName()))
				.doOnComplete(() -> System.out.println("The end!"));  // TO BE REMOVED
	}

//========================================================================================

	Person skyler = new Person("Skyler", "White");
	Person jesse = new Person("Jesse", "Pinkman");
	Person walter = new Person("Walter", "Pinkman");
	Person saul = new Person("Saul", "Goodman");

}
