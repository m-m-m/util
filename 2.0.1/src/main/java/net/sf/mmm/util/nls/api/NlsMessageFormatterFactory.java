/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface to create an {@link NlsFormatter}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@ComponentSpecification
public interface NlsMessageFormatterFactory {

  /**
   * This method creates a new {@link NlsMessageFormatter} for the given
   * <code>message</code>.<br/>
   * The format of the <code>message</code> is described in {@link NlsMessage}.
   * 
   * @param message is the template for the message where potential
   *        {@link NlsMessageFormatter#format(Void, java.util.Locale, java.util.Map, NlsTemplateResolver)
   *        arguments will be filled in}.
   * @return the {@link NlsMessageFormatter} for the given <code>message</code>.
   */
  NlsMessageFormatter create(String message);

}
