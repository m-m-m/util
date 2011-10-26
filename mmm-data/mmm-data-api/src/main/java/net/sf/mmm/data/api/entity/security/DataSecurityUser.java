/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.security;

import java.security.Principal;


/**
 * This is the interface for a content-object that represents a <em>user</em> of
 * the system.<br>
 * In order to interact with the system (the repository) you need to create a
 * <em>connection</em> as a specific {@link DataSecurityUser user} supplying its
 * {@link #getTitle() name} as <em>login</em>. Typically this requires an
 * <em>authentication</em> of the connections initiator (e.g. by verifying a
 * password).<br>
 * For each {@link DataSecurityOperation operation} performed on that connection the
 * associated {@link DataSecurityUser} needs to have sufficient
 * {@link DataSecurityPermission permissions} to do so.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataSecurityUser extends DataSecuityGroup, Principal {

  /**
   * This method is the same as {@link #getTitle()}.
   * 
   * {@inheritDoc}
   */
  String getName();

}
