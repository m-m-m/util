/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import javax.inject.Named;

/**
 * {@link NlsBundle} for testing.
 */
@SuppressWarnings("javadoc")
public interface NlsBundleTestRoot extends NlsBundle {

  @NlsBundleMessage("The value has to be {comparator} '{value}'!")
  NlsMessage errorIllegalValue(@Named("comparator") Object comparator, @Named("value") Object value);

}
