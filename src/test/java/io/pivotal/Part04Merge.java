package io.pivotal;

import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.test.TestSubscriber;

/**
 * Learn how to merge flux
 */
public class Part04Merge {

//========================================================================================

	@Test
	public void mergeWithInterleave() {
		Flux<Person> flux = mergeFluxWithInterleave(emitter1, emitter2);
		ts.bindTo(flux);
		emitValues();
		ts.assertValues(skyler, jesse, walter, saul).assertComplete();
	}

	// TODO Merge flux1 and flux2 values with interleave
	Flux<Person> mergeFluxWithInterleave(Flux<Person> flux1, Flux<Person> flux2) {
		return Flux.merge(flux1, flux2); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void mergeWithNoInterleave() {
		Flux<Person> flux = mergeFluxWithNoInterleave(emitter1, emitter2);
		ts.bindTo(flux);
		emitValues();
		ts.assertValues(skyler, walter, jesse, saul).assertComplete();
	}

	// TODO Merge flux1 and flux2 values with no interleave (flux1 values, and then flux2 values)
	Flux<Person> mergeFluxWithNoInterleave(Flux<Person> flux1, Flux<Person> flux2) {
		return Flux.concat(flux1, flux2); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void multipleMonoToFlux() {
		Mono<Person> skylerMono = Mono.just(skyler);
		Mono<Person> jesseMono = Mono.just(jesse);
		Flux<Person> flux = createFluxFromMultipleMono(skylerMono, jesseMono);
		ts.bindTo(flux).assertValues(skyler, jesse).assertComplete();
	}

	// TODO Create a Flux with the values of the 2 Monos
	Flux<Person> createFluxFromMultipleMono(Mono<Person> mono1, Mono<Person> mono2) {
		return Flux.concat(mono1, mono2);  // TO BE REMOVED
	}

//========================================================================================

	TestSubscriber<Person> ts;
	EmitterProcessor<Person> emitter1;
	EmitterProcessor<Person> emitter2;

	Person skyler = new Person("Skyler", "White");
	Person jesse = new Person("Jesse", "Pinkman");
	Person walter = new Person("Walter", "Pinkman");
	Person saul = new Person("Saul", "Goodman");

	@Before
	public void before() {
		ts = new TestSubscriber<>();
		emitter1 = EmitterProcessor.replay();
		emitter2 = EmitterProcessor.replay();
		emitter1.start();
		emitter2.start();
	}

	void emitValues() {
		emitter1.onNext(skyler);
		emitter2.onNext(jesse);
		emitter1.onNext(walter);
		emitter2.onNext(saul);
		emitter1.onComplete();
		emitter2.onComplete();
	}

}
