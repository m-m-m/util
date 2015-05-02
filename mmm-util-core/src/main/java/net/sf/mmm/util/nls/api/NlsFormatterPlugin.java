/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This interface extends {@link NlsFormatter} with the methods required to register this automatically as
 * plugin via {@link javax.inject.Inject injection}. <br>
 * All subclasses annotated with &#64;{@link javax.inject.Named} will automatically be registered. If no
 * {@link NlsFormatterPlugin} with default {@link #getStyle() style} (<code>null</code>) is found for some
 * {@link #getType() type} the one with {@link NlsFormatterManager#STYLE_MEDIUM medium} style is used as
 * default.
 * 
 * @param <O> is the generic type of the object to
 *        {@link #format(Object, java.util.Locale, java.util.Map, NlsTemplateResolver) format}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@ComponentSpecification(plugin = true)
public interface NlsFormatterPlugin<O> extends NlsFormatter<O> {

  /**
   * This method gets the {@link net.sf.mmm.util.nls.api.NlsFormatterManager#getFormatter(String) type} of
   * this formatter. See <code>TYPE_*</code> constants of {@link net.sf.mmm.util.nls.api.NlsFormatterManager}
   * e.g. {@link net.sf.mmm.util.nls.api.NlsFormatterManager#TYPE_NUMBER}.
   * 
   * @return the type or <code>null</code> for the
   *         {@link net.sf.mmm.util.nls.api.NlsFormatterManager#getFormatter() default formatter}. If type is
   *         <code>null</code> then also {@link #getStyle() style} needs to be <code>null</code>.
   */
  String getType();

  /**
   * This method gets the {@link net.sf.mmm.util.nls.api.NlsFormatterManager#getFormatter(String, String)
   * style} of this formatter. See <code>STYLE_*</code> constants of
   * {@link net.sf.mmm.util.nls.api.NlsFormatterManager} e.g.
   * {@link net.sf.mmm.util.nls.api.NlsFormatterManager#STYLE_LONG}.
   * 
   * @return the style or <code>null</code> for no style.
   */
  String getStyle();

}
