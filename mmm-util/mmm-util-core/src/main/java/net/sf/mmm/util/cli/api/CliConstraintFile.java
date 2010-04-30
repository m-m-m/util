/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.mmm.util.file.api.FileType;

/**
 * This annotation is used to annotate a property (field or setter) of the type
 * {@link java.io.File} that is also annotated with {@link CliOption} or
 * {@link CliArgument}. It allows to define a constraint for the {@link #type()}
 * or {@link #exists() existence} of the {@link java.io.File}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface CliConstraintFile {

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
