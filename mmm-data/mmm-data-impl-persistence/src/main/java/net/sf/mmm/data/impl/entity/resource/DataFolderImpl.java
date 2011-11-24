/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.entity.resource;

import javax.persistence.Entity;

import net.sf.mmm.data.api.entity.resource.DataFolder;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.base.entity.resource.AbstractDataFolder;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity(name = DataFolder.CLASS_TITLE)
@DataClassAnnotation(id = DataFolder.CLASS_ID)
public class DataFolderImpl extends AbstractDataFolder {

  /** UID for serialization. */
  private static final long serialVersionUID = -2071512131636950676L;

  /**
   * The constructor.
   */
  public DataFolderImpl() {

    super();
  }

}
