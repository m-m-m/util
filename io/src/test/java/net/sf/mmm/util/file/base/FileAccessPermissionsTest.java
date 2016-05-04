/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import net.sf.mmm.util.file.api.FileAccessClass;

/**
 * This is the test-case for {@link FileAccessPermissions}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FileAccessPermissionsTest {

  @Test
  public void testChmod() {

    FileAccessPermissions mask = new FileAccessPermissions();
    mask.chmod("a+x");
    assertEquals(00111, mask.getMaskBits());
    mask.chmod("g-x");
    assertEquals(00101, mask.getMaskBits());
    mask.chmod("u+w,g+r,o-x");
    assertEquals(00340, mask.getMaskBits());
    mask.chmod("o+rxw");
    assertEquals(00347, mask.getMaskBits());
    mask.chmod("o=rw");
    assertEquals(00346, mask.getMaskBits());
    mask.chmod("o=x");
    assertEquals(00341, mask.getMaskBits());
    mask.chmod("a=");
    assertEquals(00000, mask.getMaskBits());
    mask.chmod("0124");
    assertEquals(00124, mask.getMaskBits());
    mask.chmod("=u");
    assertEquals(00111, mask.getMaskBits());
    mask.chmod("=");
    assertEquals(00000, mask.getMaskBits());
    mask.chmod("g=rw,uo=x,+s");
    assertEquals(04161, mask.getMaskBits());
    mask.chmod("+g");
    assertEquals(04767, mask.getMaskBits());
    mask.chmod("+S,u-w");
    assertEquals(06567, mask.getMaskBits());
    mask.chmod("+t,-s,o-u,+o");
    assertEquals(03762, mask.getMaskBits());
  }

  @Test
  public void testUmask() {

    int umask = 0174;
    assertEquals(0602, FileAccessPermissions.createByUmask(umask, false).getMaskBits());
    assertEquals(0603, FileAccessPermissions.createByUmask(umask, true).getMaskBits());
  }

  @Test
  public void testBitWrites() {

    FileAccessPermissions mask = new FileAccessPermissions();
    mask.setSetuid(true);
    mask.setSticky(true);
    mask.setReadable(FileAccessClass.USER, true);
    mask.setWritable(FileAccessClass.GROUP, true);
    mask.setExecutable(FileAccessClass.OTHERS, true);
    assertEquals(05421, mask.getMaskBits());
    mask.setSticky(false);
    mask.setSetgid(true);
    mask.setExecutable(FileAccessClass.USER, true);
    mask.setExecutable(FileAccessClass.OTHERS, false);
    mask.setExecutable(FileAccessClass.GROUP, false);
    mask.setReadable(FileAccessClass.GROUP, true);
    assertEquals(06560, mask.getMaskBits());
  }

  @Test
  public void testBitReads() {

    // check - --x -w- r--
    FileAccessPermissions mask0124 = new FileAccessPermissions(0124);
    assertFalse(mask0124.isSticky());
    assertFalse(mask0124.isSetgid());
    assertFalse(mask0124.isSetuid());
    assertTrue(mask0124.isExecutable(FileAccessClass.USER));
    assertFalse(mask0124.isExecutable(FileAccessClass.GROUP));
    assertFalse(mask0124.isExecutable(FileAccessClass.OTHERS));
    assertFalse(mask0124.isWritable(FileAccessClass.USER));
    assertTrue(mask0124.isWritable(FileAccessClass.GROUP));
    assertFalse(mask0124.isWritable(FileAccessClass.OTHERS));
    assertFalse(mask0124.isReadable(FileAccessClass.USER));
    assertFalse(mask0124.isReadable(FileAccessClass.GROUP));
    assertTrue(mask0124.isReadable(FileAccessClass.OTHERS));
    // check (sticky) - r-- --x -w-
    FileAccessPermissions mask1412 = new FileAccessPermissions(01412);
    assertTrue(mask1412.isSticky());
    assertFalse(mask1412.isSetgid());
    assertFalse(mask1412.isSetuid());
    assertFalse(mask1412.isExecutable(FileAccessClass.USER));
    assertTrue(mask1412.isExecutable(FileAccessClass.GROUP));
    assertFalse(mask1412.isExecutable(FileAccessClass.OTHERS));
    assertFalse(mask1412.isWritable(FileAccessClass.USER));
    assertFalse(mask1412.isWritable(FileAccessClass.GROUP));
    assertTrue(mask1412.isWritable(FileAccessClass.OTHERS));
    assertTrue(mask1412.isReadable(FileAccessClass.USER));
    assertFalse(mask1412.isReadable(FileAccessClass.GROUP));
    assertFalse(mask1412.isReadable(FileAccessClass.OTHERS));
    // check (sticky) - rws rwS rwx
    FileAccessPermissions mask7777 = new FileAccessPermissions(07777);
    assertTrue(mask7777.isSticky());
    assertTrue(mask7777.isSetgid());
    assertTrue(mask7777.isSetuid());
    assertTrue(mask7777.isExecutable(FileAccessClass.USER));
    assertTrue(mask7777.isExecutable(FileAccessClass.GROUP));
    assertTrue(mask7777.isExecutable(FileAccessClass.OTHERS));
    assertTrue(mask7777.isWritable(FileAccessClass.USER));
    assertTrue(mask7777.isWritable(FileAccessClass.GROUP));
    assertTrue(mask7777.isWritable(FileAccessClass.OTHERS));
    assertTrue(mask7777.isReadable(FileAccessClass.USER));
    assertTrue(mask7777.isReadable(FileAccessClass.GROUP));
    assertTrue(mask7777.isReadable(FileAccessClass.OTHERS));
  }

  @Test
  public void testToString() {

    assertEquals("0001", new FileAccessPermissions(01).toString());
    assertEquals("0123", new FileAccessPermissions(0123).toString());
    assertEquals("7777", new FileAccessPermissions(07777).toString());
  }

  @Test
  public void testOutOfRange() {

    try {
      new FileAccessPermissions(-1);
      fail();
    } catch (IllegalArgumentException e) {
      // okay
    }
    try {
      new FileAccessPermissions(07777 + 1);
      fail();
    } catch (IllegalArgumentException e) {
      // okay
    }
  }

}
