/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.model.base;

import junit.framework.TestCase;
import net.sf.mmm.data.api.reflection.DataFieldModifiers;
import net.sf.mmm.data.base.reflection.DataFieldModifiersBean;
import net.sf.mmm.data.base.reflection.DataModifiersIllegalException;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the {@link TestCase} for {@link DataFieldModifiersBean}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class ContentFieldModifiersBeanTest {

  private void checkModifiers(DataFieldModifiers modifiers) {

    DataFieldModifiers instance = DataFieldModifiersBean.getInstance(modifiers.isSystem(),
        modifiers.isFinal(), modifiers.isReadOnly(), modifiers.isStatic(), modifiers.isTransient(),
        modifiers.isInheritedFromParent());
    Assert.assertSame(modifiers, instance);
  }

  @Test
  public void testFieldModifiersConstants() {

    checkModifiers(DataFieldModifiersBean.FINAL);
    checkModifiers(DataFieldModifiersBean.FINAL_READONLY);
    checkModifiers(DataFieldModifiersBean.FINAL_TRANSIENT);
    checkModifiers(DataFieldModifiersBean.NORMAL);
    checkModifiers(DataFieldModifiersBean.READ_ONLY);
    checkModifiers(DataFieldModifiersBean.STATIC);
    checkModifiers(DataFieldModifiersBean.STATIC_FINAL);
    checkModifiers(DataFieldModifiersBean.STATIC_FINAL_READONLY);
    checkModifiers(DataFieldModifiersBean.STATIC_READONLY);
    checkModifiers(DataFieldModifiersBean.SYSTEM);
    checkModifiers(DataFieldModifiersBean.SYSTEM_FINAL);
    checkModifiers(DataFieldModifiersBean.SYSTEM_FINAL_READONLY);
    checkModifiers(DataFieldModifiersBean.SYSTEM_FINAL_TRANSIENT);
    checkModifiers(DataFieldModifiersBean.SYSTEM_READONLY);
    checkModifiers(DataFieldModifiersBean.SYSTEM_STATIC);
    checkModifiers(DataFieldModifiersBean.SYSTEM_STATIC_FINAL);
    checkModifiers(DataFieldModifiersBean.SYSTEM_STATIC_FINAL_READONLY);
    checkModifiers(DataFieldModifiersBean.SYSTEM_STATIC_READONLY);
    checkModifiers(DataFieldModifiersBean.SYSTEM_TRANSIENT);
    checkModifiers(DataFieldModifiersBean.TRANSIENT);
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
  public void testFieldModifiersNewInstance() {

    int illegalModifiers = 0;
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
              for (int inherit = 0; inherit < 2; inherit++) {
                boolean isInherited = toBoolean(inherit);
                try {
                  DataFieldModifiers modifiers = DataFieldModifiersBean.getInstance(isSystem,
                      isFinal, isReadOnly, isStatic, isTransient, isInherited);
                  Assert.assertEquals(isSystem, modifiers.isSystem());
                  Assert.assertEquals(isFinal, modifiers.isFinal());
                  Assert.assertEquals(isReadOnly, modifiers.isReadOnly());
                  Assert.assertEquals(isStatic, modifiers.isStatic());
                  Assert.assertEquals(isTransient, modifiers.isTransient());
                  Assert.assertEquals(isInherited, modifiers.isInheritedFromParent());
                } catch (DataModifiersIllegalException e) {
                  // ignore
                  illegalModifiers++;
                }
              }
            }
          }
        }
      }
    }
    Assert.assertEquals(12, illegalModifiers);
  }
}
