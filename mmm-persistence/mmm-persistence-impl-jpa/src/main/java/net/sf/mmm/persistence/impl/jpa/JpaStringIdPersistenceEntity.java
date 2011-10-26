/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * This class extends {@link JpaPersistenceEntity} with a {@link #getId() ID} of
 * the type {@link String}. The {@link #getId() ID} will NOT be
 * {@link javax.persistence.GeneratedValue generated}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class JpaStringIdPersistenceEntity extends JpaPersistenceEntity<String> {

  /**
   * The constructor.
   */
  public JpaStringIdPersistenceEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Id
  public String getId() {

    return super.getId();
  }

  /**
   * This method sets the {@link #getId() primary key}.<br>
   * <b>ATTENTION:</b><br>
   * This method is only intended for internal usage. It should NOT be set
   * manually outside this persistence-layer.
   * 
   * @param id is the ID to set.
   */
  @Override
  public void setId(String id) {

    super.setId(id);
  }

}
