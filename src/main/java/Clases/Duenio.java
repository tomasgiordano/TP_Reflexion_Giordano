package Clases;

import java.math.BigInteger;
import java.util.Objects;
import Anotaciones.Id;
import Anotaciones.Columna;
import Anotaciones.Compuesto;
import Anotaciones.Tabla;

@Tabla(nombre = "Duenios")
public class Duenio
{
    @Id
    @Columna(nombre = "id")
    private int id;
    
    @Columna(nombre = "dni")
    private String dni;
    
    @Columna(nombre = "nombre")
    private String nombre;
    
    @Columna(nombre = "apellido")
    private String apellido;
    
    @Compuesto(clase = Domicilio.class)
    @Columna(nombre = "domicilio")
    private Domicilio domicilio;
    
    @Columna(nombre = "telefono")
    private Integer telefono;

    public Duenio(){ }

    public Duenio(int id, String dni, String nombre, String apellido, Integer telefono, Domicilio domicilio){
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.domicilio = domicilio;
        this.telefono = telefono;
    }

    public Duenio(String dni, String nombre, String apellido, Integer telefono, Domicilio domicilio){
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.domicilio = domicilio;
        this.telefono = telefono;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getApellido(){
        return apellido;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public String getDni(){
        return dni;
    }

    public void setDni(String dni){
        this.dni = dni;
    }

    public Domicilio getDomicilio(){
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio){
        this.domicilio = domicilio;
    }

    public Integer getTelefono(){
        return telefono;
    }

    public void setTelefono(Integer telefono){
        this.telefono = telefono;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Duenio{");
        sb.append("dni=").append(dni);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", apellido='").append(apellido).append('\'');
        sb.append(", telefono=").append(telefono);
        sb.append(", domicilio=").append(domicilio).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o){
    	
        if (!(o instanceof Duenio)) {
        	return false;
        }
        
        if (this == o) {
        	return true;
        }

        Duenio duenio = (Duenio) o;
        return Objects.equals(getNombre(), duenio.getNombre()) && Objects.equals(getApellido(), duenio.getApellido()) && Objects.equals(getTelefono(), duenio.getTelefono()) && Objects.equals(getDni(), duenio.getDni()) && Objects.equals(getDomicilio(), duenio.getDomicilio());
    }

    @Override
    public int hashCode(){
        return Objects.hash(getNombre(), getApellido(), getDni(), getTelefono(), getDomicilio());
    }


}