Want to improve the content? [Fork me on GitHub](https://github.com/reactor/lite-rx-api-hands-on/tree/techio_course)

# Introduction to Reactive Programming

Reactor 3 is a library built around the `Reactive Streams` specification, bringing the
paradigm of **Reactive Programming** on the JVM.

In this course, you'll familiarize with the Reactor API. So let's make a quick introduction
to the more general concepts in Reactive Streams and Reactive Programming.

## Why

Reactive Programming is a new paradigm in which you use _declarative code_ (in a manner that
is similar to _functional programming_) in order to build asynchronous processing pipelines.
It is an event-based model where data is pushed to the consumer, as it becomes available:
we deal with asynchronous sequences of events.

This is important in order to be more efficient with resources and increase an application's
capacity to serve large number of clients, without the headache of writing low-level concurrent or
and/or parallelized code.

By being built around the core pillars of being fully **asynchronous** and **non-blocking**,
Reactive Programming is an alternative to the more limited ways of doing asynchronous code
in the JDK: namely _Callback_ based APIs and `Future`.

It also facilitates composition, which in turn makes asynchronous code more readable and
maintainable.

## Reactive Streams

The **Reactive Streams** specification is an industry-driven effort to standardize Reactive
Programming libraries on the JVM, and more importantly specify how they must behave so
that they are interoperable. Implementors include Reactor 3 but also RxJava 2, Akka Streams,
Vert.x and Ratpack.

It contains 4 very simple interfaces as well as a TCK, which shouldn't be overlooked since
it is the rules of the specification that bring the most value to it.

From a user perspective however, it is fairly low-level. Reactor 3 aims at offering an
higher level API that can be leverage in a large breadth of situations, building it on top
of Reactive Streams `Publisher`.

## Interactions

In reactive stream sequences, the source `Publisher` produces data. But by default, it does
nothing until a `Subscriber` has registered (**subscribed**), at which point it will _push_
data to it.

![Publisher and Subscriber](/techio/assets/PublisherSubscriber.png)

Reactor adds the concept of **operators**, which are chained together to describe what
processing to apply at each stage to the data. Applying an operator returns a new intermediate
`Publisher` (in fact it can be though of as both a Subscriber to the operator upstream
and a Publisher for downstream). The final form of the data ends up in the final `Subscriber`
that defines what to do from a user perspective.

## Quizz

?[Which of these types are defined in the Reactive Streams specification?]
 - [ ] `Observable`
 - [X] `Subscriber`
 - [ ] `Listener`
 - [X] `Publisher`
 - [ ] `Operator`

?[In order to create a Reactive Stream Publisher or Subscriber...]
 - [ ] ... I simply need to implement 4 interfaces
 - [X] ... its code must comply with the spec and pass the TCK
 - [X] ... I should favor using an existing library like Reactor

```java
Publisher<Integer> source = Flux.range(1, 10);
```

?[In the code above, what did I forget?]
 - [ ] To apply an operator
 - [X] To subscribe
 - [ ] To register a callback

```java
Flux<String> flux = Flux.just("A");
flux.map(s -> "foo" + s);
flux.subscribe(System.out::println);
```

?[I expected the code above to emit "fooA" but it didn't, why?]
 - [ ] I forgot to subscribe
 - [X] The result of the `map` operator is a new Flux which was discarded
 - [ ] I made a syntax error

## In the next lesson, we'll dive into the Reactor API, starting with its two specialized `Publisher`