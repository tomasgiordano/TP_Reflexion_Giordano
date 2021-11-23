package Anotaciones;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Compuesto
{
    @SuppressWarnings("rawtypes")
	Class clase();
}
