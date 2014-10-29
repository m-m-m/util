/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the SPI (service-provider-interface) for 
 * {@link net.sf.mmm.util.resource.api.DataResource data-resources}.
 * <a name="documentation"></a><h2>Resource-Util SPI</h2>
 * This package provides the {@link net.sf.mmm.util.resource.api.spi.DataResourceProvider} 
 * interface. You can write an implementation of this interface (annotated 
 * with @Named) to register a custom implementation of 
 * {@link net.sf.mmm.util.resource.api.DataResource} (or 
 * {@link net.sf.mmm.util.resource.api.BrowsableResource}) to the 
 * {@link net.sf.mmm.util.resource.api.DataResourceFactory} (or 
 * {@link net.sf.mmm.util.resource.api.BrowsableResourceFactory}).
 */
package net.sf.mmm.util.resource.api.spi;

