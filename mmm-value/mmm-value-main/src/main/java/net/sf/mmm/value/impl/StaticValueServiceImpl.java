/* $Id$ */
package net.sf.mmm.value.impl;

import net.sf.mmm.value.api.ValueException;
import net.sf.mmm.value.impl.type.DateValueManager;
import net.sf.mmm.value.impl.type.XmlValueManager;

/**
 * This is an implementation of the {@link net.sf.mmm.value.api.ValueService}
 * interface used for testing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class StaticValueServiceImpl extends ValueServiceImpl {

  /**
   * The constructor.
   * 
   */
  public StaticValueServiceImpl() {

    super();
    try {
      addManager(new GenericValueManager<Boolean>(Boolean.class));
      addManager(new DateValueManager());
      addManager(new GenericValueManager<Integer>(Integer.class));
      addManager(new GenericValueManager<Long>(Long.class));
      addManager(new GenericValueManager<Float>(Float.class));
      addManager(new GenericValueManager<Double>(Double.class));
      addManager(new GenericValueManager<String>(String.class));
      addManager(new XmlValueManager());
    } catch (ValueException e) {
      throw new IllegalStateException("ValueService initialization is illegal!", e);
    }

  }

}
