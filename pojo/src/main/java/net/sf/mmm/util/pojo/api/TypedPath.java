/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.api;

/**
 * This interface extends {@link PojoPropertyPath} with a generic for the type this path resolves to.
 *
 * @param <T> is the generic type of the property identified by this {@link PojoPropertyPath path}.
 *
 * @author hohwille
 * @since 7.6.0 (moved from 7.1.0)
 */
public interface TypedPath<T> extends PojoPropertyPath {

}
