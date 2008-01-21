/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import org.junit.Test;

import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.FieldModifiers;

import static org.junit.Assert.*;

/**
 * This is the {@link TestCase} for {@link ClassModifiersImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ClassModifiersImplTest {

  private void checkModifiers(ClassModifiers modifiers) {

    boolean extendable = modifiers.isExtendable();
    ClassModifiers instance = ClassModifiersImpl.getInstance(modifiers.isSystem(), modifiers
        .isFinal(), modifiers.isAbstract(), extendable);
    assertSame(modifiers, instance);
  }

  @Test
  public void testClassModifiers() {

    checkModifiers(ClassModifiersImpl.ABSTRACT);
    checkModifiers(ClassModifiersImpl.FINAL);
    checkModifiers(ClassModifiersImpl.NORMAL);
    checkModifiers(ClassModifiersImpl.SYSTEM);
    checkModifiers(ClassModifiersImpl.SYSTEM_ABSTRACT);
    checkModifiers(ClassModifiersImpl.SYSTEM_ABSTRACT_UNEXTENDABLE);
    checkModifiers(ClassModifiersImpl.SYSTEM_FINAL);
    checkModifiers(ClassModifiersImpl.SYSTEM_UNEXTENDABLE);
  }

  private boolean toBoolean(int flag) {

    if (flag == 0) {
      return false;
    } else if (flag == 1) {
      return true;
    } else {
      throw new IllegalStateException("Error in test-case!");
    }
  }

  @Test
  public void testClassModifiers2() {

    for (int sys = 0; sys < 2; sys++) {
      boolean isSystem = toBoolean(sys);
      for (int fin = 0; fin < 2; fin++) {
        boolean isFinal = toBoolean(fin);
        for (int abstr = 0; abstr < 2; abstr++) {
          boolean isAbstract = toBoolean(abstr);
          for (int ext = 0; ext < 2; ext++) {
            boolean isExtendable = toBoolean(ext);
            try {
              ClassModifiers modifiers = ClassModifiersImpl.getInstance(isSystem, isFinal,
                  isAbstract, isExtendable);
              assertEquals(isSystem, modifiers.isSystem());
              assertEquals(isFinal, modifiers.isFinal());
              assertEquals(isAbstract, modifiers.isAbstract());
              assertEquals(isExtendable, modifiers.isExtendable());
            } catch (IllegalModifiersException e) {
              // ignore
            }
          }
        }
      }
    }

  }

}
