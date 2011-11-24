/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.entity.resource;

import javax.persistence.Entity;

import net.sf.mmm.data.api.entity.resource.DataFile;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.base.entity.resource.AbstractDataFile;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity(name = DataFile.CLASS_TITLE)
@DataClassAnnotation(id = DataFile.CLASS_ID)
public class DataFileImpl extends AbstractDataFile {

  /** UID for serialization. */
  private static final long serialVersionUID = -3147586344019126290L;

  /**
   * The constructor.
   */
  public DataFileImpl() {

    super();
  }

}
