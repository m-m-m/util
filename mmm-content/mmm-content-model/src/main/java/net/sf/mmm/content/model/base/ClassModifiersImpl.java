/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;

/**
 * This is the base implementation of the {@link ClassModifiersImpl} interface.
 * 
 * @see net.sf.mmm.content.model.api.ContentClass#getModifiers()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ClassModifiersImpl extends AbstractModifiers implements ClassModifiers {

  /** UID for serialization */
  private static final long serialVersionUID = 2603625618112910413L;

  /**
   * the
   * {@link net.sf.mmm.content.model.api.ContentClass#getModifiers() modifiers}
   * for a {@link #isSystem() system}
   * {@link net.sf.mmm.content.model.api.ContentClass content-class}.
   */
  public static final ClassModifiersImpl SYSTEM = new ClassModifiersImpl(true, false, false, true);

  /**
   * the
   * {@link net.sf.mmm.content.model.api.ContentClass#getModifiers() modifiers}
   * for a {@link #isSystem() system}
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that is
   * NOT {@link #isExtendable() extendable}.
   */
  public static final ClassModifiersImpl SYSTEM_UNEXTENDABLE = new ClassModifiersImpl(true, false, false,
      false);

  /**
   * the
   * {@link net.sf.mmm.content.model.api.ContentClass#getModifiers() modifiers}
   * for a {@link net.sf.mmm.content.model.api.Modifiers#isSystem() system}
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that is
   * {@link #isFinal() final}.
   */
  public static final ClassModifiersImpl SYSTEM_FINAL = new ClassModifiersImpl(true, true, false, false);

  /**
   * the
   * {@link net.sf.mmm.content.model.api.ContentClass#getModifiers() modifiers}
   * for a {@link net.sf.mmm.content.model.api.Modifiers#isSystem() system}
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that is
   * {@link #isAbstract() abstract}.
   */
  public static final ClassModifiersImpl SYSTEM_ABSTRACT = new ClassModifiersImpl(true, false, true, true);

  /**
   * the
   * {@link net.sf.mmm.content.model.api.ContentClass#getModifiers() modifiers}
   * for a {@link net.sf.mmm.content.model.api.Modifiers#isSystem() system}
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that is
   * {@link #isAbstract() abstract} and NOT {@link #isExtendable() extendable}.
   */
  public static final ClassModifiersImpl SYSTEM_ABSTRACT_UNEXTENDABLE = new ClassModifiersImpl(true,
      false, true, false);

  /**
   * the
   * {@link net.sf.mmm.content.model.api.ContentClass#getModifiers() modifiers}
   * for a {@link #isFinal() final}
   * {@link net.sf.mmm.content.model.api.ContentClass content-class}.
   */
  public static final ClassModifiersImpl FINAL = new ClassModifiersImpl(false, true, false, false);

  /**
   * the
   * {@link net.sf.mmm.content.model.api.ContentClass#getModifiers() modifiers}
   * for a {@link #isAbstract() abstract}
   * {@link net.sf.mmm.content.model.api.ContentClass content-class}.
   */
  public static final ClassModifiersImpl ABSTRACT = new ClassModifiersImpl(false, false, true, true);

  /**
   * the
   * {@link net.sf.mmm.content.model.api.ContentClass#getModifiers() modifiers}
   * for a "normal"
   * {@link net.sf.mmm.content.model.api.ContentClass content-class}.
   */
  public static final ClassModifiersImpl NORMAL = new ClassModifiersImpl(false, false, false, true);

  /** @see #isAbstract() */
  private final boolean abstractFlag;

  /** @see #isAbstract() */
  private final boolean extendableFlag;

  /**
   * The constructor.
   * 
   * @see AbstractModifiers#AbstractModifiers(boolean, boolean)
   * 
   * @param isAbstract
   *        is the {@link #isAbstract() abstract-flag}.
   * @param isExtendable
   *        is the {@link #isExtendable() extendable-flag}.
   */
  public ClassModifiersImpl(boolean isSystem, boolean isFinal, boolean isAbstract, boolean isExtendable) {

    super(isSystem, isFinal);
    this.abstractFlag = isAbstract;
    this.extendableFlag = isExtendable;
    validate();
  }

  /**
   * The constructor.
   * 
   * @param modifiers
   *        is the modifiers object to copy.
   */
  public ClassModifiersImpl(ClassModifiersImpl modifiers) {

    super(modifiers);
    this.abstractFlag = modifiers.isAbstract();
    this.extendableFlag = modifiers.isExtendable();
    validate();
  }

  /**
   * This method is called from the constructor and validates the consistence of
   * the modifier flags.
   * 
   * @throws IllegalArgumentException
   *         if the flags are inconsistent.
   */
  protected void validate() throws IllegalArgumentException {

    if (isFinal() == this.extendableFlag) {
      if (!isSystem() || isFinal()) {
        // TODO:
        throw new IllegalArgumentException("extendable flag illegal!");
      }
    }
    if (isAbstract() && isFinal()) {
      // TODO:
      throw new IllegalArgumentException("abstract + final = illegal!");
    }
  }

  /**
   * @see net.sf.mmm.content.model.api.ClassModifiers#isAbstract()
   */
  public boolean isAbstract() {

    return this.abstractFlag;
  }

  /**
   * @see net.sf.mmm.content.model.api.ClassModifiers#isExtendable()
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

  /**
   * @see net.sf.mmm.content.model.base.AbstractModifiers#setXmlAttributes(net.sf.mmm.util.xml.api.XmlWriter)
   */
  @Override
  protected void setXmlAttributes(XmlWriter xmlWriter) throws XmlException {

    super.setXmlAttributes(xmlWriter);
    if (isAbstract()) {
      xmlWriter.writeAttribute(XML_ATR_ROOT_ABSTRACT, Boolean.toString(this.abstractFlag));
      xmlWriter.writeAttribute(XML_ATR_ROOT_EXTENDABLE, Boolean.toString(this.extendableFlag));
    }
  }

  /**
   * @see net.sf.mmm.util.xml.api.XmlSerializable#toXml(XmlWriter)
   */
  public void toXml(XmlWriter xmlWriter) throws XmlException {

    xmlWriter.writeStartElement(XML_TAG_ROOT);
    setXmlAttributes(xmlWriter);
    xmlWriter.writeEndElement(XML_TAG_ROOT);
  }

  /**
   * This method gets the modifiers.
   * 
   * @param isSystem
   *        is the value for the {@link #isSystem() system-flag}.
   * @param isFinal
   *        is the value for the {@link #isFinal() final-flag}.
   * @param isAbstract
   *        is the {@link #isAbstract() abstract-flag}.
   * @param isExtendable
   *        is the {@link #isExtendable() extendable-flag}.
   * @return the requested modifiers.
   */
  public static ClassModifiers getInstance(boolean isSystem, boolean isFinal, boolean isAbstract, boolean isExtendable) {
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
  
}
