# Mono

## Description

A `Mono<T>` is a Reactive Streams `Publisher`, also augmented with a lot of operators that
can be used to generate, transform, orchestrate Mono sequences.

It is a specialization of `Flux` that can emit **at most 1 `<T>` element**: a Mono is either
valued (complete with element), empty (complete without element) or failed (error).

A `Mono<Void>` can be used in cases where only the completion signal is interesting (the
Reactive Streams equivalent of a `Runnable` task completing).

Like for `Flux`, the operators can be used to define an asynchronous pipeline which will be
materialized anew for each `Subscription`.

Note that some API that change the sequence's cardinality will return a `Flux` (and vice-versa,
APIs that reduce the cardinality to 1 in a `Flux` return a `Mono`).

See the javadoc [here](http://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html).

![Marble diagram representation of a Mono](/techio/assets/mono.png)

`Mono` in action:

```java
Mono.just(1)
    .map(integer -> "foo" + integer)
    .or(Mono.delay(Duration.ofMillis(100)))
    .subscribe(System.out::println);
```

## Practice 


As for the Flux let's return a empty `Mono` using the static factory.

```java
static <T> Mono<T> empty()
// Create a Mono that completes without emitting any item.
```
@[Empty Mono]({"stubs": ["/src/main/java/io/pivotal/literx/Part02Mono.java"], "command": "io.pivotal.literx.Part02MonoTest#empty", "layout":"aside"})



Now, we will try to create a `Mono` which never emits anything.
Unlike `empty()`, it won't even emit an `onComplete` event.

@[No Emission]({"stubs": ["/src/main/java/io/pivotal/literx/Part02Mono.java"], "command": "io.pivotal.literx.Part02MonoTest#noSignal", "layout":"aside"})


Like `Flux`, you can create a `Mono` from an available (unique) value.

@[Create a Mono from an item]({"stubs": ["/src/main/java/io/pivotal/literx/Part02Mono.java"], "command": "io.pivotal.literx.Part02MonoTest#fromValue", "layout":"aside"})


And exactly as we did for the flux, we can propagate exceptions.


@[Create a Mono that emits an IllegalStateException]({"stubs": ["/src/main/java/io/pivotal/literx/Part02Mono.java"], "command": "io.pivotal.literx.Part02MonoTest#error", "layout":"aside"})


