/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * This annotation is used to associate code with usecases. A usecase is a functionality of the system that can be
 * performed by an end-user, an external system or automatically by the system itself. Typically the implementation of a
 * usecase contains aspects in different layers of the application and is not directly reflected by grouping units such
 * as modules or java packages. Therefore this annotation may be used to identify which code belongs to a particular
 * usecase. Therefore you should define a class, interface, or enum with string constants for the names of your
 * usecases. The javadoc of these constants shall provide further documentation of the usecase like the detailed
 * description or links to further documentation.<br/>
 * <b>Note:</b><br/>
 * This {@link Annotation} is also a {@link Qualifier} for CDI.
 *
 * @deprecated create your own annotation and keep control of it. This annotation will be deleted in a future release.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Usecase {

  /**
   * The name of the usecase. Please use centralized constants for the usecases as described above.
   */
  String name();

}
