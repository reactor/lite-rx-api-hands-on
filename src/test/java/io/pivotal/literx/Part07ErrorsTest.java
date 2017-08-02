/*
 * Copyright (c) 2011-2017 Pivotal Software Inc, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import org.junit.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Learn how to deal with errors.
 *
 * @author Sebastien Deleuze
 * @see Exceptions#propagate(Throwable)
 */
public class Part07ErrorsTest {

	Part07Errors workshop = new Part07Errors();

//========================================================================================

	@Test
	public void monoWithValueInsteadOfError() {
		Mono<User> mono = workshop.betterCallSaulForBogusMono(Mono.error(new IllegalStateException()));
		StepVerifier.create(mono)
				.expectNext(User.SAUL)
				.verifyComplete();

		mono = workshop.betterCallSaulForBogusMono(Mono.just(User.SKYLER));
		StepVerifier.create(mono)
				.expectNext(User.SKYLER)
				.verifyComplete();
	}

//========================================================================================

	@Test
	public void fluxWithValueInsteadOfError() {
		Flux<User> flux = workshop.betterCallSaulAndJesseForBogusFlux(Flux.error(new IllegalStateException()));
		StepVerifier.create(flux)
				.expectNext(User.SAUL, User.JESSE)
				.verifyComplete();

		flux = workshop.betterCallSaulAndJesseForBogusFlux(Flux.just(User.SKYLER, User.WALTER));
		StepVerifier.create(flux)
				.expectNext(User.SKYLER, User.WALTER)
				.verifyComplete();
	}

//========================================================================================

	@Test
	public void handleCheckedExceptions() {
		Flux<User> flux = workshop.capitalizeMany(Flux.just(User.SAUL, User.JESSE));

		StepVerifier.create(flux)
				.verifyError(Part07Errors.GetOutOfHereException.class);
	}

}
