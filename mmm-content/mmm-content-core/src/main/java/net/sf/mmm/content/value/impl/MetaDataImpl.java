/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.impl;

import java.util.Map;

import net.sf.mmm.content.value.api.MetaData;

/**
 * This is a simple implementation of the {@link MetaData} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MetaDataImpl implements MetaData {

  /** UID for serialization. */
  private static final long serialVersionUID = -2350369253408863852L;
  
  /** The actual metadata. */
  private Map<String, Object> metadata;

  /**
   * The constructor.
   */
  public MetaDataImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Object getValue(String key) {

    return this.metadata.get(key);
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String key, Object newValue) {

    this.metadata.put(key, newValue);
  }

  /**
   * {@inheritDoc}
   */
  public Object removeValue(String key) {

    return this.metadata.remove(key);
  }

}
