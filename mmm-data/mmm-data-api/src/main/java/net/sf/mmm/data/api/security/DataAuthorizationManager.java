/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.security;

import net.sf.mmm.data.api.DataObjectView;
import net.sf.mmm.data.api.entity.security.DataSecurityOperation;
import net.sf.mmm.data.api.entity.security.DataSecurityUser;
import net.sf.mmm.security.api.DataRelatedAuthorizationManager;

/**
 * This is the interface for the authorization manager of the
 * {@link net.sf.mmm.data.api.repository.DataRepository}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataAuthorizationManager extends
    DataRelatedAuthorizationManager<DataSecurityUser, DataSecurityOperation, DataObjectView> {

  //
}
