/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.impl;

import net.sf.mmm.util.context.api.GenericContext;
import net.sf.mmm.util.context.api.GenericContextFactory;
import net.sf.mmm.util.context.api.MutableGenericContext;
import net.sf.mmm.util.value.api.ValueNotSetException;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the test-case for {@link GenericContextFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class GenericContextFactoryTest {

  /**
   * This method gets an instance of the {@link GenericContextFactory}.
   * 
   * @return the {@link GenericContextFactory}.
   */
  protected GenericContextFactory getFactory() {

    GenericContextFactoryImpl factory = new GenericContextFactoryImpl();
    factory.initialize();
    return factory;
  }

  /**
   * Simple test of a {@link MutableGenericContext}.
   */
  @Test
  public void testSimple() {

    GenericContextFactory factory = getFactory();
    MutableGenericContext context = factory.createContext();
    Assert.assertTrue(context.getVariableNames().isEmpty());
    String keyFoo = "foo";
    Assert.assertNull(context.getVariable(keyFoo));
    Assert.assertNull(context.removeVariable(keyFoo));
    Assert.assertTrue(context.toMap().isEmpty());
    Assert.assertFalse(context.hasVariable(keyFoo));

    GenericContext immutableContext = context.getImmutableContext();
    Assert.assertFalse(immutableContext instanceof MutableGenericContext);
    Assert.assertNull(immutableContext.getVariable(keyFoo));
    String valueFoo = "424242";
    Assert.assertNull(context.setVariable(keyFoo, valueFoo));
    Assert.assertTrue(context.hasVariable(keyFoo));
    Assert.assertSame(valueFoo, context.getVariable(keyFoo));
    Assert.assertSame(valueFoo, immutableContext.getVariable(keyFoo));
    Assert.assertSame(valueFoo, context.requireVariable(keyFoo));
    Assert.assertSame(valueFoo, immutableContext.requireVariable(keyFoo));
    String keyBar = "bar";
    try {
      context.requireVariable(keyBar);
      Assert.fail("Exception expected");
    } catch (ValueNotSetException e) {
      // expected...
    }
    String valueBar = "fourty-two";
    Assert.assertNull(context.setVariable(keyBar, valueBar));
    Assert.assertSame(valueBar, context.getVariable(keyBar, String.class));
    Assert.assertSame(valueBar, immutableContext.getVariable(keyBar, String.class));
    Assert.assertSame(valueBar, context.requireVariable(keyBar, String.class));
    Assert.assertSame(valueBar, immutableContext.requireVariable(keyBar, String.class));
    Assert.assertEquals(2, context.getVariableNames().size());
    Assert.assertTrue(context.getVariableNames().contains(keyFoo));
    Assert.assertTrue(context.getVariableNames().contains(keyBar));
    Assert.assertSame(valueFoo, context.removeVariable(keyFoo));
    Assert.assertFalse(context.hasVariable(keyFoo));
  }

  /**
   * Test of a {@link MutableGenericContext} with conversion of values.
   */
  @Test
  public void testConversion() {

    GenericContextFactory factory = getFactory();
    MutableGenericContext context = factory.createContext();
    String key = "foo";
    Integer value = Integer.valueOf(12345);
    context.setVariable(key, value);
    Assert.assertEquals(value, context.getVariable(key, Integer.class));
    Assert.assertEquals(value.intValue(), context.getVariable(key, int.class).intValue());
  }

  /**
   * This method tests {@link GenericContext#createChildContext() child contexts}.
   */
  @Test
  public void testChildContexts() {

    GenericContextFactory factory = getFactory();
    MutableGenericContext context = factory.createContext();
    String keyFoo = "foo";
    String valueFoo = "424242";
    Assert.assertNull(context.setVariable(keyFoo, valueFoo));
    String keyBar = "bar";
    String valueBar = "fourty-two";
    Assert.assertNull(context.setVariable(keyBar, valueBar));
    GenericContext immutableContext = context.getImmutableContext();
    MutableGenericContext childContext = immutableContext.createChildContext();
    Assert.assertSame(valueBar, childContext.requireVariable(keyBar, String.class));
    MutableGenericContext childChildContext = childContext.createChildContext();
    Assert.assertSame(valueFoo, childChildContext.requireVariable(keyFoo, String.class));

    String valueFooNew = "child-child";
    Assert.assertNull(childChildContext.setVariable(keyFoo, valueFooNew));
    Assert.assertSame(valueFoo, childContext.requireVariable(keyFoo, String.class));
    Assert.assertSame(valueFooNew, childChildContext.requireVariable(keyFoo, String.class));
    Assert.assertSame(valueFooNew, childChildContext.removeVariable(keyFoo));
    Assert.assertSame(valueFoo, childChildContext.getVariable(keyFoo, String.class));
    Assert.assertNull(childChildContext.removeVariable(keyFoo));
    Assert.assertSame(valueFoo, childChildContext.getVariable(keyFoo, String.class));
    Assert.assertSame(valueFoo, context.setVariable(keyFoo, valueFooNew));
    Assert.assertSame(valueFooNew, childChildContext.requireVariable(keyFoo, String.class));

    String keySome = "some";
    String valueSome = "magic";
    Assert.assertNull(childContext.setVariable(keySome, valueSome));
    Assert.assertSame(valueSome, childContext.requireVariable(keySome, String.class));
    Assert.assertSame(valueSome, childChildContext.requireVariable(keySome, String.class));
    Assert.assertNull(context.getVariable(keySome, String.class));
  }

  /**
   * This method tests modifications of a root-context while concurrent threads read from individual
   * {@link GenericContext#createChildContext() child contexts}.
   * 
   * @throws Exception on failure.
   */
  @Test
  public void testConcurrent() throws Exception {

    GenericContextFactory factory = getFactory();
    MutableGenericContext context = factory.createContext();
    String keyFoo = "foo";
    String valueFoo = "424242";
    Assert.assertNull(context.setVariable(keyFoo, valueFoo));
    String keyBar = "bar";
    String valueBar = "fourty-two";
    Assert.assertNull(context.setVariable(keyBar, valueBar));

    for (int i = 0; i < 10; i++) {
      ContextReader reader = new ContextReader(context.createChildContext(), "thread-" + i);
      Thread readerThread = new Thread(reader);
      readerThread.start();
    }

    for (int i = 0; i < 100; i++) {
      context.setVariable(ContextReader.KEY, Integer.toString(i));
      Thread.sleep(1);
      context.setVariable(ContextReader.KEY + i, Integer.toString(i));
      context.removeVariable(ContextReader.KEY);
      Thread.sleep(1);
    }
    context.setVariable(ContextReader.DONE, ContextReader.DONE);
  }

  /**
   * This inner class is a {@link Runnable} that reads variables from a context.
   */
  private static class ContextReader implements Runnable {

    /** Variable to read concurrently. */
    private static final String KEY = "key";

    /** Name of variable to indicate stop. */
    private static final String DONE = "done";

    /** @see #run() */
    private final GenericContext context;

    /** @see #run() */
    private final Logger logger;

    /** @see #run() */
    private final String name;

    /**
     * The constructor.
     * 
     * @param context is the context to read from.
     * @param name is the name of this reader.
     */
    public ContextReader(GenericContext context, String name) {

      super();
      this.context = context;
      this.logger = LoggerFactory.getLogger(getClass());
      this.name = name;
    }

    public void run() {

      try {
        while (!this.context.hasVariable(DONE)) {
          Object value = this.context.getVariable(KEY);
          if (value != null) {
            this.logger.trace(this.name + ":" + value);
          }
          Thread.sleep(10);
        }
        this.logger.debug(this.name + " successfully terminated");
      } catch (Exception e) {
        this.logger.error("unexpected error", e);
      }
    }
  }

}
