package Mysql.databasemetadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import Mysql.utilidades.MySQL;

public class EjemploDatabaseMetaData {

    private static Connection conexion = MySQL.establecerConexion();

    public static void main(String[] args)
    {
        /* objeto de metadatos */
        DatabaseMetaData dbmd;
        try
        {

            //crea objeto de metadatos y obitene información
            dbmd = conexion.getMetaData();

            informacionBaseDatos(dbmd);
            tablasBaseDatos(dbmd,"empresa");
            columnasTablaBaseDatos(dbmd,"empresa","departamentos");
            primaryKeysTablaBaseDatos(dbmd,"empresa","departamentos");
            primaryKeysTablaBaseDatos(dbmd,"empresa","empleados");
            foreignKeysTablaBaseDatos(dbmd,"empresa","departamentos");
            foreignKeysTablaBaseDatos(dbmd,"empresa","empleados");
            procedimientosBaseDatos(dbmd,"empresa");
            MySQL.cerrarConexion();
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }


    }

    private static void informacionBaseDatos(DatabaseMetaData dbmd)
    {
        String nombre, driver, url_name, usuario, catalogo, esquema, tabla, tipo;

        try {
            nombre = dbmd.getDatabaseProductName();
            driver = dbmd.getDriverName();
            url_name = dbmd.getURL();
            usuario = dbmd.getUserName();

            /* imprime valores */
            System.out.println("INFORMACIÓN SOBRE LA BASE DE DATOS:");
            System.out.println("===================================");
            System.out.println("Nombre: " + nombre);
            System.out.println("Driver: " + driver);
            System.out.println("URL: " + url_name);
            System.out.println("Usuario: " + usuario + "\n");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void tablasBaseDatos(DatabaseMetaData dbmd,String esquema)
    {
        /* variables locales */
        String catalogo, tabla, tipo;
        ResultSet resultado;

        System.out.println("TABLAS BASE DE DATOS EMPRESA:");
        System.out.println("=============================");

        /* obtiene la informaciónn de las tablas y vistas existentes */
        try {
            resultado = dbmd.getTables(null, esquema, null, null);

            while (resultado.next())
            {
                catalogo = resultado.getString(1);
                //esquema = result.getString(2);
                tabla = resultado.getString(3);
                tipo = resultado.getString(4);

                /* imprime */
                System.out.println(tipo + " - Catálogo: " + catalogo + ", Nombre: " + tabla);
            }

            resultado.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void columnasTablaBaseDatos(DatabaseMetaData dbmd,String esquema, String tabla)
    {
        String nombreCol, tipoCol, tamanoCol, nula;
        ResultSet resultado;

        System.out.println("\n\nCOLUMNAS TABLA DEPARTAMENTOS:");
        System.out.println("=============================");

        /* obtiene las columnas de la tabla 'Departamentos' */
        try {
            resultado = dbmd.getColumns(null, esquema, tabla, null);

            while (resultado.next())
            {
                nombreCol = resultado.getString("COLUMN_NAME");
                tipoCol = resultado.getString("TYPE_NAME");
                tamanoCol = resultado.getString("COLUMN_SIZE");
                nula = resultado.getString("IS_NULLABLE");

                System.out.println(" Columna: " + nombreCol + ", Tipo: "
                        + tipoCol + ", Tamaño: " + tamanoCol + ", ¿Puede ser Nula?: "+ nula);
            }

            resultado.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());

        }
    }

    private static void primaryKeysTablaBaseDatos(DatabaseMetaData dbmd,String esquema,String tabla)
    {
        String pkDep = "", separador = "";

        ResultSet resultado;
        System.out.println("\n\nPRIMARY KEYS TABLA "+ tabla.toUpperCase() + ":");
        System.out.println("===================================");

        try {
            resultado = dbmd.getPrimaryKeys(null, esquema, tabla);

            while (resultado.next())
            {
                pkDep += separador + resultado.getString("COLUMN_NAME");
                separador = "+";
            }

            System.out.println("Clave primaria: " + pkDep);

            resultado.close();

        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void foreignKeysTablaBaseDatos(DatabaseMetaData dbmd,String esquema,String tabla)
    {
        String fk_name, pk_name, fk_table, pk_table;
        boolean tieneForeignKey=false;
        ResultSet resultado;

        System.out.println("\n\nFOREIGN KEYS TABLA " + tabla.toUpperCase() + ":");
        System.out.println("===================================");

        try {
            resultado = dbmd.getExportedKeys(null, esquema, tabla);

            while (resultado.next()) {
                tieneForeignKey=true;
                pk_name = resultado.getString("PKCOLUMN_NAME");
                pk_table = resultado.getString("PKTABLE_NAME");

                fk_name = resultado.getString("FKCOLUMN_NAME");
                fk_table = resultado.getString("FKTABLE_NAME");

                System.out.print("La clave primaria " + pk_name + " de la tabla " + pk_table) ;
                System.out.println(" está como clave foránea con nombre " + fk_name + " en la tabla " + fk_table);
            }

            if (!tieneForeignKey)
                System.out.println("La tabla " + tabla + " de la base de datos " + esquema + " no tiene Foreign Key.");

            resultado.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void procedimientosBaseDatos(DatabaseMetaData dbmd, String esquema)
    {
        ResultSet resultado;
        String proc_name, proc_type, tipo;

        System.out.println("\n\nPROCEDIMIENTOS Y FUNCIONES DE LA BASE DE DATOS");
        System.out.println("================================================");

        try {
            resultado = dbmd.getProcedures(esquema, esquema, null);

            while (resultado.next())
            {
                proc_name = resultado.getString("PROCEDURE_NAME");
                proc_type = resultado.getString("PROCEDURE_TYPE");
                tipo= proc_type.equals("1") ? "Procedimiento" : "Función";
                System.out.println("Nombre Procedimiento: " + proc_name + " - Tipo: " + tipo);
            }

            resultado.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }


}
