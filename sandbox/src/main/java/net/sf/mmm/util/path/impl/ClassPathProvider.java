/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.path.impl;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Named;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.path.api.PathProvider;
import net.sf.mmm.util.path.api.PathUri;

/**
 * This is the implementation of {@link PathProvider} for the {@link PathUri#SCHEME_PREFIX_CLASSPATH
 * classpath} {@link PathUri#getSchemePrefix() scheme}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named
public class ClassPathProvider extends AbstractLoggableComponent implements PathProvider {

  /**
   * The constructor.
   */
  public ClassPathProvider() {

    super();
  }

  @Override
  public String[] getSchemePrefixes() {

    return new String[] { PathUri.SCHEME_PREFIX_CLASSPATH };
  }

  @Override
  public Path createResource(PathUri pathUri) {

    String path = pathUri.getPath();
    if (path.startsWith("/")) {
      path = path.substring(1);
    }
    try {
      return Paths.get(ClassLoader.getSystemResource(path).toURI());
    } catch (URISyntaxException e) {
      throw new IllegalStateException(e);
    }
  }

}
