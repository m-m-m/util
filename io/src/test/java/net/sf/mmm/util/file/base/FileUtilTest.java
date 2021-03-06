/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.base;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.junit.Test;

import net.sf.mmm.test.TestResourceHelper;
import net.sf.mmm.util.file.api.FileType;
import net.sf.mmm.util.file.api.FileUtil;

/**
 * This is the test-case for {@link FileUtilImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FileUtilTest extends FileUtilLimitedTest {

  @Override
  protected FileUtil getFileUtil() {

    return FileUtilImpl.getInstance();
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
    FileFilter filter = new FileFilter() {

      @Override
      public boolean accept(File pathname) {

        return pathname.getName().endsWith(".properties");
      }
    };
    int deleteCount = util.deleteRecursive(subdir, filter);
    assertThat(subdir.exists()).isFalse();
    assertThat(deleteCount).isEqualTo(3);

    deleteCount = util.deleteRecursive(copyDir);
    assertThat(copyDir.exists()).isFalse();
    assertThat(deleteCount).isEqualTo(3);
  }

  /** Test of {@link FileUtil#touch(File)}. */
  @Test
  public void testTouch() throws Exception {

    FileUtil util = getFileUtil();
    File tmpFile = File.createTempFile("test-", "-touch");
    assertThat(tmpFile).isFile();
    assertThat(util.ensureFileExists(tmpFile)).isFalse();
    long before = System.currentTimeMillis();
    assertThat(util.touch(tmpFile)).isFalse();
    long after = System.currentTimeMillis();
    long delta = 5000; // precision of filesystem is quite low...
    assertThat(tmpFile.lastModified()).isBetween(before - delta, after + delta);
    assertThat(tmpFile.delete()).isTrue();
    assertThat(tmpFile).doesNotExist();
    assertThat(util.touch(tmpFile)).isTrue();
    assertThat(tmpFile).isFile();
    tmpFile.delete();
    File tmpFile2 = new File(tmpFile, "sub1/sub2/test-file");
    assertThat(tmpFile2).doesNotExist();
    assertThat(tmpFile).doesNotExist();
    assertThat(util.touch(tmpFile2)).isTrue();
    assertThat(tmpFile2).isFile();
    util.deleteRecursive(tmpFile);
  }

  /** Tests {@link FileUtil#collectMatchingFiles(File, String, FileType, java.util.List)}. */
  @Test
  public void testCollectMatchingFiles() {

    // given
    FileUtil util = getFileUtil();
    List<File> list = new ArrayList<File>();
    String testPath = TestResourceHelper.getTestPath() + "../../main/java";
    // when
    boolean hasPattern = util.collectMatchingFiles(new File(testPath), "net/sf/mmm/ut?l/**/impl/*.java", FileType.FILE, list);
    // then
    assertThat(hasPattern).isTrue();
    assertThat(list).contains(create(testPath, net.sf.mmm.util.io.impl.BufferInputStream.class, net.sf.mmm.util.pool.impl.ByteArrayPoolImpl.class,
        net.sf.mmm.util.io.impl.DetectorStreamProviderImpl.class));
    assertThat(list).doesNotContain(create(testPath, net.sf.mmm.util.file.impl.spring.UtilFileSpringConfig.class));
  }

  private File[] create(String testPath, Class<?>... classes) {

    File[] files = new File[classes.length];
    int i = 0;
    for (Class<?> c : classes) {
      files[i++] = new File(testPath, c.getName().replace('.', '/') + ".java");
    }
    return files;
  }

}
