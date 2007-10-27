/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file;

import org.junit.Test;

import net.sf.mmm.util.file.FileModeClass;
import net.sf.mmm.util.file.FileModeMask;

import junit.framework.TestCase;

import static org.junit.Assert.*;

/**
 * This is the test-case for {@link FileModeMask}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FileModeMaskTest {
  
  @Test
  public void testChmod() {
    
    FileModeMask mask = new FileModeMask();
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
    assertEquals(0602, FileModeMask.createByUmask(umask, false).getMaskBits());
    assertEquals(0603, FileModeMask.createByUmask(umask, true).getMaskBits());
  }
  
  @Test
  public void testBitWrites() {
    
    FileModeMask mask = new FileModeMask();
    mask.setSetuid(true);
    mask.setSticky(true);
    mask.setReadable(FileModeClass.USER, true);
    mask.setWritable(FileModeClass.GROUP, true);
    mask.setExecutable(FileModeClass.OTHERS, true);
    assertEquals(05421, mask.getMaskBits());
    mask.setSticky(false);
    mask.setSetgid(true);
    mask.setExecutable(FileModeClass.USER, true);
    mask.setExecutable(FileModeClass.OTHERS, false);
    mask.setExecutable(FileModeClass.GROUP, false);
    mask.setReadable(FileModeClass.GROUP, true);
    assertEquals(06560, mask.getMaskBits());    
  }
  
  @Test
  public void testBitReads() {
    // check - --x -w- r--
    FileModeMask mask0124 = new FileModeMask(0124);
    assertFalse(mask0124.isSticky());
    assertFalse(mask0124.isSetgid());
    assertFalse(mask0124.isSetuid());
    assertTrue(mask0124.isExecutable(FileModeClass.USER));
    assertFalse(mask0124.isExecutable(FileModeClass.GROUP));
    assertFalse(mask0124.isExecutable(FileModeClass.OTHERS));
    assertFalse(mask0124.isWritable(FileModeClass.USER));
    assertTrue(mask0124.isWritable(FileModeClass.GROUP));
    assertFalse(mask0124.isWritable(FileModeClass.OTHERS));
    assertFalse(mask0124.isReadable(FileModeClass.USER));
    assertFalse(mask0124.isReadable(FileModeClass.GROUP));
    assertTrue(mask0124.isReadable(FileModeClass.OTHERS));
    // check (sticky) - r-- --x -w-
    FileModeMask mask1412 = new FileModeMask(01412);
    assertTrue(mask1412.isSticky());
    assertFalse(mask1412.isSetgid());
    assertFalse(mask1412.isSetuid());
    assertFalse(mask1412.isExecutable(FileModeClass.USER));
    assertTrue(mask1412.isExecutable(FileModeClass.GROUP));
    assertFalse(mask1412.isExecutable(FileModeClass.OTHERS));
    assertFalse(mask1412.isWritable(FileModeClass.USER));
    assertFalse(mask1412.isWritable(FileModeClass.GROUP));
    assertTrue(mask1412.isWritable(FileModeClass.OTHERS));
    assertTrue(mask1412.isReadable(FileModeClass.USER));
    assertFalse(mask1412.isReadable(FileModeClass.GROUP));
    assertFalse(mask1412.isReadable(FileModeClass.OTHERS));
    // check (sticky) - rws rwS rwx
    FileModeMask mask7777 = new FileModeMask(07777);
    assertTrue(mask7777.isSticky());
    assertTrue(mask7777.isSetgid());
    assertTrue(mask7777.isSetuid());
    assertTrue(mask7777.isExecutable(FileModeClass.USER));
    assertTrue(mask7777.isExecutable(FileModeClass.GROUP));
    assertTrue(mask7777.isExecutable(FileModeClass.OTHERS));
    assertTrue(mask7777.isWritable(FileModeClass.USER));
    assertTrue(mask7777.isWritable(FileModeClass.GROUP));
    assertTrue(mask7777.isWritable(FileModeClass.OTHERS));
    assertTrue(mask7777.isReadable(FileModeClass.USER));
    assertTrue(mask7777.isReadable(FileModeClass.GROUP));
    assertTrue(mask7777.isReadable(FileModeClass.OTHERS));
  }
  
  @Test
  public void testToString() {
    
    assertEquals("0001", new FileModeMask(01).toString());
    assertEquals("0123", new FileModeMask(0123).toString());
    assertEquals("7777", new FileModeMask(07777).toString());
  }
  
  @Test
  public void testOutOfRange() {
    
    try {
      new FileModeMask(-1);
      fail();
    } catch (IllegalArgumentException e) {
      // okay
    }
    try {
      new FileModeMask(07777 + 1);
      fail();
    } catch (IllegalArgumentException e) {
      // okay
    }
  }
  
}
