/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.impl;

import net.sf.mmm.content.value.base.SmartId;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.content.value.base.SmartIdManager SmartIdManager} interface
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class StaticSmartIdManager extends AbstractSmartIdManager {

  /** @see #createUniqueId(int) */
  private static long objectIdCounter = SmartId.OID_MINIMUM_CUSTOM;

  /** @see #createUniqueClassId() */
  private static int classIdCounter = SmartId.CLASS_ID_MINIMUM_CUSTOM;
  
  /** @see #createUniqueFieldId() */
  private static int fieldIdCounter = SmartId.FIELD_ID_MINIMUM_CUSTOM;
  
  /**
   * The constructor.
   */
  public StaticSmartIdManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public SmartId createUniqueId(int classId) {

    return getId(objectIdCounter++, classId);
  }

  /**
   * {@inheritDoc}
   */
  public SmartId createUniqueClassId() {

    return getClassId(classIdCounter++);
  }

  /**
   * {@inheritDoc}
   */
  public SmartId createUniqueFieldId() {

    return getFieldId(fieldIdCounter++);
  }

}
