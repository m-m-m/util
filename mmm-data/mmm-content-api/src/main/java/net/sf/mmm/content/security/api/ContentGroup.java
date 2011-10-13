/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.security.api;

import java.util.List;

/**
 * This is the interface for a group. Such object is associated with a set of
 * {@link #getRules() rules} that determines if a {@link ContentUser} owning the
 * group is allowed to perform a specific {@link ContentAction operation}.
 * 
 * @see java.security.acl.Group
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentGroup extends ContentSecurityObject {

  /**
   * This method gets the parent groups this group inherits from.
   * 
   * @return
   */
  List<ContentGroup> getParentGroups();

  /**
   * This method gets the rules of this group.
   * 
   * @return the list of rules.
   */
  List<ContentRule> getRules();

  /**
   * 
   * @param group
   * @return
   */
  boolean isSubGroup(ContentGroup group);

}
