/* $Id$ */
package net.sf.mmm.framework.demo.impl;

import java.util.Properties;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.PropertyConfigurator;

import net.sf.mmm.framework.api.ExtendedComponentDescriptor;
import net.sf.mmm.framework.api.LifecycleManager;
import net.sf.mmm.framework.base.ComponentInstantiationManager;
import net.sf.mmm.framework.base.SingletonComponentInstantiationManager;
import net.sf.mmm.framework.demo.api.ComponentA;
import net.sf.mmm.framework.demo.api.ComponentB;
import net.sf.mmm.framework.impl.DefaultLifecycleManager;
import net.sf.mmm.framework.impl.DefaultThreadFactoryProvider;
import net.sf.mmm.framework.impl.DescriptorBuilder;
import net.sf.mmm.framework.impl.GenericComponentProvider;
import net.sf.mmm.framework.impl.IocContainerImpl;
import net.sf.mmm.framework.impl.LoggerProvider;
import net.sf.mmm.framework.impl.ThreadPoolProvider;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TestMain {

    protected static <S, I extends S> void add(IocContainerImpl container, Class<S> specification,
            Class<I> implementation) {

        DescriptorBuilder db = new DescriptorBuilder();
        ExtendedComponentDescriptor<S, I> descriptor = db.createDescriptor(specification,
                implementation);
        SingletonComponentInstantiationManager<S, I> cim = new SingletonComponentInstantiationManager<S, I>();
        cim.setDescriptor(descriptor);
        GenericComponentProvider<S, I> gcp = new GenericComponentProvider<S, I>(descriptor, cim);
        container.addComponentProvider(gcp);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty("log4j.rootLogger", "DEBUG,STDOUT");
        properties.setProperty("log4j.appender.STDOUT", "org.apache.log4j.ConsoleAppender");
        properties.setProperty("log4j.appender.STDOUT.layout", "org.apache.log4j.PatternLayout");
        properties.setProperty("log4j.appender.STDOUT.layout.ConversionPattern",
                "[%d{dd MMM yyyy HH:mm:ss}] %-5p [%C][%M]: %m%n");

        PropertyConfigurator.configure(properties);
        IocContainerImpl container = new IocContainerImpl();
        container.setLogger(new Log4JLogger("container"));
        container.addComponentProvider(new LoggerProvider());
        container.addComponentProvider(new DefaultThreadFactoryProvider());
        container.addComponentProvider(new ThreadPoolProvider());
        add(container, LifecycleManager.class, DefaultLifecycleManager.class);
        add(container, ComponentA.class, TestComponentA.class);
        add(container, ComponentB.class, TestComponentB.class);
        container.start();
        ComponentA a = container.getComponentManager().requestComponent(ComponentA.class);
        System.out.println(a.sayAhh());
        container.getComponentManager().releaseComponent(a);
        container.stop();
    }

}
