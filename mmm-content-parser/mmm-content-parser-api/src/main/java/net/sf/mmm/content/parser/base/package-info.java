/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains the base-implementation of the 
 * {@link net.sf.mmm.content.parser.api content-parser API}.
 * <h2>Content-Parser Base</h2>
 * This package contains the base-implementations that helps to implement
 * the API.<br/>
 * {@link net.sf.mmm.content.parser.base.AbstractContentParser} implements 
 * {@link net.sf.mmm.content.parser.base.LimitBufferSize} so the memory footprint
 * of  {@link net.sf.mmm.content.parser.api.ContentParser}-implementations
 * can be limited. This is an important feature if you want to parse large
 * sets of content without getting an {@link java.lang.OutOfMemoryError}.
 */
package net.sf.mmm.content.parser.base;

