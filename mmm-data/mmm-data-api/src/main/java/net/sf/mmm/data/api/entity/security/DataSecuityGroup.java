/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.security;

import java.util.List;


/**
 * This is the interface for a group. Such object is associated with a set of
 * {@link #getRules() rules} that determines if a {@link DataSecurityUser}
 * owning the group is allowed to perform a specific {@link DataSecurityOperation
 * operation}.
 * 
 * @see java.security.acl.Group
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataSecuityGroup extends DataSecurityObject {

  /**
   * This method gets the parent groups this group inherits from.
   * 
   * @return the list of parent groups.
   */
  List<DataSecuityGroup> getParentGroups();

  /**
   * This method gets the rules of this group.
   * 
   * @return the list of rules.
   */
  List<DataSecurityPermission> getRules();

  /**
   * This method determines if this {@link DataSecuityGroup} is a descendant of
   * the given <code>group</code>.
   * 
   * @param group
   * @return
   */
  boolean isDescendant(DataSecuityGroup group);

}
