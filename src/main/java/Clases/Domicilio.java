package Clases;

import java.math.BigInteger;
import java.util.Objects;
import Anotaciones.Id;
import Anotaciones.Tabla;
import Anotaciones.Columna;


@Tabla(nombre = "Domicilios")
public class Domicilio
{
    @Id
    @Columna(nombre = "id")
    private BigInteger id;
    @Columna(nombre = "calle")
    private String calle;
    @Columna(nombre = "altura")
    private Integer altura;
    @Columna(nombre = "codigoPostal")
    private Integer codigoPostal;
    @Columna(nombre = "localidad")
    private String localidad;

    public Domicilio()
    {

    }

    public Domicilio(String calle, Integer altura, Integer codigoPostal, String localidad)
    {
        this.calle = calle;
        this.altura = altura;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
    }

    public Domicilio(BigInteger id, String calle, Integer altura, Integer codigoPostal, String localidad)
    {
        this.id = id;
        this.calle = calle;
        this.altura = altura;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
    }

    public BigInteger getId()
    {
        return id;
    }

    public void setId(BigInteger id)
    {
        this.id = id;
    }

    public String getCalle()
    {
        return calle;
    }

    public void setCalle(String calle)
    {
        this.calle = calle;
    }

    public Integer getAltura()
    {
        return altura;
    }

    public void setAltura(Integer altura)
    {
        this.altura = altura;
    }

    public Integer getCodigoPostal()
    {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal)
    {
        this.codigoPostal = codigoPostal;
    }

    public String getLocalidad()
    {
        return localidad;
    }

    public void setLocalidad(String localidad)
    {
        this.localidad = localidad;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("\'");
        sb.append(id).append('\'').append(",");
        sb.append('\'').append(calle).append('\'').append(",");
        sb.append('\'').append(altura).append('\'').append(",");
        sb.append('\'').append(codigoPostal).append('\'').append(",");
        sb.append('\'').append(localidad).append('\'');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Domicilio)) return false;
        Domicilio domicilio = (Domicilio) o;
        return Objects.equals(getId(), domicilio.getId()) && Objects.equals(getCalle(), domicilio.getCalle()) && Objects.equals(getAltura(), domicilio.getAltura()) && Objects.equals(getCodigoPostal(), domicilio.getCodigoPostal()) && Objects.equals(getLocalidad(), domicilio.getLocalidad());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getCalle(), getAltura(), getCodigoPostal(), getLocalidad());
    }
}