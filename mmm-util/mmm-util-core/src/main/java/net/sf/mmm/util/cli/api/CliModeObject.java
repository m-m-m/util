/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.util.Set;

import net.sf.mmm.util.cli.base.CliModeContainer;

/**
 * This is a container for a {@link CliMode} together with additional associated
 * information.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public interface CliModeObject {

  /**
   * This method gets the actual {@link CliMode}.
   * 
   * @return the {@link CliMode}.
   */
  CliMode getMode();

  /**
   * This method gets the {@link Class} that was annotated with the
   * {@link #getMode() mode}.
   * 
   * @return the annotated {@link Class}.
   */
  Class<?> getAnnotatedClass();

  /**
   * This method gets the {@link Set} of {@link CliModeContainer modes} that are
   * {@link CliMode#parentIds() extended} by this {@link #getMode() mode}.
   * 
   * @return the extended {@link CliModeContainer modes}.
   */
  Set<CliModeContainer> getExtendedModes();

}
