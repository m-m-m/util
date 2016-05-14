/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.path;

import net.sf.mmm.util.bean.api.entity.EntityBean;
import net.sf.mmm.util.query.api.argument.EntityBeanArgument;

/**
 * This is the interface for a {@link Path} that is a {@link EntityBeanArgument}.
 *
 * @param <V> the generic type of the property value identified by this path.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface EntityBeanPath<V extends EntityBean> extends Path<V>, EntityBeanArgument<V> {

}
