/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.NlsBundleContentModel;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;

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
  public static final FieldModifiersImpl SYSTEM_STATIC_READONLY = new FieldModifiersImpl(true, false, true,
      true, false);

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
   * @param isReadOnly
   *        is the value for the {@link #isReadOnly() read-only flag}.
   * @param isStatic
   *        is the value for the {@link #isStatic() static-flag}.
   * @param isTransient
   *        is the value for the {@link #isTransient() transient-flag}.
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
   * @param modifiers
   *        is the modifiers object to copy.
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
   * @param isSystem
   *        is the value for the {@link #isSystem() system-flag}.
   * @param isFinal
   *        is the value for the {@link #isFinal() final-flag}.
   * @param isReadOnly
   *        is the value for the {@link #isReadOnly() read-only flag}.
   * @param isStatic
   *        is the value for the {@link #isStatic() static-flag}.
   * @param isTransient
   *        is the value for the {@link #isTransient() transient-flag}.
   * @return the requested modifiers.
   */
  public static FieldModifiers getInstance(boolean isSystem, boolean isFinal,
      boolean isReadOnly, boolean isStatic, boolean isTransient) {

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
   * @param isSystem
   *        is the value for the {@link #isSystem() system-flag}.
   * @param isFinal
   *        is the value for the {@link #isFinal() final-flag}.
   * @param isReadOnly
   *        is the value for the {@link #isReadOnly() read-only flag}.
   * @param isStatic
   *        is the value for the {@link #isStatic() static-flag}.
   * @param isTransient
   *        is the value for the {@link #isTransient() transient-flag}.
   * @throws IllegalModifiersException
   *         if the flags are inconsistent.
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
   * @see net.sf.mmm.content.model.api.FieldModifiers#isReadOnly()
   */
  public boolean isReadOnly() {

    if (isTransient()) {
      return true;
    }
    return this.readOnlyFlag;
  }

  /**
   * @see net.sf.mmm.content.model.api.FieldModifiers#isTransient()
   */
  public boolean isTransient() {

    return this.transientFlag;
  }

  /**
   * @see net.sf.mmm.content.model.api.FieldModifiers#isStatic()
   */
  public boolean isStatic() {

    return this.staticFlag;
  }

  /**
   * @see net.sf.mmm.content.model.base.AbstractModifiers#setXmlAttributes(net.sf.mmm.util.xml.api.XmlWriter)
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
   * @see net.sf.mmm.util.xml.api.XmlSerializable#toXml(XmlWriter)
   */
  public void toXml(XmlWriter xmlWriter) throws XmlException {

    xmlWriter.writeStartElement(XML_TAG_ROOT);
    setXmlAttributes(xmlWriter);
    xmlWriter.writeEndElement(XML_TAG_ROOT);
  }

  /**
   * @see java.lang.Object#toString()
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

}
