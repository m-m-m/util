/* $Id$ */
package net.sf.mmm.value.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.value.api.GenericValueIF;
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
     * 
     * @param configuration
     * @throws ConfigurationException
     */
    @Resource
    public void configure(ConfigurationIF configuration) throws ConfigurationException {

        for (ConfigurationIF typeNode : configuration.getDescendants("type")) {
            GenericValueIF managerValue = typeNode.getDescendant("@manager").getValue();
            if (managerValue.hasValue()) {
                ValueManagerIF manager = managerValue.getJavaClassInstance(ValueManagerIF.class);
                registerManager(manager);
            } else {
                Class<?> type = typeNode.getDescendant("@class").getValue().getJavaClass();
                registerManager(new GenericValueManager(type));
            }
        }
    }

    /**
     * This method registers the default value types to this service. It can be
     * called for testing when the service is created manually without a
     * servicemanager.
     */
    @PostConstruct
    public void initialize() {

        try {
            // just some test code...
            registerManager(new GenericValueManager<Boolean>(Boolean.class));
            // registerManager(new GenericValueManager<Date>(Date.class));
            registerManager(new DateValueManager());
            // registerManager(new ValueDuration.Manager());
            // registerManager(new ValueGenericList.Manager());
            // registerManager(new ValueHomogenList.Manager());
            registerManager(new GenericValueManager<Integer>(Integer.class));
            registerManager(new GenericValueManager<Long>(Long.class));
            // registerManager(new ValuePercent.Manager());
            registerManager(new GenericValueManager<Float>(Float.class));
            registerManager(new GenericValueManager<Double>(Double.class));
            registerManager(new GenericValueManager<String>(String.class));
            registerManager(new XmlValueManager());
            // use org.w3c.Element but write secific manager...
            // registerManager(new ValueXml.Manager());
        } catch (ValueException e) {
            throw new IllegalStateException("ValueService initialization is illegal!", e);
        }
    }

}