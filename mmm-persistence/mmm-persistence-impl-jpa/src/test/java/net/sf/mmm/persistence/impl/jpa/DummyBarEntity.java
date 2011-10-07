/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Version;

/**
 * This is a simple entity for testing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Entity
public class DummyBarEntity extends JpaIntegerIdPersistenceEntity {

  private Date timestamp;

  /** @see #getValue() */
  private String value;

  /**
   * The constructor.
   */
  public DummyBarEntity() {

    super();
  }

  /**
   * @return the bar
   */
  public String getValue() {

    return this.value;
  }

  /**
   * @param bar is the bar to set
   */
  public void setValue(String bar) {

    this.value = bar;
  }

  /**
   * @return the timestamp
   */
  @Version
  public Date getTimestamp() {

    return this.timestamp;
  }

  /**
   * @param timestamp is the timestamp to set
   */
  public void setTimestamp(Date timestamp) {

    this.timestamp = timestamp;
  }

}
