package io.pivotal.literx.repository;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Mono;

public class BlockingUserRepository implements BlockingRepository<User>{

	private final ReactiveRepository<User> reactiveRepository;

	private int callCount = 0;

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
		callCount++;
		reactiveRepository.save(Mono.just(user)).block();
	}

	@Override
	public User findFirst() {
		callCount++;
		return reactiveRepository.findFirst().block();
	}

	@Override
	public Iterable<User> findAll() {
		callCount++;
		return reactiveRepository.findAll().toIterable();
	}

	@Override
	public User findById(String username) {
		callCount++;
		return reactiveRepository.findById(username).block();
	}
	
	public int getCallCount() {
		return callCount;
	}
}
