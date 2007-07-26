/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.repository.base;

import net.sf.mmm.content.resource.api.ContentFile;
import net.sf.mmm.content.value.api.MutableBlob;
import net.sf.mmm.content.value.base.SmartId;


/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentBlobStore {

  MutableBlob createBlob(SmartId id, ContentFile file);

  MutableBlob createBlob(SmartId id, int blobRevision);

  void destoryBlob(SmartId id, int blobRevision);
  
}
