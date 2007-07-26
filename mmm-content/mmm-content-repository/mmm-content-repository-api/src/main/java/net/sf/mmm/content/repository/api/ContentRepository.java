/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.repository.api;

import net.sf.mmm.content.model.api.ContentModelService;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentRepository extends ContentResourceReadAccess, ContentResourceWriteAccess {

  ContentModelService getContentModel();
  
}
