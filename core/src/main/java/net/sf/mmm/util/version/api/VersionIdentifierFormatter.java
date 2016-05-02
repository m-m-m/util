/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.api;

import net.sf.mmm.util.lang.api.Formatter;

/**
 * This is the interface used to format a {@link VersionIdentifier}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface VersionIdentifierFormatter extends Formatter<VersionIdentifier> {

  /**
   * {@inheritDoc}
   */
  @Override
  String format(VersionIdentifier value);

}
