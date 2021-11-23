package Utilidades;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class UBean
{
    public ArrayList<Field> obtenerAtributos(@NotNull Object objeto){
        ArrayList<Field> listaAtributos = new ArrayList<Field>();

        Field[] atributos = objeto.getClass().getDeclaredFields();

        for (Field atributo : atributos){
            listaAtributos.add(atributo);
        }
        
        return listaAtributos;
    }
    
    public Object obtenerAnotaciones(@NotNull Object object, Class anotacion){
        Object objetoAnotacion = new Object();
        try{
            objetoAnotacion = object.getClass().getAnnotation(anotacion);
            return objetoAnotacion;
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
        }
        
        return objetoAnotacion;
    }

    public Object ejecutarSet(@NotNull Object object, @NotNull String att, Object valor){
        Method[] methods = object.getClass().getDeclaredMethods();
        String attribute = att.substring(0,1).toUpperCase() + att.substring(1);

        try{
            for (Method metodo: methods){
                if(metodo.getName().startsWith("set" + attribute)){
                    metodo.invoke(object, valor);
                }
            }
        }
        catch (InvocationTargetException e){
            e.printStackTrace();
        }
        catch (IllegalAccessException e){
            e.printStackTrace();
        }
        
        return object;
    }

    public Object ejecutarGet(@NotNull Object object, @NotNull String att){
        Object value = new Object();
        Method[] methods = object.getClass().getDeclaredMethods();
        String attribute = att.substring(0,1).toUpperCase() + att.substring(1);

        try{
            for (Method method: methods){
                if(method.getName().startsWith("get" + attribute)){
                    value = method.invoke(object);
                }
            }
        }
        catch (IllegalAccessException e){
            e.printStackTrace();
        }
        catch (InvocationTargetException e){
            e.printStackTrace();
        }
        
        return value;
    }
}