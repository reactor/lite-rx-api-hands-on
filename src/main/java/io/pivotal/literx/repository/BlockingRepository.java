package io.pivotal.literx.repository;

public interface BlockingRepository<T> {

	void save(T value);

	T findFirst();

	Iterable<T> findAll();

	T findById(String id);
}
