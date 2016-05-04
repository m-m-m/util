/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.cli.api.CliArgument;
import net.sf.mmm.util.cli.api.CliContainerStyle;
import net.sf.mmm.util.cli.api.CliException;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.cli.api.CliModes;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.cli.api.CliStyle;
import net.sf.mmm.util.cli.api.CliStyleHandling;
import net.sf.mmm.util.collection.base.NodeCycle;
import net.sf.mmm.util.collection.base.NodeCycleException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;
import net.sf.mmm.util.reflect.api.AnnotationUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.AnnotationUtilImpl;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

/**
 * This is the test-case for {@link CliMode}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class CliStateTest extends Assert {

  /** The option for a string. */
  private static final String OPTION_NAME_STRING = "--string";

  /** The option-alias for a string. */
  private static final String OPTION_ALIAS_STRING = "-s";

  /** The option for a boolean-flag. */
  private static final String OPTION_NAME_BOOLEAN = "--boolean";

  /** The option for a list. */
  private static final String OPTION_NAME_LIST = "--list";

  /** The option for a option. */
  private static final String OPTION_NAME_OPTION = "--option";

  /** Usage for string-option (should be in NlsBundle if not test-case). */
  private static final String OPTION_USAGE_STRING = "some string.";

  /** Usage for boolean-option (should be in NlsBundle if not test-case). */
  private static final String OPTION_USAGE_BOOLEAN = "some boolean trigger.";

  private static final String MODE_X_EXTENDS_DEFAULT = "X";

  private static final String MODE_Y_EXTENDS_HELP = "Y";

  private static final String MODE_Z_EXTENDS_X_Y_HELP = "Z";

  /** The name of a string-argument. */
  private static final String ARGUMENT_NAME_STRING = "string";

  /** The name of a boolean-argument. */
  private static final String ARGUMENT_NAME_BOOLEAN = "boolean";

  /** The name of an argument called {@value} . */
  private static final String ARGUMENT_NAME_FOO = "foo";

  /** The name of an argument called {@value} . */
  private static final String ARGUMENT_NAME_BAR = "bar";

  /** The name of an argument called {@value} . */
  private static final String ARGUMENT_NAME_THING = "thing";

  /** The name of an argument called {@value} . */
  private static final String ARGUMENT_NAME_WHOOP = "whoop";

  /**
   * @return the {@link PojoDescriptorBuilder} instance to use.
   */
  protected PojoDescriptorBuilderFactory getPojoDescriptorBuilderFactory() {

    PojoDescriptorBuilderFactoryImpl factory = new PojoDescriptorBuilderFactoryImpl();
    factory.initialize();
    return factory;
  }

  protected ReflectionUtil getReflectionUtil() {

    return ReflectionUtilImpl.getInstance();
  }

  protected AnnotationUtil getAnnotationUtil() {

    return AnnotationUtilImpl.getInstance();
  }

  protected CliState createState(Class<?> stateClass) {

    CliState state = new CliState(stateClass, getPojoDescriptorBuilderFactory(),
        LoggerFactory.getLogger(CliStateTest.class), getReflectionUtil(), getAnnotationUtil());
    return state;
  }

  /**
   * This method gets the {@link CliModeCycle} in the hierarchy of the given {@code exception}.
   *
   * @param exception the {@link CliException}
   * @return the {@link CliModeCycle}.
   */
  protected NodeCycle getCycle(Exception exception) {

    assertNotNull(exception);
    NodeCycle cycle = null;
    Throwable cause = exception;
    while ((cycle == null) && (cause != null)) {
      if (cause instanceof NodeCycleException) {
        NodeCycleException cycleException = (NodeCycleException) cause;
        cycle = (NodeCycle) cycleException.getNlsMessage().getArgument(NodeCycleException.KEY_CYCLE);
      }
      cause = cause.getCause();
    }
    assertNotNull(cycle);
    return cycle;
  }

  /**
   * Tests the options of a proper state.
   */
  @Test
  public void testOptions() {

    PojoDescriptorBuilderFactory descriptorBuilder = getPojoDescriptorBuilderFactory();

    CliState state;
    state = createState(OptionTest1.class);
    List<CliOptionContainer> optionList = state.getOptions();
    assertNotNull(optionList);
    assertEquals(2, optionList.size());
    CliOptionContainer option = state.getOption(OPTION_NAME_STRING);
    assertNotNull(option);
    assertTrue(optionList.contains(option));
    assertEquals(OPTION_NAME_STRING, option.getOption().name());
    assertEquals(OPTION_USAGE_STRING, option.getOption().usage());
    assertEquals(String.class, option.getSetter().getPropertyClass());
    assertSame(option, state.getOption(OPTION_ALIAS_STRING));
    option = state.getOption(OPTION_NAME_BOOLEAN);
    assertNotNull(option);
    assertTrue(optionList.contains(option));
    assertEquals(OPTION_NAME_BOOLEAN, option.getOption().name());
    assertEquals(OPTION_USAGE_BOOLEAN, option.getOption().usage());
    assertEquals(boolean.class, option.getSetter().getPropertyClass());
    assertNull(state.getOption("undefined"));
  }

  /**
   * Tests that {@link CliMode#parentIds() hierarchy} of {@link CliMode}s is properly build.
   */
  @Test
  public void testModeExtensions() {

    PojoDescriptorBuilderFactory descriptorBuilder = getPojoDescriptorBuilderFactory();
    CliState state;
    state = createState(ArgumentTest1.class);
    CliModeContainer modeObject = (CliModeContainer) state.getMode(MODE_Z_EXTENDS_X_Y_HELP);
    assertNotNull(modeObject);
    assertEquals(MODE_Z_EXTENDS_X_Y_HELP, modeObject.getId());
    Set<? extends CliModeObject> extendedModes = modeObject.getExtendedModes();
    assertTrue(extendedModes.contains(state.getMode(MODE_Z_EXTENDS_X_Y_HELP)));
    assertTrue(extendedModes.contains(state.getMode(MODE_X_EXTENDS_DEFAULT)));
    assertTrue(extendedModes.contains(state.getMode(MODE_Y_EXTENDS_HELP)));
    assertTrue(extendedModes.contains(state.getMode(CliMode.ID_HELP)));
    assertTrue(extendedModes.contains(state.getMode(CliMode.ID_DEFAULT)));
    assertEquals(5, extendedModes.size());
  }

  /**
   * Tests that {@link CliArgument}s are ordered correnctly and properly assigned to modes.
   */
  @Test
  public void testArguments() {

    PojoDescriptorBuilderFactory descriptorBuilder = getPojoDescriptorBuilderFactory();
    CliState state;

    // test regular order
    state = createState(ArgumentTest1.class);
    List<CliArgumentContainer> argumentsList = state.getArguments(state.getMode(MODE_Z_EXTENDS_X_Y_HELP));
    assertEquals(1, argumentsList.size());
    assertEquals(ARGUMENT_NAME_STRING, argumentsList.get(0).getId());
    argumentsList = state.getArguments(state.getMode(CliMode.ID_HELP));
    assertEquals(1, argumentsList.size());
    assertEquals(ARGUMENT_NAME_STRING, argumentsList.get(0).getId());
    argumentsList = state.getArguments(state.getMode(CliMode.ID_DEFAULT));
    assertEquals(4, argumentsList.size());
    assertEquals(ARGUMENT_NAME_BOOLEAN, argumentsList.get(0).getId());
    assertEquals(ARGUMENT_NAME_FOO, argumentsList.get(1).getId());
    assertEquals(ARGUMENT_NAME_BAR, argumentsList.get(2).getId());
    assertEquals(ARGUMENT_NAME_STRING, argumentsList.get(3).getId());

    // Test inheritance
    state = createState(ArgumentTest2.class);
    argumentsList = state.getArguments(state.getMode(CliMode.ID_DEFAULT));
    assertEquals(6, argumentsList.size());
    assertEquals(ARGUMENT_NAME_BOOLEAN, argumentsList.get(0).getId());
    assertEquals(ARGUMENT_NAME_WHOOP, argumentsList.get(1).getId());
    assertEquals(ARGUMENT_NAME_FOO, argumentsList.get(2).getId());
    assertEquals(ARGUMENT_NAME_BAR, argumentsList.get(3).getId());
    assertEquals(ARGUMENT_NAME_STRING, argumentsList.get(4).getId());
    assertEquals(ARGUMENT_NAME_THING, argumentsList.get(5).getId());

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
      state = createState(CyclicTest1.class);
      fail();
    } catch (Exception e) {
      List<CliModeContainer> inverseCycle = getCycle(e).getInverseCycle();
      assertNotNull(inverseCycle);
      assertEquals(2, inverseCycle.size());
      assertEquals("foo", inverseCycle.get(0).getMode().id());
      assertEquals("foo", inverseCycle.get(1).getMode().id());
    }

    // complex dependency...
    try {
      state = createState(CyclicTest2.class);
      fail();
    } catch (Exception e) {
      List<CliModeContainer> inverseCycle = getCycle(e).getInverseCycle();
      assertNotNull(inverseCycle);
      assertEquals(4, inverseCycle.size());
      String firstModeId = inverseCycle.get(0).getMode().id();
      // map order is not deterministic across JDK versions...
      if ("foo".equals(firstModeId)) {
        assertEquals("bar2", inverseCycle.get(1).getMode().id());
        assertEquals("bar1", inverseCycle.get(2).getMode().id());
        assertEquals("foo", inverseCycle.get(3).getMode().id());
      } else if ("bar1".equals(firstModeId)) {
        assertEquals("foo", inverseCycle.get(1).getMode().id());
        assertEquals("bar2", inverseCycle.get(2).getMode().id());
        assertEquals("bar1", inverseCycle.get(3).getMode().id());
      } else if ("bar2".equals(firstModeId)) {
        assertEquals("bar1", inverseCycle.get(1).getMode().id());
        assertEquals("foo", inverseCycle.get(2).getMode().id());
        assertEquals("bar2", inverseCycle.get(3).getMode().id());
      } else {
        fail();
      }
    }
    assertNull(state);
  }

  /**
   * Tests that {@link CliContainerStyle} is properly handled.
   */
  @Test
  public void testContainerStyle() {

    CliState state = createState(ContainerStyleTest.class);
    CliOptionContainer option = state.getOption(OPTION_NAME_OPTION);
    assertEquals(CliContainerStyle.MULTIPLE_OCCURRENCE, option.getContainerStyle(state.getCliStyle()));
    CliOptionContainer list = state.getOption(OPTION_NAME_LIST);
    assertEquals(CliContainerStyle.COMMA_SEPARATED, list.getContainerStyle(state.getCliStyle()));
  }

  /**
   * Tests that illegal container-style is properly reported.
   */
  @Test
  public void testIllegalContainerStyle() {

    CliState state = null;
    // illegal container style
    try {
      state = createState(IllegalContainerStyleTest.class);
      fail();
      // } catch (NlsIllegalArgumentException e) {
    } catch (Exception e) {
      assertTrue(e.getMessage().contains(CliContainerStyle.DEFAULT.toString()));
    }
  }

  @CliStyle(modeUndefined = CliStyleHandling.DEBUG)
  public static class ContainerStyleTest {

    @CliOption(name = OPTION_NAME_OPTION, usage = "The usage of the option.")
    private List<String> option;

    @CliOption(name = OPTION_NAME_LIST, usage = "The usage of the option.", containerStyle = CliContainerStyle.COMMA_SEPARATED)
    private List<String> list;

  }

  @CliStyle(containerStyle = CliContainerStyle.DEFAULT, modeUndefined = CliStyleHandling.DEBUG)
  public static class IllegalContainerStyleTest {

    @CliOption(name = OPTION_NAME_OPTION, usage = "The usage of the option.")
    private String option;

  }

  @CliMode(id = "foo", title = "Foo", parentIds = "foo")
  public static class CyclicTest1 {

  }

  @CliModes(value = { @CliMode(id = "foo", title = "Foo", parentIds = { "bar1", "bar2" }),
  @CliMode(id = "bar1", title = "Bar1", parentIds = { "bar2" }),
  @CliMode(id = "bar2", title = "Bar2", parentIds = { "foo", "bar1" }) })
  public static class CyclicTest2 {

  }

  @CliMode(id = CliMode.ID_DEFAULT, title = "default")
  public static class OptionTest1 {

    @CliOption(name = OPTION_NAME_STRING, aliases = OPTION_ALIAS_STRING, usage = OPTION_USAGE_STRING)
    private String string;

    @CliOption(name = OPTION_NAME_BOOLEAN, usage = OPTION_USAGE_BOOLEAN)
    private boolean bool;
  }

  @CliModes({ @CliMode(id = MODE_X_EXTENDS_DEFAULT, parentIds = CliMode.ID_DEFAULT),
  @CliMode(id = CliMode.ID_DEFAULT, title = "default"),
  @CliMode(id = MODE_Z_EXTENDS_X_Y_HELP, parentIds = { MODE_X_EXTENDS_DEFAULT, MODE_Y_EXTENDS_HELP,
  CliMode.ID_HELP }), @CliMode(id = MODE_Y_EXTENDS_HELP, parentIds = CliMode.ID_HELP),
  @CliMode(id = CliMode.ID_HELP, title = "help") })
  public static class ArgumentTest1 {

    @CliArgument(name = ARGUMENT_NAME_STRING, usage = OPTION_USAGE_STRING, //
        mode = MODE_Z_EXTENDS_X_Y_HELP)
    private String string;

    @CliArgument(name = ARGUMENT_NAME_BOOLEAN, addCloseTo = CliArgument.ID_FIRST, //
        addAfter = false, usage = OPTION_USAGE_BOOLEAN)
    private boolean bool;

    @CliArgument(name = ARGUMENT_NAME_FOO, addCloseTo = ARGUMENT_NAME_BAR, //
        addAfter = false, usage = "some foo", mode = MODE_X_EXTENDS_DEFAULT)
    private String foo;

    @CliArgument(name = ARGUMENT_NAME_BAR, addCloseTo = ARGUMENT_NAME_BOOLEAN, //
        addAfter = true, usage = "some bar", mode = MODE_X_EXTENDS_DEFAULT)
    private String bar;

  }

  public static class ArgumentTest2 extends ArgumentTest1 {

    @CliArgument(name = ARGUMENT_NAME_THING, addCloseTo = CliArgument.ID_LAST, //
        addAfter = true, usage = "some thing", mode = MODE_X_EXTENDS_DEFAULT)
    private String thing;

    @CliArgument(name = ARGUMENT_NAME_WHOOP, addCloseTo = ARGUMENT_NAME_FOO, //
        addAfter = false, usage = "some whoop")
    private boolean whoop;

  }

}
