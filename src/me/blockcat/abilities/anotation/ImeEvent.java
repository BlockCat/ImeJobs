package me.blockcat.abilities.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.bukkit.event.Event;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ImeEvent {
	
	public Class<? extends Event> event();

}
