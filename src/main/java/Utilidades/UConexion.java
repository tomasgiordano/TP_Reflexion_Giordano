package Utilidades;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UConexion
{
    private static UConexion instancia;
    private Connection connection;
    private static final String CONFIG_FILE = "framework.properties.json";


    private UConexion(){
        try{
            JSONObject configuration = this.getConfiguration(CONFIG_FILE);
            if(configuration != null){
                Class.forName(configuration.get("DRIVER").toString());
                this.connection = DriverManager.getConnection(configuration.get("DB").toString(),configuration.get("USER").toString(),configuration.get("PASSWORD").toString());
            }
        }
        catch (NullPointerException | SQLException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static UConexion getInstance(){
        if(UConexion.instancia == null)
        {
            UConexion.instancia = new UConexion();
        }
        return UConexion.instancia;
    }

    private JSONObject getConfiguration(String file)
    {
        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader(file)){
            JSONObject object = (JSONObject) jsonParser.parse(reader);
            return object;
        }
        catch (FileNotFoundException exception){
            exception.printStackTrace();
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public Connection getConnection(){
        return this.connection;
    }
}