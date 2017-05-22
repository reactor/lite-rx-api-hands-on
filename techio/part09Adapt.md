# Adapt

You can make RxJava2 and Reactor 3 types interact without a single external library.

In the first two examples we will adapt from `Flux` to `Flowable`, which implements `Publisher`,
and vice-versa.

This is straightforward as both libraries provide a factory method to do that conversion from
any `Publisher`. The checker below runs the two opposite conversions in one go:
@[Flux to Flowable back to Flux]({"stubs": ["/src/main/java/io/pivotal/literx/Part09Adapt.java"], "command": "io.pivotal.literx.Part09AdaptTest#adaptToFlowable", "layout":"aside"})


The next two examples are a little trickier: we need to adapt between `Flux` and `Observable`,
but the later doesn't implement `Publisher`.

In the first case, you can transform any publisher to `Observable`.
In the second case, you have to first transform the `Observable` into a `Flowable`, which
forces you to define a strategy to deal with backpressure (RxJava 2 `Observable` doesn't
support backpressure).

@[Flux to Observable and back to Flux]({"stubs": ["/src/main/java/io/pivotal/literx/Part09Adapt.java"], "command": "io.pivotal.literx.Part09AdaptTest#adaptToObservable", "layout":"aside"})

Next, let's try to transform a `Mono` to a RxJava `Single`, and vice-versa. You can
simply call the `firstOrError` method from `Observable`. For the other way around, you'll
once again need to transform the `Single` into a `Flowable` first.

@[Mono to Single and back to Mono]({"stubs": ["/src/main/java/io/pivotal/literx/Part09Adapt.java"], "command": "io.pivotal.literx.Part09AdaptTest#adaptToSingle", "layout":"aside"})

Finally, you can easily transform a `Mono` to a Java 8 `CompletableFuture` and vice-versa.
Notice how these conversion methods all begin with `from` (when converting an external type
to a Reactor one) and `to` (when converting a Reactor type to an external one).

@[Mono to CompletableFuture and back to Mono]({"stubs": ["/src/main/java/io/pivotal/literx/Part09Adapt.java"], "command": "io.pivotal.literx.Part09AdaptTest#adaptToCompletableFuture", "layout":"aside"})