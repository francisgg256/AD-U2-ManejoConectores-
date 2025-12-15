package Mysql.dml;

import Mysql.utilidades.MySQL;

import java.sql.*;

public class EjemploExecute {
    private static Connection conexion = MySQL.establecerConexion();

    public static void main(String[] args) {

        consultaSelect("SELECT * FROM departamentos");
        consultaUpdate("UPDATE departamentos SET dnombre=LOWER(dnombre)");
        MySQL.cerrarConexion();

    }

    private static void consultaSelect(String sql)
    {
        Statement sentencia = null;

        try {
            sentencia = conexion.createStatement();
            boolean valor = sentencia.execute(sql);

            if (valor)
            {
                ResultSet rs = sentencia.getResultSet();

                while (rs.next())
                    System.out.printf("%d, %s, %s %n", rs.getInt(1), rs.getString(2), rs.getString(3));

                rs.close();
            }
            else
            {
                int f = sentencia.getUpdateCount();
                System.out.printf("Filas afectadas:%d %n", f);
            }

            sentencia.close();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void consultaUpdate(String sql)
    {
        Statement sentencia = null;

        try {
            sentencia = conexion.createStatement();
            boolean valor = sentencia.execute(sql);

            if (valor)
            {
                ResultSet rs = sentencia.getResultSet();
                while (rs.next())
                    System.out.printf("%d, %s, %s %n", rs.getInt(1), rs.getString(2), rs.getString(3));
                rs.close();
            }
            else
            {
                int f = sentencia.getUpdateCount();
                System.out.printf("Filas afectadas:%d %n", f);
            }

            sentencia.close();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
