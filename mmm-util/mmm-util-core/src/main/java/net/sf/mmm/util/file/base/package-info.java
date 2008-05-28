/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains public implementations of {@link java.io.File}-utilities.
 * <h2>File-Util Base</h2> 
 * This package contains public implementations that help to deal with 
 * {@link java.io.File}s and other related stuff.<br>
 * The {@link net.sf.mmm.util.file.base.FileAccessPermissions} represent the 
 * permissions of a file in a POSIX (Unix/Linux) filesystem and virtually 
 * implements the logic of 
 * {@link net.sf.mmm.util.file.base.FileAccessPermissions#createByUmask(int, boolean) umask}
 * and {@link net.sf.mmm.util.file.base.FileAccessPermissions#chmod(String) chmod}.<br>
 * Further there are filters like {@link net.sf.mmm.util.file.base.DirectoryFilter},
 * {@link net.sf.mmm.util.file.base.PlainFileFilter} and 
 * {@link net.sf.mmm.util.file.base.PatternFileFilter}.
 */
package net.sf.mmm.util.file.base;

