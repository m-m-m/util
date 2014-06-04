/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This class holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public interface NlsBundleUtilResourceRoot extends NlsBundle {

  /**
   * @see net.sf.mmm.util.resource.api.ResourceNotAvailableException
   *
   * @param resource is the unavailable resource.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The resource \"{resource}\" is not available!")
  NlsMessage errorResourceNotAvailable(@Named("resource") Object resource);

  /**
   * @see net.sf.mmm.util.resource.api.ResourceNotWritableException
   *
   * @param resource is the read-only resource.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The resource \"{resource}\" is not writable!")
  NlsMessage errorResourceNotWritable(@Named("resource") Object resource);

  /**
   * @see net.sf.mmm.util.resource.api.ResourceUriUndefinedException
   *
   * @param uri is the URI of the undefined resource.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The resource URI \"{uri}\" is undefined!")
  NlsMessage errorResourceUndefinedUri(@Named("uri") Object uri);

}
