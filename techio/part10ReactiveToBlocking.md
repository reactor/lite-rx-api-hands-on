# Reactive to Blocking

Sometimes you can only migrate part of your code to be reactive, and you need to reuse
reactive sequences in more imperative code.

Thus if you need to block until the value from a `Mono` is available, use `Mono#block()`
method. It will throw an `Exception` if the `onError` event is triggered.

Note that you should avoid this by favoring having reactive code end-to-end, as much as
possible. You MUST avoid this at all cost in the middle of other reactive code, as this has
the potential to lock your whole reactive pipeline.

@[Value from Mono]({"stubs": ["/src/main/java/io/pivotal/literx/Part10ReactiveToBlocking.java"], "command": "io.pivotal.literx.Part10ReactiveToBlockingTest#mono", "layout":"aside"})


Similarly, you can block for the first or last value in a `Flux` with `blockFirst()`/`blockLast()`.
You can also transform a `Flux` to an `Iterable` with `toIterable`. Same restrictions as
above still apply.

@[Flux to iterable]({"stubs": ["/src/main/java/io/pivotal/literx/Part10ReactiveToBlocking.java"], "command": "io.pivotal.literx.Part10ReactiveToBlockingTest#flux", "layout":"aside"})
