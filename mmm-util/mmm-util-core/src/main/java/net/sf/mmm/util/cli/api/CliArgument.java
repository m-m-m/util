/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A {@link CliArgument} is used to annotate a
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface CliArgument {

  /** The maximum {@link #index()}. */
  Integer INDEX_MAX = Integer.valueOf(100);

  /** The minimum {@link #index()}. */
  Integer INDEX_MIN = Integer.valueOf(0);

  /**
   * The name of the argument used for help usage output.
   */
  String name();

  /**
   * The index of the argument relative to other {@link CliArgument arguments}
   * in the order of the command-line arguments. The value has to be in the
   * range from {@link #INDEX_MIN} to {@link #INDEX_MAX}. There should be no
   * other {@link CliArgument} with the same index and for each index from
   * {@link #INDEX_MIN} to this {@link #index() index} there should exist an
   * {@link CliArgument} annotation.
   */
  int index();

}
