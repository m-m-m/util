/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.reflection.api.access;

import net.sf.mmm.data.api.ContentException;
import net.sf.mmm.data.api.ContentObject;
import net.sf.mmm.util.nls.api.NlsClassCastException;
import net.sf.mmm.util.nls.api.ReadOnlyException;

/**
 * This is the interface for an accessor used to {@link #getFieldValue(Object)
 * read} or {@link #setFieldValue(Object, Object) write} a
 * {@link net.sf.mmm.data.reflection.api.ContentField field} of a
 * {@link net.sf.mmm.data.api.ContentObject content-object}. According to the
 * {@link net.sf.mmm.data.reflection.api.ContentField#getContentModifiers()
 * modifiers} the field may be
 * {@link net.sf.mmm.data.reflection.api.ContentFieldModifiers#isReadOnly()
 * read-only}. Then the write access will NOT be allowed and therefore fail.
 * 
 * @param <CLASS> is the generic type for the bound of
 *        {@link net.sf.mmm.data.reflection.api.ContentField#getJavaClass()}.
 * @param <FIELD> is the generic type of the value reflected by this field.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentFieldAccessor<CLASS extends ContentObject, FIELD> {

  /**
   * This method gets the value of the field from the given <code>object</code>.
   * 
   * @param object is where to read the field value from.
   * @return the value of the field. May be <code>null</code>.
   * @throws ContentException if the operation fails.
   */
  FIELD getFieldValue(CLASS object) throws ContentException;

  /**
   * This method sets the field in the <code>object</code> to the given
   * <code>value</code>. The field must NOT be
   * {@link net.sf.mmm.data.reflection.api.ContentFieldModifiers#isReadOnly()
   * read-only}.
   * 
   * @param object is where to write the fields value to. Will be ignored if the
   *        field is
   *        {@link net.sf.mmm.data.reflection.api.ContentFieldModifiers#isStatic()
   *        static}.
   * @param value is the value of the field. May be <code>null</code>.
   * @throws ReadOnlyException if the field is
   *         {@link net.sf.mmm.data.reflection.api.ContentFieldModifiers#isReadOnly()
   *         read-only}.
   * @throws NlsClassCastException if the given <code>value</code> is not
   *         compatible with the
   *         {@link net.sf.mmm.data.reflection.api.ContentField#getFieldType()
   *         type} of the field.
   */
  // * @throws ContentException if the operation failed. This can have one of
  // the
  // * following reasons:
  // * <ul>
  // * <li>the object does not have a
  // * {@link net.sf.mmm.content.model.api.ContentField field} with the
  // * given
  // * <code>{@link net.sf.mmm.content.model.api.ContentField#getName()
  // fieldName}</code>
  // * . See
  // * {@link net.sf.mmm.content.model.api.ContentFieldNotExistsException
  // * FieldNotExistsException}</li>
  // * <li>If {@link net.sf.mmm.content.security.api.ContentUser you} do
  // * NOT have {@link net.sf.mmm.content.security.api.ContentRule
  // * permission} to do so. See
  // * {@link net.sf.mmm.content.security.api.PermissionDeniedException
  // * PermissionDeniedException}.</li>
  // * </ul>
  void setFieldValue(CLASS object, FIELD value) throws ReadOnlyException, NlsClassCastException;

}
