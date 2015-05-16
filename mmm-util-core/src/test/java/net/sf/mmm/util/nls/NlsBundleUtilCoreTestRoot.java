/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsBundleOptions;
import net.sf.mmm.util.nls.api.NlsBundleWithLookup;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is a {@link NlsBundleWithLookup} for testing.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
@NlsBundleOptions(productive = false)
public interface NlsBundleUtilCoreTestRoot extends NlsBundleWithLookup {

  /**
   * @see net.sf.mmm.util.lang.api.Conjunction#AND
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("and")
  NlsMessage infoAnd();

  /**
   * @see net.sf.mmm.util.exception.api.ObjectNotFoundException
   *
   * @param object describes the missing object (e.g. the expected type).
   * @param key is the key associated with the object (e.g. in a {@link java.util.Map}).
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Could NOT find object \"{object}\"{key,choice,(?==null)''(else)' for \"'{key}'\"'}!")
  NlsMessage errorObjectNotFound(@Named("object") Object object, @Named("key") Object key);

}
