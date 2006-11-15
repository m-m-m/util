/* $Id$ */
package net.sf.mmm.gui.model.content;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentModelService;
import net.sf.mmm.content.value.api.Id;
import net.sf.mmm.ui.toolkit.api.model.UITableModel;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentClassTableManager {

  private ContentModelService model;

  private final Map<Id, ContentClassTableModel> id2modelMap;

  /**
   * The constructor.
   */
  public ContentClassTableManager() {

    super();
    this.id2modelMap = new HashMap<Id, ContentClassTableModel>();
  }

  /**
   * 
   * @param modelService
   */
  @Resource
  public void setContentModelService(ContentModelService modelService) {

    this.model = modelService;
  }

  /**
   * 
   * @param contentClass
   * @return
   */
  public UITableModel getTableModel(ContentClass contentClass) {

    synchronized (this.model) {
      ContentClassTableModel tableModel = this.id2modelMap.get(contentClass.getId());
      if (tableModel == null) {
        tableModel = new ContentClassTableModel(contentClass, this.model);
        this.id2modelMap.put(contentClass.getId(), tableModel);
      }
      return tableModel;
    }
  }

}
