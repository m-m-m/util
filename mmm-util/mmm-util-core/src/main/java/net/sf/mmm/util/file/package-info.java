/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides utilities for dealing with files.
 * <h2>File Utilities</h2> 
 * This package contains utilities that allow easy and efficient operations 
 * related to {@link java.io.File}. The {@link net.sf.mmm.util.file.FileUtil} 
 * allows to copy or delete files and directories (recursively), to collect 
 * files that match a given path-pattern and many other useful tasks.<br>
 * The {@link net.sf.mmm.util.file.base.FileAccessPermissions} represent the 
 * permissions of a file in a POSIX (Unix/Linux) filesystem and virtually 
 * implements the logic of 
 * {@link net.sf.mmm.util.file.base.FileAccessPermissions#createByUmask(int, boolean) umask}
 * and {@link net.sf.mmm.util.file.base.FileAccessPermissions#chmod(String) chmod}.
 */
package net.sf.mmm.util.file;

