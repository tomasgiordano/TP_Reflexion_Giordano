package Program;

import Clases.Perro;
import Clases.Domicilio;
import Clases.Duenio;
import Servicios.Consultas;
import Utilidades.UBean;
import Utilidades.UConexion;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;

public class main {

    public static void main(String[] args)
    {
       //pruebaGeneral();
    	System.out.println("TP reflexion OK");
    }

    private static void pruebaGeneral()
    {
        Perro perro = new Perro("Golden","Amarillo","Reina");
        Domicilio domicilio = new Domicilio("Av. Belgrano",690,1228,"Ciudad Autonoma de Buenos Aires");
        Duenio duenio = new Duenio("43444906","Tomas","Giordano",1155284595, domicilio);
        
        Perro modifPerro = (Perro) Consultas.obtenerPorId(Perro.class, 3);
        System.out.println(modifPerro.toString());
        
        Duenio modifDuenio = (Duenio) Consultas.obtenerPorId(Duenio.class, 9);
        System.out.println(modifDuenio.toString());
        
        //Consultas.guardar(duenio);
        //Consultas.guardar(perro);
        
        Consultas.modificar(perro);
        Consultas.eliminar(duenio);
        Consultas.guardarModificar(perro);

        ArrayList lista = Consultas.obtenerTodos(Perro.class);

        for (Object auxPerro: lista)
        {
            System.out.println("perro = " + auxPerro);
        }
    }
}
