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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.test.TestSubscriber;
import reactor.rx.Fluxion;
import rx.Observable;
import rx.Single;

/**
 * Learn how to convert from/to Java 8+ CompletableFuture, RxJava Observable/Single and
 * Reactor Stream.
 *
 * Mono and Flux already implements Reactive Streams interfaces so they are natively
 * Reactive Streams compliant + there are Mono.from(Publisher) and Flux.from(Publisher)
 * factory methods.
 *
 * @author Sebastien Deleuze
 */
public class Part08Conversion {

	ReactiveRepository<User> repository = new ReactiveUserRepository();

//========================================================================================

	@Test
	public void observableConversion() {
		Flux<User> flux = repository.findAll();
		Observable<User> observable = fromFluxToObservable(flux);
		TestSubscriber<User> testSubscriber = new TestSubscriber<>();
		testSubscriber
				.bindTo(fromObservableToFlux(observable))
				.await()
				.assertValues(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
				.assertComplete();
	}

	// TODO Convert Flux to RxJava Observable thanks to a Reactor converter
	Observable<User> fromFluxToObservable(Flux<User> flux) {
		return null;
	}

	// TODO Convert RxJava Observable to Flux thanks to a Reactor converter
	Flux<User> fromObservableToFlux(Observable<User> observable) {
		return null;
	}

//========================================================================================

	@Test
	public void singleConversion() {
		Mono<User> mono = repository.findFirst();
		Single<User> single = fromMonoToSingle(mono);
		TestSubscriber<User> testSubscriber = new TestSubscriber<>();
		testSubscriber
				.bindTo(fromSingleToMono(single))
				.await()
				.assertValues(User.SKYLER)
				.assertComplete();
	}

	// TODO Convert Mono to RxJava Single thanks to a Reactor converter
	Single<User> fromMonoToSingle(Mono<User> mono) {
		return null;
	}

	// TODO Convert RxJava Single to Mono thanks to a Reactor converter
	Mono<User> fromSingleToMono(Single<User> single) {
		return null;
	}

//========================================================================================

	@Test
	public void completableFutureConversion() {
		Mono<User> mono = repository.findFirst();
		CompletableFuture<User> future = fromMonoToCompletableFuture(mono);
		TestSubscriber<User> testSubscriber = new TestSubscriber<>();
		testSubscriber
				.bindTo(fromCompletableFutureToMono(future))
				.await()
				.assertValues(User.SKYLER)
				.assertComplete();
	}

	// TODO Convert Mono to Java 8+ CompletableFuture thanks to a Reactor converter
	CompletableFuture<User> fromMonoToCompletableFuture(Mono<User> mono) {
		return null;
	}

	// TODO Convert Java 8+ CompletableFuture to Mono thanks to a Reactor converter
	Mono<User> fromCompletableFutureToMono(CompletableFuture<User> future) {
		return null;
	}

//========================================================================================

	@Test
	public void fluxionConversion() {
		Flux<User> flux = repository.findAll();
		TestSubscriber<User> testSubscriber = new TestSubscriber<>();
		testSubscriber
				.bindTo(fromFluxToFluxion(flux))
				.await()
				.assertValues(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
				.assertComplete();
	}

	// TODO Convert Flux to Reactor Stream thanks to Flux as operator + Stream static builder method
	Fluxion<User> fromFluxToFluxion(Flux<User> flux) {
		return null; // TO BE REMOVED
	}



}
