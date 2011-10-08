/* $Id: $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.envers.Audited;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Entity
@Audited
public class DummyRevisionedFooEntity extends EnversPersistenceEntity<Long> {

  /** @see #getId() */
  private Long id;

  /** @see #getValue() */
  private String value;

  /**
   * The constructor.
   */
  public DummyRevisionedFooEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Id
  @GeneratedValue
  public Long getId() {

    return this.id;
  }

  /**
   * This method sets the {@link #getId() primary key}.<br>
   * <b>ATTENTION:</b><br>
   * This method is only intended for internal usage. It should NOT be set
   * manually outside this persistence-layer.
   * 
   * @param id is the ID to set.
   */
  public void setId(Long id) {

    this.id = id;
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
