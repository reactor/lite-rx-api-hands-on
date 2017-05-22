# Flux

## Description

A `Flux<T>` is a Reactive Streams `Publisher`, augmented with a lot of operators that can be
used to generate, transform, orchestrate Flux sequences.

It can emit 0 to _n_ `<T>` elements (`onNext` event) then either completes or errors
(`onComplete` and `onError` terminal events). If no terminal event is triggered, the
`Flux` is infinite.

- Static factories on Flux allow to create sources, or generate them from several callbacks types.
- Instance methods, the operators, let you build an asynchronous processing pipeline that
  will produce an asynchronous sequence.
- Each `Flux#subscribe()` or multicasting operation such as `Flux#publish` and `Flux#publishNext`
  will materialize a dedicated instance of the pipeline and trigger the data flow inside it.

See the javadoc [here](http://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html).

![Marble diagram representation of a Flux](/techio/assets/flux.png)

`Flux` in action :

```java
Flux.fromIterable(getSomeLongList())
    .delayElements(Duration.ofMillis(100))
    .doOnNext(serviceA::someObserver)
    .map(d -> d * 2)
    .take(3)
    .onErrorResumeWith(errorHandler::fallback)
    .doAfterTerminate(serviceM::incrementTerminate)
    .subscribe(System.out::println);
```

## Practice

In this lesson we'll see different factory methods to create a `Flux`. 
Let's try a very simple example: just return an empty flux.


```java
static <T> Flux<T> empty()
// Create a Flux that completes without emitting any item.
```
@[Empty flux]({"stubs": ["/src/main/java/io/pivotal/literx/Part01Flux.java"], "command": "io.pivotal.literx.Part01FluxTest#empty", "layout":"aside"})



One can also create a `Flux` out of readily available data:

```java
static <T> Flux<T> just(T... data)
// Create a new Flux that emits the specified item(s) and then complete.
```

@[Flux from values]({"stubs": ["/src/main/java/io/pivotal/literx/Part01Flux.java"], "command": "io.pivotal.literx.Part01FluxTest#fromValues", "layout":"aside"})


This time we will use items of a list to publish on the flux with `fromIterable`:
```java
static <T> Flux<T> fromIterable(Iterable<? extends T> it)
// Create a Flux that emits the items contained in the provided Iterable.
```

@[Create a Flux from a List]({"stubs": ["/src/main/java/io/pivotal/literx/Part01Flux.java"], "command": "io.pivotal.literx.Part01FluxTest#fromList", "layout":"aside"})


In imperative synchronous code, it's easy to manage exceptions with familiar `try`-`catch`
blocks, `throw` instructions...

But in an asynchronous context, we have to do things a bit differently. Reactive Streams
defines the `onError` signal to deal with exceptions. Note that such an event **is terminal:
this is the last event the `Flux` will produce**.

`Flux#error` produces a `Flux` that simply emits this signal, terminating immediately:

```java
static <T> Flux<T> error(Throwable error)
// Create a Flux that completes with the specified error.
```

@[Create a Flux that emits an IllegalStateException]({"stubs": ["/src/main/java/io/pivotal/literx/Part01Flux.java"], "command": "io.pivotal.literx.Part01FluxTest#error", "layout":"aside"})


To finish with `Flux`, let's try to create a Flux that produces ten elements, at a regular pace.
In order to do that regular publishing, we can use `interval`.
But it produces an infinite stream (like ticks of a clock), and we want to `take` only
10 elements, so don't forget to precise it.

```java
static Flux<Long> interval(Duration period)
// Create a new Flux that emits an ever incrementing long starting with 0 every period on the global timer.
```

@[Create a Flux that emits 10 increasing values]({"stubs": ["/src/main/java/io/pivotal/literx/Part01Flux.java"], "command": "io.pivotal.literx.Part01FluxTest#countEach100ms", "layout":"aside"})
