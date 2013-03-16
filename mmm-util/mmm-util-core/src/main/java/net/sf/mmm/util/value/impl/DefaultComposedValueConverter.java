/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.api.ValueConverter;
import net.sf.mmm.util.value.base.AbstractRecursiveValueConverter;

/**
 * This is a default {@link net.sf.mmm.util.value.api.ComposedValueConverter} to be used without
 * IoC-Container. It extends {@link ComposedValueConverterImpl} by
 * {@link #addConverter(net.sf.mmm.util.value.api.ValueConverter) adding} typical
 * {@link net.sf.mmm.util.value.api.ValueConverter}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class DefaultComposedValueConverter extends ComposedValueConverterImpl {

  /** @see #getInstance() */
  private static ComposedValueConverter instance;

  /**
   * The constructor.
   */
  public DefaultComposedValueConverter() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link ComposedValueConverter}.<br>
   * This design is the best compromise between easy access (via this indirection you have direct, static
   * access to all offered functionality) and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and construct new instances via
   * the container-framework of your choice (like plexus, pico, springframework, etc.). To wire up the
   * dependent components everything is properly annotated using common-annotations (JSR-250). If your
   * container does NOT support this, you should consider using a better one.
   * 
   * @return the singleton instance.
   * @since 3.1.0
   */
  public static ComposedValueConverter getInstance() {

    if (instance == null) {
      synchronized (DefaultComposedValueConverter.class) {
        if (instance == null) {
          DefaultComposedValueConverter impl = new DefaultComposedValueConverter();
          impl.initialize();
          instance = impl;
        }
      }
    }
    return instance;
  }

  /**
   * @see #addConverter(ValueConverter)
   * 
   * @param converter is the converter to add.
   */
  public void addConverterComponent(ValueConverter<?, ?> converter) {

    if (converter instanceof AbstractRecursiveValueConverter) {
      ((AbstractRecursiveValueConverter<?, ?>) converter).setComposedValueConverter(this);
    }
    if (converter instanceof AbstractComponent) {
      ((AbstractComponent) converter).initialize();
    }
    addConverter(converter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    addConverterComponent(new ValueConverterToBoolean());
    addConverterComponent(new ValueConverterToDate());
    addConverterComponent(new ValueConverterToCalendar());
    addConverterComponent(new ValueConverterToNumber());
    addConverterComponent(new ValueConverterToString());
    addConverterComponent(new ValueConverterToEnum());
    addConverterComponent(new ValueConverterToClass());
    addConverterComponent(new ValueConverterToCompatiblePojo());
    addConverterComponent(new ValueConverterToCollection());
    addConverterComponent(new ValueConverterToArrayOfBoolean());
    addConverterComponent(new ValueConverterToArrayOfByte());
    addConverterComponent(new ValueConverterToArrayOfDouble());
    addConverterComponent(new ValueConverterToArrayOfFloat());
    addConverterComponent(new ValueConverterToArrayOfInt());
    addConverterComponent(new ValueConverterToArrayOfLong());
    addConverterComponent(new ValueConverterToArrayOfObject());
    addConverterComponent(new ValueConverterToArrayOfShort());
    addConverterComponent(new ValueConverterToFile());
    addConverterComponent(new ValueConverterToMap());
    super.doInitialize();
  }
}
