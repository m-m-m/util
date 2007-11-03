/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * This is the test-case for {@link FileAttributes}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FileAttributesTest {

  @Test
  public void testClone() {

    FileAttributes attributes = new FileAttributes();
    String user = "user";
    attributes.setUser(user);
    attributes.setGroup("group");
    int maskBits = 01234;
    FileAccessPermissions mask = new FileAccessPermissions(maskBits);
    attributes.setPermissions(mask);
    FileAttributes clonedAttributes = attributes.clone();
    assertNotSame(attributes, clonedAttributes);
    assertEquals(attributes, clonedAttributes);
    clonedAttributes.setUser("newUser");
    assertSame(user, attributes.getUser());
    assertFalse(attributes.equals(clonedAttributes));
    clonedAttributes.getPermissions().setMaskBits(04321);
    assertEquals(maskBits, attributes.getPermissions().getMaskBits());
  }

}
