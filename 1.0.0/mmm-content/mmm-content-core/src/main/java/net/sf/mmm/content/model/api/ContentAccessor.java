/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

/**
 * This is the interface for an accessor used to read or write a
 * {@link ContentField field} of a
 * {@link net.sf.mmm.content.api.ContentObject content-object}. According to
 * the fields {@link ContentField#getModifiers() modifiers} the field may be
 * {@link FieldModifiers#isReadOnly() read-only}. Then the write access will
 * NOT be allowed and therefore fail.
 * 
 * @see ContentField#getAccessor()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentAccessor extends ContentReadAccessor, ContentWriteAccessor {

}
