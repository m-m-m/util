/* $Id$ */
package net.sf.mmm.util.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * This is the test case for the class {@link AnnotationUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class AnnotationUtilTest extends TestCase {

    private static final String FOO_IF = "interface";

    private static final String FOO_CLASS = "Foo-class";

    private static final String BAR_CLASS = "Foo-class";

    /**
     * The constructor.
     */
    public AnnotationUtilTest() {

        super();
    }

    @Test
    public static void testMethodAnnotation() throws Exception {

        // annotation with retention "runtime" ?
        assertTrue(AnnotationUtil.isRuntimeAnnotation(MyAnnotation.class));
        assertFalse(AnnotationUtil.isRuntimeAnnotation(SuppressWarnings.class));
        assertTrue(AnnotationUtil.isRuntimeAnnotation(Inherited.class));

        // annotation target type
        assertTrue(AnnotationUtil.isAnnotationForType(MyAnnotation.class, ElementType.TYPE));
        assertTrue(AnnotationUtil.isAnnotationForType(Inherited.class, ElementType.ANNOTATION_TYPE));
        assertFalse(AnnotationUtil.isAnnotationForType(Inherited.class, ElementType.TYPE));

        // annotation inheritence
        MyAnnotation classAnnotation = AnnotationUtil.getClassAnnotation(Bar.class,
                MyAnnotation.class);
        assertNotNull(classAnnotation);
        assertEquals(FOO_CLASS, classAnnotation.value());
        Method barMethod = Bar.class.getMethod("foo", ReflectionUtil.NO_PARAMETERS);
        MyAnnotation methodAnnotation = AnnotationUtil.getMethodAnnotation(barMethod,
                MyAnnotation.class);
        assertNotNull(methodAnnotation);
        assertEquals(FOO_IF, methodAnnotation.value());
        
        
        assertNotNull(FooIF.class.getAnnotation(SomeAnnotation.class));
        
        // this should work... bug in eclipse, jdk 5.0, ...???
        SomeAnnotation typeAnnotation = AnnotationUtil.getTypeAnnotation(Bar.class,
                SomeAnnotation.class);
        assertNotNull(typeAnnotation);
        assertEquals(FOO_IF, typeAnnotation.value());

    }

    @Retention(RetentionPolicy.RUNTIME)
    private @interface MyAnnotation {

        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    private @interface SomeAnnotation {

        String value();
    }

    @SomeAnnotation(FOO_IF)
    private static interface FooIF {

        @MyAnnotation(FOO_IF)
        void foo();

    }

    private static interface BarIF extends FooIF {

    }

    @MyAnnotation(FOO_CLASS)
    private static class Foo implements BarIF, Comparable<Foo> {

        public void foo() {

        }

        public int compareTo(Foo o) {

            return 0;
        }

    }

    private static class Bar extends Foo {

        public void foo() {

        }
    }

}
