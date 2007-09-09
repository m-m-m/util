/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the interface for the factory to create
 * {@link net.sf.mmm.content.model.api.ContentReflectionObject reflection-objects}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentReflectionFactory {

  /**
   * This method creates a new content-class.
   * 
   * @param id is the {@link AbstractContentClass#getId() id}.
   * @return the created class.
   */
  AbstractContentClass createNewClass(SmartId id);

  /**
   * This method creates a new content-field.
   * 
   * @param id is the {@link AbstractContentField#getId() id}.
   * @return the created field.
   */
  AbstractContentField createNewField(SmartId id);

}
