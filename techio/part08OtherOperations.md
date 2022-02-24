# Other Operations

## Description

In this section, we'll have a look at a few more useful operators that don't fall into
the broad categories we explored earlier. Reactor 3 contains a _lot_ of operators, so don't
hesitate to have a look at the [Flux](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html)
and [Mono](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html)
javadocs as well as the [reference guide](https://projectreactor.io/docs/core/release/reference/docs/index.html#which-operator)
to learn about more of them.

## Practice

In the first exercise we'll receive 3 `Flux<String>`. Their elements could arrive with
latency, yet each time the three sequences have all emitted an element, we want to combine
these 3 elements and create a new `User`. This concatenate-and-transform operation is
called `zip`:

@[Zip Operator]({"stubs": ["/src/main/java/io/pivotal/literx/Part08OtherOperations.java"], "command": "io.pivotal.literx.Part08OtherOperationsTest#zipFirstNameAndLastName", "layout":"aside"})

If you have 3 possible Mono sources and you only want to keep the one that emits its value the fastest,
you can use the `firstWithValue` static method:
@[Fatest Mono]({"stubs": ["/src/main/java/io/pivotal/literx/Part08OtherOperations.java"], "command": "io.pivotal.literx.Part08OtherOperationsTest#fastestMono", "layout":"aside"})

`Flux` also has the `firstWithValue` static method. Only the first element emitted by each `Flux`
is considered to select the fastest `Flux` (which is then mirrored in the output):
@[First]({"stubs": ["/src/main/java/io/pivotal/literx/Part08OtherOperations.java"], "command": "io.pivotal.literx.Part08OtherOperationsTest#fastestFlux", "layout":"aside"})

Sometimes you're not interested in elements of a `Flux<T>`. If you want to still keep a
`Flux<T>` type, you can use `ignoreElements()`. But if you really just want the completion,
represented as a `Mono<Void>`, you can use `then()` instead:
@[Flux Completion]({"stubs": ["/src/main/java/io/pivotal/literx/Part08OtherOperations.java"], "command": "io.pivotal.literx.Part08OtherOperationsTest#complete", "layout":"aside"})

Reactive Streams does not allow null values in `onNext`. There's an operator that allow to
_just_ emit one value, unless it is null in which case it will revert to an _empty_ `Mono`.
Can you find it?

@[Null aware user to mono]({"stubs": ["/src/main/java/io/pivotal/literx/Part08OtherOperations.java"], "command": "io.pivotal.literx.Part08OtherOperationsTest#nullHandling", "layout":"aside"})

Similarly, if you want to prevent the _empty_ `Mono` case by falling back to a different one,
you can find an operator that does this _switch_:

@[Otherwise if empty]({"stubs": ["/src/main/java/io/pivotal/literx/Part08OtherOperations.java"], "command": "io.pivotal.literx.Part08OtherOperationsTest#emptyHandling", "layout":"aside"})

Sometimes you want to capture all values emitted by `Flux` into separate `List`.
In this case you can use `collectList` operator that would return `Mono` containing that `List`.

@[Collect to list]({"stubs": ["/src/main/java/io/pivotal/literx/Part08OtherOperations.java"], "command": "io.pivotal.literx.Part08OtherOperationsTest#collect", "layout":"aside"})

There are more operators belonging to the _collect_ family. You can check them out in `Flux` [documentation](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html). 
