package io.pivotal.literx.repository;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Mono;

public class BlockingUserRepository implements BlockingRepository<User>{

	private final ReactiveRepository<User> reactiveRepository;


	public BlockingUserRepository() {
		reactiveRepository = new ReactiveUserRepository();
	}

	public BlockingUserRepository(long delayInMs) {
		reactiveRepository = new ReactiveUserRepository(delayInMs);
	}

	public BlockingUserRepository(User... users) {
		reactiveRepository = new ReactiveUserRepository(users);
	}

	public BlockingUserRepository(long delayInMs, User... users) {
		reactiveRepository = new ReactiveUserRepository(delayInMs, users);
	}


	@Override
	public void save(User user) {
		reactiveRepository.save(Mono.just(user)).get();
	}

	@Override
	public User findFirst() {
		return reactiveRepository.findFirst().get();
	}

	@Override
	public Iterable<User> findAll() {
		return reactiveRepository.findAll().toIterable();
	}

	@Override
	public User findById(String username) {
		return reactiveRepository.findById(username).get();
	}
}
