package com.example.reactive;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Assert;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;

/**
 * https://github.com/reactor/reactor-samples/blob/master/src/main/java/org/projectreactor/samples/MonoSamples.java
 *
 * @author <a href="mailto:paul.russo@jchart.com>Paul Russo</a>
 * @since Feb 4, 2017
 */
public class TestMonoProcessor {

   private static final Logger LOG = LoggerFactory
         .getLogger(TestMonoProcessor.class);
   
   // extension that implements stateful semantics
   private MonoProcessor<String> promiseObject = MonoProcessor.create();
   
   // returns "at most one"
   private Mono<String> monoResult;
   
   @Before
   public void setUp() {
      monoResult = promiseObject
            .doOnSuccess(p -> LOG.info("Promise completed {}", p))
            .doOnTerminate((s, e) -> LOG.info("Got value: {}", s))
            .doOnError(t -> LOG.error(t.getMessage(), t));
   }
   
   @Test
   public void test1() {
      String monoText = "Test1";
      promiseObject.subscribe(); // start the chain and request unbounded demand.
      promiseObject.onNext(monoText);
      String result = monoResult.block();
      Assert.assertEquals(monoText, result);
   }

   @Test
   public void test2() {
      String monoText = "Test2";
      promiseObject.subscribe(); // start the chain and request unbounded demand.
      promiseObject.onNext(monoText);
      String result = monoResult.block();
      Assert.assertEquals(monoText, result);
   }

}
