/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceUri;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;
import net.sf.mmm.util.resource.api.spi.DataResourceProvider;

/**
 * This is the abstract base implementation of the {@link BrowsableResourceFactory} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractBrowsableResourceFactory extends AbstractDataResourceFactory implements BrowsableResourceFactory {

  private final Map<String, DataResourceProvider<? extends DataResource>> schema2providerMap;

  /**
   * The constructor.
   */
  public AbstractBrowsableResourceFactory() {

    super();
    this.schema2providerMap = new HashMap<>();
  }

  /**
   * This method registers the given {@code provider}.
   *
   * @param provider is the {@link DataResourceProvider} to register.
   */
  public void registerProvider(DataResourceProvider<? extends DataResource> provider) {

    registerProvider(provider, null);
  }

  /**
   * This method registers the given {@code provider} for the given {@code schemaPrefix}.
   *
   * @param provider is the {@link DataResourceProvider} to register.
   * @param schemaPrefix is the {@link ResourceUriImpl#getSchemePrefix() scheme-prefix} for which the provider
   *        shall be registered.
   */
  public void registerProvider(DataResourceProvider<? extends DataResource> provider, String schemaPrefix) {

    getInitializationState().requireNotInitilized();
    Objects.requireNonNull(provider, "provider");
    Objects.requireNonNull(provider.getResourceType(), "provider.resourceType");
    String[] schemaPrefixes;
    if (schemaPrefix == null) {
      schemaPrefixes = provider.getSchemePrefixes();
    } else {
      schemaPrefixes = new String[] { schemaPrefix };
    }
    Objects.requireNonNull(provider.getResourceType(), "provider.schemaPrefixes");
    if (schemaPrefixes.length == 0) {
      throw new IllegalArgumentException(provider.getClass().getName() + ".getSchemaPrefix().length == 0");
    }
    for (String prefix : schemaPrefixes) {
      if (this.schema2providerMap.containsKey(prefix)) {
        throw new IllegalStateException("Duplicate provider (" + provider + ") for prefix: " + prefix);
      }
      this.schema2providerMap.put(prefix, provider);
    }
  }

  /**
   * This method gets the {@link DataResourceProvider provider} for the given {@code resourceUri}.
   *
   * @param resourceUri is the {@link ResourceUriImpl}.
   * @return the {@link DataResourceProvider} {@link DataResourceProvider#getSchemePrefixes() responsible} for
   *         the given {@code resourceUri}.
   * @throws ResourceUriUndefinedException if no {@link DataResourceProvider provider} is
   *         {@link #registerProvider(DataResourceProvider) registered} that is responsible.
   */
  protected DataResourceProvider<? extends DataResource> getProvider(ResourceUri resourceUri) throws ResourceUriUndefinedException {

    String schemePrefix = resourceUri.getSchemePrefix();
    if (schemePrefix == null) {
      // default is file://
      schemePrefix = FileResource.SCHEME_PREFIX;
    }
    DataResourceProvider<? extends DataResource> provider = this.schema2providerMap.get(schemePrefix);
    if (provider == null) {
      throw new ResourceUriUndefinedException(resourceUri.getUri());
    }
    return provider;
  }

  @Override
  protected DataResource createDataResource(ResourceUri resourceUri) throws ResourceUriUndefinedException {

    return getProvider(resourceUri).createResource(resourceUri);
  }

  /**
   * This method {@link #createBrowsableResource(String) creates} the actual raw {@link BrowsableResource}.
   *
   * @param resourceUri is the parsed and qualified {@link ResourceUriImpl}.
   * @return the created {@link BrowsableResource}.
   * @throws ResourceUriUndefinedException if the given {@code resourceUri} is undefined, e.g. the
   *         {@link ResourceUriImpl#getSchemePrefix() scheme-prefix} is NOT supported by this factory.
   */
  protected BrowsableResource createBrowsableResource(ResourceUri resourceUri) throws ResourceUriUndefinedException {

    DataResourceProvider<? extends DataResource> provider = getProvider(resourceUri);
    if (!BrowsableResource.class.isAssignableFrom(provider.getResourceType())) {
      Exception cause = new IllegalArgumentException(provider.getResourceType().getSimpleName());
      throw new ResourceUriUndefinedException(cause, resourceUri.getUri());
    }
    return (BrowsableResource) provider.createResource(resourceUri);
  }

  @Override
  public BrowsableResource createBrowsableResource(String resourceUri) throws ResourceUriUndefinedException {

    ResourceUri uri = new ResourceUriImpl(resourceUri);
    BrowsableResource resource = createBrowsableResource(uri);
    return new BrowsableResourceAdapter(resource);
  }

  /**
   * This is an implementation of the {@link BrowsableResource} interface, that adapts {@link #getDelegate()
   * another} {@link BrowsableResource} and enhances the {@link #navigate(String)}-method to support switching
   * schemes via the {@link BrowsableResourceFactory} that created this {@link BrowsableResource}.
   *
   * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
   */
  protected class BrowsableResourceAdapter extends AbstractBrowsableResourceProxy {

    private final BrowsableResource delegate;

    /**
     * The constructor.
     *
     * @param delegate is the {@link BrowsableResource} to adapt.
     */
    public BrowsableResourceAdapter(BrowsableResource delegate) {

      super();
      this.delegate = delegate;
    }

    @Override
    protected BrowsableResource getDelegate() {

      return this.delegate;
    }

    @Override
    public DataResource navigate(String relativePath) throws ResourceUriUndefinedException {

      ResourceUri resourceUri = new ResourceUriImpl(relativePath);
      DataResource result;
      if (resourceUri.getSchemePrefix() == null) {
        result = super.navigate(relativePath);
      } else {
        result = createBrowsableResource(resourceUri);
      }
      if (result instanceof BrowsableResource) {
        return new BrowsableResourceAdapter((BrowsableResource) result);
      } else {
        return new DataResourceAdapter(result);
      }
    }

    @Override
    public BrowsableResource cd(String path) {

      BrowsableResource target;
      ResourceUri resourceUri = new ResourceUriImpl(path);
      if (resourceUri.getSchemePrefix() == null) {
        target = super.cd(path);
      } else {
        target = createBrowsableResource(resourceUri);
      }
      return new BrowsableResourceAdapter(target);
    }

  }

}
