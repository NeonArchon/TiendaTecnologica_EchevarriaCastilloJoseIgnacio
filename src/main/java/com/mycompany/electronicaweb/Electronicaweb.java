/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.electronicaweb;

import com.mycompany.electronicaweb.Conexion.ConexionBBDD;
import com.mycompany.electronicaweb.Conexion.JSon;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author alumno
 */
public class Electronicaweb {

    public static void main(String[] args) throws ParseException, SQLException {
        System.out.println("Hello World!");
        

        
        
       JSon json = new JSon();
       JSon.LeerJson();

       System.out.print(json);
        
        
        ConexionBBDD conexionBBDD = new ConexionBBDD();
        Connection conexion = conexionBBDD.conectar();
        conexionBBDD.cerrarConexion(conexion);
        
        
    }
}
