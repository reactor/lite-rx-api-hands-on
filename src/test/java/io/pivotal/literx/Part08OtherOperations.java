package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Learn how to use various other operators.
 *
 * @author Sebastien Deleuze
 */
public class Part08OtherOperations {

	final static User MARIE = new User("mschrader", "Marie", "Schrader");
	final static User MIKE = new User("mehrmantraut", "Mike", "Ehrmantraut");

//========================================================================================

	@Test
	public void zipFirstNameAndLastName() {
		Flux<String> usernameFlux = Flux.just(User.SKYLER.getUsername(), User.JESSE.getUsername(), User.WALTER.getUsername(), User.SAUL.getUsername());
		Flux<String> firstnameFlux = Flux.just(User.SKYLER.getFirstname(), User.JESSE.getFirstname(), User.WALTER.getFirstname(), User.SAUL.getFirstname());
		Flux<String> lastnameFlux = Flux.just(User.SKYLER.getLastname(), User.JESSE.getLastname(), User.WALTER.getLastname(), User.SAUL.getLastname());
		Flux<User> userFlux = userFluxFromStringFlux(usernameFlux, firstnameFlux, lastnameFlux);
		StepVerifier.create(userFlux)
				.expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
				.expectComplete()
				.verify();
	}

	// TODO Create a Flux of user from Flux of username, firstname and lastname.
	Flux<User> userFluxFromStringFlux(Flux<String> usernameFlux, Flux<String> firstnameFlux, Flux<String> lastnameFlux) {
		return Flux
				.zip(usernameFlux, firstnameFlux, lastnameFlux)
				.map(tuple -> new User(tuple.getT1(), tuple.getT2(), tuple.getT3())); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void fastestMono() {
		ReactiveRepository<User> repository1 = new ReactiveUserRepository(MARIE);
		ReactiveRepository<User> repository2 = new ReactiveUserRepository(250, MIKE);
		Mono<User> mono = useFastestMono(repository1.findFirst(), repository2.findFirst());
		StepVerifier.create(mono)
				.expectNext(MARIE)
				.expectComplete()
				.verify();

		repository1 = new ReactiveUserRepository(250, MARIE);
		repository2 = new ReactiveUserRepository(MIKE);
		mono = useFastestMono(repository1.findFirst(), repository2.findFirst());
		StepVerifier.create(mono)
				.expectNext(MIKE)
				.expectComplete()
				.verify();
	}

	// TODO return the mono which returns faster its value
	Mono<User> useFastestMono(Mono<User> mono1, Mono<User> mono2) {
		return Mono.first(mono1, mono2); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void fastestFlux() {
		ReactiveRepository<User> repository1 = new ReactiveUserRepository(MARIE, MIKE);
		ReactiveRepository<User> repository2 = new ReactiveUserRepository(250);
		Flux<User> flux = useFastestFlux(repository1.findAll(), repository2.findAll());
		StepVerifier.create(flux)
				.expectNext(MARIE, MIKE)
				.expectComplete()
				.verify();

		repository1 = new ReactiveUserRepository(250, MARIE, MIKE);
		repository2 = new ReactiveUserRepository();
		flux = useFastestFlux(repository1.findAll(), repository2.findAll());
		StepVerifier.create(flux)
				.expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
				.expectComplete()
				.verify();
	}

	// TODO return the flux which returns faster the first value
	Flux<User> useFastestFlux(Flux<User> flux1, Flux<User> flux2) {
		return Flux.firstEmitting(flux1, flux2); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void complete() {
		ReactiveRepository<User> repository = new ReactiveUserRepository();
		Mono<Void> completion = fluxCompletion(repository.findAll());
		StepVerifier.create(completion)
				.expectComplete()
				.verify();
	}

	// TODO Convert the input Flux<User> to a Mono<Void> that represents the complete signal of the flux
	Mono<Void> fluxCompletion(Flux<User> flux) {
		return flux.then(); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void nullHandling() {
		Mono<User> mono = nullAwareUserToMono(User.SKYLER);
		StepVerifier.create(mono)
				.expectNext(User.SKYLER)
				.expectComplete()
				.verify();
		mono = nullAwareUserToMono(null);
		StepVerifier.create(mono)
				.expectComplete()
				.verify();
	}

	// TODO Return a valid Mono of user for null input and non null input user (hint: Reactive Streams does not accept null values)
	Mono<User> nullAwareUserToMono(User user) {
		return Mono.justOrEmpty(user); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void emptyHandling() {
		Mono<User> mono = emptyToSkyler(Mono.just(User.WALTER));
		StepVerifier.create(mono)
				.expectNext(User.WALTER)
				.expectComplete()
				.verify();
		mono = emptyToSkyler(Mono.empty());
		StepVerifier.create(mono)
				.expectNext(User.SKYLER)
				.expectComplete()
				.verify();
	}

	// TODO Return the same mono passed as input parameter, expect that it will emit User.SKYLER when empty
	Mono<User> emptyToSkyler(Mono<User> mono) {
		return mono.defaultIfEmpty(User.SKYLER); // TO BE REMOVED
	}

}
