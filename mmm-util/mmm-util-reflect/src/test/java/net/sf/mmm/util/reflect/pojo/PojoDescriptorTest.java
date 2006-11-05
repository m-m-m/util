/* $Id$ */
package net.sf.mmm.util.reflect.pojo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.sf.mmm.util.reflect.pojo.api.PojoDescriptorBuilder;
import net.sf.mmm.util.reflect.pojo.api.PojoDescriptor;
import net.sf.mmm.util.reflect.pojo.api.PojoPropertyAccessor;
import net.sf.mmm.util.reflect.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.reflect.pojo.impl.PojoDescriptorBuilderImpl;

import junit.framework.TestCase;

/**
 * This is the test-case for {@link net.sf.mmm.util.reflect.pojo}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PojoDescriptorTest extends TestCase {

  /**
   * The constructor.
   */
  public PojoDescriptorTest() {

    super();
  }
  
  private void checkProperty(PojoDescriptor<MyPojo> pojoDescriptor, String propertyName,
      Class readType, Class writeType) {

    PojoPropertyDescriptor propertyDescriptor = pojoDescriptor
        .getPropertyDescriptor(propertyName);
    assertNotNull(propertyDescriptor);
    assertEquals(propertyName, propertyDescriptor.getName());
    PojoPropertyAccessor accessor;
    // test read accessor
    accessor = propertyDescriptor.getReadAccess();
    if (readType == null) {
      assertNull(accessor);
    } else {
      assertEquals(propertyName, accessor.getName());
      assertEquals(readType, accessor.getRawType());
      assertEquals(readType, accessor.getMethod().getReturnType());
      // getter must be non-arg method
      assertEquals(0, accessor.getMethod().getParameterTypes().length);
    }
    // test write accessor
    accessor = propertyDescriptor.getWriteAccess();
    if (writeType == null) {
      assertNull(accessor);
    } else {
      assertEquals(propertyName, accessor.getName());
      assertEquals(writeType, accessor.getRawType());
      // setter method must take one argument
      assertEquals(1, accessor.getMethod().getParameterTypes().length);      
      assertEquals(writeType, accessor.getMethod().getParameterTypes()[0]);
    }
  }

  @Test
  public void testPojoDescriptor() throws Exception {

    PojoDescriptorBuilder factory = new PojoDescriptorBuilderImpl();
    PojoDescriptor<MyPojo> pojoDescriptor = factory.getDescriptor(MyPojo.class);
    assertEquals(MyPojo.class, pojoDescriptor.getPojoType());
    MyPojo pojoInstance = new MyPojo();
    // test property "class"
    assertEquals(MyPojo.class, pojoDescriptor.getProperty(pojoInstance, "class"));
    // test property "name"
    checkProperty(pojoDescriptor, "name", String.class, String.class);
    String name = "Emma";
    pojoDescriptor.setProperty(pojoInstance, "name", name);
    String retrievedName = (String) pojoDescriptor.getProperty(pojoInstance, "name");
    assertEquals(name, retrievedName);
    // test property "port"
    checkProperty(pojoDescriptor, "port", Integer.class, int.class);
    int port = 4242;
    assertNull(pojoDescriptor.getProperty(pojoInstance, "port"));
    pojoDescriptor.setProperty(pojoInstance, "port", port);
    Integer retrievedPort = (Integer) pojoDescriptor.getProperty(pojoInstance, "port");
    assertEquals(port, retrievedPort.intValue());
    // test property "flag"
    checkProperty(pojoDescriptor, "flag", Boolean.class, boolean.class);
    boolean flag = true;
    assertNull(pojoDescriptor.getProperty(pojoInstance, "flag"));
    pojoDescriptor.setProperty(pojoInstance, "flag", Boolean.valueOf(flag));
    assertEquals(Boolean.valueOf(flag), pojoDescriptor.getProperty(pojoInstance, "flag"));
    // test property "items"/"item"
    checkProperty(pojoDescriptor, "items", List.class, List.class);
    
  }

  public static class MyPojo {

    private String name;

    private Integer port;

    private Boolean flag;

    private List<String> items;

    public MyPojo() {

      super();
      this.name = null;
      this.port = null;
      this.flag = null;
      this.items = new ArrayList<String>();
    }

    public String getName() {

      return this.name;
    }

    public void setName(String name) {

      this.name = name;
    }

    public Integer getPort() {

      return this.port;
    }

    public void setPort(int port) {

      this.port = Integer.valueOf(port);
    }

    public Boolean hasFlag() {

      return this.flag;
    }

    public void setFlag(boolean newFlag) {

      this.flag = Boolean.valueOf(newFlag);
    }

    public List<String> getItems() {

      return this.items;
    }

    public void addItem(String item) {

      this.items.add(item);
    }

    public void setItems(List<String> items) {

      this.items = items;
    }

  }

}
