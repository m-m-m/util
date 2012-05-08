/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is a sub-interface of {@link GenericValueConverter} for the most common value type {@link String}.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@ComponentSpecification
public interface StringValueConverter extends GenericValueConverter<String> {

  // nothing to add, just the generic is bound.

}
