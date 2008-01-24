/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file;

import static org.junit.Assert.assertEquals;

import java.io.File;

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
  public void testExtension() {

    FileUtil util = getFileUtil();
    assertEquals("java", util.getExtension("test.java"));
    assertEquals("", util.getExtension(".java"));
    assertEquals("gz", util.getExtension("archive.tar.gz"));
  }

  @Test
  public void testNormalizePath() {

    FileUtil util = getFileUtil();
    assertEquals(File.separator, util.normalizePath("////.//"));
    assertEquals(File.separator, util.normalizePath("/foo/../bar/.."));
    assertEquals(File.separator + "foo", util.normalizePath("/foo/bar/../bar/.."));
    assertEquals(File.separator + "foo", util.normalizePath("/foo\\bar/..\\bar/.."));
    assertEquals(File.separator + "foo", util.normalizePath("/foo\\//.\\./bar/..\\bar/.."));
    assertEquals("foo" + File.separator + "bar", util.normalizePath("foo\\//.\\./bar/."));
    assertEquals(System.getProperty("user.home"), util.normalizePath("~"));
    assertEquals(System.getProperty("user.home") + File.separator, util.normalizePath("~/"));
    assertEquals(System.getProperty("user.home"), util.normalizePath("~/foo/./.."));
    assertEquals(util.normalizePath(System.getProperty("user.home") + "/../someuser"), util
        .normalizePath("~someuser"));
  }

  @Test
  public void testBasename() {

    FileUtil util = getFileUtil();
    assertEquals("", util.getBasename(""));
    assertEquals("a", util.getBasename("a"));
    assertEquals("/", util.getBasename("/"));
    assertEquals("/", util.getBasename("///"));
    assertEquals("\\", util.getBasename("\\"));
    assertEquals("\\", util.getBasename("\\\\"));
    assertEquals("\\", util.getBasename("\\/\\"));
    assertEquals("/", util.getBasename("/\\/"));
    assertEquals(".", util.getBasename("/."));
    assertEquals("..", util.getBasename("/.."));
    assertEquals("foo", util.getBasename("foo"));
    assertEquals("bar", util.getBasename("foo/bar"));
    assertEquals("foo.bar", util.getBasename("foo.bar"));
    assertEquals("foo.bar", util.getBasename("./foo.bar"));
    assertEquals("foo", util.getBasename("/foo"));
    assertEquals("foo", util.getBasename("/foo/"));
    assertEquals("bar", util.getBasename("/foo/bar"));
    assertEquals("bar", util.getBasename("/foo/bar//"));
    assertEquals("", util.getBasename("c:\\"));
    assertEquals("", util.getBasename("http://"));
    assertEquals("foo", util.getBasename("c:\\foo"));
    assertEquals("foo", util.getBasename("http://foo"));
    assertEquals("bar", util.getBasename("http://foo.org/bar"));
  }

  @Test
  public void testDirname() {

    FileUtil util = getFileUtil();
    assertEquals("/", util.getDirname("/"));
    assertEquals("/", util.getDirname("/foo"));
    assertEquals("/", util.getDirname("/foo/"));
    assertEquals("\\", util.getDirname("\\foo\\"));
    assertEquals(".", util.getDirname("foo"));
    assertEquals(".", util.getDirname("foo/"));
    assertEquals(".", util.getDirname("foo\\"));
    assertEquals("/foo", util.getDirname("/foo/bar"));
    assertEquals("/foo", util.getDirname("/foo/bar/"));
    assertEquals("foo", util.getDirname("foo/bar"));
    assertEquals("foo", util.getDirname("foo/bar/"));
    assertEquals("foo", util.getDirname("foo\\bar\\"));
    assertEquals("foo/bar", util.getDirname("foo/bar/test"));
    assertEquals("foo\\bar/test", util.getDirname("foo\\bar/test/xxx"));
    assertEquals("foo\\bar/test", util.getDirname("foo\\bar/test/xxx\\"));
    assertEquals("C:\\", util.getDirname("C:\\foo"));
    assertEquals("C:", util.getDirname("C:"));
    assertEquals("http://", util.getDirname("http://"));
    assertEquals("http://", util.getDirname("http://foo"));
  }

}
