/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import net.sf.mmm.util.nls.AbstractResourceBundle;

@SuppressWarnings("all")
public class MyResourceBundle extends AbstractResourceBundle {

  public static final String ERR_NULL = "NullPointerException caused by \"{0}\"!";

  public static final String MSG_WELCOME = "Welcome \"{0}\"!";

  public static final String MSG_BYE = "Bye,bye \"{0}\"!";

  public static final String MSG_TEST_DATE = "Date formatted by locale: {0}, by ISO-8601: {0,datetime,iso8601} and by custom pattern: {0,date,yyyy.MM.dd-HH:mm:ssZ}!";

  public static final String MSG_TEST_NUMBER = "Number formatted by default: {0}, as percent: {0,number,percent}, as currency: {0,number,currency} and by custom pattern: {0,number,'#'##.##}!";

}
