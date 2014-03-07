/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.test.api;

import net.sf.mmm.util.entity.api.GenericEntity;

/**
 * This is the read only interface for the {@link DummyBarEntity}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public interface DummyBarEntityView extends GenericEntity<Long> {

  /** The name of the property {@link #getValue() value}. */
  String PROPERTY_VALUE = "value";

  /**
   * @return the value or <code>null</code>.
   */
  String getValue();

}
