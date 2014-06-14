/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.path.base;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.path.api.PathFactory;
import net.sf.mmm.util.path.api.PathProvider;
import net.sf.mmm.util.path.api.PathUri;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;

/**
 * This is the abstract base implementation of the {@link PathFactory} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public abstract class AbstractPathFactory extends AbstractLoggableComponent implements PathFactory {

  /** @see #createPath(String) */
  private final Map<String, PathProvider> schema2providerMap;

  /**
   * The constructor.
   */
  public AbstractPathFactory() {

    super();
    this.schema2providerMap = new HashMap<>();
  }

  /**
   * This method registers the given {@link PathProvider}.
   *
   * @param provider is the {@link PathProvider} to register.
   * @throws DuplicateObjectException if a {@link PathProvider} is already registered for one of the
   *         {@link PathProvider#getSchemePrefixes() scheme-prefixes}.
   */
  public void registerProvider(PathProvider provider) throws DuplicateObjectException {

    getInitializationState().requireNotInitilized();
    if (provider == null) {
      throw new NlsNullPointerException("provider");
    }
    String[] schemaPrefixes = provider.getSchemePrefixes();
    if (schemaPrefixes == null) {
      throw new NlsNullPointerException(provider.getClass().getName() + ".getSchemaPrefix()");
    }
    if (schemaPrefixes.length == 0) {
      throw new NlsIllegalArgumentException(provider.getClass().getName() + ".getSchemaPrefix()");
    }
    for (String schemePrefix : schemaPrefixes) {
      registerProvider(provider, schemePrefix);
    }
  }

  /**
   * This method registers the given {@link PathProvider}.
   *
   * @param provider is the {@link PathProvider} to register.
   * @param schemePrefix is the {@link PathUri#getSchemePrefix() scheme-prefix} for which the
   *        {@link PathProvider} shall be registered.
   * @throws DuplicateObjectException if a {@link PathProvider} is already registered for one of the
   *         {@link PathProvider#getSchemePrefixes() scheme-prefixes}.
   */
  public void registerProvider(PathProvider provider, String schemePrefix) throws DuplicateObjectException {

    getInitializationState().requireNotInitilized();
    if (provider == null) {
      throw new NlsNullPointerException("provider");
    }
    if (this.schema2providerMap.containsKey(schemePrefix)) {
      throw new DuplicateObjectException(provider, schemePrefix);
    }
    this.schema2providerMap.put(schemePrefix, provider);
  }

  /**
   * This method gets the {@link PathProvider} for the given {@link PathUri}.
   *
   * @param pathUri is the {@link PathUri}.
   * @return the {@link PathProvider} {@link PathProvider#getSchemePrefixes() responsible} for the given
   *         {@link PathUri}.
   * @throws ResourceUriUndefinedException if no {@link PathProvider provider} is
   *         {@link #registerProvider(PathProvider) registered} that is responsible.
   */
  protected PathProvider getProvider(PathUri pathUri) throws ResourceUriUndefinedException {

    String schemePrefix = pathUri.getSchemePrefix();
    if (schemePrefix == null) {
      // default is file://
      schemePrefix = PathUri.SCHEME_PREFIX_FILE;
    }
    PathProvider provider = this.schema2providerMap.get(schemePrefix);
    return provider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Path createPath(String resourceUri) {

    PathUri uri = new PathUriImpl(resourceUri);
    return createPath(uri);
  }

  /**
   * @see #createPath(String)
   *
   * @param uri is the {@link PathUri}.
   * @return the according {@link Path}.
   */
  private Path createPath(PathUri uri) {

    PathProvider provider = getProvider(uri);
    if (provider == null) {
      if (uri.getSchemePrefix() == null) {
        return Paths.get(uri.getPath());
      } else {
        try {
          return Paths.get(new URI(uri.getUri()));
        } catch (URISyntaxException e) {
          throw new ResourceUriUndefinedException(e, uri.getUri());
        }
      }
    }
    return provider.createResource(uri);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Path createPath(Path basePath, String resourcePath) {

    PathUri uri = new PathUriImpl(resourcePath);
    if (uri.getSchemePrefix() == null) {
      String path = uri.getPath();
      return basePath.resolve(Paths.get(path));
    }
    return createPath(uri);
  }

}
