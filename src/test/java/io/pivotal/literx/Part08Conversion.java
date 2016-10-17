/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pivotal.literx;

import java.util.concurrent.CompletableFuture;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import org.junit.Test;
import reactor.adapter.RxJava1Adapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.subscriber.ScriptedSubscriber;
import rx.Observable;
import rx.Single;

/**
 * Learn how to convert from/to Java 8+ CompletableFuture and RxJava Observable/Single.
 *
 * Mono and Flux already implements Reactive Streams interfaces so they are natively
 * Reactive Streams compliant + there are Mono.from(Publisher) and Flux.from(Publisher)
 * factory methods.
 *
 * @author Sebastien Deleuze
 * @see <a href="http://projectreactor.io/core/docs/api/reactor/core/publisher/Flux.html">Flux Javadoc</a>
 * @see <a href="http://projectreactor.io/core/docs/api/reactor/core/publisher/Mono.html">Mono Javadoc</a>
 * @see <a href="https://github.com/reactor/reactor-addons/blob/master/reactor-test/src/main/java/reactor/test/subscriber/ScriptedSubscriber.java>ScriptedSubscriber</a>
 */
public class Part08Conversion {

	ReactiveRepository<User> repository = new ReactiveUserRepository();

//========================================================================================

	@Test
	public void observableConversion() {
		Flux<User> flux = repository.findAll();
		Observable<User> observable = fromFluxToObservable(flux);
		ScriptedSubscriber.create()
				.expectValues(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
				.expectComplete()
				.verify(fromObservableToFlux(observable));
	}

	// TODO Convert Flux to RxJava Observable thanks to a Reactor converter
	Observable<User> fromFluxToObservable(Flux<User> flux) {
		return RxJava1Adapter.publisherToObservable(flux); // TO BE REMOVED
	}

	// TODO Convert RxJava Observable to Flux thanks to a Reactor converter
	Flux<User> fromObservableToFlux(Observable<User> observable) {
		return RxJava1Adapter.observableToFlux(observable); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void singleConversion() {
		Mono<User> mono = repository.findFirst();
		Single<User> single = fromMonoToSingle(mono);
		ScriptedSubscriber.create()
				.expectValue(User.SKYLER)
				.expectComplete()
				.verify(fromSingleToMono(single));
	}

	// TODO Convert Mono to RxJava Single thanks to a Reactor converter
	Single<User> fromMonoToSingle(Mono<User> mono) {
		return RxJava1Adapter.publisherToSingle(mono); // TO BE REMOVED
	}

	// TODO Convert RxJava Single to Mono thanks to a Reactor converter
	Mono<User> fromSingleToMono(Single<User> single) {
		return RxJava1Adapter.singleToMono(single); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void completableFutureConversion() {
		Mono<User> mono = repository.findFirst();
		CompletableFuture<User> future = fromMonoToCompletableFuture(mono);
		ScriptedSubscriber.create()
				.expectValue(User.SKYLER)
				.expectComplete()
				.verify(fromCompletableFutureToMono(future));
	}

	// TODO Convert Mono to Java 8+ CompletableFuture thanks to Mono
	CompletableFuture<User> fromMonoToCompletableFuture(Mono<User> mono) {
		return mono.toFuture(); // TO BE REMOVED
	}

	// TODO Convert Java 8+ CompletableFuture to Mono
	Mono<User> fromCompletableFutureToMono(CompletableFuture<User> future) {
		return Mono.fromFuture(future); // TO BE REMOVED
	}

}
