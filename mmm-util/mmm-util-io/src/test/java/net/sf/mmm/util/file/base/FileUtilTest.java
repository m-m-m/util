/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import net.sf.mmm.test.TestResourceHelper;
import net.sf.mmm.util.file.api.FileType;
import net.sf.mmm.util.file.api.FileUtil;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * This is the test-case for {@link FileUtilImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FileUtilTest extends Assertions {

  protected FileUtil getFileUtil() {

    return FileUtilImpl.getInstance();
  }

  /**
   * Tests {@link FileUtil#normalizePath(String)}.
   */
  @Test
  public void testNormalizePath() {

    FileUtil util = getFileUtil();
    assertThat(util.normalizePath("/\\///.//", '/')).isEqualTo("/");
    assertThat(util.normalizePath("/foo/../bar/..", '/')).isEqualTo("/");
    assertThat(util.normalizePath("/foo/bar/../bar/..", '/')).isEqualTo("/foo");
    assertThat(util.normalizePath("/foo\\bar/..\\bar/..", '/')).isEqualTo("/foo");
    assertThat(util.normalizePath("/foo\\//.\\./bar/..\\bar/..", '/')).isEqualTo("/foo");
    assertThat(util.normalizePath("foo\\//.\\./bar/.", '/')).isEqualTo("foo/bar");
    String homeDir = System.getProperty("user.home").replace('\\', '/');
    assertThat(util.normalizePath("~", '/')).isEqualTo(homeDir);
    assertThat(util.normalizePath("~/foo/./..", '/')).isEqualTo(homeDir);
    assertThat(util.normalizePath("~/foo/./..", '/')).isEqualTo(homeDir);
    assertThat(util.normalizePath("~/.mmm/search.xml", '/')).isEqualTo(homeDir + "/.mmm/search.xml");
    assertThat(util.normalizePath("~root/.ssh/authorized_keys", '/')).isEqualTo("/root/.ssh/authorized_keys");
    if ("/root".equals(homeDir)) {
      homeDir = "/home/nobody";
    }
    assertThat(util.normalizePath(homeDir + "/../someuser", '/')).isEqualTo(util.normalizePath("~someuser", '/'));
    String uncPath = "\\\\10.0.0.1\\share";
    assertThat(util.normalizePath(uncPath, '\\')).isEqualTo(uncPath);
    assertThat(util.normalizePath("http://www.host.com/foo/bar/./test/.././..")).isEqualTo("http://www.host.com/foo");
    assertThat(util.normalizePath("../..\\foo/../bar\\.\\some", '/')).isEqualTo("../../bar/some");
  }

  /**
   * Tests special cases of {@link FileUtil#normalizePath(String)}.
   */
  @Test
  public void testNormalizePathSpeical() {

    FileUtilImpl impl = new FileUtilImpl();
    impl.setUserLogin("root");
    String rootHome = "/my/root/dir";
    impl.setUserHomeDirectoryPath(rootHome);
    impl.initialize();
    FileUtil util = impl;
    assertThat(util.normalizePath("~root", '/')).isEqualTo(rootHome);
  }

  protected void checkTestdata(File originalFile, File copyFile) throws IOException {

    assertThat(originalFile.length()).isEqualTo(copyFile.length());
    Properties copyProperties = new Properties();
    FileInputStream in = new FileInputStream(copyFile);
    copyProperties.load(in);
    in.close();
    assertThat(copyProperties.get("Message1")).isEqualTo("This is only a test");
    assertThat(copyProperties.get("Message2")).isEqualTo("The second test");
  }

  /**
   * Tests {@link FileUtil#copyFile(File, File)}.
   */
  @Test
  public void testCopyFile() throws IOException {

    FileUtil util = getFileUtil();
    File originalFile = new File(TestResourceHelper.getTestPath("net/sf/mmm/util/file/testdata.properties"));
    File copyFile = File.createTempFile("testdata", ".properties");
    util.copyFile(originalFile, copyFile);
    checkTestdata(originalFile, copyFile);
    copyFile.delete();
  }

  /** Tests {@link FileUtil#copyFile(File, File)}. */
  @Test
  public void testDirectoryTree() throws IOException {

    FileUtil util = getFileUtil();
    File tempDir = util.getTemporaryDirectory();
    String uidName = "mmm-" + UUID.randomUUID();
    File subdir = new File(tempDir, uidName);
    assertThat(subdir.mkdir()).isTrue();
    assertThat(subdir.isDirectory()).isTrue();
    File originalFile = new File(TestResourceHelper.getTestPath("net/sf/mmm/util/file/testdata.properties"));
    File copyFile = new File(subdir, originalFile.getName());
    util.copyFile(originalFile, copyFile);
    checkTestdata(originalFile, copyFile);
    File testFile = new File(subdir, "test.properties");
    assertThat(testFile.createNewFile()).isTrue();
    File subsubdir = new File(subdir, "folder");
    assertThat(subsubdir.mkdir()).isTrue();
    File fooFile = new File(subsubdir, "foo.properties");
    assertThat(fooFile.createNewFile()).isTrue();
    // test matching files
    File[] matchingFiles = util.getMatchingFiles(subdir, "*/*.properties", FileType.FILE);
    assertThat(1).isEqualTo(matchingFiles.length);
    assertThat(matchingFiles[0]).isEqualTo(fooFile);
    matchingFiles = util.getMatchingFiles(subdir, "**/*.properties", FileType.FILE);
    assertThat(matchingFiles.length).isEqualTo(3);
    int magic = 0;
    for (File file : matchingFiles) {
      if (file.equals(fooFile)) {
        magic += 1;
      } else if (file.equals(testFile)) {
        magic += 2;
      } else if (file.equals(copyFile)) {
        magic += 4;
      }
    }
    assertThat(7).isEqualTo(magic);
    matchingFiles = util.getMatchingFiles(subdir, "**/fo*", null);
    magic = 0;
    for (File file : matchingFiles) {
      if (file.equals(fooFile)) {
        magic += 1;
      } else if (file.equals(subsubdir)) {
        magic += 2;
      }
    }
    assertThat(3).isEqualTo(magic);
    // copy recursive
    File copyDir = new File(tempDir, uidName + "-copy");
    util.copyRecursive(subdir, copyDir, false);
    // collect matching files of copy
    matchingFiles = util.getMatchingFiles(copyDir, "*/*.properties", FileType.FILE);
    assertThat(matchingFiles.length).isEqualTo(1);
    assertThat(matchingFiles[0].getName()).isEqualTo(fooFile.getName());

    matchingFiles = util.getMatchingFiles(copyDir, "**/*.properties", FileType.FILE);
    assertThat(matchingFiles.length).isEqualTo(3);
    assertThat(new String[] { matchingFiles[0].getName(), matchingFiles[1].getName(), matchingFiles[2].getName() })
        .containsOnly(new String[] { fooFile.getName(), testFile.getName(), copyFile.getName() });
    // delete recursive
    util.deleteRecursive(subdir);
    util.deleteRecursive(copyDir);
    assertThat(subdir.exists()).isFalse();
    assertThat(copyDir.exists()).isFalse();
  }
}
