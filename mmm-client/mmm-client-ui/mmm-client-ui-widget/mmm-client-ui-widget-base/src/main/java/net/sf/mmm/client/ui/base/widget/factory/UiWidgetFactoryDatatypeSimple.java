/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import javax.inject.Named;

import net.sf.mmm.client.ui.api.widget.UiWidgetFactoryDatatype;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetFactoryDatatype;

/**
 * This is a simple implementation of {@link UiWidgetFactoryDatatype}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named(UiWidgetFactoryDatatype.CDI_NAME)
public class UiWidgetFactoryDatatypeSimple extends AbstractUiWidgetFactoryDatatype {

  /**
   * The constructor.
   */
  public UiWidgetFactoryDatatypeSimple() {

    super();
    registerStandardDatatypes();
  }

}
