/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
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
public abstract class AbstractBrowsableResourceFactory extends AbstractDataResourceFactory implements
    BrowsableResourceFactory {

  /** @see #createBrowsableResource(ResourceUri) */
  private final Map<String, DataResourceProvider<? extends DataResource>> schema2providerMap;

  /**
   * The constructor.
   */
  public AbstractBrowsableResourceFactory() {

    super();
    this.schema2providerMap = new HashMap<String, DataResourceProvider<? extends DataResource>>();
  }

  /**
   * This method registers the given <code>provider</code>.
   *
   * @param provider is the {@link DataResourceProvider} to register.
   * @throws DuplicateObjectException if a {@link DataResourceProvider} is already registered for one of the
   *         {@link DataResourceProvider#getSchemePrefixes() scheme-prefixes}.
   */
  public void registerProvider(DataResourceProvider<? extends DataResource> provider) throws DuplicateObjectException {

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
    if (provider.getResourceType() == null) {
      throw new NlsNullPointerException(provider.getClass().getName() + ".getResourceType()");
    }
    for (String schemaPrefix : schemaPrefixes) {
      if (this.schema2providerMap.containsKey(schemaPrefix)) {
        throw new DuplicateObjectException(provider, schemaPrefix);
      }
      this.schema2providerMap.put(schemaPrefix, provider);
    }
  }

  /**
   * This method registers the given <code>provider</code> for the given <code>schemaPrefix</code>.
   *
   * @param provider is the {@link DataResourceProvider} to register.
   * @param schemaPrefix is the {@link ResourceUriImpl#getSchemePrefix() scheme-prefix} for which the provider
   *        shall be registered.
   *
   * @throws DuplicateObjectException if a {@link DataResourceProvider} is already registered with the same
   *         {@link DataResourceProvider#getSchemePrefixes() scheme-prefix}.
   */
  public void registerProvider(DataResourceProvider<? extends DataResource> provider, String schemaPrefix)
      throws DuplicateObjectException {

    getInitializationState().requireNotInitilized();
    if (provider == null) {
      throw new NlsNullPointerException("provider");
    }
    if (provider.getResourceType() == null) {
      throw new NlsNullPointerException(provider.getClass().getName() + ".getResourceType()");
    }
    if (this.schema2providerMap.containsKey(schemaPrefix)) {
      throw new DuplicateObjectException(provider, schemaPrefix);
    }
    this.schema2providerMap.put(schemaPrefix, provider);
  }

  /**
   * This method gets the {@link DataResourceProvider provider} for the given <code>resourceUri</code>.
   *
   * @param resourceUri is the {@link ResourceUriImpl}.
   * @return the {@link DataResourceProvider} {@link DataResourceProvider#getSchemePrefixes() responsible} for
   *         the given <code>resourceUri</code>.
   * @throws ResourceUriUndefinedException if no {@link DataResourceProvider provider} is
   *         {@link #registerProvider(DataResourceProvider) registered} that is responsible.
   */
  protected DataResourceProvider<? extends DataResource> getProvider(ResourceUri resourceUri)
      throws ResourceUriUndefinedException {

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

  /**
   * {@inheritDoc}
   */
  @Override
  protected DataResource createDataResource(ResourceUri resourceUri) throws ResourceUriUndefinedException {

    return getProvider(resourceUri).createResource(resourceUri);
  }

  /**
   * This method {@link #createBrowsableResource(String) creates} the actual raw {@link BrowsableResource}.
   *
   * @param resourceUri is the parsed and qualified {@link ResourceUriImpl}.
   * @return the created {@link BrowsableResource}.
   * @throws ResourceUriUndefinedException if the given <code>resourceUri</code> is undefined, e.g. the
   *         {@link ResourceUriImpl#getSchemePrefix() scheme-prefix} is NOT supported by this factory.
   */
  protected BrowsableResource createBrowsableResource(ResourceUri resourceUri) throws ResourceUriUndefinedException {

    DataResourceProvider<? extends DataResource> provider = getProvider(resourceUri);
    if (!BrowsableResource.class.isAssignableFrom(provider.getResourceType())) {
      Exception cause = new NlsIllegalArgumentException(provider.getResourceType());
      throw new ResourceUriUndefinedException(cause, resourceUri.getUri());
    }
    return (BrowsableResource) provider.createResource(resourceUri);
  }

  /**
   * {@inheritDoc}
   */
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

    /** @see #getDelegate() */
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected BrowsableResource getDelegate() {

      return this.delegate;
    }

    /**
     * {@inheritDoc}
     */
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

  }

}
