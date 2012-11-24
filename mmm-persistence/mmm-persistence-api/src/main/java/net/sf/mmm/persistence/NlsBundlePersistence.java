/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence;

import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for util-persistence.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundlePersistence extends AbstractResourceBundle {

  /** @see net.sf.mmm.persistence.base.RevisionedPersistenceEntityWithoutRevisionSetterException */
  public static final String ERR_REVISIONED_ENTITY_WITHOUT_REVISION_SETTER = "The "
      + "entity implementation \"{type}\" has no setter method for the revision property (setRevision(Number))!";

}
