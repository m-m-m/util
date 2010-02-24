/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.List;

import net.sf.mmm.util.cli.api.CliException;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliModeCycleException;
import net.sf.mmm.util.cli.api.CliModes;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.cli.base.CliClassContainer.CliModeCycle;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link CliMode}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
@SuppressWarnings("all")
public class CliStateTest {

  /** The option for a string. */
  private static final String OPTION_NAME_STRING = "--string";

  /** The option-alias for a string. */
  private static final String OPTION_ALIAS_STRING = "-s";

  /** The option for a boolean-flag. */
  private static final String OPTION_NAME_BOOLEAN = "--boolean";

  /** Usage for string-option (should be in NlsBundle if not test-case). */
  private static final String OPTION_USAGE_STRING = "some string.";

  /** Usage for boolean-option (should be in NlsBundle if not test-case). */
  private static final String OPTION_USAGE_BOOLEAN = "some boolean trigger.";

  /**
   * @return the {@link PojoDescriptorBuilder} instance to use.
   */
  protected PojoDescriptorBuilderFactory getPojoDescriptorBuilderFactory() {

    PojoDescriptorBuilderFactoryImpl factory = new PojoDescriptorBuilderFactoryImpl();
    factory.initialize();
    return factory;
  }

  /**
   * This method gets the {@link CliModeCycle} in the hierarchy of the given
   * <code>exception</code>.
   * 
   * @param exception the {@link CliException}
   * @return the {@link CliModeCycle}.
   */
  protected CliModeCycle getCycle(CliException exception) {

    Assert.assertNotNull(exception);
    CliModeCycle cycle = null;
    Throwable cause = exception;
    while ((cycle == null) && (cause != null)) {
      if (cause instanceof CliModeCycleException) {
        CliModeCycleException cycleException = (CliModeCycleException) cause;
        cycle = (CliModeCycle) cycleException.getNlsMessage().getArgument(
            CliModeCycleException.KEY_CYCLE);
      }
      cause = cause.getCause();
    }
    Assert.assertNotNull(cycle);
    return cycle;
  }

  /**
   * Tests the options of a proper state.
   */
  @Test
  public void testOptions() {

    PojoDescriptorBuilderFactory descriptorBuilder = getPojoDescriptorBuilderFactory();

    CliState state;
    state = new CliState(OptionTest1.class, descriptorBuilder);
    List<CliOptionContainer> optionList = state.getOptions();
    Assert.assertNotNull(optionList);
    Assert.assertEquals(2, optionList.size());
    CliOptionContainer option = state.getOption(OPTION_NAME_STRING);
    Assert.assertNotNull(option);
    Assert.assertTrue(optionList.contains(option));
    Assert.assertEquals(OPTION_NAME_STRING, option.getOption().name());
    Assert.assertEquals(OPTION_USAGE_STRING, option.getOption().usage());
    Assert.assertEquals(String.class, option.getSetter().getPropertyClass());
    Assert.assertSame(option, state.getOption(OPTION_ALIAS_STRING));
    option = state.getOption(OPTION_NAME_BOOLEAN);
    Assert.assertNotNull(option);
    Assert.assertTrue(optionList.contains(option));
    Assert.assertEquals(OPTION_NAME_BOOLEAN, option.getOption().name());
    Assert.assertEquals(OPTION_USAGE_BOOLEAN, option.getOption().usage());
    Assert.assertEquals(boolean.class, option.getSetter().getPropertyClass());
    Assert.assertNull(state.getOption("undefined"));
  }

  /**
   * Tests that a cyclic mode dependency is discovered and properly reported.
   */
  @Test
  public void testCyclicModeDependency() {

    PojoDescriptorBuilderFactory descriptorBuilderFactory = getPojoDescriptorBuilderFactory();

    // self dependency...
    CliState state = null;
    try {
      state = new CliState(CyclicTest1.class, descriptorBuilderFactory);
      Assert.fail();
    } catch (CliException e) {
      List<CliModeContainer> inverseCycle = getCycle(e).getInverseCycle();
      Assert.assertNotNull(inverseCycle);
      Assert.assertEquals(2, inverseCycle.size());
      Assert.assertEquals("foo", inverseCycle.get(0).getMode().id());
      Assert.assertEquals("foo", inverseCycle.get(1).getMode().id());
    }

    // complex dependency...
    try {
      state = new CliState(CyclicTest2.class, descriptorBuilderFactory);
      Assert.fail();
    } catch (CliException e) {
      List<CliModeContainer> inverseCycle = getCycle(e).getInverseCycle();
      Assert.assertNotNull(inverseCycle);
      Assert.assertEquals(4, inverseCycle.size());
      Assert.assertEquals("foo", inverseCycle.get(0).getMode().id());
      Assert.assertEquals("bar2", inverseCycle.get(1).getMode().id());
      Assert.assertEquals("bar1", inverseCycle.get(2).getMode().id());
      Assert.assertEquals("foo", inverseCycle.get(3).getMode().id());
    }
    Assert.assertNull(state);
  }

  @CliMode(id = "foo", title = "Foo", parentIds = "foo")
  public static class CyclicTest1 {

  }

  @CliModes(value = { @CliMode(id = "foo", title = "Foo", parentIds = { "bar1", "bar2" }),
      @CliMode(id = "bar1", title = "Bar1", parentIds = { "bar2" }),
      @CliMode(id = "bar2", title = "Bar2", parentIds = { "foo", "bar1" }) })
  public static class CyclicTest2 {

  }

  @CliMode(id = CliMode.MODE_DEFAULT, title = "default")
  public static class OptionTest1 {

    @CliOption(name = OPTION_NAME_STRING, aliases = OPTION_ALIAS_STRING, usage = OPTION_USAGE_STRING)
    private String string;

    @CliOption(name = OPTION_NAME_BOOLEAN, usage = OPTION_USAGE_BOOLEAN)
    private boolean bool;
  }

}
