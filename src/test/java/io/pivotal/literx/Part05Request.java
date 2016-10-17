package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.subscriber.ScriptedSubscriber;

/**
 * Learn how to control the demand.
 *
 * @author Sebastien Deleuze
 */
public class Part05Request {

	ReactiveRepository<User> repository = new ReactiveUserRepository();

//========================================================================================

	@Test
	public void requestAll() {
		Flux<User> flux = repository.findAll();
		ScriptedSubscriber<Object> subscriber = requestAllExpectFour();
		subscriber.verify(flux);
	}

	// TODO Create a ScriptedSubscriber that requests initially all values and expect a 4 values to be received
	ScriptedSubscriber<Object> requestAllExpectFour() {
		return null;
	}

//========================================================================================

	@Test
	public void requestOneByOne() {
		Flux<User> flux = repository.findAll();
		ScriptedSubscriber<Object> subscriber = requestOneExpectSkylerThenRequestOneExpectJesse();
		subscriber.verify(flux);
	}

	// TODO Create a ScriptedSubscriber that requests initially 1 value and expects {@link User.SKYLER} then requests another value and expects {@link User.JESSE}.
	ScriptedSubscriber<Object> requestOneExpectSkylerThenRequestOneExpectJesse() {
		return null;
	}

//========================================================================================

	@Test
	public void experimentWithLog() {
		Flux<User> flux = fluxWithLog();
		ScriptedSubscriber.create(0)
				.doRequest(1)
				.expectValueWith(u -> true)
				.doRequest(1)
				.expectValueWith(u -> true)
				.doRequest(2)
				.expectValueWith(u -> true)
				.expectValueWith(u -> true)
				.expectComplete()
				.verify(flux);
	}

	// TODO Return a Flux with all users stored in the repository that prints automatically logs for all Reactive Streams signals
	Flux<User> fluxWithLog() {
		return null;
	}


//========================================================================================

	@Test
	public void experimentWithDoOn() {
		Flux<User> flux = fluxWithDoOnPrintln();
		ScriptedSubscriber.expectValueCount(4)
				.expectComplete()
				.verify(flux);
	}

	// TODO Return a Flux with all users stored in the repository that prints "Starring:" on subscribe, "firstname lastname" for all values and "The end!" on complete
	Flux<User> fluxWithDoOnPrintln() {
		return null;
	}

}
