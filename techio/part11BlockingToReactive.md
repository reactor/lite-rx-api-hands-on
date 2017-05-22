# Blocking to Reactive

## Description

The big question is "How to deal with legacy, non reactive code?".

Say you have blocking code (eg. a JDBC connection to a database), and you want to integrate
that into your reactive pipelines while avoiding too much of an impact on performance.

The best course of action is to isolate such intrinsically blocking parts of your code into
their own execution context via a `Scheduler`, keeping the efficiency of the rest of the
pipeline high and only creating extra threads when absolutely needed.

In the JDBC example you could use the `fromIterable` factory method. But how do you prevent
the call to block the rest of the pipeline?

## Practice
The `subscribeOn` method allow to isolate a sequence from the start on a provided `Scheduler`.
For example, the `Schedulers.elastic()` will create a pool of threads that grows on demand,
releasing threads that haven't been used in a while automatically.

Use that trick to slowly read all users from the blocking `repository` in the first exercise.
Note that you will need to wrap the call to the repository inside a `Flux.defer` lambda.

@[Slow publisher]({"stubs": ["/src/main/java/io/pivotal/literx/Part11BlockingToReactive.java"], "command": "io.pivotal.literx.Part11BlockingToReactiveTest#slowPublisherFastSubscriber", "layout":"aside"})

For slow subscribers (eg. saving to a database), you can isolate a smaller section of the
sequence with the `publishOn` operator. Unlike `subscribeOn`, it only affects the part of
the chain **below** it, switching it to a new `Scheduler`.

As an example, you can use `doOnNext` to perform a `save` on the `repository`, but first
use the trick above to isolate that save into its own execution context. You can make it
more explicit that you're only interested in knowing if the save succeeded or failed by
chaining the `then()` operator at the end, which returns a `Mono<Void>`.

@[Slow subscriber]({"stubs": ["/src/main/java/io/pivotal/literx/Part11BlockingToReactive.java"], "command": "io.pivotal.literx.Part11BlockingToReactiveTest#fastPublisherSlowSubscriber", "layout":"aside"})

