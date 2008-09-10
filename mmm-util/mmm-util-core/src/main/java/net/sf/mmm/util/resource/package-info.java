/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains utilities for dealing with 
 * {@link net.sf.mmm.util.resource.api.DataResource}s.
 * <h2>Resource Utilities</h2>
 * A {@link net.sf.mmm.util.resource.api.DataResource} represents a source of data.
 * Unlike a {@link java.io.File} it abstracts from the underlying source that
 * can be the regular filesystem (see {@link net.sf.mmm.util.resource.base.FileResource}), 
 * as well as the classpath (see {@link net.sf.mmm.util.resource.base.ClasspathResource})
 * the network (see {@link net.sf.mmm.util.resource.base.UrlResource}), etc.
 */
package net.sf.mmm.util.resource;

