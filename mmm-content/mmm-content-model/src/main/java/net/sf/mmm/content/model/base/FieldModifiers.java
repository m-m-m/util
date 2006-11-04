/* $ Id: $ */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.model.api.FieldModifiersIF;
import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;

/**
 * This is the base implementation of the {@link FieldModifiersIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FieldModifiers extends AbstractModifiers implements FieldModifiersIF {

  /** uid for serialization */
  private static final long serialVersionUID = -7486568372216293843L;

  /** the modifier of a "normal" field */
  public static final FieldModifiers NORMAL = new FieldModifiers(false, false, false, false, false);

  /** the modifier of a final field */
  public static final FieldModifiers FINAL = new FieldModifiers(false, true, false, false, false);

  /** the modifier of a final and immutable field */
  public static final FieldModifiers FINAL_IMMUTABLE = new FieldModifiers(false, true, true, false,
      false);

  /** the modifier of a static field */
  public static final FieldModifiers STATIC = new FieldModifiers(false, false, false, true, false);

  /** the modifier of a static and final field */
  public static final FieldModifiers STATIC_FINAL = new FieldModifiers(false, true, false, true,
      false);

  /** the modifier of a static and immutable field */
  public static final FieldModifiers STATIC_IMMUTABLE = new FieldModifiers(false, false, true,
      true, false);

  /** the modifier of a static, final and immutable field */
  public static final FieldModifiers STATIC_FINAL_IMMUTABLE = new FieldModifiers(false, true, true,
      true, false);

  /** the modifier of a transient (and immutable) field */
  public static final FieldModifiers TRANSIENT = new FieldModifiers(false, false, true, false, true);

  /** the modifier of a transient field */
  public static final FieldModifiers FINAL_TRANSIENT = new FieldModifiers(false, true, true, false,
      true);

  /** the modifier of a final system field */
  public static final FieldModifiers SYSTEM_FINAL = new FieldModifiers(true, true, false, false,
      false);

  /** the modifier of a final and immutable system field */
  public static final FieldModifiers SYSTEM_FINAL_IMMUTABLE = new FieldModifiers(true, true, true,
      false, false);

  /** @see #isImmutable() */
  private final boolean immutableFlag;

  /** @see #isStatic() */
  private final boolean staticFlag;

  /** @see #isTransient() */
  private final boolean transientFlag;

  /**
   * The constructor.
   * 
   * @see AbstractModifiers#AbstractModifiers(boolean, boolean)
   * 
   * @param isImmutable
   *        is the value for the {@link #isImmutable() immutable-flag}.
   * @param isStatic
   *        is the value for the {@link #isStatic() static-flag}.
   * @param isTransient
   *        is the value for the {@link #isTransient() transient-flag}.
   */
  public FieldModifiers(boolean isSystem, boolean isFinal, boolean isImmutable, boolean isStatic,
      boolean isTransient) {

    super(isSystem, isFinal);
    this.immutableFlag = isImmutable;
    this.staticFlag = isStatic;
    this.transientFlag = isTransient;
    validate();
  }

  /**
   * The constructor.
   * 
   * @param modifiers
   *        is the modifiers object to copy.
   */
  public FieldModifiers(FieldModifiersIF modifiers) {

    super(modifiers);
    this.immutableFlag = modifiers.isImmutable();
    this.staticFlag = modifiers.isStatic();
    this.transientFlag = modifiers.isTransient();
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

    if (this.transientFlag && !this.immutableFlag) {
      throw new IllegalArgumentException("Transient field must be immutable!");
    }
  }

  /**
   * @see net.sf.mmm.content.model.api.FieldModifiersIF#isImmutable()
   */
  public boolean isImmutable() {

    if (isTransient()) {
      return true;
    }
    return this.immutableFlag;
  }

  /**
   * @see net.sf.mmm.content.model.api.FieldModifiersIF#isTransient()
   */
  public boolean isTransient() {

    return this.transientFlag;
  }

  /**
   * @see net.sf.mmm.content.model.api.FieldModifiersIF#isStatic()
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
    if (!isSystem() && isImmutable()) {
      xmlWriter.writeAttribute(XML_ATR_ROOT_IMMUTABLE, StringUtil.TRUE);
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

}
