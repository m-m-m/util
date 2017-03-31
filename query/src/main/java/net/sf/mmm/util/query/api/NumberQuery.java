/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api;

/**
 * A {@link Query} that {@link #execute() results} in a {@link Number}.
 *
 * @param <E> the generic type of the {@link #execute() result}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface NumberQuery<E extends Number & Comparable<?>> extends Query<E> {

}
