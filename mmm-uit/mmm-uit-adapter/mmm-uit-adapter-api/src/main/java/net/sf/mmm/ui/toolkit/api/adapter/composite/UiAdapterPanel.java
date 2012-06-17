/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.adapter.composite;

import net.sf.mmm.ui.toolkit.api.adapter.UiAdapterRegular;

/**
 * This is the interface for an {@link UiAdapterRegular} that represents a
 * <em>panel</em>. A panel is a widget that acts as a container for other
 * widgets organizing them in a specific layout. Such widget is also called
 * container or layout.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiAdapterPanel extends UiAdapterRegularComposite<Void> {

  void addChild(UiAdapterRegular<?> child);

  void addChild(UiAdapterRegular<?> child, int index);

  void removeChild(UiAdapterRegular<?> child);

  void removeChild(int index);

}
