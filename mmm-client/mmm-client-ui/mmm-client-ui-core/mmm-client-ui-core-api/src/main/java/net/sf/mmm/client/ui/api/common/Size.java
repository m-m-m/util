/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

import net.sf.mmm.client.ui.api.attribute.AttributeReadSizeInPixel;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteLengthProperty;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteMaximumSize;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteMinimumSize;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSizeAdvanced;

/**
 * This interface gives full featured read and write access to the size of an object. It can directly be
 * extended (e.g. by widgets) or allows to provide to the size of an object as a separate aspect to avoid
 * overloading the API (e.g. with respect to auto completion). In this API the size has the following
 * dimensions:
 * <ul>
 * <li>{@link LengthProperty}:
 * <ul>
 * <li>{@link net.sf.mmm.util.lang.api.Orientation}: width or length</li>
 * <li>range: actual, minimum, or maximum value</li>
 * </ul>
 * </li>
 * <li>{@link Length}:
 * <ul>
 * <li>{@link Length#getAmount() amount}</li>
 * <li>{@link Length#getUnit() unit}: {@link LengthUnit#PIXEL}, {@link LengthUnit#PERCENT},
 * {@link LengthUnit#EM}</li>
 * </ul>
 * </li>
 * </ul>
 * The most generic way to set the size is via {@link #setLength(LengthProperty, Length)}. For practical use
 * there are also methods such as {@link #setWidthInPixel(double)}. <br>
 * Getting the width or length typically returns the {@link Length} in the {@link LengthUnit unit} that has
 * been set or configured internally. <br>
 * <b>ATTENTION:</b><br>
 * Additionally, there is also {@link #getWidthInPixel()} and {@link #getHeightInPixel()} but these may only
 * return precise results if the object (widget) is actually attached to the screen. This is by the nature of
 * the concept as you can hardly tell how many pixels an object with a width of 80% will take. So use these
 * two methods with care or you might get unpredictable results.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Size extends AttributeWriteSizeAdvanced, AttributeWriteMinimumSize, AttributeWriteMaximumSize,
    AttributeReadSizeInPixel, AttributeWriteLengthProperty {

  // nothing to add...

}
