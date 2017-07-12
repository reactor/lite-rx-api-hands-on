# Request

## Description

Remember this diagram?

![Publisher and Subscriber](/techio/assets/PublisherSubscriber.png)

There's one aspect to it that we didn't cover: the volume control. In Reactive Streams terms
this is called **backpressure**. It is a feedback mechanism that allows a `Subscriber` to
signal to its `Publisher` how much data it is prepared to process, limiting the rate at
which the `Publisher` produces data.

This control of the demand is done at the `Subscription` level: a `Subscription` is created
for each `subscribe()` call and it can be manipulated to either `cancel()` the flow of data
or tune demand with `request(long)`.

Making a `request(Long.MAX_VALUE)` means an **unbounded demand**, so the `Publisher` will
emit data at its fastest pace.

## Practice

The demand can be tuned in the `StepVerifier` as well, by using the relevant parameter to
`create` and `withVirtualTime` for the initial request, then chaining in `thenRequest(long)`
in your expectations for further requests.

In this first example, create a `StepVerifier` that produces an initial unbounded demand
and verifies 4 values to be received, before completion. This is equivalent to the way you've
been using StepVerifier so far.

@[Request All]({"stubs": ["/src/main/java/io/pivotal/literx/Part06Request.java"], "command": "io.pivotal.literx.Part06RequestTest#requestAll", "layout":"aside"})

Next we will request values one by one: for that you need an initial request, but also a
second single request after you've received and asserted the first element.

Without more request, the source will never complete unless you cancel it. This can be done
instead of the terminal expectations by using `.thenCancel()`.

@[Request One By One]({"stubs": ["/src/main/java/io/pivotal/literx/Part06Request.java"], "command": "io.pivotal.literx.Part06RequestTest#requestOneByOne", "layout":"aside"})

## A note on debugging

How to check that the previous sequence was requested one by one, and that a cancellation
happened?

It's important to be able to debug reactive APIs, so in the next example we will make use
of the `log` operator to know exactly what happens in term of signals and events.

Use the `repository` to get a `Flux` of **all** users, then apply a log to it. Observe in
the console below how the underlying test requests it, and the other events like subscribe,
onNext...

@[Request Log]({"stubs": ["/src/main/java/io/pivotal/literx/Part06Request.java"], "command": "io.pivotal.literx.Part06RequestTest#experimentWithLog", "layout":"aside"})

If you want to perform custom actions without really modifying the elements in the sequence,
you can use the "side effect" methods that start with `doOn`.
 
For example, if you want to print "Starting:" upon subscription, use `doOnSubscribe`.

Each `doOn` method takes a relevant callback representing the custom action for the
corresponding event.

Note that you should not block or invoke operations with latency in these callbacks (which
is also true of other operator callbacks like `map`): it's more for quick operations.

@[Custom Operations]({"stubs": ["/src/main/java/io/pivotal/literx/Part06Request.java"], "command": "io.pivotal.literx.Part06RequestTest#experimentWithDoOn", "layout":"aside"})

Go ahead an modify the first two methods in this exercise in order to get some insight into
their sequences using `log` and `doOnXXX`.
