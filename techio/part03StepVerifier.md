# StepVerifier

## Description

Until now, your solution for each exercise was checked by passing the `Publisher` you
defined to a test using a `StepVerifier`.

This class from the `reactor-test` artifact is capable of subscribing to any `Publisher`
(eg. a `Flux` or an Akka Stream...) and then assert a set of user-defined expectations with
regard to the sequence.

If any event is triggered that doesn't match the current expectation, the `StepVerifier`
will produce an `AssertionError`.

You can obtain an instance of `StepVerifier` from the static factory `create`. It offers a
DSL to set up expectations on the data part and finish with a single terminal expectation
(completion, error, cancellation...).

**Note that you must always call the `verify()` method** or one of the shortcuts that
combine the terminal expectation and verify, like `.verifyErrorMessage(String)`. Otherwise
the `StepVerifier` won't subscribe to your sequence and nothing will be asserted.

```java
StepVerifier.create(T<Publisher>).{expectations...}.verify()
```

## Practice 

Let's verify the sequence passed to the code emits two specific elements and that the `Flux`
then completes successfully.

@[Verify Simple flux]({"stubs": ["/src/main/java/io/pivotal/literx/Part03StepVerifier.java"], "command": "io.pivotal.literx.Part03StepVerifierTest#expectElementsThenComplete", "layout":"aside"})

Now, let's do the same test but verifying that an exception is propagated at the end.

@[Verify an error]({"stubs": ["/src/main/java/io/pivotal/literx/Part03StepVerifier.java"], "command": "io.pivotal.literx.Part03StepVerifierTest#expect2ElementsThenError", "layout":"aside"})

Let's try to create a `StepVerifier` with an expectation on a `User`'s `username` field.
Some expectations can work by checking a `Predicate` on the next value, or even by consuming
the next value by passing it to an assertion library like `Assertions.assertThat(T)` from `AssertJ`.
Try these lambda-based versions:

@[Lambda and assertion]({"stubs": ["/src/main/java/io/pivotal/literx/Part03StepVerifier.java"], "command": "io.pivotal.literx.Part03StepVerifierTest#expectElementsWithThenComplete", "layout":"aside"})

On this next test we will receive a `Flux` which takes some time to emit. As you can expect,
the test will take some time to run.

@[Wait some time]({"stubs": ["/src/main/java/io/pivotal/literx/Part03StepVerifier.java"], "command": "io.pivotal.literx.Part03StepVerifierTest#count", "layout":"aside"})

The next one is even worse: it emits 1 element per second, completing only after having
emitted 3600 of them!

Since we don't want our tests to run for hours, we need a way to speed that up while still
being able to assert the data itself (eliminating the time factor).

Fortunately, `StepVerifier` comes with a **virtual time** option: by using `StepVerifier.withVirtualTime(Supplier<Publisher>)`,
the verifier will temporarily replace default core `Schedulers` (the component that define
the execution context in Reactor). All these default `Scheduler` are replaced by a single
instance of a `VirtualTimeScheduler`, which has a virtual clock that can be manipulated.

In order for the operators to pick up that `Scheduler`, you should lazily build your operator
chain inside the lambda passed to `withVirtualTime`.

You must then advance time as part of your test scenario, by calling either `thenAwait(Duration)`
or `expectNoEvent(Duration)`. The former simply advances the clock, while the later additionally
fails if any unexpected event triggers during the provided duration (note that almost all
the time there will at least be a "subscription" event even though the clock hasn't advanced,
so you should usually put a `expectSubscription()` after `.withVirtualTime()` if you're
going to use `expectNoEvent` right after).

```Java
StepVerifier.withVirtualtime(() -> Mono.delay(Duration.ofHours(3)))
            .expectSubscription()
            .expectNoEvent(Duration.ofHours(2))
            .thenAwait(Duration.ofHours(1))
            .expectNextCount(1)
            .verify();
```

Let's try that by making a fast test of our hour-long publisher:

@[Virtual time]({"stubs": ["/src/main/java/io/pivotal/literx/Part03StepVerifier.java"], "command": "io.pivotal.literx.Part03StepVerifierTest#countWithVirtualTime", "layout":"aside"})