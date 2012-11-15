/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.test.api;

/**
 * This is the read/write interface for {@link net.sf.mmm.persistence.impl.jpa.test.impl.DummyBarEntityImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DummyBarEntity extends DummyBarEntityView {

  /**
   * @param value is the value to set.
   */
  void setValue(String value);

}
