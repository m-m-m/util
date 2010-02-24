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
 * @since 1.1.2
 */
public class AbstractVersionedMainTest {

  /**
   * Test the option "--version".
   */
  @Test
  public void testVersion() {

    final String magic = "it's a kind of magic!";
    AbstractVersionedMain main = new AbstractVersionedMain() {

      @Override
      protected String getVersion() {

        return magic;
      }
    };

    StringBuilder errorBuffer = new StringBuilder();
    main.setStandardError(errorBuffer);
    StringBuilder outputBuffer = new StringBuilder();
    main.setStandardOutput(outputBuffer);
    int exitCode = main.run(new String[] { "--version" });
    Assert.assertEquals(0, exitCode);
    String version = outputBuffer.toString();
    Assert.assertEquals(magic + StringUtil.LINE_SEPARATOR, version);
    Assert.assertEquals("", errorBuffer.toString());
  }
}
