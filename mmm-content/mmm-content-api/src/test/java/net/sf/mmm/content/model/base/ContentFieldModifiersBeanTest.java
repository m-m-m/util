/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import junit.framework.TestCase;
import net.sf.mmm.content.reflection.api.ContentFieldModifiers;
import net.sf.mmm.content.reflection.base.ContentFieldModifiersBean;
import net.sf.mmm.content.reflection.base.ContentModifiersIllegalException;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the {@link TestCase} for {@link ContentFieldModifiersBean}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class ContentFieldModifiersBeanTest {

  private void checkModifiers(ContentFieldModifiers modifiers) {

    ContentFieldModifiers instance = ContentFieldModifiersBean.getInstance(modifiers.isSystem(),
        modifiers.isFinal(), modifiers.isReadOnly(), modifiers.isStatic(), modifiers.isTransient(),
        modifiers.isInheritedFromParent());
    Assert.assertSame(modifiers, instance);
  }

  @Test
  public void testFieldModifiersConstants() {

    checkModifiers(ContentFieldModifiersBean.FINAL);
    checkModifiers(ContentFieldModifiersBean.FINAL_READONLY);
    checkModifiers(ContentFieldModifiersBean.FINAL_TRANSIENT);
    checkModifiers(ContentFieldModifiersBean.NORMAL);
    checkModifiers(ContentFieldModifiersBean.READ_ONLY);
    checkModifiers(ContentFieldModifiersBean.STATIC);
    checkModifiers(ContentFieldModifiersBean.STATIC_FINAL);
    checkModifiers(ContentFieldModifiersBean.STATIC_FINAL_READONLY);
    checkModifiers(ContentFieldModifiersBean.STATIC_READONLY);
    checkModifiers(ContentFieldModifiersBean.SYSTEM);
    checkModifiers(ContentFieldModifiersBean.SYSTEM_FINAL);
    checkModifiers(ContentFieldModifiersBean.SYSTEM_FINAL_READONLY);
    checkModifiers(ContentFieldModifiersBean.SYSTEM_FINAL_TRANSIENT);
    checkModifiers(ContentFieldModifiersBean.SYSTEM_READONLY);
    checkModifiers(ContentFieldModifiersBean.SYSTEM_STATIC);
    checkModifiers(ContentFieldModifiersBean.SYSTEM_STATIC_FINAL);
    checkModifiers(ContentFieldModifiersBean.SYSTEM_STATIC_FINAL_READONLY);
    checkModifiers(ContentFieldModifiersBean.SYSTEM_STATIC_READONLY);
    checkModifiers(ContentFieldModifiersBean.SYSTEM_TRANSIENT);
    checkModifiers(ContentFieldModifiersBean.TRANSIENT);
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
                  ContentFieldModifiers modifiers = ContentFieldModifiersBean.getInstance(isSystem,
                      isFinal, isReadOnly, isStatic, isTransient, isInherited);
                  Assert.assertEquals(isSystem, modifiers.isSystem());
                  Assert.assertEquals(isFinal, modifiers.isFinal());
                  Assert.assertEquals(isReadOnly, modifiers.isReadOnly());
                  Assert.assertEquals(isStatic, modifiers.isStatic());
                  Assert.assertEquals(isTransient, modifiers.isTransient());
                  Assert.assertEquals(isInherited, modifiers.isInheritedFromParent());
                } catch (ContentModifiersIllegalException e) {
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
