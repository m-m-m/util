/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl.type;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import net.sf.mmm.value.api.ValueManager;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueService;
import net.sf.mmm.value.base.AbstractValueManager;
import net.sf.mmm.value.base.ValueServiceAware;

/**
 * This is the {@link net.sf.mmm.value.api.ValueManager manager} for
 * {@link List} values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ListValueManager extends AbstractValueManager<List> implements ValueServiceAware {

  /** the {@link #getName() "logical name"} of the managed value. */
  public static final String VALUE_NAME = "List";

  /** @see #setValueService(ValueService) */
  private ValueService valueService;

  /**
   * The constructor.
   */
  public ListValueManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void setValueService(ValueService valueService) {

    this.valueService = valueService;
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return VALUE_NAME;
  }

  /**
   * {@inheritDoc}
   */
  public Class<List> getValueClass() {

    return List.class;
  }

  /**
   * {@inheritDoc}
   */
  public List fromString(String valueAsString) throws ValueParseException {

    List result = new ArrayList();
    // TODO Auto-generated method stub
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toStringNotNull(List value) {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('[');
    int length = value.size();
    for (int i = 0; i < length; i++) {
      if (i > 0) {
        stringBuilder.append(',');
      }
      Object item = value.get(i);
      String itemString = this.valueService.getGenericManager().toString(item);
      stringBuilder.append(itemString);
    }
    stringBuilder.append(']');
    return stringBuilder.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void toXmlContent(XMLStreamWriter xmlWriter, List value) throws XMLStreamException {

    ValueManager<Object> manager = this.valueService.getGenericManager();
    for (Object item : value) {
      manager.toXml(xmlWriter, item);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List fromXmlContent(XMLStreamReader xmlReader) throws XMLStreamException {

    // TODO Auto-generated method stub
    return super.fromXmlContent(xmlReader);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEqual(java.util.List value1, java.util.List value2) {
  
    // TODO Auto-generated method stub
    return super.isEqual(value1, value2);
  }
  
}
