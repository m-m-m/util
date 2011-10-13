/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import net.sf.mmm.content.reflection.api.ContentModifiers;

/**
 * This is the base implementation of the {@link ContentModifiers} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractContentModifiersBean implements ContentModifiers {

  /** UID for serialization. */
  private static final long serialVersionUID = 3521732876191875621L;

  /** @see #isSystem() */
  @XmlAttribute(name = XML_ATR_SYSTEM)
  private boolean systemFlag;

  /** @see #isFinal() */
  @XmlAttribute(name = XML_ATR_FINAL)
  private boolean finalFlag;

  /**
   * The constructor.
   */
  public AbstractContentModifiersBean() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param isSystem is the value for the {@link #isSystem() system-flag}.
   * @param isFinal is the value for the {@link #isFinal() final-flag}.
   */
  public AbstractContentModifiersBean(boolean isSystem, boolean isFinal) {

    super();
    this.systemFlag = isSystem;
    this.finalFlag = isFinal;
  }

  /**
   * The constructor.
   * 
   * @param contentModifiers is the modifiers object to copy.
   */
  public AbstractContentModifiersBean(ContentModifiers contentModifiers) {

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

}
