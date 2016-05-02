/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

import java.util.UUID;

/**
 * This interface gives read access to the {@link #getUuid() UUID} of an object.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract interface AttributeReadUuid {

  /**
   * This method gets the {@link UUID} of this object. When the object is created, a {@link UUID} is
   * generated. In case the object is created from another object that already {@link #getUuid() has a UUID},
   * the existing {@link UUID} will be used (e.g. for chained
   * {@link net.sf.mmm.util.exception.api.NlsThrowable exceptions}). <br>
   * The {@link UUID} will appear in a
   * {@link net.sf.mmm.util.exception.api.NlsThrowable#printStackTrace(java.util.Locale, Appendable)
   * stacktrace} but NOT in the {@link net.sf.mmm.util.exception.api.NlsThrowable#getMessage() message}. It
   * will therefore be written to log-files if the {@link net.sf.mmm.util.exception.api.NlsThrowable} is
   * logged. If you supply the {@link UUID} to the end-user in an error panel or popup (see
   * {@link net.sf.mmm.util.lang.api.Message}), he can provide it with the problem report so an administrator
   * or software developer can easily find the stacktrace in the log-files.
   *
   * @return the {@link UUID} of this object. It may be {@code null} if this feature is NOT supported by
   *         the type of this object or turned of (it is turned on by default).
   */
  UUID getUuid();

}
