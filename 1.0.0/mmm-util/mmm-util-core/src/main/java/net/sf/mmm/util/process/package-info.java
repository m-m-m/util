/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains utilities for dealing with {@link java.lang.Process}es.
 * <h2>Process Utilities</h2>
 * Dealing with {@link java.lang.Process} is NOT an easy task. The streams for
 * <code>stdin</code>, <code>stdout</code>, and <code>stderr</code> need to be 
 * transferred asynchronously. Creating a pipe is the typical task and is easily
 * done with a shell like <code>bash</code>. However solving this with java is
 * quite a little challenge if you do this from scratch. Even worse there is a 
 * bug in java when creating processes via {@link java.lang.Runtime} which 
 * causes that sub-processes are NOT terminated and keep running if a 
 * {@link java.lang.Process} is {@link java.lang.Process#destroy() destroyed}.<br/>
 * This package prevents you from those headaches by providing utilities that 
 * make it easier to deal with {@link java.lang.Process}es.<br/>
 * If you want to use this in a server application, you can inject an 
 * {@link java.util.concurrent.Executor} providing a thread-pool.
 */
package net.sf.mmm.util.process;

