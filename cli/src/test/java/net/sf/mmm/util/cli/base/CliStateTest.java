/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import net.sf.mmm.util.cli.api.CliArgument;
import net.sf.mmm.util.cli.api.CliContainerStyle;
import net.sf.mmm.util.cli.api.CliException;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.cli.api.CliModes;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.cli.api.CliStyle;
import net.sf.mmm.util.cli.api.CliStyleHandling;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;
import net.sf.mmm.util.reflect.api.AnnotationUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.AnnotationUtilImpl;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * This is the test-case for {@link CliMode}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class CliStateTest extends Assertions {

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

    CliState state = new CliState(stateClass, getPojoDescriptorBuilderFactory(), getReflectionUtil(), getAnnotationUtil());
    return state;
  }

  /**
   * This method gets the {@link CliModeCycle} in the hierarchy of the given {@code exception}.
   *
   * @param exception the {@link CliException}
   * @return the {@link CliModeCycle}.
   */
  protected String getCycle(Exception exception) {

    assertThat(exception).isNotNull();
    String cycle = null;
    Throwable cause = exception;
    while ((cycle == null) && (cause != null)) {
      if (cause instanceof IllegalStateException) {
        String message = cause.getMessage();
        if (message.startsWith("Cyclic dependency ")) {
          cycle = message.substring(message.indexOf(':') + 1).trim();
        }
      }
      cause = cause.getCause();
    }
    assertThat(cycle).isNotNull();
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
    assertThat(optionList).isNotNull();
    assertThat(optionList).hasSize(2);
    CliOptionContainer option = state.getOption(OPTION_NAME_STRING);
    assertThat(option).isNotNull();
    assertThat(optionList.contains(option)).isTrue();
    assertThat(option.getOption().name()).isEqualTo(OPTION_NAME_STRING);
    assertThat(option.getOption().usage()).isEqualTo(OPTION_USAGE_STRING);
    assertThat(option.getSetter().getPropertyClass()).isEqualTo(String.class);
    assertThat(state.getOption(OPTION_ALIAS_STRING)).isSameAs(option);
    option = state.getOption(OPTION_NAME_BOOLEAN);
    assertThat(option).isNotNull();
    assertThat(optionList).contains(option);
    assertThat(option.getOption().name()).isEqualTo(OPTION_NAME_BOOLEAN);
    assertThat(option.getOption().usage()).isEqualTo(OPTION_USAGE_BOOLEAN);
    assertThat(option.getSetter().getPropertyClass()).isEqualTo(boolean.class);
    assertThat(state.getOption("undefined")).isNull();
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
    assertThat(modeObject).isNotNull();
    assertThat(modeObject.getId()).isEqualTo(MODE_Z_EXTENDS_X_Y_HELP);
    Set<CliModeObject> extendedModes = (Set) modeObject.getExtendedModes();
    CliModeObject[] states = new CliModeObject[] { state.getMode(MODE_Z_EXTENDS_X_Y_HELP), state.getMode(MODE_X_EXTENDS_DEFAULT),
    state.getMode(MODE_Y_EXTENDS_HELP), state.getMode(CliMode.ID_HELP), state.getMode(CliMode.ID_DEFAULT) };
    assertThat(extendedModes).containsExactlyInAnyOrder(states);
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
    assertThat(argumentsList).hasSize(1);
    assertThat(argumentsList.get(0).getId()).isEqualTo(ARGUMENT_NAME_STRING);
    argumentsList = state.getArguments(state.getMode(CliMode.ID_HELP));
    assertThat(argumentsList).hasSize(1);
    assertThat(argumentsList.get(0).getId()).isEqualTo(ARGUMENT_NAME_STRING);
    argumentsList = state.getArguments(state.getMode(CliMode.ID_DEFAULT));
    assertThat(argumentsList).hasSize(4);
    assertThat(argumentsList.get(0).getId()).isEqualTo(ARGUMENT_NAME_BOOLEAN);
    assertThat(argumentsList.get(1).getId()).isEqualTo(ARGUMENT_NAME_FOO);
    assertThat(argumentsList.get(2).getId()).isEqualTo(ARGUMENT_NAME_BAR);
    assertThat(argumentsList.get(3).getId()).isEqualTo(ARGUMENT_NAME_STRING);

    // Test inheritance
    state = createState(ArgumentTest2.class);
    argumentsList = state.getArguments(state.getMode(CliMode.ID_DEFAULT));
    assertThat(argumentsList).hasSize(6);
    assertThat(argumentsList.get(0).getId()).isEqualTo(ARGUMENT_NAME_BOOLEAN);
    assertThat(argumentsList.get(1).getId()).isEqualTo(ARGUMENT_NAME_WHOOP);
    assertThat(argumentsList.get(2).getId()).isEqualTo(ARGUMENT_NAME_FOO);
    assertThat(argumentsList.get(3).getId()).isEqualTo(ARGUMENT_NAME_BAR);
    assertThat(argumentsList.get(4).getId()).isEqualTo(ARGUMENT_NAME_STRING);
    assertThat(argumentsList.get(5).getId()).isEqualTo(ARGUMENT_NAME_THING);

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
      failBecauseExceptionWasNotThrown(IllegalStateException.class);
    } catch (Exception e) {
      assertThat(getCycle(e)).isEqualTo("foo-->foo");
    }

    // complex dependency...
    try {
      state = createState(CyclicTest2.class);
      failBecauseExceptionWasNotThrown(IllegalStateException.class);
    } catch (Exception e) {
      assertThat(Arrays.asList("foo-->bar1-->bar2-->foo", "bar2-->foo-->bar1-->bar2", "bar1-->bar2-->foo-->bar1")).contains(getCycle(e));
    }
    assertThat(state).isNull();
  }

  /**
   * Tests that {@link CliContainerStyle} is properly handled.
   */
  @Test
  public void testContainerStyle() {

    CliState state = createState(ContainerStyleTest.class);
    CliOptionContainer option = state.getOption(OPTION_NAME_OPTION);
    assertThat(option.getContainerStyle(state.getCliStyle())).isEqualTo(CliContainerStyle.MULTIPLE_OCCURRENCE);
    CliOptionContainer list = state.getOption(OPTION_NAME_LIST);
    assertThat(list.getContainerStyle(state.getCliStyle())).isEqualTo(CliContainerStyle.COMMA_SEPARATED);
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
      failBecauseExceptionWasNotThrown(NlsIllegalArgumentException.class);
    } catch (Exception e) {
      assertThat(e).hasMessageContaining(CliContainerStyle.DEFAULT.toString());
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
  @CliMode(id = "bar1", title = "Bar1", parentIds = { "bar2" }), @CliMode(id = "bar2", title = "Bar2", parentIds = { "foo", "bar1" }) })
  public static class CyclicTest2 {

  }

  @CliMode(id = CliMode.ID_DEFAULT, title = "default")
  public static class OptionTest1 {

    @CliOption(name = OPTION_NAME_STRING, aliases = OPTION_ALIAS_STRING, usage = OPTION_USAGE_STRING)
    private String string;

    @CliOption(name = OPTION_NAME_BOOLEAN, usage = OPTION_USAGE_BOOLEAN)
    private boolean bool;
  }

  @CliModes({ @CliMode(id = MODE_X_EXTENDS_DEFAULT, parentIds = CliMode.ID_DEFAULT), @CliMode(id = CliMode.ID_DEFAULT, title = "default"),
  @CliMode(id = MODE_Z_EXTENDS_X_Y_HELP, parentIds = { MODE_X_EXTENDS_DEFAULT, MODE_Y_EXTENDS_HELP, CliMode.ID_HELP }),
  @CliMode(id = MODE_Y_EXTENDS_HELP, parentIds = CliMode.ID_HELP), @CliMode(id = CliMode.ID_HELP, title = "help") })
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
