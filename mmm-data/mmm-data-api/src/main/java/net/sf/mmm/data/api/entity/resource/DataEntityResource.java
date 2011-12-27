/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.resource;

import net.sf.mmm.data.api.DataNode;
import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface for a mutable {@link DataEntityResourceView entity
 * resource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataEntityResourceView.CLASS_ID, title = DataEntityResourceView.CLASS_TITLE)
public interface DataEntityResource extends DataEntityResourceView, DataEntity,
    DataNode<DataFolderView, DataFolder> {

  // nothing to add

}
