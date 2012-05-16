/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to associate code with requirements. A requirement is a specific need a system has
 * to fulfill. Typically the implementation of a requirement contains aspects in different layers of the
 * application and is not directly reflected by grouping units such as modules or java packages. Therefore
 * this annotation may be used to identify which code belongs to a particular requirement. Therefore you
 * should define a class, interface, or enum with string constants for the names of your requirements. The
 * javadoc of these constants shall provide further documentation of the requirement like the detailed
 * description or links to further documentation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
@Retention(RetentionPolicy.CLASS)
@Target(value = { ElementType.TYPE, ElementType.METHOD })
public @interface Requirement {

  /**
   * The name of the requirement. Please use centralized constants for the requirements as described above.
   */
  String name();

}
