/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api.validator;

/**
 * This annotation is used to annotate a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor property}
 * that shall NOT be <code>null</code>. <br/>
 * This annotation applies to any type including
 * {@link ValueConstraintContainer containers} and NOT to elements of a
 * container.
 * 
 * @see ValueConstraintProcessor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public @interface ValueConstraintNotNull {

}
