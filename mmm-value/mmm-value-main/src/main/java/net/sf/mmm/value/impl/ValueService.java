/* $Id$ */
package net.sf.mmm.value.impl;

import net.sf.mmm.value.api.ValueException;
import net.sf.mmm.value.api.ValueManagerIF;
import net.sf.mmm.value.base.AbstractValueService;
import net.sf.mmm.value.impl.type.DateValueManager;
import net.sf.mmm.value.impl.type.XmlValueManager;

/**
 * This is the implementation of the ValueServiceIF interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueService extends AbstractValueService {

  /**
   * The constructor.
   */
  public ValueService() {

    super();
  }

  /**
   * This method {@link #addManager(ValueManagerIF) registers} a
   * {@link GenericValueManager} for the given value to this service.
   * 
   * @param <V>
   *        is the value type to register.
   * @param valueClass
   *        is the implementation of the value. It should have a String arg
   *        constructor and a compliant {@link Object#toString()} method.
   * @param name
   *        is the {@link ValueManagerIF#getName() "logical name"} of the value.
   * @throws ValueException
   *         if the registration fails (e.g. value for this name is already
   *         registered).
   */
  public <V> void addValue(Class<V> valueClass, String name) throws ValueException {

    addManager(new GenericValueManager<V>(valueClass, name));
  }

  /**
   * This method registers the default value types to this service. It can be
   * called for testing when the service is created manually without a
   * servicemanager.
   */
  // @PostConstruct
  public void initialize() {

    try {
      // just some test code...
      addManager(new GenericValueManager<Boolean>(Boolean.class));
      // registerManager(new GenericValueManager<Date>(Date.class));
      addManager(new DateValueManager());
      // registerManager(new ValueDuration.Manager());
      // registerManager(new ValueGenericList.Manager());
      // registerManager(new ValueHomogenList.Manager());
      addManager(new GenericValueManager<Integer>(Integer.class));
      addManager(new GenericValueManager<Long>(Long.class));
      // registerManager(new ValuePercent.Manager());
      addManager(new GenericValueManager<Float>(Float.class));
      addManager(new GenericValueManager<Double>(Double.class));
      addManager(new GenericValueManager<String>(String.class));
      addManager(new XmlValueManager());
      // use org.w3c.Element but write secific manager...
      // registerManager(new ValueXml.Manager());
    } catch (ValueException e) {
      throw new IllegalStateException("ValueService initialization is illegal!", e);
    }
  }

}
