package Mysql.resultsetmetadata;

import java.sql.*;
import Mysql.utilidades.MySQL;

public class EjemploResultSetMetaData {
    public static Connection conexion = MySQL.establecerConexion();

    public static void main(String[] args)
    {
        try
        {

            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery("SELECT * FROM departamentos");

            ResultSetMetaData rsmd = rs.getMetaData();

            int nColumnas = rsmd.getColumnCount();
            String nula;
            System.out.printf("Número de columnas recuperadas: %d%n", nColumnas);

            for (int i = 1; i <= nColumnas; i++)
            {
                System.out.printf("Columna %d: %n ", i);
                System.out.printf("  Nombre: %s %n   Tipo: %s %n ", rsmd.getColumnName(i), 			rsmd.getColumnTypeName(i));

                if (rsmd.isNullable(i) == 0)
                    nula = "NO";
                else
                    nula = "SI";

                System.out.printf("  Puede ser nula?: %s %n ", nula);

            } // for

            sentencia.close();
            rs.close();
            MySQL.cerrarConexion();

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }// fin de main
}
