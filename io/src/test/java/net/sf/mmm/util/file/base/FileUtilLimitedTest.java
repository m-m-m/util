/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.base;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import ch.qos.logback.core.util.FileUtil;
import net.sf.mmm.util.file.api.FileUtilLimited;

/**
 * This is the test-case for {@link FileUtilLimitedImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FileUtilLimitedTest extends Assertions {

  protected FileUtilLimited getFileUtil() {

    return FileUtilLimitedImpl.getInstance();
  }

  /**
   * Tests {@link FileUtil#getExtension(String)}.
   */
  @Test
  public void testExtension() {

    FileUtilLimited util = getFileUtil();
    assertThat(util.getExtension("test.java")).isEqualTo("java");
    assertThat(util.getExtension(".java")).isEqualTo("");
    assertThat(util.getExtension("archive.tar.gz")).isEqualTo("gz");
  }

  /**
   * Tests {@link FileUtil#normalizePath(String)}.
   */
  @Test
  public void testNormalizePath() {

    FileUtilLimited util = getFileUtil();
    assertThat(util.normalizePath("/\\///.//", '/')).isEqualTo("/");
    assertThat(util.normalizePath("/foo/../bar/..", '/')).isEqualTo("/");
    assertThat(util.normalizePath("/foo/bar/../bar/..", '/')).isEqualTo("/foo");
    assertThat(util.normalizePath("/foo\\bar/..\\bar/..", '/')).isEqualTo("/foo");
    assertThat(util.normalizePath("/foo\\//.\\./bar/..\\bar/..", '/')).isEqualTo("/foo");
    assertThat(util.normalizePath("foo\\//.\\./bar/.", '/')).isEqualTo("foo/bar");
    String homeDir = "~";
    assertThat(util.normalizePath("~", '/')).isEqualTo(homeDir);
    assertThat(util.normalizePath("~/", '/')).isEqualTo(homeDir);
    assertThat(util.normalizePath("~/foo/./..", '/')).isEqualTo(homeDir);
    assertThat(util.normalizePath("~/foo/./..", '/')).isEqualTo(homeDir);
    assertThat(util.normalizePath("~/.mmm/search.xml", '/')).isEqualTo(homeDir + "/.mmm/search.xml");
    assertThat(util.normalizePath("~root/.ssh/authorized_keys", '/')).isEqualTo("/root/.ssh/authorized_keys");
    assertThat(util.normalizePath("~someuser", '/')).isEqualTo("~/../someuser");
    String uncPath = "\\\\10.0.0.1/share";
    assertThat(util.normalizePath(uncPath, '/')).isEqualTo(uncPath);
    assertThat(util.normalizePath("http://www.host.com/foo/bar/./test/.././..")).isEqualTo("http://www.host.com/foo");
    assertThat(util.normalizePath("../..\\foo/../bar\\.\\some", '/')).isEqualTo("../../bar/some");
  }

  /**
   * Tests {@link FileUtil#getBasename(String)}.
   */
  @Test
  public void testBasename() {

    FileUtilLimited util = getFileUtil();
    assertThat(util.getBasename("")).isEqualTo("");
    assertThat(util.getBasename("a")).isEqualTo("a");
    assertThat(util.getBasename("/")).isEqualTo("/");
    assertThat(util.getBasename("///")).isEqualTo("/");
    assertThat(util.getBasename("\\")).isEqualTo("\\");
    assertThat(util.getBasename("\\\\")).isEqualTo("\\");
    assertThat(util.getBasename("\\/\\")).isEqualTo("\\");
    assertThat(util.getBasename("/\\/")).isEqualTo("/");
    assertThat(util.getBasename("/.")).isEqualTo(".");
    assertThat(util.getBasename("/..")).isEqualTo("..");
    assertThat(util.getBasename("foo")).isEqualTo("foo");
    assertThat(util.getBasename("foo/bar")).isEqualTo("bar");
    assertThat(util.getBasename("foo.bar")).isEqualTo("foo.bar");
    assertThat(util.getBasename("./foo.bar")).isEqualTo("foo.bar");
    assertThat(util.getBasename("/foo")).isEqualTo("foo");
    assertThat(util.getBasename("/foo/")).isEqualTo("foo");
    assertThat(util.getBasename("/foo/bar")).isEqualTo("bar");
    assertThat(util.getBasename("/foo/bar//")).isEqualTo("bar");
    assertThat(util.getBasename("c:\\")).isEqualTo("");
    assertThat(util.getBasename("http://")).isEqualTo("");
    assertThat(util.getBasename("c:\\foo")).isEqualTo("foo");
    assertThat(util.getBasename("http://foo")).isEqualTo("foo");
    assertThat(util.getBasename("http://foo.org/bar")).isEqualTo("bar");
  }

  /**
   * Tests {@link FileUtil#getDirname(String)}.
   */
  @Test
  public void testDirname() {

    FileUtilLimited util = getFileUtil();
    assertThat(util.getDirname("/")).isEqualTo("/");
    assertThat(util.getDirname("/foo")).isEqualTo("/");
    assertThat(util.getDirname("/foo/")).isEqualTo("/");
    assertThat(util.getDirname("\\foo\\")).isEqualTo("\\");
    assertThat(util.getDirname("foo")).isEqualTo(".");
    assertThat(util.getDirname("foo/")).isEqualTo(".");
    assertThat(util.getDirname("foo\\")).isEqualTo(".");
    assertThat(util.getDirname("/foo/bar")).isEqualTo("/foo");
    assertThat(util.getDirname("/foo/bar/")).isEqualTo("/foo");
    assertThat(util.getDirname("foo/bar")).isEqualTo("foo");
    assertThat(util.getDirname("foo/bar/")).isEqualTo("foo");
    assertThat(util.getDirname("foo\\bar\\")).isEqualTo("foo");
    assertThat(util.getDirname("./foo/bar")).isEqualTo("./foo");
    assertThat(util.getDirname("foo/bar/test")).isEqualTo("foo/bar");
    assertThat(util.getDirname("foo\\bar/test/xxx")).isEqualTo("foo\\bar/test");
    assertThat(util.getDirname("foo\\bar/test/xxx\\")).isEqualTo("foo\\bar/test");
    assertThat(util.getDirname("C:\\foo")).isEqualTo("C:\\");
    assertThat(util.getDirname("C:")).isEqualTo("C:");
    assertThat(util.getDirname("http://")).isEqualTo("http://");
    assertThat(util.getDirname("http://foo")).isEqualTo("http://");
  }

}
