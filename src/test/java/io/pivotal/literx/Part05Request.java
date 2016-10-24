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
 * @see <a href="http://projectreactor.io/core/docs/api/reactor/core/publisher/Flux.html">Flux Javadoc</a>
 * @see <a href="http://projectreactor.io/core/docs/api/reactor/core/publisher/Mono.html">Mono Javadoc</a>
 * @see <a href="https://github.com/reactor/reactor-addons/blob/master/reactor-test/src/main/java/reactor/test/subscriber/ScriptedSubscriber.java>ScriptedSubscriber</a>
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
		return ScriptedSubscriber.create()
				.expectNextCount(4)
				.expectComplete(); // TO BE REMOVED
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
		return ScriptedSubscriber.create(1)
				.expectNext(User.SKYLER)
				.thenRequest(1)
				.expectNext(User.JESSE)
				.thenCancel(); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void experimentWithLog() {
		Flux<User> flux = fluxWithLog();
		ScriptedSubscriber.create(0)
				.thenRequest(1)
				.expectNextWith(u -> true)
				.thenRequest(1)
				.expectNextWith(u -> true)
				.thenRequest(2)
				.expectNextWith(u -> true)
				.expectNextWith(u -> true)
				.expectComplete()
				.verify(flux);
	}

	// TODO Return a Flux with all users stored in the repository that prints automatically logs for all Reactive Streams signals
	Flux<User> fluxWithLog() {
		return repository
				.findAll()
				.log(); // TO BE REMOVED
	}


//========================================================================================

	@Test
	public void experimentWithDoOn() {
		Flux<User> flux = fluxWithDoOnPrintln();
		ScriptedSubscriber.create()
				.expectNextCount(4)
				.expectComplete()
				.verify(flux);
	}

	// TODO Return a Flux with all users stored in the repository that prints "Starring:" on subscribe, "firstname lastname" for all values and "The end!" on complete
	Flux<User> fluxWithDoOnPrintln() {
		return repository
				.findAll()
				.doOnSubscribe(s -> System.out.println("Starring:"))
				.doOnNext(p -> System.out.println(p.getFirstname() + " " + p.getLastname()))
				.doOnComplete(() -> System.out.println("The end!")); // TO BE REMOVED
	}

}
