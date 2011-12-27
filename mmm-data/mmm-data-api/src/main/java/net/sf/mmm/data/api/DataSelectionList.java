/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface of a mutable {@link DataSelectionListView
 * selection-list}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataSelectionListView.CLASS_ID, title = DataSelectionListView.CLASS_TITLE)
public interface DataSelectionList extends DataSelectionListView, DataSelection {

  // nothing to add.

}
