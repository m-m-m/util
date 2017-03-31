/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api.mapping;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a generic {@link PojoBeanMapper}.
 *
 * @author hohwille
 * @since 8.5.0
 */
@ComponentSpecification
public interface GenericPojoBeanMapper extends PojoBeanMapper<Object, Bean> {

}
