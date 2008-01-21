/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import net.sf.mmm.content.api.ContentException;
import net.sf.mmm.content.api.ContentObject;

/**
 * This is the interface used to {@link #set(ContentObject, Object) write} the
 * {@link ContentField field} of a {@link ContentObject content-object}.
 * According to the fields {@link ContentField#getModifiers() modifiers} the
 * field may be {@link FieldModifiers#isReadOnly() read-only}. Then the write
 * access will NOT be allowed and therefore fail.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentWriteAccessor {

  /**
   * This method sets the field in the <code>object</code> to the given
   * <code>value</code>.
   * 
   * @param object is where to write the fields value to.
   * @param value is the value of the field. May be <code>null</code>.
   * @throws ContentException if the operation fails. E.g. if the field is
   *         {@link FieldModifiers#isReadOnly() read-only}.
   */
  void set(ContentObject object, Object value) throws ContentException;

}
