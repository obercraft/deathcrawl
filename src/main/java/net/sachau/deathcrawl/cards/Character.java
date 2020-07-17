package net.sachau.deathcrawl.cards;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Character {

    String uniqueId() default "";
    Class<? extends Card>[] startingDeck()  default {};
}
