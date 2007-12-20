/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

/**
 * This is the test-case for {@link ProcessUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ProcessUtilTest {

  public ProcessUtil getProcessUtil() {

    return ProcessUtil.getInstance();
  }

  @Test
  public void testExecute() throws Exception {

    ProcessContext context = new ProcessContext();
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    context.setErrStream(errStream);
    int exitCode = getProcessUtil().execute(context, new ProcessBuilder("java", "-version"));
    assertEquals(0, exitCode);
    byte[] errBytes = errStream.toByteArray();
    String errString = new String(errBytes);
    assertTrue(errString.contains("java version"));
  }

}
