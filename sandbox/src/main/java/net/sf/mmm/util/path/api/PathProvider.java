/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.path.api;

import java.nio.file.Path;

/**
 * This is the interface for a provider of {@link Path}s for a {@link #getSchemePrefixes() specific}
 * {@link java.nio.file.FileSystem}. It is not intended to be used by end-users. Instead it allows service
 * providers to add support for new {@link #getSchemePrefixes() scheme prefixes}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface PathProvider {

  /**
   * This method gets the scheme-prefix of the {@link Path}s managed by this provider.
   *
   * @see PathUri#getSchemePrefix()
   *
   * @return the scheme-prefix.
   */
  String[] getSchemePrefixes();

  /**
   * This method creates a new {@link Path} for the given {@link PathUri}.
   *
   * @param pathUri is the {@link PathUri}.
   * @return the {@link Path} for the given {@link PathUri}.
   */
  Path createResource(PathUri pathUri);

}
