/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import javax.persistence.Entity;

/**
 * This is a simple entity for testing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Entity
public class TestFooEntity extends JpaIntegerIdPersistenceEntity {

  /**
   * The constructor.
   */
  public TestFooEntity() {

    super();
  }

}
