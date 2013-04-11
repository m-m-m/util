/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a {@link PojoPathFunction} that has a {@link #getName() name}. This allows to
 * register such functions as plugin via {@link javax.inject.Inject}.
 * 
 * @param <IN> is the generic {@link #getInputClass() input-type}.
 * @param <VALUE> is the generic {@link #getValueClass() value-type}
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@ComponentSpecification(plugin = true)
public interface PojoPathNamedFunction<IN, VALUE> extends PojoPathFunction<IN, VALUE> {

  /**
   * This method gets the name of this function.<br/>
   * <b>ATTENTION:</b><br/>
   * The name has to be unique for all functions registered to a {@link PojoPathNavigator} implementation.<br/>
   * E.g. if the name is "foo", then the function can be invoked via "@foo" as {@link PojoPath#getSegment()
   * segment}.
   * 
   * @return the name of the function excluding the {@link #FUNCTION_NAME_PREFIX}.
   */
  String getName();

}
