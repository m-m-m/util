/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import org.w3c.dom.Element;

import net.sf.mmm.content.NlsBundleContentModel;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueParseStringException;
import net.sf.mmm.value.base.AbstractValueManager;

/**
 * This is the base implementation of the {@link ClassModifiersImpl} interface.
 * 
 * @see net.sf.mmm.content.model.api.ContentClass#getModifiers()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ClassModifiersImpl extends AbstractModifiers implements ClassModifiers {

  /** UID for serialization. */
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
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that is NOT
   * {@link #isExtendable() extendable}.
   */
  public static final ClassModifiersImpl SYSTEM_UNEXTENDABLE = new ClassModifiersImpl(true, false,
      false, false);

  /**
   * the
   * {@link net.sf.mmm.content.model.api.ContentClass#getModifiers() modifiers}
   * for a {@link net.sf.mmm.content.model.api.Modifiers#isSystem() system}
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that is
   * {@link #isFinal() final}.
   */
  public static final ClassModifiersImpl SYSTEM_FINAL = new ClassModifiersImpl(true, true, false,
      false);

  /**
   * the
   * {@link net.sf.mmm.content.model.api.ContentClass#getModifiers() modifiers}
   * for a {@link net.sf.mmm.content.model.api.Modifiers#isSystem() system}
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that is
   * {@link #isAbstract() abstract}.
   */
  public static final ClassModifiersImpl SYSTEM_ABSTRACT = new ClassModifiersImpl(true, false,
      true, true);

  /**
   * the
   * {@link net.sf.mmm.content.model.api.ContentClass#getModifiers() modifiers}
   * for a {@link net.sf.mmm.content.model.api.Modifiers#isSystem() system}
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that is
   * {@link #isAbstract() abstract} and NOT {@link #isExtendable() extendable}.
   */
  public static final ClassModifiersImpl SYSTEM_ABSTRACT_UNEXTENDABLE = new ClassModifiersImpl(
      true, false, true, false);

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
   * @param isAbstract is the {@link #isAbstract() abstract-flag}.
   * @param isExtendable is the {@link #isExtendable() extendable-flag}.
   */
  public ClassModifiersImpl(boolean isSystem, boolean isFinal, boolean isAbstract,
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
  public ClassModifiersImpl(ClassModifiersImpl modifiers) {

    super(modifiers);
    // if this really fails we have an evil attack here...
    validate(modifiers.isSystem(), modifiers.isFinal(), modifiers.isAbstract(), modifiers
        .isExtendable());
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
  public static ClassModifiers getInstance(boolean isSystem, boolean isFinal, boolean isAbstract,
      boolean isExtendable) {

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
   * @throws IllegalModifiersException if the flags are inconsistent.
   */
  protected static void validate(boolean isSystem, boolean isFinal, boolean isAbstract,
      boolean isExtendable) throws IllegalModifiersException {

    if (isFinal && isExtendable) {
      throw new IllegalModifiersException(NlsBundleContentModel.ERR_MODIFIERS_FINAL_EXTENDABLE);
    }
    if (!isExtendable && !isFinal && !isSystem) {
      throw new IllegalModifiersException(NlsBundleContentModel.ERR_MODIFIERS_USER_UNEXTENDABLE);
    }
    if (isAbstract && isFinal) {
      throw new IllegalModifiersException(NlsBundleContentModel.ERR_MODIFIERS_ABSTRACT_FINAL);
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

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  public void toXml(XmlWriter xmlWriter) throws XmlException {

    xmlWriter.writeStartElement(XML_TAG_ROOT);
    setXmlAttributes(xmlWriter);
    xmlWriter.writeEndElement(XML_TAG_ROOT);
  }

  /**
   * This inner class is the manager for the value.
   */
  public static class Manager extends AbstractValueManager<ClassModifiers> {

    /** @see #getName() */
    public static final String VALUE_NAME = "ClassModifiers";

    /**
     * @see net.sf.mmm.value.api.ValueManager#getName()
     */
    public String getName() {

      return VALUE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    public Class<ClassModifiers> getValueClass() {

      return ClassModifiers.class;
    }

    /**
     * {@inheritDoc}
     */
    public ClassModifiers parse(String valueAsString) throws ValueParseException {

      if ("N".equals(valueAsString)) {
        return NORMAL;
      } else if ("X".equals(valueAsString)) {
        return SYSTEM;
      } else if ("A".equals(valueAsString)) {
        return ABSTRACT;
      } else if ("F".equals(valueAsString)) {
        return FINAL;
      } else if ("XA".equals(valueAsString)) {
        return SYSTEM_ABSTRACT;
      } else if ("XF".equals(valueAsString)) {
        return SYSTEM_FINAL;
      } else if ("XAU".equals(valueAsString)) {
        return SYSTEM_ABSTRACT_UNEXTENDABLE;
      } else if ("XU".equals(valueAsString)) {
        return SYSTEM_UNEXTENDABLE;
      } else {
        throw new ValueParseStringException(valueAsString, ClassModifiers.class, VALUE_NAME);
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(ClassModifiers value) {

      StringBuffer buffer = new StringBuffer(4);
      if (value.isSystem()) {
        buffer.append('X');
      } else if (value.isAbstract()) {
        buffer.append('A');
      } else if (!value.isExtendable() && !value.isFinal()) {
        buffer.append('U');
      } else if (value.isFinal()) {
        buffer.append('F');
      } else if (buffer.length() == 0) {
        buffer.append('N');
      }
      return buffer.toString();
    }

    /**
     * TODO: javadoc
     * 
     * @param element
     * @param attributeName
     * @param defaultValue
     * @return
     * @throws ValueParseException
     */
    private boolean getAttributeAsBoolean(Element element, String attributeName,
        boolean defaultValue) throws ValueParseException {

      if (element.hasAttribute(attributeName)) {
        String value = element.getAttribute(attributeName);
        Boolean result = StringUtil.parseBoolean(value);
        if (result == null) {
          throw new ValueParseStringException(value, boolean.class, VALUE_NAME);
        }
        return result.booleanValue();
      } else {
        return defaultValue;
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClassModifiers parse(Element valueAsXml) throws ValueParseException {

      checkXml(valueAsXml);

      boolean isSystem = getAttributeAsBoolean(valueAsXml, XML_ATR_ROOT_SYSTEM, false);
      boolean isAbstract = getAttributeAsBoolean(valueAsXml, XML_ATR_ROOT_ABSTRACT, false);
      boolean isFinal = getAttributeAsBoolean(valueAsXml, XML_ATR_ROOT_FINAL, false);
      boolean isExtendable = getAttributeAsBoolean(valueAsXml, XML_ATR_ROOT_EXTENDABLE, !isFinal);
      return getInstance(isSystem, isFinal, isAbstract, isExtendable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void toXmlValue(XmlWriter xmlWriter, ClassModifiers value) throws XmlException {

      if (value.isSystem()) {
        xmlWriter.writeAttribute(XML_ATR_ROOT_SYSTEM, StringUtil.TRUE);
      }
      if (value.isAbstract()) {
        xmlWriter.writeAttribute(XML_ATR_ROOT_ABSTRACT, StringUtil.TRUE);
      }
      if (value.isFinal()) {
        xmlWriter.writeAttribute(XML_ATR_ROOT_FINAL, StringUtil.TRUE);
      } else if (!value.isExtendable()) {
        xmlWriter.writeAttribute(XML_ATR_ROOT_EXTENDABLE, StringUtil.FALSE);
      }
    }

  }

}
