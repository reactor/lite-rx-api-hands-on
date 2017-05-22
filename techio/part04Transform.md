# Transform

## Description
Reactor ships with several operators that can be used to transform data.

## Practice 

In the first place, we will capitalize a `String`. Since this is a simple 1-1 transformation
with no expected latency, we can use the `map` operator with a lambda transforming a `T`
into a `U`.

@[Capitalize data on Mono]({"stubs": ["/src/main/java/io/pivotal/literx/Part04Transform.java"], "command": "io.pivotal.literx.Part04TransformTest#transformMono", "layout":"aside"})

We can use exactly the same code on a `Flux`, applying the mapping to each element as it
becomes available.

@[Capitalize data on Flux]({"stubs": ["/src/main/java/io/pivotal/literx/Part04Transform.java"], "command": "io.pivotal.literx.Part04TransformTest#transformFlux", "layout":"aside"})

Now imagine that we have to call a webservice to capitalize our String. This new call can
have latency so we cannot use the synchronous `map` anymore. Instead, we want to represent
the asynchronous call as a `Flux` or `Mono`, and use a different operator: `flatMap`.

`flatMap` takes a transformation `Function` that returns a `Publisher<U>` instead of a `U`.
This publisher represents the asynchronous transformation to apply to each element. If we
were using it with `map`, we'd obtain a stream of `Flux<Publisher<U>>`. Not very useful.

But `flatMap` on the other hand knows how to deal with these inner publishers: it will
subscribe to them then merge all of them into a single global output, a much more useful
`Flux<U>`. Note that if values from inner publishers arrive at different times, they can
interleave in the resulting `Flux`.

<img class="marble" src="https://raw.githubusercontent.com/reactor/projectreactor.io/master/src/main/static/assets/img/marble/flatmap.png" alt="">


@[Async transformation]({"stubs": ["/src/main/java/io/pivotal/literx/Part04Transform.java"], "command": "io.pivotal.literx.Part04TransformTest#asyncTransformFlux", "layout":"aside"})

