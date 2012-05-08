/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

/**
 * A {@link PojoValidator} is a {@link ValueValidator} for {@link net.sf.mmm.util.pojo.api.Pojo}s. It should
 * typically be able to {@link #validate(Object) validate} objects of various types and therefore may be
 * composed out of {@link ValueValidator}s.<br/>
 * A proper implementation will just adapt to JSR 303 (Bean Validation). However this interface allows
 * decoupling the according dependencies. Therefore this API is mainly intended for internal usage of the
 * mmm-project.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface PojoValidator extends ValueValidator<Object> {

  // nothing to add - just the generic is bound.

}
