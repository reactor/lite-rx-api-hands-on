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

import java.util.List;
import java.util.concurrent.CompletableFuture;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.junit.Test;
import org.reactivestreams.Publisher;
import reactor.adapter.rxjava.RxJava2Adapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.subscriber.ScriptedSubscriber;

/**
 * Learn how to adapt from/to RxJava 2 Observable/Single/Flowable and transform from/to
 * Java 8+ CompletableFuture and List.
 *
 * Mono and Flux already implements Reactive Streams interfaces so they are natively
 * Reactive Streams compliant + there are {@link Mono#from(Publisher)} and {@link Flux#from(Publisher)}
 * factory methods.
 *
 * @author Sebastien Deleuze
 * @see <a href="https://github.com/reactor/reactor-addons/blob/master/reactor-adapter/src/main/java/reactor/adapter/rxjava/RxJava2Adapter.java">RxJava2Adapter</a>
 * @see <a href="http://projectreactor.io/core/docs/api/reactor/core/publisher/Flux.html">Flux Javadoc</a>
 * @see <a href="http://projectreactor.io/core/docs/api/reactor/core/publisher/Mono.html">Mono Javadoc</a>
 * @see <a href="https://github.com/reactor/reactor-addons/blob/master/reactor-test/src/main/java/reactor/test/subscriber/ScriptedSubscriber.java>ScriptedSubscriber</a>
 */
public class Part08Conversion {

	ReactiveRepository<User> repository = new ReactiveUserRepository();

//========================================================================================

	@Test
	public void adaptToObservable() {
		Flux<User> flux = repository.findAll();
		Observable<User> observable = fromFluxToObservable(flux);
		ScriptedSubscriber.create()
				.expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
				.expectComplete()
				.verify(fromObservableToFlux(observable));
	}

	// TODO Convert Flux to RxJava Observable thanks to a Reactor adapter
	Observable<User> fromFluxToObservable(Flux<User> flux) {
		return RxJava2Adapter.fluxToObservable(flux); // TO BE REMOVED
	}

	// TODO Convert RxJava Observable to Flux thanks to a Reactor adapter
	Flux<User> fromObservableToFlux(Observable<User> observable) {
		return RxJava2Adapter.observableToFlux(observable, BackpressureStrategy.BUFFER); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void adaptToSingle() {
		Mono<User> mono = repository.findFirst();
		Single<User> single = fromMonoToSingle(mono);
		ScriptedSubscriber.create()
				.expectNext(User.SKYLER)
				.expectComplete()
				.verify(fromSingleToMono(single));
	}

	// TODO Convert Mono to RxJava Single thanks to a Reactor adapter
	Single<User> fromMonoToSingle(Mono<User> mono) {
		return RxJava2Adapter.monoToSingle(mono); // TO BE REMOVED
	}

	// TODO Convert RxJava Single to Mono thanks to a Reactor adapter
	Mono<User> fromSingleToMono(Single<User> single) {
		return RxJava2Adapter.singleToMono(single); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void adaptToFlowable() {
		Flux<User> flux = repository.findAll();
		Observable<User> observable = fromFluxToObservable(flux);
		ScriptedSubscriber.create()
				.expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
				.expectComplete()
				.verify(fromObservableToFlux(observable));
	}

	// TODO Convert Flux to RxJava Flowable thanks to a Reactor adapter or using the fact that Flowable implements Publisher
	Flowable<User> fromFluxToFlowable(Flux<User> flux) {
		return RxJava2Adapter.fluxToFlowable(flux); // TO BE REMOVED
	}

	// TODO Convert RxJava Flowable to Flux thanks to a Reactor adapter or using the fact that Flowable implements Publisher
	Flux<User> fromFlowableToFlux(Flowable<User> flowable) {
		return RxJava2Adapter.flowableToFlux(flowable); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void transformToCompletableFuture() {
		Mono<User> mono = repository.findFirst();
		CompletableFuture<User> future = fromMonoToCompletableFuture(mono);
		ScriptedSubscriber.create()
				.expectNext(User.SKYLER)
				.expectComplete()
				.verify(fromCompletableFutureToMono(future));
	}

	// TODO Transform Mono to Java 8+ CompletableFuture thanks to Mono
	CompletableFuture<User> fromMonoToCompletableFuture(Mono<User> mono) {
		return mono.toFuture(); // TO BE REMOVED
	}

	// TODO Transform Java 8+ CompletableFuture to Mono
	Mono<User> fromCompletableFutureToMono(CompletableFuture<User> future) {
		return Mono.fromFuture(future); // TO BE REMOVED
	}

//========================================================================================

	@Test
	public void transformToList() {
		Flux<User> flux = repository.findAll();
		List<User> list = fromFluxToList(flux);
		ScriptedSubscriber.create()
				.expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
				.expectComplete()
				.verify(fromListToFlux(list));
	}

	// TODO Transform Flux to List
	List<User> fromFluxToList(Flux<User> flux) {
		return flux.collectList().block(); // TO BE REMOVED
	}

	// TODO Transform List to Flux
	Flux<User> fromListToFlux(List<User> list) {
		return Flux.fromIterable(list); // TO BE REMOVED
	}

}
