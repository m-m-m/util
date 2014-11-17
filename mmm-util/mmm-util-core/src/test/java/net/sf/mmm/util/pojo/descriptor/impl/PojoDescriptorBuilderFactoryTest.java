package net.sf.mmm.util.pojo.descriptor.impl;

import java.util.Collection;

import junit.framework.Assert;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;

import org.junit.Test;

public class PojoDescriptorBuilderFactoryTest {
	
	private String testField;

	@Test
	public void testAbstractPojoDescriptorInstanceCreatedOnlyOnce() {
		PojoDescriptorBuilderFactory instance = PojoDescriptorBuilderFactoryImpl.getInstance();
		PojoDescriptorBuilder descriptorBuilder = instance.createPrivateFieldDescriptorBuilder();
		PojoDescriptor<?> descriptor = descriptorBuilder.getDescriptor(getClass());

		Collection<?> propertyDescriptors = descriptor.getPropertyDescriptors();
		
		Assert.assertNotNull(propertyDescriptors);
		Assert.assertEquals(1, propertyDescriptors.size());
		PojoPropertyDescriptor testFieldProperty = (PojoPropertyDescriptor) propertyDescriptors.iterator().next();
		Assert.assertEquals("testField", testFieldProperty.getName());
		Assert.assertEquals(getClass().getDeclaredFields()[0], testFieldProperty.getField());
	}

}
