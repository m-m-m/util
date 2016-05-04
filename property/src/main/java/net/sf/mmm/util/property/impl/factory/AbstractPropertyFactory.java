/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;

/**
 * The abstract base implementation of {@link PropertyFactory}.
 *
 * @param <V> the generic type of the {@link WritableProperty#getValue() property value}.
 * @param <P> the generic type of the {@link WritableProperty property}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractPropertyFactory<V, P extends WritableProperty<V>> implements PropertyFactory<V, P> {

}
