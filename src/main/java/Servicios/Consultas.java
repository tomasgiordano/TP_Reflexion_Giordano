package Servicios;

import Anotaciones.*;
import Utilidades.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Consultas
{
    public static Object guardar(Object object)
    {
        try{
            UBean ubean = new UBean();

            Tabla table = (Tabla) ubean.obtenerAnotaciones(object, Tabla.class);
            ArrayList<Field> atributos = ubean.obtenerAtributos(object);
            
            String query = "INSERT INTO " + table.nombre();
            String columnas = " (";
            String valores = " (";

            for (Field atributo: atributos){
                if(atributo.getAnnotation(Id.class) != null){
                    continue;
                }
                System.out.println(atributo.getName());
                Columna columna = atributo.getAnnotation(Columna.class);
                columnas += columna.nombre() + ",";

                if(atributo.getAnnotation(Compuesto.class) != null){
                    Object idAttribute = Consultas.guardarCompuesto(object,atributo);
                    valores += idAttribute + ",";
                }
                else if(atributo.getType().equals(Integer.class)){
                    valores += ubean.ejecutarGet(object,atributo.getName()) + ",";
                }
                else{
                    valores += "'" + ubean.ejecutarGet(object,atributo.getName()) + "',";
                }
            }
            
            columnas = columnas.substring(0,columnas.length()-1);
            columnas += ") ";
            
            valores = valores.substring(0,valores.length()-1);
            valores += ")";

            query += columnas + "valores" + valores + ";";
            System.out.println(query);

            PreparedStatement insert = UConexion.getInstance().getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            insert.execute();
            
            ResultSet generatedKeys = insert.getGeneratedKeys();
            if(generatedKeys.first()){
                for (Field attribute: atributos){
                    if(attribute.getAnnotation(Id.class) != null){
                        object = ubean.ejecutarSet(object,attribute.getName(),generatedKeys.getObject(1));
                    }
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (ClassCastException ex){
            ex.printStackTrace();
        }
        return object;
    }

    public static Object guardarModificar(Object object){
        UBean ubean = new UBean();
        ArrayList<Field> atributos = ubean.obtenerAtributos(object);
       
        Object id = new Object();

        for (Field atributo: atributos){
            if(atributo.getAnnotation(Id.class) != null){
                id = ubean.ejecutarGet(object,atributo.getName());
            }
        }
        if(Consultas.obtenerPorId(object.getClass(),id) != null){
            object = Consultas.modificar(object);
        }
        else{
            object = Consultas.guardar(object);
        }
        return object;
    }

    public static Object modificar(Object object){
        try{
            UBean ubean = new UBean();
            Tabla tabla = (Tabla) ubean.obtenerAnotaciones(object, Tabla.class);
            ArrayList<Field> atributos = ubean.obtenerAtributos(object);

            String query = "UPDATE " + tabla.nombre() + " SET ";
            String filtro = " WHERE ";

            for (Field atributo: atributos)
            {
                Columna columna = atributo.getAnnotation(Columna.class);
                if(atributo.getAnnotation(Id.class) != null)
                {
                    filtro += columna.nombre() +  " = '" + ubean.ejecutarGet(object,atributo.getName()) + "';";
                }
                else if(atributo.getClass().equals(Integer.class))
                {
                    query += columna.nombre() + " = " + ubean.ejecutarGet(object,atributo.getName()) + ",";
                }
                else
                {
                    query += columna.nombre() + " = '" + ubean.ejecutarGet(object,atributo.getName()) + "',";
                }
            }
            query = query.substring(0,query.length()-1);
            PreparedStatement update = UConexion.getInstance().getConnection().prepareStatement(query + filtro);

            update.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return object;
    }

    public static @Nullable Object obtenerPorId(@SuppressWarnings("rawtypes") @NotNull Class clase, Object id)
    {
        try
        {
            @SuppressWarnings("unchecked")
			Tabla table = (Tabla) clase.getAnnotation(Tabla.class);
            
            Field[] atributos = clase.getDeclaredFields();
            
            @SuppressWarnings("rawtypes")
			Constructor[] constructores = clase.getConstructors();
            

            @SuppressWarnings("rawtypes")
			Optional<Constructor> constructor = Arrays.stream(constructores).filter(con -> con.getParameterCount() == atributos.length).findFirst();
            Object[] parametros = constructor.get().getParameterTypes();
            
            String query = "SELECT ";
            String atributo_id = "";
            for (Field atributo: atributos)
            {
                String nombreAtributo = atributo.getAnnotation(Columna.class).nombre();
                query += nombreAtributo + ",";
                if(atributo.getAnnotation(Id.class) != null)
                {
                    atributo_id = nombreAtributo;
                }
            }
            query = query.substring(0,query.length()-1);
            query += " FROM " + table.nombre() + " WHERE " + atributo_id + " = " + id + ";";

            System.out.println(query);
            System.out.println("SELECT");
            PreparedStatement select = UConexion.getInstance().getConnection().prepareStatement(query);
            ResultSet result = select.executeQuery();
            Object[] arguments = new Object[parametros.length];
            while(result.next())
            {
                for (int i = 0; i < atributos.length; i++)
                {
                    arguments[i] = result.getObject(atributos[i].getAnnotation(Columna.class).nombre());
                    if(i == 0)
                    {
                        arguments[i] = new BigInteger(String.valueOf(arguments[i]));
                    }
                }
            }

            return constructor.get().newInstance(arguments);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Object guardarCompuesto(Object object, @NotNull Field attribute)
    {
        try
        {
            UBean ubean = new UBean();
            attribute.trySetAccessible();
            Object attributeObject = attribute.get(object);
            attributeObject = Consultas.guardar(attributeObject);
            Field nombreIdCompuesto = Arrays.stream(attributeObject.getClass().getDeclaredFields()).filter(f -> f.getAnnotation(Id.class) != null).findFirst().get();
            Object idAttributeObject = ubean.ejecutarGet(attributeObject, nombreIdCompuesto.getName());

            return idAttributeObject;
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return new Object();
    }

    @SuppressWarnings("rawtypes")
	public static @Nullable ArrayList obtenerTodos(@NotNull Class clase)
    {
        try
        {
            ArrayList<Object> list = new ArrayList<Object>();
            
            @SuppressWarnings("unchecked")
			Tabla table = (Tabla) clase.getAnnotation(Tabla.class);
            Field[] atributos = clase.getDeclaredFields();
            
            Constructor[] constructores = clase.getConstructors();
            Optional<Constructor> constructor = Arrays.stream(constructores).filter(con -> con.getParameterCount() == atributos.length).findFirst();
            Object[] parametros = constructor.get().getParameterTypes();
            
            String query = "SELECT * FROM " + table.nombre() + ";";
            System.out.println(query);

            PreparedStatement select = UConexion.getInstance().getConnection().prepareStatement(query);
            ResultSet result = select.executeQuery();

            while(result.next())
            {
                Object[] arguments = new Object[parametros.length];

                for (int i = 0; i < atributos.length; i++)
                {
                    arguments[i] = result.getObject(atributos[i].getAnnotation(Columna.class).nombre());
                    if(i == 0)
                    {
                        arguments[i] = new BigInteger(String.valueOf(arguments[i]));
                    }
                }
                list.add(constructor.get().newInstance(arguments));
            }
            return list;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean eliminar(Object object)
    {
        try
        {
            UBean ubean = new UBean();
            ArrayList<Field> atributos = ubean.obtenerAtributos(object);
            Tabla tabla = (Tabla) ubean.obtenerAnotaciones(object,Tabla.class);

            String query = "DELETE FROM " + tabla.nombre() + " WHERE ";

            for (Field atributo: atributos)
            {
                if(atributo.getAnnotation(Id.class) != null)
                {
                    query += atributo.getAnnotation(Columna.class).nombre() + " = " + ubean.ejecutarGet(object,atributo.getName());
                }
            }
            System.out.println("DELETE");
            PreparedStatement delete = UConexion.getInstance().getConnection().prepareStatement(query);

            return delete.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}