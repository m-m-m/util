/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.model;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.swing.JLabel;
import javax.swing.JSlider;

import net.sf.mmm.ui.toolkit.api.event.EventType;
import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListener;
import net.sf.mmm.ui.toolkit.api.model.UIListModel;

/**
 * This class adapts a {@link net.sf.mmm.ui.toolkit.api.model.UIListModel} to
 * a swing {@link javax.swing.JSlider}. It is the controller of the MVC.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SlideBarModelAdapter<E> implements UIListModelListener {

  /**
   * TODO This type ...
   */
  private class IndexEnumeration implements Enumeration<Integer> {

    /** current index */
    private float index;

    /** increment for index */
    private float increment;

    /** maximum (if reached enum is done) */
    private int maximum;

    /** the next index or <code>-1</code> if not recalculated */
    private int nextIndex;

    /**
     * The constructor.
     * 
     * @param inc
     * @param max
     */
    private IndexEnumeration(float inc, int max) {

      super();
      this.index = inc;
      this.increment = inc;
      this.maximum = max;
      this.nextIndex = 0;
    }

    /**
     * @see java.util.Enumeration#hasMoreElements()
     * 
     */
    public boolean hasMoreElements() {

      if (this.nextIndex == -1) {
        int newIndex = Math.round(this.index);
        this.index += this.increment;
        if (newIndex >= this.maximum) {
          newIndex = this.maximum;
        }
        this.nextIndex = newIndex;
      } else if (this.nextIndex > this.maximum) {
        return false;
      }
      return true;
    }

    /**
     * @see java.util.Enumeration#nextElement()
     * 
     */
    public Integer nextElement() {

      if (hasMoreElements()) {
        int result = this.nextIndex;
        if (this.nextIndex == this.maximum) {
          this.nextIndex = this.maximum + 1;
        } else {
          this.nextIndex = -1;
        }
        return result;
      }
      throw new NoSuchElementException();
    }

  }

  /**
   * TODO This type ...
   */
  private class DynamicLabelTable extends Dictionary<Integer, JLabel> {

    /** label cache */
    private final Map<Integer, JLabel> cache;

    /**
     * The constructor.
     */
    public DynamicLabelTable() {

      super();
      this.cache = new HashMap<Integer, JLabel>();
    }

    /**
     * @see java.util.Dictionary#elements()
     * 
     */
    @Override
    public Enumeration<JLabel> elements() {

      throw new InternalError("I hoped this never happens!");
    }

    /**
     * @see java.util.Dictionary#get(java.lang.Object)
     * 
     */
    @Override
    public JLabel get(Object key) {

      Integer index = (Integer) key;
      JLabel label = this.cache.get(index);
      if (label == null) {
        label = new JLabel(SlideBarModelAdapter.this.model.getElementAsString(index.intValue()));
        this.cache.put(index, label);
      }
      return label;
    }

    /**
     * @see java.util.Dictionary#isEmpty()
     * 
     */
    @Override
    public boolean isEmpty() {

      return false;
    }

    /**
     * @see java.util.Dictionary#keys()
     * 
     */
    @Override
    public Enumeration<Integer> keys() {

      int count = SlideBarModelAdapter.this.model.getElementCount() - 1;
      float step;
      if (count <= 20) {
        step = 1;
      } else {
        step = (count) / 20F;
      }
      return new IndexEnumeration(step, count);
    }

    /**
     * @see java.util.Dictionary#put(Object, Object)
     * 
     */
    @Override
    public JLabel put(Integer key, JLabel value) {

      throw new InternalError("I hoped this never happens!");
    }

    /**
     * @see java.util.Dictionary#remove(java.lang.Object)
     * 
     */
    @Override
    public JLabel remove(Object key) {

      throw new InternalError("I hoped this never happens!");
    }

    /**
     * @see java.util.Dictionary#size()
     * 
     */
    @Override
    public int size() {

      throw new InternalError("I hoped this never happens!");
    }

  }

  /** */
  private final JSlider slideBar;

  /** */
  private UIListModel<E> model;

  /** */
  private final DynamicLabelTable lableTable;

  /**
   * The constructor.
   * 
   * @param slider
   * @param miniModel
   */
  public SlideBarModelAdapter(JSlider slider, UIListModel<E> miniModel) {

    super();
    this.slideBar = slider;
    this.slideBar.setMinimum(0);
    this.model = miniModel;
    this.model.addListener(this);
    this.lableTable = new DynamicLabelTable();
    update();
    this.slideBar.setLabelTable(this.lableTable);
  }

  /**
   * 
   * 
   */
  private void update() {

    int modelCount = this.model.getElementCount();
    int max = modelCount - 1;
    this.slideBar.setMaximum(max);
    // this.lableTable.clear();
    if (modelCount <= 20) {
      this.slideBar.setMajorTickSpacing(1);
    } else {
      double step = max / 10d;
      int intStep = (int) step;
      this.slideBar.setMajorTickSpacing(intStep);
      this.slideBar.setMinorTickSpacing(intStep / 2);
    }
    this.slideBar.setLabelTable(this.lableTable);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.event.UIListModelListener#listModelChanged(net.sf.mmm.ui.toolkit.api.event.UIListModelEvent)
   * 
   */
  public void listModelChanged(UIListModelEvent event) {

    EventType type = event.getType();
    int startIndex = event.getStartIndex();
    int endIndex;
    if ((type == EventType.REMOVE) || (type == EventType.UPDATE)) {
      endIndex = event.getEndIndex();
    } else if (type == EventType.ADD) {
      endIndex = this.model.getElementCount() - 1;
    } else {
      // this is an error
      startIndex = 1;
      endIndex = 0;
    }
    for (int i = startIndex; i <= endIndex; i++) {
      this.lableTable.cache.remove(Integer.valueOf(i));
    }
    update();
  }

  /**
   * This method gets the adapted list model.
   * 
   * @return the model.
   */
  public UIListModel<E> getModel() {

    return this.model;
  }

  /**
   * This method set the model to the given value.
   * 
   * @param newModel
   *        is the new model to set.
   */
  public void setModel(UIListModel<E> newModel) {

    this.model.removeListener(this);
    this.lableTable.cache.clear();
    this.model = newModel;
    this.model.addListener(this);
    update();
  }

}
