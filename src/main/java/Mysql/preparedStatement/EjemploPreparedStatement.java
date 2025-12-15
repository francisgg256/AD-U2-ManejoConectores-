package Mysql.preparedStatement;

import java.sql.*;

public class EjemploPreparedStatement {

    private static Connection conexion = Mysql.utilidades.MySQL.establecerConexion();

    public static void main(String[] args) {

        datosDepartamento(10);
        insertarDepartamento(100, "DEP 100", "TALAVERA");
    }

    private static void datosDepartamento(int dept) {

        String sql = "SELECT * FROM departamentos WHERE dept_no = ?";
        try {
            PreparedStatement sentencia = (PreparedStatement) conexion.prepareStatement(sql);

            sentencia.setInt(1, 10);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                /* recupera los datos por su ordinal en la tabla o por el nombre
                 * de la columna, y los muestra en la Salida debidamente tabulados */
                System.out.printf("%2d %-15s %s\n", resultado.getInt(1),
                        resultado.getString("dnombre"), resultado.getString(3));
            }
            resultado.close();
            sentencia.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private static boolean existeDepartamento(int dept) {
        boolean existe = false;
        String sql = "SELECT * FROM departamentos WHERE dept_no =  ?";
        try {
            PreparedStatement sentencia = (PreparedStatement)conexion.prepareStatement(sql);
            sentencia.setInt(1,dept);
            ResultSet resultado = sentencia.executeQuery();
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

    public static void insertarDepartamento(int dep, String nombre, String localidad) {

        int filas=0;

        if (existeDepartamento(dep)) {
            System.out.println("El departamento a insertar existe: " + dep);
        } else {
            String sql = "INSERT INTO departamentos VALUES (?, ?, ?)";
            PreparedStatement pstmt = null;
            try {
                pstmt = conexion.prepareStatement(sql);

                //establecemos los parámetros mediante métodos set
                pstmt.setInt(1, dep);    //número departamento
                pstmt.setString(2, nombre); //nombre
                pstmt.setString(3, localidad); //localidad

                //ejecutamos la consulta de actualización
                filas = pstmt.executeUpdate();	//filas afectadas

                pstmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


            System.out.println("Filas insertadas: " + filas);


        }
    }
}
