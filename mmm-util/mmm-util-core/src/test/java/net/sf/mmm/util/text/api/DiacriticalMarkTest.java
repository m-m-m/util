/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

import org.junit.Test;

/**
 * This is the test-case for {@link DiacriticalMark}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class DiacriticalMarkTest {

  @Test
  public void test() {

    for (DiacriticalMark mark : DiacriticalMark.values()) {
      System.out.println("--------------");
      System.out.println(mark);
      System.out.println(mark.getCombiningCharacter());
      System.out.println("--------------");
      for (Character c : mark.getComposedCharacters()) {
        System.out.println(mark.decompose(c) + "-->" + c);
      }
    }

  }
}
