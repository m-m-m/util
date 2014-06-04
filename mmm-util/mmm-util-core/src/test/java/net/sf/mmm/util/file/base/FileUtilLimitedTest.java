/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.base;

import net.sf.mmm.util.file.api.FileUtilLimited;

import org.junit.Assert;
import org.junit.Test;

import ch.qos.logback.core.util.FileUtil;

/**
 * This is the test-case for {@link FileUtilImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FileUtilLimitedTest extends Assert {

  protected FileUtilLimited getFileUtil() {

    return FileUtilLimitedImpl.getInstance();
  }

  /**
   * Tests {@link FileUtil#getExtension(String)}.
   */
  @Test
  public void testExtension() {

    FileUtilLimited util = getFileUtil();
    assertEquals("java", util.getExtension("test.java"));
    assertEquals("", util.getExtension(".java"));
    assertEquals("gz", util.getExtension("archive.tar.gz"));
  }

  /**
   * Tests {@link FileUtil#normalizePath(String)}.
   */
  @Test
  public void testNormalizePath() {

    FileUtilLimited util = getFileUtil();
    assertEquals("/", util.normalizePath("/\\///.//", '/'));
    assertEquals("/", util.normalizePath("/foo/../bar/..", '/'));
    assertEquals("/foo", util.normalizePath("/foo/bar/../bar/..", '/'));
    assertEquals("/foo", util.normalizePath("/foo\\bar/..\\bar/..", '/'));
    assertEquals("/foo", util.normalizePath("/foo\\//.\\./bar/..\\bar/..", '/'));
    assertEquals("foo/bar", util.normalizePath("foo\\//.\\./bar/.", '/'));
    String homeDir = "~";
    assertEquals(homeDir, util.normalizePath("~", '/'));
    assertEquals(homeDir + "/", util.normalizePath("~/", '/'));
    assertEquals(homeDir, util.normalizePath("~/foo/./..", '/'));
    assertEquals(homeDir, util.normalizePath("~/foo/./..", '/'));
    assertEquals(homeDir + "/.mmm/search.xml", util.normalizePath("~/.mmm/search.xml", '/'));
    assertEquals("/root/.ssh/authorized_keys", util.normalizePath("~root/.ssh/authorized_keys", '/'));
    if ("/root".equals(homeDir)) {
      homeDir = "/home/nobody";
    }
    assertEquals(util.normalizePath(homeDir + "/../someuser", '/'), util.normalizePath("~someuser", '/'));
    String uncPath = "\\\\10.0.0.1\\share";
    assertEquals(uncPath, util.normalizePath(uncPath, '/'));
    assertEquals("http://www.host.com/foo", util.normalizePath("http://www.host.com/foo/bar/./test/.././.."));
    assertEquals("../../bar/some", util.normalizePath("../..\\foo/../bar\\.\\some", '/'));
  }

  /**
   * Tests {@link FileUtil#getBasename(String)}.
   */
  @Test
  public void testBasename() {

    FileUtilLimited util = getFileUtil();
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

  /**
   * Tests {@link FileUtil#getDirname(String)}.
   */
  @Test
  public void testDirname() {

    FileUtilLimited util = getFileUtil();
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
    assertEquals("./foo", util.getDirname("./foo/bar"));
    assertEquals("foo/bar", util.getDirname("foo/bar/test"));
    assertEquals("foo\\bar/test", util.getDirname("foo\\bar/test/xxx"));
    assertEquals("foo\\bar/test", util.getDirname("foo\\bar/test/xxx\\"));
    assertEquals("C:\\", util.getDirname("C:\\foo"));
    assertEquals("C:", util.getDirname("C:"));
    assertEquals("http://", util.getDirname("http://"));
    assertEquals("http://", util.getDirname("http://foo"));
  }

}
