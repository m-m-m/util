/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.repository;

import net.sf.mmm.data.api.datatype.MutableBlob;
import net.sf.mmm.data.resource.api.ContentFile;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentBlobStore {

  MutableBlob createBlob(SmartId id, ContentFile file);

  MutableBlob createBlob(SmartId id, int blobRevision);

  void destoryBlob(SmartId id, int blobRevision);

}
