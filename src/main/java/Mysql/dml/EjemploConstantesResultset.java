package Mysql.dml;

import Mysql.utilidades.MySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EjemploConstantesResultset {

    private static Connection conexion = MySQL.establecerConexion();

    public static void main(String[] args) {
        Statement sentencia = null;
        try {
            sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = sentencia.executeQuery("SELECT emp_no, apellido, salario FROM empleados");

            while (rs.next()) {
                float salario = rs.getFloat("salario");

                rs.updateFloat("salario", salario + 100); // Se suma 100

                rs.updateRow(); // Se actualiza

                System.out.println("Actualizado: " + rs.getString("apellido") + ", salario anterior: " + salario
                        + ", nuevo: " + rs.getFloat("salario"));
            }

            rs.close();
            sentencia.close();
            MySQL.cerrarConexion();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
