package net.sachau.zarrax.engine;

import net.sachau.zarrax.card.catalog.Catalog;
import org.reflections.Reflections;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {


    private static ApplicationContext applicationContext;

    private Map<Class<?>, Object> contextData = new ConcurrentHashMap<>();
    private double width, height;

    private ApplicationContext() {
    }

    public static ApplicationContext getInstance() {

        if (applicationContext == null) {
            applicationContext = new ApplicationContext();
        }
        return applicationContext;
    }

    public static void init(double width, double height) throws Exception {
        getInstance().start(width, height);
    }


    public  void start(double width, double height) {
        this.height = height;
        this.width = width;

        {
            Reflections reflections = new Reflections();
            Set<Class<?>> components = reflections.getTypesAnnotatedWith(GameData.class);

            for (Class<?> component : components) {

                createComponent(component);

            }
        }


        {
            Reflections reflections = new Reflections();
            Set<Class<?>> components = reflections.getTypesAnnotatedWith(GameComponent.class);

            for (Class<?> component : components) {

                createComponent(component);
            }
        }



//            {
//                Method[] methods = component.getClass().getDeclaredMethods();
//                for (Method method : methods) {
//                    Annotation[] annotations = method.getAnnotations();
//                    for (Annotation a : annotations) {
//                        if (a.annotationType().equals(StartUp.class)) {
//                            method.invoke(component);
//                            // only one startup method
//                            return;
//                        }
//                    }
//                }
//            }


    }

    private Object createComponent(Class<?> component) {
        try {
            if (contextData.get(component.getClass()) != null) {
                return contextData.get(component.getClass());
            }
            Object obj = component.newInstance();
            contextData.put(component, obj);
            createResources(obj);
            callInitMethod(obj);
            contextData.put(component, obj);
            return obj;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Map<Class<?>, Object> getContextData() {
        return contextData;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public static Catalog getCatalog() {
        return (Catalog) getInstance().getContextData().get(Catalog.class);
    }

    private void callInitMethod(Object component) {
        Method[] methods = component.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation a : annotations) {
                if  (a.annotationType().equals(PostConstruct.class)) {
                    try {
                        method.setAccessible(true);
                        method.invoke(component);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private void createResources(Object component) {
        Field[] fields = component.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation a : annotations) {
                if  (a.annotationType().equals(Resource.class)) {
                    Object resource = createComponent(field.getType());
                    try {
                        field.setAccessible(true);
                        System.out.print(component.getClass() + "->" + resource.getClass());
                        field.set(component, resource);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
