/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api.validator;

/**
 * This annotation is used to annotate a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor property}
 * that has the type {@link String}. <br/>
 * If this annotation is used for a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor property}
 * that has a {@link ValueConstraintContainer container}-type, then it applies
 * to all elements of that container.
 * 
 * @see ValueConstraintProcessor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public @interface ValueConstraintString {

  /**
   * The minimum {@link String#length() length} of the {@link String} value.
   */
  int minimumLength() default 0;

  /**
   * The maximum {@link String#length() length} of the {@link String} value.
   */
  int maximumLength() default Integer.MAX_VALUE;

  /**
   * The pattern the {@link String} value has to match or the empty
   * {@link String} for no pattern validation.
   */
  String pattern() default "";

  /**
   * Determines if the {@link #pattern()} should be treated as a
   * {@link net.sf.mmm.util.pattern.base.GlobPatternCompiler glob-pattern} or as
   * a {@link java.util.regex.Pattern#compile(String) regex-pattern}.
   */
  boolean isGlobPattern() default false;

}
