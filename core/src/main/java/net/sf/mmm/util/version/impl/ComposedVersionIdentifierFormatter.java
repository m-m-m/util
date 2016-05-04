/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.base.ComposedFormatter;
import net.sf.mmm.util.version.api.VersionIdentifier;
import net.sf.mmm.util.version.api.VersionIdentifierFormatter;

/**
 * This is the default implementation of the {@link VersionIdentifierFormatter} interface. It extends
 * {@link ComposedFormatter} to solve the problem by composing sub-formatters.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ComposedVersionIdentifierFormatter extends ComposedFormatter<VersionIdentifier>
    implements VersionIdentifierFormatter {

  /**
   * The constructor.
   *
   * @param subFormatters are the {@link Formatter}s to delegate to in the given order.
   */
  @SafeVarargs
  public ComposedVersionIdentifierFormatter(Formatter<VersionIdentifier>... subFormatters) {

    super(subFormatters);
  }

}
