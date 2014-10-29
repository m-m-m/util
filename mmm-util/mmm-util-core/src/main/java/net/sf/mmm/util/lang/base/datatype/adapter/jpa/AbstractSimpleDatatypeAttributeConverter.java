/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter.jpa;

import javax.persistence.AttributeConverter;

import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * This is the abstract base implementation of {@link AttributeConverter} to allow the usage of
 * {@link SimpleDatatype}s in JPA {@link javax.persistence.Entity} classes. <br>
 * If you create an implementation of {@link SimpleDatatype} that you want to use in persistent
 * {@link javax.persistence.Entity entities}, you need to:
 * <ol>
 * <li>Create a converter implementation derived from {@link AbstractSimpleDatatypeAttributeConverter}.</li>
 * <li>Annotate that converter implementation with <code>{@literal @Converter(autoApply = true)}</code></li>
 * <li>Ensure that the package containing your converter(s) will actually be scanned. When using spring this
 * is configured in
 * <code>org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.setPackagesToScan</code>.</li>
 * </ol>
 *
 * For an example how to implement such a converter see e.g. {@link VersionIdentifierAttributeConverter}.
 *
 * @param <V> the generic for the basic java type representing the {@link SimpleDatatype#getValue() value}.
 * @param <T> the generic for the adapted {@link SimpleDatatype}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public abstract class AbstractSimpleDatatypeAttributeConverter<T extends SimpleDatatype<V>, V> implements
    AttributeConverter<T, V> {

  /**
   * The constructor.
   */
  public AbstractSimpleDatatypeAttributeConverter() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public V convertToDatabaseColumn(T datatype) {

    if (datatype == null) {
      return null;
    }
    return datatype.getValue();
  }

}
