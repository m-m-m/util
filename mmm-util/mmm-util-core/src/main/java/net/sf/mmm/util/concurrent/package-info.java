/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides utilities for concurrent programming.
 * <h2>Concurrent Utilities</h2> 
 * Java provides great utilities for concurrent programming in the package 
 * {@link java.util.concurrent}. Therefore we do NOT reinvent the wheel here.
 * You find a dummy implementation of {@link java.util.concurrent.Executor} that
 * executes all {@link java.lang.Runnable}s in a new {@link java.lang.Thread}. 
 * It is used by some other utils as fallback but can be replaced easily with a
 * full-fledged thread-pool as provided via {@link java.util.concurrent.Executors}.
 */
package net.sf.mmm.util.concurrent;

