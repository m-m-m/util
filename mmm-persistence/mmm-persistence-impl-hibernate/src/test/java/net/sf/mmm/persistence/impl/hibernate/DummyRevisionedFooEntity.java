/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate;

import javax.persistence.Entity;

import net.sf.mmm.persistence.impl.jpa.JpaRevisionedPersistenceEntity;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Entity
// @Audited
public class DummyRevisionedFooEntity extends JpaRevisionedPersistenceEntity<Long> {

  /** @see #getValue() */
  private String value;

  /**
   * The constructor.
   */
  public DummyRevisionedFooEntity() {

    super();
  }

  /**
   * @return the value
   */
  public String getValue() {

    return this.value;
  }

  /**
   * @param value is the value to set
   */
  public void setValue(String value) {

    this.value = value;
  }

}
