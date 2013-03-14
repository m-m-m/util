/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This interface gives read access to the {@link #getCode() code} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract interface AttributeReadMessageCode {

  /**
   * This method gets the <em>code</em> that identifies the detailed type of this object. While
   * {@link AttributeReadUuid#getUuid() UUID} or {@link AttributeReadId#getId() ID} are typically unique per
   * instance of an object the code is unique for all instances of the exact same kind. So e.g. a particular
   * kind of {@link net.sf.mmm.util.nls.api.NlsThrowable exception} or
   * {@link net.sf.mmm.util.validation.api.ValidationFailure} can be identified by its code. A simple generic
   * implementation may return the classname or the key of the NLS message. However, the code should remain
   * stable after refactoring (so at least after the rename the previous code should be returned as
   * {@link String} literal). This code may be used as a compact identifier to reference the related problem
   * or information as well as for automatic tests (especially of error situations) that should remain stable
   * even if the message text gets improved or the locale is unknown.
   * 
   * @see Throwable#getMessage()
   * @see net.sf.mmm.util.nls.api.NlsThrowable
   * @see net.sf.mmm.util.lang.api.Message
   * 
   * @return the message text.
   */
  String getCode();

}
