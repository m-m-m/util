/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api.validator;

import net.sf.mmm.util.file.api.FileType;

/**
 * This annotation is used to annotate a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor property}
 * that has the type {@link java.io.File}. It allows to define a constraint for
 * the {@link #type()} or {@link #exists() existence} of the
 * {@link java.io.File}.<br/>
 * If this annotation is used for a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor property}
 * that has a {@link ValueConstraintContainer container}-type, then it applies
 * to all elements of that container.
 * 
 * @see ValueConstraintProcessor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public @interface ValueConstraintFile {

  /**
   * <code>true</code> if {@link #exists()} should be ignored,
   * <code>false</code> otherwise (default).
   */
  boolean ignoreExists() default false;

  /**
   * <code>true</code> if the {@link java.io.File} has to
   * {@link java.io.File#exists() exist} and be of the {@link #type() specified
   * type}. <code>false</code> if the should NOT {@link java.io.File#exists()
   * exist}.
   */
  boolean exists() default true;

  /**
   * The type of the file.
   */
  FileType type() default FileType.FILE;

}
