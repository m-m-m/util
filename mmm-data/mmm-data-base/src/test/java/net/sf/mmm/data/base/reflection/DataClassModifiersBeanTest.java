/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import junit.framework.TestCase;
import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.api.reflection.DataModifiersIllegalException;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the {@link TestCase} for {@link DataClassModifiersBean}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class DataClassModifiersBeanTest {

  private void checkModifiers(DataClassModifiers modifiers) {

    DataClassModifiers instance = DataClassModifiersBean.getInstance(modifiers.isSystem(),
        modifiers.isFinal(), modifiers.isAbstract(), modifiers.isExtendable());
    Assert.assertSame(modifiers, instance);
  }

  @Test
  public void testClassModifiersConstants() {

    checkModifiers(DataClassModifiersBean.ABSTRACT);
    checkModifiers(DataClassModifiersBean.FINAL);
    checkModifiers(DataClassModifiersBean.NORMAL);
    checkModifiers(DataClassModifiersBean.SYSTEM);
    checkModifiers(DataClassModifiersBean.SYSTEM_ABSTRACT);
    checkModifiers(DataClassModifiersBean.SYSTEM_ABSTRACT_UNEXTENDABLE);
    checkModifiers(DataClassModifiersBean.SYSTEM_FINAL);
    checkModifiers(DataClassModifiersBean.SYSTEM_UNEXTENDABLE);
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
              DataClassModifiersBean modifiers = DataClassModifiersBean.getInstance(isSystem,
                  isFinal, isAbstract, isExtendable);
              Assert.assertEquals(isSystem, modifiers.isSystem());
              Assert.assertEquals(isFinal, modifiers.isFinal());
              Assert.assertEquals(isAbstract, modifiers.isAbstract());
              Assert.assertEquals(isExtendable, modifiers.isExtendable());
              Assert
                  .assertSame(modifiers, DataClassModifiersBean.getInstance(modifiers.getValue()));
            } catch (DataModifiersIllegalException e) {
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
