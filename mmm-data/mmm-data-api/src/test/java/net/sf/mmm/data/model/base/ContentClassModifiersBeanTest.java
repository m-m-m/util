/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.model.base;

import junit.framework.TestCase;
import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.base.reflection.ContentClassModifiersBean;
import net.sf.mmm.data.base.reflection.ContentModifiersIllegalException;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the {@link TestCase} for {@link ContentClassModifiersBean}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class ContentClassModifiersBeanTest {

  private void checkModifiers(DataClassModifiers modifiers) {

    boolean extendable = modifiers.isExtendable();
    DataClassModifiers instance = ContentClassModifiersBean.getInstance(modifiers.isSystem(),
        modifiers.isFinal(), modifiers.isAbstract(), extendable);
    Assert.assertSame(modifiers, instance);
  }

  @Test
  public void testClassModifiersConstants() {

    checkModifiers(ContentClassModifiersBean.ABSTRACT);
    checkModifiers(ContentClassModifiersBean.FINAL);
    checkModifiers(ContentClassModifiersBean.NORMAL);
    checkModifiers(ContentClassModifiersBean.SYSTEM);
    checkModifiers(ContentClassModifiersBean.SYSTEM_ABSTRACT);
    checkModifiers(ContentClassModifiersBean.SYSTEM_ABSTRACT_UNEXTENDABLE);
    checkModifiers(ContentClassModifiersBean.SYSTEM_FINAL);
    checkModifiers(ContentClassModifiersBean.SYSTEM_UNEXTENDABLE);
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
  public void testClassModifiersNewInstance() {

    int illegalModifiers = 0;
    for (int sys = 0; sys < 2; sys++) {
      boolean isSystem = toBoolean(sys);
      for (int fin = 0; fin < 2; fin++) {
        boolean isFinal = toBoolean(fin);
        for (int abstr = 0; abstr < 2; abstr++) {
          boolean isAbstract = toBoolean(abstr);
          for (int ext = 0; ext < 2; ext++) {
            boolean isExtendable = toBoolean(ext);
            try {
              DataClassModifiers modifiers = ContentClassModifiersBean.getInstance(isSystem,
                  isFinal, isAbstract, isExtendable);
              Assert.assertEquals(isSystem, modifiers.isSystem());
              Assert.assertEquals(isFinal, modifiers.isFinal());
              Assert.assertEquals(isAbstract, modifiers.isAbstract());
              Assert.assertEquals(isExtendable, modifiers.isExtendable());
            } catch (ContentModifiersIllegalException e) {
              // ignore
              illegalModifiers++;
            }
          }
        }
      }
    }
    Assert.assertEquals(8, illegalModifiers);
  }

}
