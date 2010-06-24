/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.lang.api.StringUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link AbstractVersionedMain}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
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
    Assert.assertEquals(TestMain.MAGIC_VERSION + StringUtil.LINE_SEPARATOR, version);
    Assert.assertEquals("", errorBuffer.toString());
  }

  @CliClass(usage = TestMain.USAGE)
  private static class TestMain extends AbstractVersionedMain {

    private static final String USAGE = "This program is used for tests only";

    private static final String MAGIC_VERSION = "it's a kind of magic!";

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getVersion() {

      return MAGIC_VERSION;
    }

  }
}
