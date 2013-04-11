/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pool.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a {@link Pool} of <code>byte[]</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@ComponentSpecification
public interface ByteArrayPool extends Pool<byte[]> {

  // nothing to add, just bound generic

}
