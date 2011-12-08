/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.resource;

import java.util.List;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface for a mutable {@link DataFolderView folder}.<br/>
 * See
 * {@link net.sf.mmm.data.api.repository.DataRepository#move(DataEntityResource, DataFolder)}
 * for details how to add or remove {@link #getChildren() children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataFileView.CLASS_ID, title = DataFileView.CLASS_TITLE)
public interface DataFolder extends DataFolderView, DataEntityResource {

  /**
   * {@inheritDoc}
   */
  DataEntityResource getChild(String title);

  /**
   * {@inheritDoc}
   */
  List<? extends DataEntityResource> getChildren();

}
