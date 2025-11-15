package Mysql.callablestatement;

import java.sql.*;
import Mysql.Utilidades.MySQL;


public class EjemploCallableStatement {

    private static Connection conexion = MySQL.establecerConexion();

    public static void main(String[] args) {


            System.out.println("SUBIDA DE SALARIO DE EMPLEADOS DE UN DEPARTAMENTO. PROCEDIMIENTO subida_salario. Solo parámetros de entrada");
            System.out.println("--------------------------------------------------------------------------");
            procSubida(30, 15);

            System.out.println("EMPLEADOS DE UN DEPARTAMENTO Y TOTAL EMPLEADOS. PROCEDIMIENTO emple_depar. Un parámetro de entrada y uno de salida");
            System.out.println("---------------------------------------------------------------------------------------");
            empleadosDepartamento(10);
            System.out.println("------------------------");
            System.out.println("NOMBRE DEL DEPARTAMENTO PASADO COMO PARÁMETRO. FUNCION nombre_dep. Un parámetro de entrada y uno de salida");
            System.out.println("---------------------------------------------------------------------------------------");
            nombreDepartamento(10);
            System.out.println("---------------------------------------------------------------------------------------");
            nombreDepartamento(200);
            System.out.println("------------------------");
            System.out.println("NOMBRE Y LOCALIDAD DE UN DEPARTAMENTO. PROCEDIMIENTO datos_dep. Un parámetro de entrada y dos de salida");
            datosDeparmamento(10);

            MySQL.cerrarConexion();



    }

    //Sube en un porcentaje el salario de todos los empleados de un deparmento
    public static void procSubida(int dep, float subida) {
        try {

            // construir orden de llamada
            String sql = "{ call subida_salario (?, ?) } ";

            // Preparar la llamada
            CallableStatement procedimiento = conexion.prepareCall(sql);

            // Dar valor a los argumentos
            procedimiento.setInt(1, dep);
            procedimiento.setFloat(2, subida);

            // Ejecutar el procedimiento
            procedimiento.execute();
            System.out.println("Subida realizada....");

            procedimiento.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    //Muestra los empleados que hay en un departamento y el total de empleados del departamento
    public static void empleadosDepartamento(int dep) {
        try {

            // construir orden de llamada
            String sql = "{ call emple_depar (?, ?) } ";

            // Preparar la llamada
            CallableStatement procedimiento = conexion.prepareCall(sql);

            // Dar valor a los argumentos
            procedimiento.setInt(1, dep);

            // registrar parámetro de resultado
            procedimiento.registerOutParameter(2, Types.INTEGER);// valor devuelto

            // Ejecutar el procedimiento
            procedimiento.execute();

            int totalEmpleados=procedimiento.getInt(2);

            ResultSet resultado=procedimiento.getResultSet();
            while(resultado.next())
            {
                System.out.printf("%4d %-15s %-15s %6.2f \n", resultado.getInt(1),
                        resultado.getString(2), resultado.getString(3) ,
                        resultado.getFloat("salario"));
            }

            System.out.println("Total empleados del deparmento: " + totalEmpleados);

            resultado.close();
            procedimiento.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    //Obtiene el nombre del departamento pasado como parámetro.
    public static void nombreDepartamento(int dep) {
        try {

            // Construir orden de llamada
            String sql = "{ ? = call nombre_dep (?) } ";

            // Preparar la llamada
            CallableStatement funcion = conexion.prepareCall(sql);

            // registrar parámetro de resultado
            funcion.registerOutParameter(1, Types.VARCHAR);// valor devuelto

            funcion.setInt(2, dep); // param de entrada

            // Ejecutar el procedimiento
            funcion.execute();
            System.out.printf("Nombre Departamento: %s %n", funcion.getString(1));
            funcion.close();

        }

        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Obtiene el nombre y la localidad del departamento pasado como parámetro
    public static void datosDeparmamento(int dep) {
        try {

            // Construir orden de llamada
            String sql = "{ call datos_dep (?, ?, ?) } ";

            // Preparar la llamada
            CallableStatement procedimiento = conexion.prepareCall(sql);

            procedimiento.setInt(1, dep); // param de entrada

            // registrar parámetro de resultado
            procedimiento.registerOutParameter(2, Types.VARCHAR);// valor devuelto

            // Registrar parámetro de salida
            procedimiento.registerOutParameter(3, Types.VARCHAR);// parámetro OUT

            // Ejecutar el procedimiento
            procedimiento.execute();
            System.out.printf("Nombre Dep: %s, Localidad: %s %n", procedimiento.getString(2), procedimiento.getString(3));
            procedimiento.close();

        }

        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
