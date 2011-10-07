/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.api.access;

import net.sf.mmm.content.reflection.api.ContentField;

/**
 * This interface allows to {@link #getContentField(String) get} a
 * {@link ContentField} by its {@link ContentField#getTitle() name}.
 * 
 * @param <CLASS> is the generic type for the bound of
 *        {@link ContentField#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentFieldReadAccessByName<CLASS> {

  /**
   * This method gets the {@link ContentField} for the given <code>id</code>.
   * 
   * @param name is the {@link ContentField#getTitle() name} of the requested
   *        field.
   * @return the content field for the given ID or <code>null</code> if it does
   *         NOT exist.
   */
  ContentField<? extends CLASS, ?> getContentField(String name);

}
