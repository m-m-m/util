/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.lang.api.StringUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link AbstractVersionedMain}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class AbstractVersionedMainTest {

  /**
   * Test the option "--help".
   */
  @Test
  public void testHelp() {

    AbstractVersionedMain main = new TestMain();
    StringBuilder errorBuffer = new StringBuilder();
    main.setStandardError(errorBuffer);
    StringBuilder outputBuffer = new StringBuilder();
    main.setStandardOutput(outputBuffer);
    int exitCode = main.run(new String[] { "--help" });
    String error = errorBuffer.toString();
    Assert.assertEquals("", error);
    Assert.assertEquals(0, exitCode);
    String help = outputBuffer.toString();
    System.out.println(help);
    Assert.assertTrue(help.contains(main.getClass().getName()));
    Assert.assertTrue(help.contains(TestMain.USAGE));
    // TODO
  }

  /**
   * Test the option "--version".
   */
  @Test
  public void testVersion() {

    AbstractVersionedMain main = new TestMain();

    StringBuilder errorBuffer = new StringBuilder();
    main.setStandardError(errorBuffer);
    StringBuilder outputBuffer = new StringBuilder();
    main.setStandardOutput(outputBuffer);
    int exitCode = main.run(new String[] { "--version" });
    Assert.assertEquals(0, exitCode);
    String version = outputBuffer.toString();
    Assert.assertEquals("42.1.2.3" + System.getProperty(StringUtil.SYSTEM_PROPERTY_LINE_SEPARATOR), version);
    Assert.assertEquals("", errorBuffer.toString());
  }

  /**
   * Test the default mode with argument.
   */
  @Test
  public void testDefaultWithEndOptions() {

    AbstractVersionedMain main = new TestMain();

    StringBuilder buffer = new StringBuilder();
    main.setStandardError(buffer);
    main.setStandardOutput(buffer);
    String argumentValue = "-argument-";
    int exitCode = main.run(new String[] { "--", argumentValue });
    String output = buffer.toString();
    Assert.assertEquals(output, 0, exitCode);
    Assert.assertEquals(argumentValue, output);
  }

  /**
   * Test the default mode with argument.
   */
  @Test
  public void testDefault() {

    AbstractVersionedMain main = new TestMain();

    StringBuilder buffer = new StringBuilder();
    main.setStandardError(buffer);
    main.setStandardOutput(buffer);
    String argumentValue = "42argument42";
    int exitCode = main.run(new String[] { argumentValue });
    String output = buffer.toString();
    Assert.assertEquals(output, 0, exitCode);
    Assert.assertEquals(argumentValue, output);
  }

  /**
   * Test the default mode with argument and combined options (-fb).
   */
  @Test
  public void testDefaultWithCombinedOptions() {

    AbstractVersionedMain main = new TestMain();

    StringBuilder buffer = new StringBuilder();
    main.setStandardError(buffer);
    main.setStandardOutput(buffer);
    String argumentValue = "-argument-";
    int exitCode = main.run(new String[] { "-fb", "--", argumentValue });
    String output = buffer.toString();
    Assert.assertEquals(output, 0, exitCode);
    Assert.assertEquals("foo bar " + argumentValue, output);
  }

  /**
   * Test the default mode with argument and combined options (-fb).
   */
  @Test
  public void testDefaultWithLongOption() {

    AbstractVersionedMain main = new TestMain();

    StringBuilder buffer = new StringBuilder();
    main.setStandardError(buffer);
    main.setStandardOutput(buffer);
    String argumentValue = "-argument-";
    int exitCode = main.run(new String[] { "--bar", "--", argumentValue });
    String output = buffer.toString();
    Assert.assertEquals(output, 0, exitCode);
    Assert.assertEquals("bar " + argumentValue, output);
  }

  @CliClass(usage = TestMain.USAGE)
  @CliMode(id = CliMode.ID_DEFAULT)
  private static class TestMain extends AbstractVersionedMain {

    public static final String USAGE = "This program is used for tests only";

    @CliOption(name = "--foo", aliases = { "-f" }, usage = "Option to print out 'foo ' for testing.")
    private boolean foo;

    @CliOption(name = "--bar", aliases = { "-b" }, usage = "Option to print out 'bar ' for testing.")
    private boolean bar;

    @CliArgument(name = "Argument", usage = "Some argument for testing.")
    private String argument;

    @Override    protected int runDefaultMode() throws Exception {

      if (this.foo) {
        getStandardOutput().print("foo ");
      }
      if (this.bar) {
        getStandardOutput().print("bar ");
      }
      getStandardOutput().print(this.argument);
      return EXIT_CODE_OK;
    }

  }
}
