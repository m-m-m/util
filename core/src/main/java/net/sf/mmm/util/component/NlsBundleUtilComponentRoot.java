/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This interface holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @deprecated will be deleted in a future release.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@Deprecated
public interface NlsBundleUtilComponentRoot extends NlsBundle {

  /**
   * @see net.sf.mmm.util.component.api.ResourceMissingException
   *
   * @param resource is the identifier (path, URL, etc.) of the missing resource.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The required resource \"{resource}\" is missing!")
  NlsMessage errorResourceMissing(@Named("resource") String resource);

  /**
   * @see net.sf.mmm.util.component.api.ResourceAmbiguousException
   *
   * @param resource is the identifier (path, URL, etc.) of the ambiguous resource.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The required resource \"{resource}\" is ambiguous!")
  NlsMessage errorResourceAmbiguous(@Named("resource") String resource);

  /**
   * @see net.sf.mmm.util.component.api.ResourceAmbiguousException
   *
   * @param resource is the identifier (path, URL, class, etc.) of the missing resource.
   * @param ids are the IDs of the ambiguous resources.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The required resource \"{resource}\" is ambiguous!\n{ids}")
  NlsMessage errorResourceAmbiguousWithIds(@Named("resource") String resource, @Named("ids") String... ids);

  /**
   * @see net.sf.mmm.util.component.api.AlreadyInitializedException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The object is already initialized!")
  NlsMessage errorAlreadyInitialized();

  /**
   * @see net.sf.mmm.util.component.api.NotInitializedException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The object is NOT initialized!")
  NlsMessage errorNotInitialized();

}
