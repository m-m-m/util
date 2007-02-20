package net.sf.mmm.util.pojo.base;

import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;

/**
 * This is the implementation of the {@link PojoPropertyDescriptor} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyDescriptorImpl implements PojoPropertyDescriptor {

  /** @see #getName() */
  private final String name;

  /** @see #getReadAccess() */
  private AbstractPojoPropertyAccessor read;

  /** @see #getWriteAccess() */
  private AbstractPojoPropertyAccessor write;

  /** @see #getAddAccess() */
  private AbstractPojoPropertyAccessor add;

  /**
   * The constructor.
   * 
   * @param propertyName
   *        is the {@link #getName() name} of the property.
   */
  public PojoPropertyDescriptorImpl(String propertyName) {

    super();
    this.name = propertyName;
    this.read = null;
    this.write = null;
    this.add = null;
  }

  /**
   * This method gets the programmatic (technical) name of this property.
   * 
   * @see java.beans.PropertyDescriptor#getName()
   * 
   * @return the property name.
   */
  public String getName() {

    return this.name;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoPropertyDescriptor#getAccessor(net.sf.mmm.util.pojo.api.PojoPropertyAccessMode)
   */
  public AbstractPojoPropertyAccessor getAccessor(PojoPropertyAccessMode mode) {

    switch (mode) {
      case READ:
        return this.read;
      case WRITE:
        return this.write;
      case ADD:
        return this.add;
      default :
        // actually an illegal argument...
        return null;
    }
  }

  /**
   * This method sets the given <code>accessor</code>.
   * 
   * @see #getAccessor(PojoPropertyAccessMode)
   * 
   * @param accessor
   *        is the accessor to set.
   */
  protected void setAccessor(AbstractPojoPropertyAccessor accessor) {

    switch (accessor.getAccessMode()) {
      case READ:
        this.read = accessor;
        break;
      case WRITE:
        this.write = accessor;
        break;
      case ADD:
        this.add = accessor;
        break;
      default :
        // actually an illegal argument...
    }
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoPropertyDescriptor#getAddAccess()
   */
  public AbstractPojoPropertyAccessor getAddAccess() {

    return this.add;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoPropertyDescriptor#getReadAccess()
   */
  public AbstractPojoPropertyAccessor getReadAccess() {

    return this.read;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoPropertyDescriptor#getWriteAccess()
   */
  public AbstractPojoPropertyAccessor getWriteAccess() {

    return this.write;
  }

}
