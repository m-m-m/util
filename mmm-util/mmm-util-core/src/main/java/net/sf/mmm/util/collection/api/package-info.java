/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains the API for utilities that help to dealing with {@link java.util.Collections}.
 * <h2>Collection-Util API</h2>
 * This package contains interfaces for factories (especially 
 * {@link net.sf.mmm.util.collection.api.CollectionFactory} and 
 * {@link net.sf.mmm.util.collection.api.MapFactory}) of the various 
 * containers offered by the JDK (see {@link java.util}). For each factory 
 * there is a default implementation accessible via the interface.<br>
 * All factories are bundled together via the 
 * {@link net.sf.mmm.util.collection.api.CollectionFactoryManager} that allows 
 * to create instances of {@link java.util.Collections} and 
 * {@link java.util.Map}s in a generic way.<br>
 * Finally the {@link net.sf.mmm.util.collection.base.AbstractIterator} makes it 
 * easier to implement read-only custom {@link java.util.Iterator}s.
 */
package net.sf.mmm.util.collection.api;

