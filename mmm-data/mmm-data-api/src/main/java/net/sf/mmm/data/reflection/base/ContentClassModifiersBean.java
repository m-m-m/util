/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.reflection.base;

import javax.xml.bind.annotation.XmlAttribute;

import net.sf.mmm.data.NlsBundleContentApi;
import net.sf.mmm.data.reflection.api.ContentClassModifiers;

/**
 * This is the base implementation of the {@link ContentClassModifiers}
 * interface.
 * 
 * @see net.sf.mmm.data.reflection.api.ContentClass#getContentModifiers()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentClassModifiersBean extends AbstractContentModifiersBean implements
    ContentClassModifiers {

  /** UID for serialization. */
  private static final long serialVersionUID = 2603625618112910413L;

  /**
   * the {@link net.sf.mmm.data.reflection.api.ContentClass#getContentModifiers()
   * modifiers} for a {@link #isSystem() system}
   * {@link net.sf.mmm.data.reflection.api.ContentClass content-class}.
   */
  public static final ContentClassModifiersBean SYSTEM = new ContentClassModifiersBean(true, false,
      false, true);

  /**
   * the {@link net.sf.mmm.data.reflection.api.ContentClass#getContentModifiers()
   * modifiers} for a {@link #isSystem() system}
   * {@link net.sf.mmm.data.reflection.api.ContentClass content-class} that is NOT
   * {@link #isExtendable() extendable}.
   */
  public static final ContentClassModifiersBean SYSTEM_UNEXTENDABLE = new ContentClassModifiersBean(
      true, false, false, false);

  /**
   * the {@link net.sf.mmm.data.reflection.api.ContentClass#getContentModifiers()
   * modifiers} for a
   * {@link net.sf.mmm.data.reflection.api.ContentModifiers#isSystem() system}
   * {@link net.sf.mmm.data.reflection.api.ContentClass content-class} that is
   * {@link #isFinal() final}.
   */
  public static final ContentClassModifiersBean SYSTEM_FINAL = new ContentClassModifiersBean(true,
      true, false, false);

  /**
   * the {@link net.sf.mmm.data.reflection.api.ContentClass#getContentModifiers()
   * modifiers} for a
   * {@link net.sf.mmm.data.reflection.api.ContentModifiers#isSystem() system}
   * {@link net.sf.mmm.data.reflection.api.ContentClass content-class} that is
   * {@link #isAbstract() abstract}.
   */
  public static final ContentClassModifiersBean SYSTEM_ABSTRACT = new ContentClassModifiersBean(
      true, false, true, true);

  /**
   * the {@link net.sf.mmm.data.reflection.api.ContentClass#getContentModifiers()
   * modifiers} for a
   * {@link net.sf.mmm.data.reflection.api.ContentModifiers#isSystem() system}
   * {@link net.sf.mmm.data.reflection.api.ContentClass content-class} that is
   * {@link #isAbstract() abstract} and NOT {@link #isExtendable() extendable}.
   */
  public static final ContentClassModifiersBean SYSTEM_ABSTRACT_UNEXTENDABLE = new ContentClassModifiersBean(
      true, false, true, false);

  /**
   * the {@link net.sf.mmm.data.reflection.api.ContentClass#getContentModifiers()
   * modifiers} for a {@link #isFinal() final}
   * {@link net.sf.mmm.data.reflection.api.ContentClass content-class}.
   */
  public static final ContentClassModifiersBean FINAL = new ContentClassModifiersBean(false, true,
      false, false);

  /**
   * the {@link net.sf.mmm.data.reflection.api.ContentClass#getContentModifiers()
   * modifiers} for a {@link #isAbstract() abstract}
   * {@link net.sf.mmm.data.reflection.api.ContentClass content-class}.
   */
  public static final ContentClassModifiersBean ABSTRACT = new ContentClassModifiersBean(false,
      false, true, true);

  /**
   * the {@link net.sf.mmm.data.reflection.api.ContentClass#getContentModifiers()
   * modifiers} for a "normal" {@link net.sf.mmm.data.reflection.api.ContentClass
   * content-class}.
   */
  public static final ContentClassModifiersBean NORMAL = new ContentClassModifiersBean(false,
      false, false, true);

  /** @see #isAbstract() */
  @XmlAttribute(name = XML_ATR_ABSTRACT)
  private boolean abstractFlag;

  /** @see #isExtendable() */
  @XmlAttribute(name = XML_ATR_EXTENDABLE)
  private boolean extendableFlag;

  /**
   * The constructor.
   * 
   * @see AbstractContentModifiersBean#AbstractContentModifiersBean(boolean,
   *      boolean)
   * 
   * @param isSystem is the {@link #isSystem() system-flag}.
   * @param isFinal is the {@link #isFinal() final-flag}.
   * @param isAbstract is the {@link #isAbstract() abstract-flag}.
   * @param isExtendable is the {@link #isExtendable() extendable-flag}.
   */
  public ContentClassModifiersBean(boolean isSystem, boolean isFinal, boolean isAbstract,
      boolean isExtendable) {

    super(isSystem, isFinal);
    validate(isSystem, isFinal, isAbstract, isExtendable);
    this.abstractFlag = isAbstract;
    this.extendableFlag = isExtendable;
  }

  /**
   * The constructor.
   * 
   * @param modifiers is the modifiers object to copy.
   */
  public ContentClassModifiersBean(ContentClassModifiersBean modifiers) {

    super(modifiers);
    // if this really fails we have an evil attack here...
    validate(modifiers.isSystem(), modifiers.isFinal(), modifiers.isAbstract(),
        modifiers.isExtendable());
    this.abstractFlag = modifiers.isAbstract();
    this.extendableFlag = modifiers.isExtendable();
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
  public static ContentClassModifiers getInstance(boolean isSystem, boolean isFinal,
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
   * @throws ContentModifiersIllegalException if the flags are inconsistent.
   */
  protected static void validate(boolean isSystem, boolean isFinal, boolean isAbstract,
      boolean isExtendable) throws ContentModifiersIllegalException {

    if (isFinal && isExtendable) {
      throw new ContentModifiersIllegalException(NlsBundleContentApi.ERR_MODIFIERS_FINAL_EXTENDABLE);
    }
    if (!isExtendable && !isFinal && !isSystem) {
      throw new ContentModifiersIllegalException(
          NlsBundleContentApi.ERR_MODIFIERS_USER_UNEXTENDABLE);
    }
    if (isAbstract && isFinal) {
      throw new ContentModifiersIllegalException(NlsBundleContentApi.ERR_MODIFIERS_ABSTRACT_FINAL);
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
