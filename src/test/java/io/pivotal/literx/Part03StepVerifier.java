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

import io.pivotal.literx.domain.User;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Learn how to use StepVerifier to test Mono, Flux or any other kind of Reactive Streams Publisher.
 *
 * @author Sebastien Deleuze
 * @see <a href="http://next.projectreactor.io/ext/docs/api/reactor/test/StepVerifier.html">StepVerifier Javadoc</a>
 */
public class Part03StepVerifier {

//========================================================================================

	@Test
	public void expect2ElementsThenComplete() {
		expect2Elements(Flux.just("foo", "bar"));
	}

	// TODO Use StepVerifier to check that the flux parameter emits 2 elements and then completes successfully.
	void expect2Elements(Flux<String> flux) {
	}


//========================================================================================

	@Test
	public void expectElementsThenComplete() {
		expectFooBarComplete(Flux.just("foo", "bar"));
	}

	// TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then completes successfully.
	void expectFooBarComplete(Flux<String> flux) {
	}

//========================================================================================

	@Test
	public void expect2ElementsThenError() {
		expectFooBarError(Flux.just("foo", "bar").concatWith(Mono.error(new RuntimeException())));
	}

	// TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then a RuntimeException error.
	void expectFooBarError(Flux<String> flux) {
	}

//========================================================================================

	@Test
	public void expectElementsWithThenComplete() {
		expectSkylerJesseComplete(Flux.just(new User("swhite", null, null), new User("jpinkman", null, null)));
	}

	// TODO Use StepVerifier to check that the flux parameter emits a User with "swhite" username and another one with "jpinkman" then completes successfully.
	void expectSkylerJesseComplete(Flux<User> flux) {
	}

}
