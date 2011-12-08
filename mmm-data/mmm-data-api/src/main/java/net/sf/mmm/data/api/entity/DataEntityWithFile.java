/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity;

import net.sf.mmm.data.api.entity.resource.DataFile;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the mutable interface for {@link DataEntityWithFileView}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataEntityWithFileView.CLASS_ID, title = DataEntityWithFileView.CLASS_TITLE)
public interface DataEntityWithFile extends DataEntityWithFileView, DataEntity {

  /**
   * {@inheritDoc}
   */
  DataFile getFile();

}
