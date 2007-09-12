/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api.access;

import net.sf.mmm.content.model.api.ContentClass;

/**
 * This interface allows to {@link #getContentClass(String) get} a
 * {@link ContentClass} by its {@link ContentClass#getName() name}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentClassReadAccessByName {

  /**
   * This method gets the content class for the given name.
   * 
   * @param name is the {@link ContentClass#getName() name} of the requested
   *        class.
   * @return the {@link ContentClass} for the given name or <code>null</code>
   *         if no such class exist.
   */
  ContentClass getContentClass(String name);

}
