package net.sachau.zarrax.v2;

import javafx.stage.Stage;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.engine.*;
import net.sachau.zarrax.gui.screen.Autowired;
import net.sachau.zarrax.map.World;
import org.reflections.Reflections;
import sun.rmi.runtime.Log;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GEngine {

    private static GEngine engine;

    private GGraphics graphics;

    private Map<Class, Object> beans = new HashMap<>();
    private GEvents events;

    private GEngine() {
            graphics = new GGraphics();
            events = new GEvents();
    }

    public static GEngine getInstance() {
        if (engine == null) {
            engine = new GEngine();
        }
        return engine;
    }

    public void init(Stage primaryStage) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Logger.debug("init engine");
        graphics.init(primaryStage);
        Logger.debug("init engine done, graphics.initialized=" + initialized());

        Logger.debug("loading gameData beans ...");


        beans.put(GEngine.class, this);

        Reflections reflections = new Reflections();
        Set<Class<?>> dataBeanClasses = reflections.getTypesAnnotatedWith(GameData.class);

        for (Class<?> dataBeanClass : dataBeanClasses) {

            Object dataBean = dataBeanClass.newInstance();
            postConstruct(dataBean);

            beans.put(dataBeanClass, dataBean);

        }

        Logger.debug("init GUI components ...");

        Set<Class<?>> guiClasses = reflections.getTypesAnnotatedWith(GuiComponent.class);

        Class mainguiClass = null;
        for (Class<?> guiClass : guiClasses) {
            MainScreen mainScreen = guiClass.getAnnotation(MainScreen.class);
            if (mainScreen != null) {
                if (mainguiClass == null) {
                    mainguiClass = guiClass;
                } else {
                    throw new RuntimeException("@MainScreen already defined");
                }
            } else {
                createBean(guiClass);
            }
        }

        if (mainguiClass != null) {
            createBean(mainguiClass);
        }

        graphics.show();
        primaryStage.setScene();
        events.send(GameEventContainer.Type.WELCOME);



    }

    public boolean initialized() {
        return graphics.isInitialized();
    }

    public GGraphics graphics() {
        return graphics;
    }

    public void send(GameEventContainer.Type type) {
        getInstance().events.send(new GameEventContainer(type));
    }
    public void send(GameEventContainer stage) {
        getInstance().events.send(stage);
    }


    private void postConstruct(Object bean) {
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation a : annotations) {
                if  (a.annotationType().equals(PostConstruct.class)) {
                    try {
                        method.setAccessible(true);
                        Logger.debug(bean.getClass() + " invoking "  + method.getName());
                        method.invoke(bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void createBean(Class beanClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (beans.get(beanClass) != null) {
            // already created
            return;
        }
        Constructor<?>[] constructors = beanClass.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            Annotation annotation = constructor.getAnnotation(Autowired.class);
            if (annotation != null) {
                Object [] parameters = new Object[constructor.getParameterTypes().length];
                int i = 0;
                for (Class parameterType :constructor.getParameterTypes()) {
                    System.out.println(beanClass + " Type : " + parameterType);
                    Object bean = beans.get(parameterType);
                    if (bean != null) {
                        parameters[i] = bean;
                    } else {
                        createBean(parameterType);
                        parameters[i] = beans.get(parameterType);
                    }
                    i++;
                }
                Logger.debug("trying to create " + beanClass);
                beans.put(beanClass, constructor.newInstance(parameters));
                Logger.debug("bean " + beanClass + " created");
            } else {
                throw new RuntimeException("bean " + beanClass + " has no @Autowired");
            }
        }
    }

    public void createGame() {
        // TODO msachau
    }

    public World getWorld() {
        // TODO msachau
        return null;
    }

    public Player getPlayer() {
        // TODO msachau
        return null;
    }

    public void gameOver() {
        // TODO msachau
    }
}
