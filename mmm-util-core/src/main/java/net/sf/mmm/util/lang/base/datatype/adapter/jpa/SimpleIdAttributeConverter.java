/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.sf.mmm.util.lang.base.SimpleId;

/**
 * This is the implementation of {@link AttributeConverter} for {@link SimpleId}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
@SuppressWarnings("rawtypes")
@Converter(autoApply = true)
// https://hibernate.atlassian.net/browse/HHH-8854
public class SimpleIdAttributeConverter implements AttributeConverter<SimpleId, Long> {

  /**
   * The constructor.
   */
  public SimpleIdAttributeConverter() {

    super();
  }

  @Override
  public SimpleId convertToEntityAttribute(Long value) {

    return SimpleId.valueOf(null, value);
  }

  @Override
  public Long convertToDatabaseColumn(SimpleId datatype) {

    if (datatype == null) {
      return null;
    }
    return Long.valueOf(datatype.getId());
  }

}
