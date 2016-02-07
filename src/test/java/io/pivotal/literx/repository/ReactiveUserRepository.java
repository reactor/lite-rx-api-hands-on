package io.pivotal.literx.repository;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.pivotal.literx.domain.User;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveUserRepository implements ReactiveRepository<User> {

	private final static long DEFAULT_DELAY_IN_MS = 50;

	private final long delayInMs;

	private final List<User> users;


	public ReactiveUserRepository() {
		delayInMs = DEFAULT_DELAY_IN_MS;
		users = Arrays.asList(User.SKYLER, User.JESSE, User.WALTER, User.SAUL);
	}

	public ReactiveUserRepository(long delayInMs) {
		this.delayInMs = delayInMs;
		users = Arrays.asList(User.SKYLER, User.JESSE, User.WALTER, User.SAUL);
	}

	public ReactiveUserRepository(User... users) {
		this.delayInMs = DEFAULT_DELAY_IN_MS;
		this.users = Arrays.asList(users);
	}

	public ReactiveUserRepository(long delayInMs, User... users) {
		this.delayInMs = delayInMs;
		this.users = Arrays.asList(users);
	}


	@Override
	public Mono<Void> insert(Publisher<User> userPublisher) {
		return withDelay(Flux.from(userPublisher)).doOnNext(u -> users.add(u)).after();
	}

	@Override
	public Mono<User> findFirst() {
		return withDelay(Mono.just(users.get(0)));
	}

	@Override
	public Flux<User> findAll() {
		return withDelay(Flux.fromIterable(users));
	}

	@Override
	public Mono<User> findById(String username) {
		User user = users.stream().filter((p) -> p.getUsername().equals(username))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No user with username " + username + " found!"));
		return withDelay(Mono.just(user));
	}


	private Mono<User> withDelay(Mono<User> userMono) {
		return Mono.delay(delayInMs, TimeUnit.MILLISECONDS).then(c -> userMono);
	}

	private Flux<User> withDelay(Flux<User> userFlux) {
		return Flux.interval(delayInMs, TimeUnit.MILLISECONDS).zipWith(userFlux, (i, user) -> user);
	}

}
