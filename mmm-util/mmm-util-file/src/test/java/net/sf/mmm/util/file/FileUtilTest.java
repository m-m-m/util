/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * This is the test-case for {@link FileUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FileUtilTest {

  protected FileUtil getFileUtil() {

    return FileUtil.getInstance();
  }

  @Test
  public void testFoo() {

    FileUtil util = getFileUtil();
    assertEquals("java", util.getExtension("test.java"));
    assertEquals("", util.getExtension(".java"));
    assertEquals("gz", util.getExtension("archive.tar.gz"));
  }
}
