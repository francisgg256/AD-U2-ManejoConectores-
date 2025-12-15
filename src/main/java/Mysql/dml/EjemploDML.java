package Mysql.dml;

import Mysql.utilidades.MySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EjemploDML {

    private static Connection conexion = MySQL.establecerConexion();

    public static void main(String[] args) {

        listarDepartamentos();

        // Insertar un departamento
        // Antes de insertar comprobar si el departamento ya existe.
        insertarDepartamento(100, "DEP 100", "TALAVERA");
        insertarDepartamento(10, "DEP 100", "TALAVERA");

        // Actualizar la comisión de los empleados de un departamento
        // Se recibe el departamento y la subida de la comisión
        actualizarEmpleados(20, 70.0f);

        // Eliminar un departamento, se recibe el código de departamento
        // Antes de eliminar comprobar si existe el departamento.
        eliminarDepartamento(10);
        eliminarDepartamento(110);
        eliminarDepartamento(100);


        MySQL.cerrarConexion();

    }

    // Comprobar si existe el departamento.
    // El metodo devuelve true si existe
    private static boolean existeDepartamento(int dept) {
        boolean existe = false;
        String sql = "SELECT * FROM departamentos WHERE dept_no =  " + dept;
        try {
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(sql);
            if (resultado.next()) {
                existe = true; // dep existe
            }
            resultado.close();
            sentencia.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return existe;
    }

    // Comprobar si existe el departamento.
    // El metodo devuelve true si existe
    private static void listarDepartamentos() {

        String sql = "SELECT * FROM departamentos";
        try {
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(sql);
            /* mientras hay registros*/
            while (resultado.next()) {
                /* recupera los datos por su ordinal en la tabla o por el nombre
                 * de la columna, y los muestra en la Salida debidamente tabulados */
                System.out.printf("%2d %-15s %s\n", resultado.getInt(1),
                        resultado.getString("dnombre"), resultado.getString(3));
            }

            /* mensaje de confirmación */
            System.out.println("\nTabla 'departamentos' consultada correctamente");

            resultado.close();
            sentencia.close();

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void insertarDepartamento(int dep, String nombre, String localidad) {

        if (existeDepartamento(dep)) {
            System.out.println("El departamento a insertar existe: " + dep);
        } else {
            String sql = "INSERT INTO departamentos (dept_no, dnombre, loc) VALUES ( " + dep + ",'" + nombre + "','"
                    + localidad + "' )";
            try {
                System.out.println("Sentencia: " + sql);
                Statement sentencia = conexion.createStatement();
                int filas = sentencia.executeUpdate(sql);
                System.out.println("Insertado:" + filas);
                sentencia.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private static void eliminarDepartamento(int dep) {
        if (!existeDepartamento(dep)) {
            System.out.println("El departamento a borrar no existe: " + dep);
        } else {
            if (registrosRelacionados(dep) == 0) {
                String sql = "DELETE from departamentos where dept_no = " + dep;
                try {
                    System.out.println("Sentencia: " + sql);
                    Statement sentencia = conexion.createStatement();
                    int filas = sentencia.executeUpdate(sql);
                    System.out.println("Borrado:" + filas);
                    sentencia.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("El departamento no se puede borrar, tiene registros relacionados.");
            }
        }
    }

    private static int registrosRelacionados(int dep) {
        int reg = 0;
        String sql = "SELECT count(*) FROM empleados WHERE dept_no =  " + dep;
        Statement sentencia;
        try {
            sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(sql);
            resultado.next();
            reg = resultado.getInt(1);
            resultado.close();
            sentencia.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reg;

    }

    private static void actualizarEmpleados(int dep, float subida) {

        String sql = "UPDATE empleados set comision = coalesce(comision,0) + " + subida + "  where dept_no = " + dep;
        try {
            System.out.println("Sentencia: " + sql);
            Statement sentencia = conexion.createStatement();
            int filas = sentencia.executeUpdate(sql);
            System.out.println("Actualizados:" + filas);
            sentencia.close();
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

    }
}
