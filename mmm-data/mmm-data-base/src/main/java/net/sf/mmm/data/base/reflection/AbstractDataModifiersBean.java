/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import net.sf.mmm.data.api.reflection.DataModifiers;
import net.sf.mmm.util.lang.api.Datatype;

/**
 * This is the base implementation of the {@link DataModifiers} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractDataModifiersBean implements DataModifiers, Datatype<String> {

  /** UID for serialization. */
  private static final long serialVersionUID = 3521732876191875621L;

  /** The character representing the {@link #isSystem() system flag}. */
  protected static final char CHARACTER_SYSTEM = 'Y';

  /** The character representing the {@link #isFinal() final flag}. */
  protected static final char CHARACTER_FINAL = 'F';

  /** @see #isSystem() */
  @XmlAttribute(name = XML_ATR_SYSTEM)
  private boolean systemFlag;

  /** @see #isFinal() */
  @XmlAttribute(name = XML_ATR_FINAL)
  private boolean finalFlag;

  /**
   * The constructor.
   */
  public AbstractDataModifiersBean() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param isSystem is the value for the {@link #isSystem() system-flag}.
   * @param isFinal is the value for the {@link #isFinal() final-flag}.
   */
  public AbstractDataModifiersBean(boolean isSystem, boolean isFinal) {

    super();
    this.systemFlag = isSystem;
    this.finalFlag = isFinal;
  }

  /**
   * The constructor.
   * 
   * @param contentModifiers is the modifiers object to copy.
   */
  public AbstractDataModifiersBean(DataModifiers contentModifiers) {

    this(contentModifiers.isSystem(), contentModifiers.isFinal());
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSystem() {

    return this.systemFlag;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isFinal() {

    return this.finalFlag;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return getValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getTitle();
  }

}
