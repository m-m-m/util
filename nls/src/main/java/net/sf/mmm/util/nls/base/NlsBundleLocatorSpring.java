/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.ClassReader;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.reflect.api.ReflectionUtil;

/**
 * Implementation of {@link NlsBundleLocator} using {@link ReflectionUtil}.
 *
 * @since 7.6.0
 */
public class NlsBundleLocatorSpring implements NlsBundleLocator {

  private static final Logger LOG = LoggerFactory.getLogger(NlsBundleLocatorSpring.class);

  @Override
  public Iterable<Class<? extends NlsBundle>> findBundles() {

    try {
      PathMatchingResourcePatternResolver scanner = new PathMatchingResourcePatternResolver();
      Resource[] resources = scanner.getResources("classpath*:**/NlsBundle*Root.class");
      BundleClassVisitor visitor = new BundleClassVisitor();
      for (Resource resource : resources) {
        try (InputStream in = resource.getInputStream()) {
          new ClassReader(in).accept(visitor, 0);
        }
      }
      return visitor.classes;
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private static class BundleClassVisitor extends ClassVisitor {

    private Set<Class<? extends NlsBundle>> classes = new HashSet<>();

    /**
     * The constructor.
     */
    private BundleClassVisitor() {

      super(Opcodes.ASM6);
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

      if ((access & Opcodes.ACC_INTERFACE) == 0) {
        return; // not an interface
      }
      if ((interfaces == null) || (interfaces.length == 0)) {
        return; // not extending any interfaces
      }
      String className = name.replace('/', '.');
      try {
        Class<?> clazz = Class.forName(className);
        if (NlsBundle.class.isAssignableFrom(clazz)) {
          this.classes.add((Class) clazz);
        }
      } catch (Exception e) {
        LOG.debug("Failed to load class {}", className, e);
      }
    }
  }

}
