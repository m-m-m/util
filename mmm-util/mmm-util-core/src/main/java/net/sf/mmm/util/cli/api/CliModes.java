/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

/**
 * This annotation is used to annotate a CLI-Class with multiple {@link CliMode}
 * -annotations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public @interface CliModes {

  /**
   * The array with all annotated {@link CliMode}s.
   */
  CliMode[] value();

}
