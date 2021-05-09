package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Learn how to control the demand.
 *
 * @author Sebastien Deleuze
 */
public class Part06RequestTest {

	Part06Request workshop = new Part06Request();
	ReactiveRepository<User> repository = new ReactiveUserRepository();

	PrintStream originalConsole = System.out;

	@Nullable //null when not useful
	ByteArrayOutputStream logConsole;

	@AfterEach
	public void afterEach() {
		if (logConsole != null) {
			originalConsole.println(logConsole.toString());
			System.setOut(originalConsole);
			logConsole = null;
		}
	}

//========================================================================================

	@Test
	public void requestAll() {
		Flux<User> flux = repository.findAll();
		StepVerifier verifier = workshop.requestAllExpectFour(flux);
		verifier.verify();
	}

//========================================================================================

	@Test
	public void requestOneByOne() {
		Flux<User> flux = repository.findAll();
		StepVerifier verifier = workshop.requestOneExpectSkylerThenRequestOneExpectJesse(flux);
		verifier.verify();
	}

//========================================================================================

	@Test
	public void experimentWithLog() {
		logConsole = new ByteArrayOutputStream();
		System.setOut(new PrintStream(logConsole));

		Flux<User> flux = workshop.fluxWithLog();

		StepVerifier.create(flux, 0)
		            .thenRequest(1)
		            .expectNextMatches(u -> true)
		            .thenRequest(1)
		            .expectNextMatches(u -> true)
		            .thenRequest(2)
		            .expectNextMatches(u -> true)
		            .expectNextMatches(u -> true)
		            .verifyComplete();

		String log = Arrays.stream(logConsole.toString().split("\n"))
		                   .filter(s -> s.contains("] INFO"))
		                   .map(s -> s.replaceAll(".*] INFO .* - ", ""))
		                   .collect(Collectors.joining("\n"));

		assertThat(log)
				.contains("onSubscribe(FluxZip.ZipCoordinator)" + System.lineSeparator()
						+ "request(1)" + System.lineSeparator()
						+ "onNext(Person{username='swhite', firstname='Skyler', lastname='White'})" + System.lineSeparator()
						+ "request(1)" + System.lineSeparator()
						+ "onNext(Person{username='jpinkman', firstname='Jesse', lastname='Pinkman'})" + System.lineSeparator()
						+ "request(2)" + System.lineSeparator()
						+ "onNext(Person{username='wwhite', firstname='Walter', lastname='White'})" + System.lineSeparator()
						+ "onNext(Person{username='sgoodman', firstname='Saul', lastname='Goodman'})" + System.lineSeparator()
						+ "onComplete()");
	}

//========================================================================================

	@Test
	public void experimentWithDoOn() {
		Flux<User> flux = workshop.fluxWithDoOnPrintln();

		//setting up the logConsole here should ensure we only capture console logs from the Flux
		logConsole = new ByteArrayOutputStream();
		System.setOut(new PrintStream(logConsole));

		StepVerifier.create(flux)
		            .expectNextCount(4)
		            .verifyComplete();

		assertThat(logConsole.toString())
				.isEqualTo("Starring:" + System.lineSeparator()
						+ "Skyler White" + System.lineSeparator()
						+ "Jesse Pinkman" + System.lineSeparator()
						+ "Walter White" + System.lineSeparator()
						+ "Saul Goodman" + System.lineSeparator()
						+ "The end!" + System.lineSeparator());
	}

}
