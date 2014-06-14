/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence;

import net.sf.mmm.util.nls.api.NlsBundle;

/**
 * This interface holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsBundlePersistenceRoot extends NlsBundle {

  // /**
  // * @see net.sf.mmm.persistence.base.RevisionedPersistenceEntityWithoutRevisionSetterException
  // *
  // * @param type is the {@link Class} reflecting the entity.
  // * @return the {@link NlsMessage}
  // */
  // @NlsBundleMessage("The revisioned entity \"{type}\" has no setter method for the revision property (setRevision(Number))!")
  // NlsMessage errorRevisionedEntityWithoutSetter(@Named("type") Type type);

}
