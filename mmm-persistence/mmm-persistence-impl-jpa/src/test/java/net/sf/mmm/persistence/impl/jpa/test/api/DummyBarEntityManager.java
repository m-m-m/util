/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.test.api;

import net.sf.mmm.persistence.api.PersistenceEntityManager;
import net.sf.mmm.persistence.impl.jpa.test.impl.DummyBarEntityImpl;

/**
 * This is the interface for the {@link PersistenceEntityManager} responsible
 * for the entity {@link DummyBarEntityImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DummyBarEntityManager extends PersistenceEntityManager<Integer, DummyBarEntity> {

  //

}
