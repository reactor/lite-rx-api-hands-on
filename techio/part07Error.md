# Error

## Description

Reactor ships with several operators that can be used to deal with errors: propagate errors
but also recover from it (eg. by falling back to a different sequence or by retrying a new
`Subscription`).

## Practice

In the first example, we will return a `Mono` containing default user Saul when an error occurs in
the original `Mono`, using the method `onErrorReturn`. If you want, you can even limit that
fallback to the `IllegalStateException` class. Use the `User#SAUL` constant.
@[onErrorReturn]({"stubs": ["/src/main/java/io/pivotal/literx/Part07Errors.java"], "command": "io.pivotal.literx.Part07ErrorsTest#monoWithValueInsteadOfError", "layout":"aside"})


Let's try the same thing with `Flux`. In this case, we don't just want a single fallback
value, but a totally separate sequence (think getting stale data from a cache). This can
be achieved with `onErrorResume`, which falls back to a `Publisher<T>`.

Emit both`User#SAUL` and `User#JESSE` whenever there is an error in the original `FLux`:
@[OnErrorResumeWith on flux]({"stubs": ["/src/main/java/io/pivotal/literx/Part07Errors.java"], "command": "io.pivotal.literx.Part07ErrorsTest#fluxWithValueInsteadOfError", "layout":"aside"})

Dealing with checked exceptions is a bit more complicated. Whenever some code that throws
checked exceptions is used in an operator (eg. the transformation function of a `map`), you
will need to deal with it. The most straightforward way is to make a more complex lambda with
a `try-catch` block that will transform the checked exception into a `RuntimeException` that
can be signalled downstream.

There is a `Exceptions#propagate` utility that will wrap a checked exception into a special
runtime exception that can be automatically unwrapped by Reactor subscribers and `StepVerifier`:
this avoid seeing an irrelevant `RuntimeException` in the stacktrace.

Try to use that on the `capitalizeMany` method within a `map`: you'll need to catch a
`GetOutOfHereException`, which is checked, but the corresponding test still expects the
`GetOutOfHereException` directly.

@[Checked exception]({"stubs": ["/src/main/java/io/pivotal/literx/Part07Errors.java"], "command": "io.pivotal.literx.Part07ErrorsTest#handleCheckedExceptions", "layout":"aside"})

