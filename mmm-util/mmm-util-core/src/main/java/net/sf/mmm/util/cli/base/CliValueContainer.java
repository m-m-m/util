/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import net.sf.mmm.util.cli.api.CliConstraintInvalidException;

/**
 * A {@link CliValueContainer} is a simple container for the {@link #getValue()
 * value} of a {@link net.sf.mmm.util.cli.api.CliOption option} or a
 * {@link net.sf.mmm.util.cli.api.CliArgument argument}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface CliValueContainer {

  /**
   * This method validates the {@link #getValue() value}. If the
   * {@link #getValue() value} is invalid, an exception will be thrown.
   * 
   * @throws CliConstraintInvalidException if the value is invalid according to
   *         a {@link CliParameterContainer#getConstraint() constraint}.
   */
  void validate() throws CliConstraintInvalidException;

  /**
   * This method gets the value of a {@link net.sf.mmm.util.cli.api.CliOption
   * option} or a {@link net.sf.mmm.util.cli.api.CliArgument argument}.
   * 
   * @return the value.
   */
  Object getValue();

  /**
   * This method sets the {@link #getValue() value} given as string. For the
   * parameter-types array, collection or map this method may be called multiple
   * times, one for each item.
   * 
   * @param argument is the argument from the commandline containing the value.
   */
  void setValue(String argument);

  /**
   * @return <code>true</code> if this is a container for a value of the type
   *         array, {@link java.util.Collection} or {@link java.util.Map} -
   *         <code>false</code> otherwise.
   */
  boolean isArrayMapOrCollection();

}
