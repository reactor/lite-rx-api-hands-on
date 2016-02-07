package io.pivotal.literx.repository;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Mono;

public class BlockingUserRepository implements BlockingRepository<User>{

	private ReactiveUserRepository reactiveRepository;

	@Override
	public void insert(User user) {
		reactiveRepository.insert(Mono.just(user)).get();
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
