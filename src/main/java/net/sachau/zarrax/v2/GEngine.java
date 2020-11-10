package net.sachau.zarrax.v2;

import javafx.collections.FXCollections;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.UniqueCardList;
import net.sachau.zarrax.card.effect.CardEffect;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.card.level.Levels;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.card.type.Monster;
import net.sachau.zarrax.engine.*;
import net.sachau.zarrax.gui.screen.Autowired;
import net.sachau.zarrax.map.World;
import net.sachau.zarrax.util.DiceUtils;
import org.reflections.Reflections;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class GEngine implements Observer {

    private static GEngine engine;

    private GState state;
    private GGraphics graphics;

    private Map<Class<?>, Object> beans = new HashMap<>();
    private GEvents events;

    private GEngine() {
        events = new GEvents();
        state = new GState();
        graphics = new GGraphics();
        events.addObserver(this);

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
        graphics.show();
        Logger.debug("init engine done, graphics.initialized=" + initialized());

        Logger.debug("loading gameData beans ...");


        beans.put(GState.class, state);
        beans.put(GEngine.class, this);
        beans.put(GGraphics.class, graphics);
        beans.put(GEvents.class, events);


        Reflections reflections = new Reflections();
        Set<Class<?>> dataBeanClasses = reflections.getTypesAnnotatedWith(GameData.class);

        for (Class<?> dataBeanClass : dataBeanClasses) {

            Object dataBean = createBean(dataBeanClass);
            beans.put(dataBeanClass, dataBean);
            postConstruct(dataBean);

            beans.put(dataBeanClass, dataBean);

        }

        Logger.debug("init GUI components ...");

        Set<Class<?>> guiClasses = reflections.getTypesAnnotatedWith(GuiComponent.class);

        Class<?> mainguiClass = null;
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
        graphics.getLoadScreen()
                .getChildren()
                .clear();

        graphics.getLoadScreen()
                .getChildren()
                .add((HBox) beans.get(mainguiClass));
        events.send(GameEventContainer.Type.WELCOME);


    }

    public boolean initialized() {
        return graphics.isInitialized();
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) getInstance().beans.get(clazz);
    }

    private void postConstruct(Object bean) {
        Method[] methods = bean.getClass()
                .getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation a : annotations) {
                if (a.annotationType()
                        .equals(PostConstruct.class)) {
                    try {
                        method.setAccessible(true);
                        Logger.debug(bean.getClass() + " invoking " + method.getName());
                        method.invoke(bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Object createBean(Class beanClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object bean;
        if (( bean = beans.get(beanClass)) != null) {
            // already created
            return bean;
        }
        Constructor<?>[] constructors = beanClass.getDeclaredConstructors();
        if (constructors != null && constructors.length > 0) {
            for (Constructor constructor : constructors) {
                Annotation annotation = constructor.getAnnotation(Autowired.class);
                if (annotation != null) {
                    Object[] parameters = new Object[constructor.getParameterTypes().length];
                    int i = 0;
                    for (Class parameterType : constructor.getParameterTypes()) {
                        System.out.println(beanClass + " Type : " + parameterType);
                        Object existingBean = beans.get(parameterType);
                        if (existingBean != null) {
                            parameters[i] = existingBean;
                        } else {
                            Object newBean = createBean(parameterType);
                            parameters[i] = newBean;
                        }
                        i++;
                    }
                    Logger.debug("trying to create " + beanClass);
                    bean = constructor.newInstance(parameters);
                    beans.put(beanClass, bean);
                    Logger.debug("bean " + beanClass + " created");
                    return bean;
                }
            }
        }
        // default behaviour
        bean = beanClass.newInstance();
        beans.put(beanClass, bean);
        beans.put(beanClass, bean);
        return bean;

    }

    public void createGame() {
        state.setPlayer(new Player());
        DiceUtils.createRandomParty(state.getPlayer());
        state.setWorld(new World());
    }


    public void gameOver() {
        // TODO msachau
    }


    @Override
    public void update(Observable o, Object arg) {
        switch (events.getType(arg)) {
            case WELCOME:
                Logger.debug("WELCOME!");
                break;
            case PARTYDONE: {
                for (Card c : state.getPlayer()
                        .getParty()) {
                    Character character = (Character) c;
                    for (Card startingCard : character.getStartingCards()) {
                        startingCard.setOwner(state.getPlayer());
                        state.getPlayer()
                                .getDraw()
                                .addToDrawPile(startingCard);
                    }
                }

                state.getPlayer()
                        .initHand();
                return;
            }
            case START_TURN: {
                state.getPlayer()
                        .setMoves(Player.MOVES);
                triggerStartEffects(GameEventContainer.Type.START_TURN);
                events.send(GameEventContainer.Type.START_MOVEMENT);
                return;
            }

            case END_MOVEMENT: {
                events.send(GameEventContainer.Type.PREPARE_ENCOUNTER);
                return;
            }
            case PREPARE_ENCOUNTER: {
                {
                    // make sure every card in the party is owned by player and has PERMANENT keyword
                    for (Card card : state.getPlayer()
                            .getParty()) {
                        card.setOwner(state.getPlayer());
                        card.setVisible(true);
                        card.addKeyword(Keyword.PERMANENT);
                    }

                    // generate an encounter and assign it to player
                    List<Card> hazards = state.getWorld()
                            .getHazards(state.getPlayer()
                                    .getX(), state.getPlayer()
                                    .getY());
                    if (hazards == null || hazards.size() == 0) {
                        events.send(GameEventContainer.Type.END_ENCOUNTER);
                        return;
                    }
                    state.getPlayer()
                            .setHazards(FXCollections.observableArrayList(hazards));

                    // check if every card in hazards is visible and not owned by player
                    for (Card card : state.getPlayer()
                            .getHazards()) {
                        card.setVisible(true);
                        card.setOwner(null);
                    }

                    // build initiativeDeck
                    state.setInitiativeOrder(new ArrayList<>());
                    state.getInitiativeOrder()
                            .addAll(state.getPlayer()
                                    .getParty());
                    state.getInitiativeOrder()
                            .addAll(state.getPlayer()
                                    .getHazards());
                    Collections.shuffle(state.getInitiativeOrder());
                    state.setCurrentInitiative(0);

                    events.send(GameEventContainer.Type.GUI_STARTENCOUNTER);

                }

                return;
            }

            case GUI_STARTENCOUNTER: {
                events.send(GameEventContainer.Type.START_ENCOUNTER);
            }

            case START_ENCOUNTER: {
                //trigger phase effects
                triggerStartEffects(GameEventContainer.Type.START_ENCOUNTER);
                events.send(GameEventContainer.Type.START_CARDPHASE);
                return;
            }

            case START_CARDPHASE:
                startCardPhase();
                return;

            case NEXT_ACTION: {

                Card card = state.getCurrentCard();
                if (card.getOwner() instanceof Player) {
                    Logger.debug("waiting for " + state.getCurrentCard());
                    events.send(GameEventContainer.Type.WAITING_FOR_PLAYER_ACTION);

                } else {
                    Logger.debug("AI for " + card);
                    GameAI.execute(state.getPlayer(), card);
                    events.send(GameEventContainer.Type.ACTION_DONE);
                }

                return;
            }

            case PLAYERACTIONDONE:
                events.send(GameEventContainer.Type.ACTION_DONE);
                return;
            case ACTION_DONE: {
                if (state.getCurrentInitiative() < state.getInitiativeOrder().size() - 1) {
                    int ini = state.getCurrentInitiative() + 1;
                    state.setCurrentInitiative(ini);
                    events.send(GameEventContainer.Type.NEXT_ACTION);
                } else {
                    events.send(GameEventContainer.Type.END_CARDPHASE);
                }
                return;
            }

            case END_CARDPHASE:
                triggerStartEffects(GameEventContainer.Type.END_CARDPHASE);
                state.getPlayer().resetHand();

                for (Card card : state.getPlayer().getParty()) {
                    card.getKeywords()
                            .remove(Keyword.EXHAUSTED);
                }
                for (Card card : state.getPlayer().getHand()) {
                    card.getKeywords()
                            .remove(Keyword.EXHAUSTED);
                }
                for (Card card : state.getPlayer().getDraw()
                        .getDiscardPile()) {
                    card.getKeywords()
                            .remove(Keyword.EXHAUSTED);
                }

                if (regenerateMonstersAndCheckDefeated()) {
                    events.send(GameEventContainer.Type.END_TURN);
                } else {
                    events.send(GameEventContainer.Type.START_CARDPHASE);
                }

                return;
            case END_TURN: {
                Levels.getInstance()
                        .gainLevel(state.getPlayer());
                events.send(GameEventContainer.Type.START_TURN);
                return;
            }
            case CHARACTERDEATH: {
                int totalHealth = 0;
                Card deadCard = (Card) events.getData();

                for (CardEffect cardEffect : deadCard.getEffects()) {
                    for (Card card : state.getPlayer().getHazards()) {
                        card.removeEffect(cardEffect);
                    }
                    for (Card card : state.getPlayer().getParty()) {
                        card.removeEffect(cardEffect);
                    }
                }

                for (Card card : state.getPlayer().getParty()) {
                    if (card instanceof Character) {
                        totalHealth += Math.max(0, card.getHits());
                    }
                }
                Card hazardCard = null;
                for (Card card : state.getPlayer().getHazards()) {
                    if (card.getId() == deadCard.getId()) {
                        hazardCard = deadCard;
                        break;
                    }
                }
                if (hazardCard != null) {
                    state.getPlayer().getHazards()
                            .remove(hazardCard);
                }


                // ALL DEAD
                if (totalHealth <= 0) {
                    Logger.debug("all characters are dead - game over");
                    events.send(GameEventContainer.Type.GAMEOVER);
                    return;
                }

                Card turnCard = null;
                int initiativeId = -1, i = 0;
                if (state.getInitiativeOrder() != null) {
                    for (Card card : state.getInitiativeOrder()) {
                        if (card.getId() == deadCard.getId()) {
                            turnCard = card;
                            initiativeId = i;
                        }
                        i++;

                    }
                }
                if (turnCard != null) {
                    state.getInitiativeOrder().remove(turnCard);

                }

                if (initiativeId >= state.getInitiativeOrder().size()) {
                    events.send(GameEventContainer.Type.END_CARDPHASE);
                }

                return;
            }
            default:
                return;

        }
    }

    public void triggerStartEffects(GameEventContainer.Type eventType) {
        state.getPlayer().setSpawnCards(new UniqueCardList());
        for (Card card : state.getPlayer().getHazards()) {
            card.triggerStartEffects(eventType);
        }
        for (Card card : state.getPlayer().getParty()) {
            card.triggerStartEffects(eventType);
        }
        if (state.getPlayer().getSpawnCards()
                .size() > 0) {
            state.getPlayer().getHazards()
                    .addAll(state.getPlayer().getSpawnCards());
        }
    }

    public void triggerEndEffects(GameEventContainer.Type eventType) {

        for (Card card : state.getPlayer().getHazards()) {
            card.triggerEndEffects(eventType);
        }
        for (Card card : state.getPlayer().getParty()) {
            card.triggerEndEffects(eventType);
        }
    }


    private void startCardPhase() {
        state.setCurrentInitiative(0);
        triggerStartEffects(GameEventContainer.Type.START_CARDPHASE);
        events.send(GameEventContainer.Type.NEXT_ACTION);
    }

    private boolean regenerateMonstersAndCheckDefeated() {
        if (state.getPlayer().getHazards() == null ||state.getPlayer().getHazards().size() == 0) {
            return true;
        }
        int monstersAlive = 0;
        for (Card card : state.getPlayer().getHazards()) {
            if (card instanceof Monster) {
                Monster monster = (Monster) card;
                if (monster.isAlive()) {
                    if (monster.getRegenerate() > 0) {
                        monster.heal(monster, monster.getRegenerate());
                    }
                    monstersAlive ++;
                }
            }
        }
        // all monsters are dead
        return monstersAlive == 0;
    }
}
