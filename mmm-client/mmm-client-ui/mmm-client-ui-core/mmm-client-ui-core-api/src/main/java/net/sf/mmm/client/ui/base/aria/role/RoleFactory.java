/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.aria.role;

import net.sf.mmm.client.ui.api.aria.role.Role;

/**
 * This is the interface for a factory used to {@link #createRole(Class) create} instances of {@link Role}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RoleFactory {

  /**
   * This method creates a new instance of the given {@link Role}.
   * 
   * @param <ROLE> is the generic type of <code>roleInterface</code>.
   * @param roleInterface is the interface of the {@link Role} to create. Must not be an abstract {@link Role}
   *        (see javadoc, e.g. {@link net.sf.mmm.client.ui.api.aria.role.RoleStructure}).
   * @return the new {@link Role} instances.
   */
  <ROLE extends Role> ROLE createRole(Class<ROLE> roleInterface);

}
