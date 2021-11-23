package Clases;

import Anotaciones.Columna;
import Anotaciones.Id;
import Anotaciones.Tabla;
import java.math.BigInteger;
import java.util.Objects;

@Tabla(nombre = "Perros")
public class Perro
{
    @Id
    @Columna(nombre = "id")
    private int id;
    @Columna(nombre = "raza")
    private String raza;
    @Columna(nombre = "color")
    private String color;
    @Columna(nombre = "nombre")
    private String nombre;

    public Perro()
    {

    }

    public Perro(String raza, String color, String nombre)
    {
        this.raza = raza;
        this.color = color;
        this.nombre = nombre;
    }

    public Perro(int id, String raza, String color, String nombre)
    {
        this.id = id;
        this.raza = raza;
        this.color = color;
        this.nombre = nombre;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getRaza()
    {
        return raza;
    }

    public void setRaza(String raza)
    {
        this.raza = raza;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Perro{");
        sb.append("id=").append(id);
        sb.append(", raza='").append(raza).append('\'');
        sb.append(", color='").append(color).append('\'');
        sb.append(", nombre=").append(nombre);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Perro)) return false;
        Perro perro = (Perro) o;
        return Objects.equals(getId(), perro.getId()) && Objects.equals(getRaza(), perro.getRaza()) && Objects.equals(getColor(), perro.getColor()) && Objects.equals(getNombre(), perro.getNombre());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getRaza(), getColor(), getNombre());
    }
}
