/* $Id$ */
package net.sf.mmm.gui.model.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelEvent;
import net.sf.mmm.content.model.api.ContentModelService;
import net.sf.mmm.ui.toolkit.api.event.UITableModelListener;
import net.sf.mmm.ui.toolkit.api.model.UITableModel;
import net.sf.mmm.ui.toolkit.base.model.AbstractUITableModel;
import net.sf.mmm.util.event.ChangeEvent.Type;
import net.sf.mmm.util.event.EventListener;

/**
 * This is an implementation of the {@link UITableModel} interface for the
 * {@link ContentField fields} of a {@link ContentClass}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentClassTableModel extends AbstractUITableModel implements
    EventListener<ContentModelEvent> {

  /** index of the "name" column */
  private static final int COLUMN_NAME = 0;

  /** index of the "defining class" column */
  private static final int COLUMN_CLASS = 1;

  /** index of the "type" column */
  private static final int COLUMN_TYPE = 2;

  /** index of the "deleted" column */
  private static final int COLUMN_DELETED = 3;

  /** index of the "modifiers" column */
  private static final int COLUMN_MODIFIERS = 4;

  /** index of the "system" column */
  private static final int COLUMN_SYSTEM = 4;

  /** index of the "static" column */
  private static final int COLUMN_STATIC = 5;

  /** index of the "final" column */
  private static final int COLUMN_FINAL = 6;

  /** index of the "read-only" column */
  private static final int COLUMN_READ_ONLY = 7;

  /** index of the "transient" column */
  private static final int COLUMN_TRANSIENT = 8;

  /** the column names */
  // private static final String[] COLUMN_NAMES = {"Name", "Class", "Type",
  // "Deleted", "Modifiers"};
  private static final String[] COLUMN_NAMES = {"Name", "Class", "Type", "Deleted", "System",
      "Static", "Final", "Read-only", "Transient"};

  /** the content class for the */
  private final ContentClass cClass;

  /** the list of all fields */
  private final List<ContentField> fields;

  /** the model service */
  private final ContentModelService model;

  /**
   * The constructor.
   * 
   * @param contentClass
   * @param modelService
   */
  public ContentClassTableModel(ContentClass contentClass, ContentModelService modelService) {

    super();
    this.cClass = contentClass;
    this.fields = new ArrayList<ContentField>();
    this.model = modelService;
    initialize();
  }

  /**
   * This method initializes the model.
   */
  public void initialize() {

    Iterator<ContentField> fieldIterator = this.cClass.getDeclatedFields();
    while (fieldIterator.hasNext()) {
      this.fields.add(fieldIterator.next());
    }
    this.model.getEventRegistrar().addListener(this);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.model.AbstractUITableModel#handleListenerException(net.sf.mmm.ui.toolkit.api.event.UITableModelListener,
   *      java.lang.Throwable)
   */
  @Override
  protected void handleListenerException(UITableModelListener listener, Throwable t) {

  // TODO Auto-generated method stub

  }

  /**
   * @see net.sf.mmm.util.event.EventListener#handleEvent(net.sf.mmm.util.event.Event)
   */
  public void handleEvent(ContentModelEvent event) {

    ContentField eventField = event.getContentField();
    // only of interest if the event is about a field...
    if (eventField != null) {
      ContentClass eventClass = event.getContentClass();
      // only of interest if the class itself or one of its super-classes has
      // changed...
      if ((this.cClass == eventClass) || this.cClass.isSubClassOf(eventClass)) {
        ContentField changedField = this.cClass.getDeclaredField(eventField.getName());
        if (changedField != null) {
          switch (event.getType()) {
            case ADD:
              this.fields.add(changedField);
              fireRowChangeEvent(Type.ADD, this.fields.size());
              break;
            case REMOVE:
            case UPDATE:
              int rowIndex = this.fields.indexOf(changedField);
              if (rowIndex >= 0) {
                fireRowChangeEvent(Type.UPDATE, rowIndex);
              }
              break;
          }
        }
      }
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UITableModel#getCellValue(int, int)
   */
  public Object getCellValue(int rowIndex, int columnIndex) {

    ContentField field = this.fields.get(rowIndex);
    switch (columnIndex) {
      case COLUMN_NAME:
        return field.getName();
      case COLUMN_CLASS:
        return field.getInitiallyDefiningClass();
      case COLUMN_TYPE:
        return field.getFieldType().getSimpleName();
      case COLUMN_DELETED:
        return Boolean.valueOf(field.isDeleted());
        // return Boolean.valueOf(field.isDeletedFlagSet());
      //case COLUMN_MODIFIERS:
      //  return field.getModifiers();
      case COLUMN_SYSTEM: 
        return Boolean.valueOf(field.getModifiers().isSystem());
      case COLUMN_STATIC: 
        return Boolean.valueOf(field.getModifiers().isStatic());
      case COLUMN_FINAL: 
        return Boolean.valueOf(field.getModifiers().isFinal());
      case COLUMN_READ_ONLY: 
        return Boolean.valueOf(field.getModifiers().isReadOnly());
      case COLUMN_TRANSIENT: 
        return Boolean.valueOf(field.getModifiers().isTransient());
    }
    throw new ArrayIndexOutOfBoundsException("Illegal column-index: " + columnIndex);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UITableModel#getColumnCount()
   */
  public int getColumnCount() {

    return COLUMN_NAMES.length;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UITableModel#getColumnName(int)
   */
  public String getColumnName(int columnIndex) {

    return COLUMN_NAMES[columnIndex];
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UITableModel#getRowCount()
   */
  public int getRowCount() {

    return this.fields.size();
  }

}
