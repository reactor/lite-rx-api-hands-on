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
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn how to adapt from/to RxJava 2 Observable/Single/Flowable and Java 8+ CompletableFuture.
 *
 * Mono and Flux already implements Reactive Streams interfaces so they are natively
 * Reactive Streams compliant + there are {@link Mono#from(Publisher)} and {@link Flux#from(Publisher)}
 * factory methods.
 *
 * For RxJava 2, you should not use Reactor Adapter but only RxJava 2 and Reactor Core.
 *
 * @author Sebastien Deleuze
 */
public class Part09Adapt {

	ReactiveRepository<User> repository = new ReactiveUserRepository();

//========================================================================================

	// TODO Adapt Flux to RxJava Flowable
	Flowable<User> fromFluxToFlowable(Flux<User> flux) {
		return null;
	}

	// TODO Adapt RxJava Flowable to Flux
	Flux<User> fromFlowableToFlux(Flowable<User> flowable) {
		return null;
	}

//========================================================================================

	// TODO Adapt Flux to RxJava Observable
	Observable<User> fromFluxToObservable(Flux<User> flux) {
		return null;
	}

	// TODO Adapt RxJava Observable to Flux
	Flux<User> fromObservableToFlux(Observable<User> observable) {
		return null;
	}

//========================================================================================

	// TODO Adapt Mono to RxJava Single
	Single<User> fromMonoToSingle(Mono<User> mono) {
		return null;
	}

	// TODO Adapt RxJava Single to Mono
	Mono<User> fromSingleToMono(Single<User> single) {
		return null;
	}

//========================================================================================

	// TODO Adapt Mono to Java 8+ CompletableFuture
	CompletableFuture<User> fromMonoToCompletableFuture(Mono<User> mono) {
		return null;
	}

	// TODO Adapt Java 8+ CompletableFuture to Mono
	Mono<User> fromCompletableFutureToMono(CompletableFuture<User> future) {
		return null;
	}

}
