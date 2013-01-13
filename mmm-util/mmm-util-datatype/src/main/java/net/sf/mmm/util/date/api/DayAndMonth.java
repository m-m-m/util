/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import net.sf.mmm.util.date.api.attribute.AttributeReadDay;
import net.sf.mmm.util.date.api.attribute.AttributeReadMonth;

/**
 * This is the interface for an object that holds the combination of {@link #getDay() day} and
 * {@link #getMonth() month}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.1
 */
public interface DayAndMonth extends AttributeReadDay, AttributeReadMonth {

  // nothing to add...

}
