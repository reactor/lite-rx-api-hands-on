package io.pivotal.literx.repository;

public interface BlockingRepository<T> {

	void insert(T value);

	T findFirst();

	Iterable<T> findAll();

	T findById(String id);
}
