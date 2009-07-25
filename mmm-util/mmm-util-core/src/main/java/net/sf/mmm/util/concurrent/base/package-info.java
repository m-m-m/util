/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the base-implementation for concurrent-util.
 * <h2>Concurrent-Util Base</h2> 
 * This package provides {@link SimpleExecutor} that executes all 
 * {@link java.lang.Runnable}s in a new {@link java.lang.Thread}. 
 * It is used by some other utils as fallback but can be replaced easily with a
 * full-fledged thread-pool as provided via {@link CachedThreadPoolExecutor}.
 * @see java.util.concurrent.Executors
 */
package net.sf.mmm.util.concurrent.base;

