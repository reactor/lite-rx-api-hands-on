package io.pivotal;

import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.test.TestSubscriber;

/**
 * Learn how to merge flux
 */
public class Part04Merge {

	private TestSubscriber<Long> ts;
	private Flux<Long> flux;
	private EmitterProcessor<Long> emitter1;
	private EmitterProcessor<Long> emitter2;

	@Before
	public void before() {
		ts = new TestSubscriber<>();
		emitter1 = EmitterProcessor.replay();
		emitter2 = EmitterProcessor.replay();
		emitter1.start();
		emitter2.start();
	}

	@Test
	public void mergeWithInterleave() {
		flux = mergeFluxWithInterleave(emitter1, emitter2);
		ts.bindTo(flux);
		emitValues();
		ts.assertValues(1L, 2L, 3L, 4L).assertComplete();
	}

	// TODO Merge flux1 and flux2 values with interleave
	Flux<Long> mergeFluxWithInterleave(Flux<Long> flux1, Flux<Long> flux2) {
		return Flux.merge(flux1, flux2); // TO BE REMOVED
	}


	@Test
	public void mergeWithNoInterleave() {
		flux = mergeFluxWithNoInterleave(emitter1, emitter2);
		ts.bindTo(flux);
		emitValues();
		ts.assertValueCount(4).assertComplete();
	}

	// TODO Merge flux1 and flux2 values with no interleave (flux1 values, and then flux2 values)
	Flux<Long> mergeFluxWithNoInterleave(Flux<Long> flux1, Flux<Long> flux2) {
		return Flux.concat(flux1, flux2); // TO BE REMOVED
	}


	private void emitValues() {
		emitter1.onNext(1L);
		emitter2.onNext(2L);
		emitter1.onNext(3L);
		emitter2.onNext(4L);
		emitter1.onComplete();
		emitter2.onComplete();
	}

}
