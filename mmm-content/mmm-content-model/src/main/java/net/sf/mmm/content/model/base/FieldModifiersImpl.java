/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import org.w3c.dom.Element;

import net.sf.mmm.content.NlsBundleContentModel;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueParseStringException;
import net.sf.mmm.value.base.AbstractValueManager;

/**
 * This is the base implementation of the {@link FieldModifiersImpl} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FieldModifiersImpl extends AbstractModifiers implements FieldModifiers {

  /** uid for serialization */
  private static final long serialVersionUID = -7486568372216293843L;

  /** the modifier of a "normal" field */
  public static final FieldModifiersImpl NORMAL = new FieldModifiersImpl(false, false, false,
      false, false);

  /** the modifier of a read-only field */
  public static final FieldModifiersImpl READ_ONLY = new FieldModifiersImpl(false, false, true,
      false, false);

  /** the modifier of a final field */
  public static final FieldModifiersImpl FINAL = new FieldModifiersImpl(false, true, false, false,
      false);

  /** the modifier of a final and read-only field */
  public static final FieldModifiersImpl FINAL_READONLY = new FieldModifiersImpl(false, true, true,
      false, false);

  /** the modifier of a static field */
  public static final FieldModifiersImpl STATIC = new FieldModifiersImpl(false, false, false, true,
      false);

  /** the modifier of a static and final field */
  public static final FieldModifiersImpl STATIC_FINAL = new FieldModifiersImpl(false, true, false,
      true, false);

  /** the modifier of a static and immutable field */
  public static final FieldModifiersImpl STATIC_READONLY = new FieldModifiersImpl(false, false,
      true, true, false);

  /** the modifier of a static, final and read-only field */
  public static final FieldModifiersImpl STATIC_FINAL_READONLY = new FieldModifiersImpl(false,
      true, true, true, false);

  /** the modifier of a transient (and read-only) field */
  public static final FieldModifiersImpl TRANSIENT = new FieldModifiersImpl(false, false, true,
      false, true);

  /** the modifier of a transient field */
  public static final FieldModifiersImpl FINAL_TRANSIENT = new FieldModifiersImpl(false, true,
      true, false, true);

  /** the modifier of a system-field */
  public static final FieldModifiersImpl SYSTEM = new FieldModifiersImpl(true, false, false, false,
      false);

  /** the modifier of a final system-field */
  public static final FieldModifiersImpl SYSTEM_FINAL = new FieldModifiersImpl(true, true, false,
      false, false);

  /** the modifier of a final and read-only system-field */
  public static final FieldModifiersImpl SYSTEM_FINAL_READONLY = new FieldModifiersImpl(true, true,
      true, false, false);

  /** the modifier of a static system-field */
  public static final FieldModifiersImpl SYSTEM_STATIC = new FieldModifiersImpl(true, false, false,
      true, false);

  /** the modifier of a static and final system-field */
  public static final FieldModifiersImpl SYSTEM_STATIC_FINAL = new FieldModifiersImpl(true, true,
      false, true, false);

  /** the modifier of a static, final and read-only system-field */
  public static final FieldModifiersImpl SYSTEM_STATIC_FINAL_READONLY = new FieldModifiersImpl(
      true, true, true, true, false);

  /** the modifier of a transient system-field */
  public static final FieldModifiersImpl SYSTEM_TRANSIENT = new FieldModifiersImpl(true, false,
      true, false, true);

  /** the modifier of a final, transient system-field */
  public static final FieldModifiersImpl SYSTEM_FINAL_TRANSIENT = new FieldModifiersImpl(true,
      true, true, false, true);

  /** the modifier of a static and read-only system-field */
  public static final FieldModifiersImpl SYSTEM_STATIC_READONLY = new FieldModifiersImpl(true,
      false, true, true, false);

  /** the modifier of a read-only system-field */
  public static final FieldModifiersImpl SYSTEM_READONLY = new FieldModifiersImpl(true, false,
      true, false, false);

  /** @see #isReadOnly() */
  private final boolean readOnlyFlag;

  /** @see #isStatic() */
  private final boolean staticFlag;

  /** @see #isTransient() */
  private final boolean transientFlag;

  /**
   * The constructor.
   * 
   * @see AbstractModifiers#AbstractModifiers(boolean, boolean)
   * 
   * @param isReadOnly is the value for the {@link #isReadOnly() read-only flag}.
   * @param isStatic is the value for the {@link #isStatic() static-flag}.
   * @param isTransient is the value for the
   *        {@link #isTransient() transient-flag}.
   */
  public FieldModifiersImpl(boolean isSystem, boolean isFinal, boolean isReadOnly,
      boolean isStatic, boolean isTransient) {

    super(isSystem, isFinal);
    validate(isSystem, isFinal, isReadOnly, isStatic, isTransient);
    this.readOnlyFlag = isReadOnly;
    this.staticFlag = isStatic;
    this.transientFlag = isTransient;
  }

  /**
   * The constructor.
   * 
   * @param modifiers is the modifiers object to copy.
   */
  public FieldModifiersImpl(FieldModifiers modifiers) {

    super(modifiers);
    // if this really fails we have an evil attack here...
    validate(modifiers.isSystem(), modifiers.isFinal(), modifiers.isReadOnly(), modifiers
        .isStatic(), modifiers.isTransient());
    this.readOnlyFlag = modifiers.isReadOnly();
    this.staticFlag = modifiers.isStatic();
    this.transientFlag = modifiers.isTransient();
  }

  /**
   * This method gets the modifiers.
   * 
   * @param isSystem is the value for the {@link #isSystem() system-flag}.
   * @param isFinal is the value for the {@link #isFinal() final-flag}.
   * @param isReadOnly is the value for the {@link #isReadOnly() read-only flag}.
   * @param isStatic is the value for the {@link #isStatic() static-flag}.
   * @param isTransient is the value for the
   *        {@link #isTransient() transient-flag}.
   * @return the requested modifiers.
   */
  public static FieldModifiers getInstance(boolean isSystem, boolean isFinal, boolean isReadOnly,
      boolean isStatic, boolean isTransient) {

    validate(isSystem, isFinal, isReadOnly, isStatic, isTransient);
    if (isSystem) {
      if (isTransient) {
        if (isFinal) {
          return SYSTEM_FINAL_TRANSIENT;
        } else {
          return SYSTEM_TRANSIENT;
        }
      } else {
        if (isFinal) {
          if (isReadOnly) {
            if (isStatic) {
              return SYSTEM_STATIC_FINAL_READONLY;
            } else {
              return SYSTEM_FINAL_READONLY;
            }
          } else {
            if (isStatic) {
              return SYSTEM_STATIC_FINAL;
            } else {
              return SYSTEM_FINAL;
            }
          }
        } else {
          if (isReadOnly) {
            if (isStatic) {
              return SYSTEM_STATIC_READONLY;
            } else {
              return SYSTEM_READONLY;
            }
          } else {
            if (isStatic) {
              return SYSTEM_STATIC;
            } else {
              return SYSTEM;
            }
          }
        }
      }
    } else {
      if (isTransient) {
        if (isFinal) {
          return FINAL_TRANSIENT;
        } else {
          return TRANSIENT;
        }
      } else {
        if (isFinal) {
          if (isReadOnly) {
            if (isStatic) {
              return STATIC_FINAL_READONLY;
            } else {
              return FINAL_READONLY;
            }
          } else {
            if (isStatic) {
              return STATIC_FINAL;
            } else {
              return FINAL;
            }
          }
        } else {
          if (isReadOnly) {
            if (isStatic) {
              return STATIC_READONLY;
            } else {
              return READ_ONLY;
            }
          } else {
            if (isStatic) {
              return STATIC;
            } else {
              return NORMAL;
            }
          }
        }
      }
    }
  }

  /**
   * This method validates the consistence of the modifier flags.
   * 
   * @param isSystem is the value for the {@link #isSystem() system-flag}.
   * @param isFinal is the value for the {@link #isFinal() final-flag}.
   * @param isReadOnly is the value for the {@link #isReadOnly() read-only flag}.
   * @param isStatic is the value for the {@link #isStatic() static-flag}.
   * @param isTransient is the value for the
   *        {@link #isTransient() transient-flag}.
   * @throws IllegalModifiersException if the flags are inconsistent.
   */
  protected static void validate(boolean isSystem, boolean isFinal, boolean isReadOnly,
      boolean isStatic, boolean isTransient) throws IllegalModifiersException {

    if (isTransient) {
      if (!isReadOnly) {
        throw new IllegalModifiersException(NlsBundleContentModel.ERR_MODIFIERS_TRANSIENT_MUTABLE);
      }
      if (isStatic) {
        throw new IllegalModifiersException(NlsBundleContentModel.ERR_MODIFIERS_TRANSIENT_STATIC);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isReadOnly() {

    if (isTransient()) {
      return true;
    }
    return this.readOnlyFlag;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isTransient() {

    return this.transientFlag;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isStatic() {

    return this.staticFlag;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setXmlAttributes(XmlWriter xmlWriter) throws XmlException {

    super.setXmlAttributes(xmlWriter);
    if (!isSystem() && isReadOnly()) {
      xmlWriter.writeAttribute(XML_ATR_ROOT_READ_ONLY, StringUtil.TRUE);
    }
    if (isStatic()) {
      xmlWriter.writeAttribute(XML_ATR_ROOT_STATIC, StringUtil.TRUE);
    }
    if (isTransient()) {
      xmlWriter.writeAttribute(XML_ATR_ROOT_TRANSIENT, StringUtil.TRUE);
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
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuffer result = new StringBuffer();
    if (isSystem()) {
      result.append("system");
    }
    if (isStatic()) {
      if (result.length() > 0) {
        result.append('-');
      }
      result.append("static");
    }
    if (isFinal()) {
      if (result.length() > 0) {
        result.append('-');
      }
      result.append("final");
    }
    if (isReadOnly()) {
      if (result.length() > 0) {
        result.append('-');
      }
      result.append("readonly");
    }
    if (isTransient()) {
      if (result.length() > 0) {
        result.append('-');
      }
      result.append("transient");
    }

    return result.toString();
  }

  /**
   * This inner class is the manager for the value.
   */
  public static class Manager extends AbstractValueManager<FieldModifiers> {

    /** @see #getName() */
    public static final String VALUE_NAME = "FieldModifiers";

    /**
     * @see net.sf.mmm.value.api.ValueManager#getName()
     */
    public String getName() {

      return VALUE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    public Class<FieldModifiers> getValueClass() {

      return FieldModifiers.class;
    }

    /**
     * {@inheritDoc}
     */
    public FieldModifiers parse(String valueAsString) throws ValueParseException {

      if ("N".equals(valueAsString)) {
        return NORMAL;
      }
      boolean isSystem = (valueAsString.indexOf('X') >= 0);
      boolean isStatic = (valueAsString.indexOf('S') >= 0);
      boolean isFinal = (valueAsString.indexOf('F') >= 0);
      boolean isReadOnly = (valueAsString.indexOf('R') >= 0);
      boolean isTransient = (valueAsString.indexOf('T') >= 0);
      return getInstance(isSystem, isFinal, isReadOnly, isStatic, isTransient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(FieldModifiers value) {

      StringBuffer buffer = new StringBuffer(4);
      if (value.isSystem()) {
        buffer.append('X');
      } else if (value.isStatic()) {
        buffer.append('S');
      } else if (value.isFinal()) {
        buffer.append('F');
      } else if (value.isReadOnly()) {
        buffer.append('R');
      } else if (value.isTransient()) {
        buffer.append('T');
      }
      if (buffer.length() == 0) {
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
    public FieldModifiers parse(Element valueAsXml) throws ValueParseException {

      checkXml(valueAsXml);

      boolean isSystem = getAttributeAsBoolean(valueAsXml, XML_ATR_ROOT_SYSTEM, false);
      boolean isStatic = getAttributeAsBoolean(valueAsXml, XML_ATR_ROOT_STATIC, false);
      boolean isFinal = getAttributeAsBoolean(valueAsXml, XML_ATR_ROOT_FINAL, false);
      boolean isReadOnly = getAttributeAsBoolean(valueAsXml, XML_ATR_ROOT_READ_ONLY, false);
      boolean isTransient = getAttributeAsBoolean(valueAsXml, XML_ATR_ROOT_TRANSIENT, false);
      return getInstance(isSystem, isFinal, isReadOnly, isStatic, isTransient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void toXmlValue(XmlWriter xmlWriter, FieldModifiers value) throws XmlException {

      if (value.isSystem()) {
        xmlWriter.writeAttribute(XML_ATR_ROOT_SYSTEM, StringUtil.TRUE);
      }
      if (value.isStatic()) {
        xmlWriter.writeAttribute(XML_ATR_ROOT_STATIC, StringUtil.TRUE);
      }
      if (value.isFinal()) {
        xmlWriter.writeAttribute(XML_ATR_ROOT_FINAL, StringUtil.TRUE);
      }
      if (value.isReadOnly()) {
        xmlWriter.writeAttribute(XML_ATR_ROOT_READ_ONLY, StringUtil.TRUE);
      }
      if (value.isTransient()) {
        xmlWriter.writeAttribute(XML_ATR_ROOT_TRANSIENT, StringUtil.TRUE);
      }
    }

  }

}
