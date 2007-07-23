/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import org.junit.Test;

import net.sf.mmm.content.model.api.FieldModifiers;

import junit.framework.TestCase;

/**
 * This is the {@link TestCase} for {@link FieldModifiersImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FieldModifiersImplTest extends TestCase {

  private void checkModifiers(FieldModifiers modifiers) {

    FieldModifiers instance = FieldModifiersImpl.getInstance(modifiers.isSystem(), modifiers
        .isFinal(), modifiers.isReadOnly(), modifiers.isStatic(), modifiers.isTransient());
    assertSame(modifiers, instance);
  }

  @Test
  public void testFieldModifiers() {

    checkModifiers(FieldModifiersImpl.FINAL);
    checkModifiers(FieldModifiersImpl.FINAL_READONLY);
    checkModifiers(FieldModifiersImpl.FINAL_TRANSIENT);
    checkModifiers(FieldModifiersImpl.NORMAL);
    checkModifiers(FieldModifiersImpl.READ_ONLY);
    checkModifiers(FieldModifiersImpl.STATIC);
    checkModifiers(FieldModifiersImpl.STATIC_FINAL);
    checkModifiers(FieldModifiersImpl.STATIC_FINAL_READONLY);
    checkModifiers(FieldModifiersImpl.STATIC_READONLY);
    checkModifiers(FieldModifiersImpl.SYSTEM);
    checkModifiers(FieldModifiersImpl.SYSTEM_FINAL);
    checkModifiers(FieldModifiersImpl.SYSTEM_FINAL_READONLY);
    checkModifiers(FieldModifiersImpl.SYSTEM_FINAL_TRANSIENT);
    checkModifiers(FieldModifiersImpl.SYSTEM_READONLY);
    checkModifiers(FieldModifiersImpl.SYSTEM_STATIC);
    checkModifiers(FieldModifiersImpl.SYSTEM_STATIC_FINAL);
    checkModifiers(FieldModifiersImpl.SYSTEM_STATIC_FINAL_READONLY);
    checkModifiers(FieldModifiersImpl.SYSTEM_STATIC_READONLY);
    checkModifiers(FieldModifiersImpl.SYSTEM_TRANSIENT);
    checkModifiers(FieldModifiersImpl.TRANSIENT);
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

  public void testFieldModifiers2() {

    for (int sys = 0; sys < 2; sys++) {
      boolean isSystem = toBoolean(sys);
      for (int fin = 0; fin < 2; fin++) {
        boolean isFinal = toBoolean(fin);
        for (int ro = 0; ro < 2; ro++) {
          boolean isReadOnly = toBoolean(ro);
          for (int stat = 0; stat < 2; stat++) {
            boolean isStatic = toBoolean(stat);
            for (int trans = 0; trans < 2; trans++) {
              boolean isTransient = toBoolean(trans);
              try {
                FieldModifiers modifiers = FieldModifiersImpl.getInstance(isSystem, isFinal,
                    isReadOnly, isStatic, isTransient);
                assertEquals(isSystem, modifiers.isSystem());
                assertEquals(isFinal, modifiers.isFinal());
                assertEquals(isReadOnly, modifiers.isReadOnly());
                assertEquals(isStatic, modifiers.isStatic());
                assertEquals(isTransient, modifiers.isTransient());
              } catch (IllegalModifiersException e) {
                // ignore
              }
            }
          }
        }
      }
    }
  }
}
