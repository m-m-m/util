/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for utilities that help to load 
 * {@link net.sf.mmm.util.resource.api.DataResource resources} from arbitrary 
 * sources.
 * <a name="documentation"/><h2>Resource-Util API</h2>
 * A {@link net.sf.mmm.util.resource.api.DataResource} represents a source of data.
 * Unlike a {@link java.io.File} it abstracts from the underlying source that
 * can be the regular filesystem (see {@link net.sf.mmm.util.resource.base.FileResource}), 
 * as well as the classpath (see {@link net.sf.mmm.util.resource.base.ClasspathResource})
 * the network (see {@link net.sf.mmm.util.resource.base.UrlResource}), etc.<br>
 * The {@link net.sf.mmm.util.resource.api.DataResourceFactory} allows to create
 * {@link net.sf.mmm.util.resource.api.DataResource}s from any URI given as 
 * string (e.g. configured by the end-user).
 */
package net.sf.mmm.util.resource.api;

