/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import net.sf.mmm.util.date.api.attribute.AttributeReadHours;
import net.sf.mmm.util.date.api.attribute.AttributeReadMilliseconds;
import net.sf.mmm.util.date.api.attribute.AttributeReadMinutes;
import net.sf.mmm.util.date.api.attribute.AttributeReadSeconds;

/**
 * This is the interface for an object that represents a time of the day.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.1
 */
public interface SimpleTime extends AttributeReadHours, AttributeReadMinutes, AttributeReadSeconds, AttributeReadMilliseconds {

  // nothing to add...

}
