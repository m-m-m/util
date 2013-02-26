/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.repository;

import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.datatype.MutableBlob;
import net.sf.mmm.data.api.entity.resource.DataFile;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentBlobStore {

  MutableBlob createBlob(DataId id, DataFile file);

  MutableBlob createBlob(DataId id, int blobRevision);

  void destoryBlob(DataId id, int blobRevision);

}
