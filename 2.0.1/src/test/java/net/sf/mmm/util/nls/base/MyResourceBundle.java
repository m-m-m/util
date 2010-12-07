/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

@SuppressWarnings("all")
public class MyResourceBundle extends AbstractResourceBundle {

  public static final String ERR_NULL = "NullPointerException caused by \"{0}\"!";

  public static final String MSG_WELCOME = "Welcome \"{name}\"!";

  public static final String MSG_BYE = "Bye,bye \"{name}\"!";

  public static final String MSG_TEST_DATE = "Date formatted by locale: {date}, by ISO-8601: {date,datetime,iso8601} and by custom pattern: {date,date,yyyy.MM.dd-HH:mm:ssZ}!";

  public static final String MSG_TEST_NUMBER = "Number formatted by default: {value}, as percent: {value,number,percent}, as currency: {value,number,currency} and by custom pattern: {value,number,'#'##.##}!";

}
