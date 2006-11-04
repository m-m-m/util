/* $ Id: $ */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.model.api.ModifiersIF;
import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;

/**
 * This is the base implementation of the {@link ModifiersIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractModifiers implements ModifiersIF {

  /** @see #isSystem() */
  private final boolean systemFlag;

  /** @see #isFinal() */
  private final boolean finalFlag;

  /**
   * The constructor.
   * 
   * @param isSystem
   *        is the value for the {@link #isSystem() system-flag}.
   * @param isFinal
   *        is the value for the {@link #isFinal() final-flag}.
   */
  public AbstractModifiers(boolean isSystem, boolean isFinal) {

    super();
    this.systemFlag = isSystem;
    this.finalFlag = isFinal;
  }

  /**
   * The constructor.
   * 
   * @param modifiers
   *        is the modifiers object to copy.
   */
  public AbstractModifiers(ModifiersIF modifiers) {

    this(modifiers.isSystem(), modifiers.isFinal());
  }

  /**
   * @see net.sf.mmm.content.model.api.ModifiersIF#isSystem()
   */
  public boolean isSystem() {

    return this.systemFlag;
  }

  /**
   * @see net.sf.mmm.content.model.api.ModifiersIF#isFinal()
   */
  public boolean isFinal() {

    return this.finalFlag;
  }

  /**
   * @param xmlWriter
   * @throws XmlException
   * @see net.sf.mmm.util.xml.api.XmlSerializable#toXml(XmlWriter)
   */
  protected void setXmlAttributes(XmlWriter xmlWriter) throws XmlException {

    if (isSystem()) {
      xmlWriter.writeAttribute(XML_ATR_ROOT_SYSTEM, StringUtil.TRUE);
    }
    if (isFinal()) {
      xmlWriter.writeAttribute(XML_ATR_ROOT_FINAL, StringUtil.TRUE);
    }
  }

}
