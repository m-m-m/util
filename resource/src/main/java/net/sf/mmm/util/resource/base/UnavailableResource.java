/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.net.URL;
import java.util.Collections;
import java.util.Date;

import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;

/**
 * This is an implementation of {@link BrowsableResource} that represents an un-{@link #isAvailable() available}
 * resource that never exists and can not contain data.
 *
 * @author hohwille
 * @since 7.4.0
 */
public class UnavailableResource extends AbstractBrowsableResource {

  /** The singleton instance of this class. */
  public static final BrowsableResource INSTANCE = new UnavailableResource();

  private static final String UNAVAILABLE = "unavailable";

  private UnavailableResource() {
    super();
  }

  @Override
  public Iterable<? extends BrowsableResource> getChildResources() {

    return Collections.emptyList();
  }

  @Override
  public boolean isFolder() {

    return false;
  }

  @Override
  public boolean isData() {

    return false;
  }

  @Override
  public BrowsableResource cd(String path) {

    return INSTANCE;
  }

  @Override
  public URL getUrl() throws ResourceNotAvailableException {

    throw new ResourceNotAvailableException(UNAVAILABLE);
  }

  @Override
  public DataResource navigate(String resourcePath) throws ResourceUriUndefinedException {

    return INSTANCE;
  }

  @Override
  public Date getLastModificationDate() {

    return null;
  }

  @Override
  public String getSchemePrefix() {

    return UNAVAILABLE;
  }

}
