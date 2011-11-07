/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import javax.xml.bind.annotation.XmlAttribute;

import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.api.reflection.DataModifiersIllegalAbstractFinalException;
import net.sf.mmm.data.api.reflection.DataModifiersIllegalException;
import net.sf.mmm.data.api.reflection.DataModifiersIllegalFinalExtendableException;
import net.sf.mmm.data.api.reflection.DataModifiersIllegalNotSystemUnextendableException;

/**
 * This is the base implementation of the {@link DataClassModifiers} interface.
 * 
 * @see net.sf.mmm.data.api.reflection.DataClass#getModifiers()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class DataClassModifiersBean extends AbstractDataModifiersBean implements
    DataClassModifiers {

  /** UID for serialization. */
  private static final long serialVersionUID = 2603625618112910413L;

  /**
   * the {@link net.sf.mmm.data.api.reflection.DataClass#getModifiers()
   * modifiers} for a {@link #isSystem() system}
   * {@link net.sf.mmm.data.api.reflection.DataClass content-class}.
   */
  public static final DataClassModifiersBean SYSTEM = new DataClassModifiersBean(true, false,
      false, true);

  /**
   * the {@link net.sf.mmm.data.api.reflection.DataClass#getModifiers()
   * modifiers} for a {@link #isSystem() system}
   * {@link net.sf.mmm.data.api.reflection.DataClass content-class} that is NOT
   * {@link #isExtendable() extendable}.
   */
  public static final DataClassModifiersBean SYSTEM_UNEXTENDABLE = new DataClassModifiersBean(true,
      false, false, false);

  /**
   * the {@link net.sf.mmm.data.api.reflection.DataClass#getModifiers()
   * modifiers} for a
   * {@link net.sf.mmm.data.api.reflection.DataModifiers#isSystem() system}
   * {@link net.sf.mmm.data.api.reflection.DataClass content-class} that is
   * {@link #isFinal() final}.
   */
  public static final DataClassModifiersBean SYSTEM_FINAL = new DataClassModifiersBean(true, true,
      false, false);

  /**
   * the {@link net.sf.mmm.data.api.reflection.DataClass#getModifiers()
   * modifiers} for a
   * {@link net.sf.mmm.data.api.reflection.DataModifiers#isSystem() system}
   * {@link net.sf.mmm.data.api.reflection.DataClass content-class} that is
   * {@link #isAbstract() abstract}.
   */
  public static final DataClassModifiersBean SYSTEM_ABSTRACT = new DataClassModifiersBean(true,
      false, true, true);

  /**
   * the {@link net.sf.mmm.data.api.reflection.DataClass#getModifiers()
   * modifiers} for a
   * {@link net.sf.mmm.data.api.reflection.DataModifiers#isSystem() system}
   * {@link net.sf.mmm.data.api.reflection.DataClass content-class} that is
   * {@link #isAbstract() abstract} and NOT {@link #isExtendable() extendable}.
   */
  public static final DataClassModifiersBean SYSTEM_ABSTRACT_UNEXTENDABLE = new DataClassModifiersBean(
      true, false, true, false);

  /**
   * the {@link net.sf.mmm.data.api.reflection.DataClass#getModifiers()
   * modifiers} for a {@link #isFinal() final}
   * {@link net.sf.mmm.data.api.reflection.DataClass content-class}.
   */
  public static final DataClassModifiersBean FINAL = new DataClassModifiersBean(false, true, false,
      false);

  /**
   * the {@link net.sf.mmm.data.api.reflection.DataClass#getModifiers()
   * modifiers} for a {@link #isAbstract() abstract}
   * {@link net.sf.mmm.data.api.reflection.DataClass content-class}.
   */
  public static final DataClassModifiersBean ABSTRACT = new DataClassModifiersBean(false, false,
      true, true);

  /**
   * the {@link net.sf.mmm.data.api.reflection.DataClass#getModifiers()
   * modifiers} for a "normal" {@link net.sf.mmm.data.api.reflection.DataClass
   * content-class}.
   */
  public static final DataClassModifiersBean NORMAL = new DataClassModifiersBean(false, false,
      false, true);

  /** @see #isAbstract() */
  @XmlAttribute(name = XML_ATR_ABSTRACT)
  private boolean abstractFlag;

  /** @see #isExtendable() */
  @XmlAttribute(name = XML_ATR_EXTENDABLE)
  private boolean extendableFlag;

  /**
   * The constructor.
   * 
   * @param isSystem is the {@link #isSystem() system-flag}.
   * @param isFinal is the {@link #isFinal() final-flag}.
   * @param isAbstract is the {@link #isAbstract() abstract-flag}.
   * @param isExtendable is the {@link #isExtendable() extendable-flag}.
   */
  private DataClassModifiersBean(boolean isSystem, boolean isFinal, boolean isAbstract,
      boolean isExtendable) {

    super(isSystem, isFinal);
    validate(isSystem, isFinal, isAbstract, isExtendable);
    this.abstractFlag = isAbstract;
    this.extendableFlag = isExtendable;
  }

  /**
   * This method gets the modifiers.
   * 
   * @param isSystem is the value for the {@link #isSystem() system-flag}.
   * @param isFinal is the value for the {@link #isFinal() final-flag}.
   * @param isAbstract is the {@link #isAbstract() abstract-flag}.
   * @param isExtendable is the {@link #isExtendable() extendable-flag}.
   * @return the requested modifiers.
   */
  public static DataClassModifiers getInstance(boolean isSystem, boolean isFinal,
      boolean isAbstract, boolean isExtendable) {

    validate(isSystem, isFinal, isAbstract, isExtendable);
    if (isSystem) {
      if (isAbstract) {
        if (isExtendable) {
          return SYSTEM_ABSTRACT;
        } else {
          return SYSTEM_ABSTRACT_UNEXTENDABLE;
        }
      } else {
        if (isFinal) {
          return SYSTEM_FINAL;
        } else if (isExtendable) {
          return SYSTEM;
        } else {
          return SYSTEM_UNEXTENDABLE;
        }
      }
    } else {
      if (isAbstract) {
        return ABSTRACT;
      } else if (isFinal) {
        return FINAL;
      } else {
        return NORMAL;
      }
    }
  }

  /**
   * This method validates the consistence of the modifier flags.
   * 
   * @param isSystem is the value for the {@link #isSystem() system-flag}.
   * @param isFinal is the value for the {@link #isFinal() final-flag}.
   * @param isAbstract is the {@link #isAbstract() abstract-flag}.
   * @param isExtendable is the {@link #isExtendable() extendable-flag}.
   * @throws DataModifiersIllegalException if the flags are inconsistent.
   */
  protected static void validate(boolean isSystem, boolean isFinal, boolean isAbstract,
      boolean isExtendable) throws DataModifiersIllegalException {

    if (isFinal && isExtendable) {
      throw new DataModifiersIllegalFinalExtendableException();
    }
    if (!isExtendable && !isFinal && !isSystem) {
      throw new DataModifiersIllegalNotSystemUnextendableException();
    }
    if (isAbstract && isFinal) {
      throw new DataModifiersIllegalAbstractFinalException();
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAbstract() {

    return this.abstractFlag;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isExtendable() {

    if (isFinal()) {
      return false;
    }
    if (isSystem()) {
      return this.extendableFlag;
    }
    return true;
  }

}
