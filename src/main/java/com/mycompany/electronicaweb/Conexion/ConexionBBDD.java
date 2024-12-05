/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.electronicaweb.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author alumno
 */
public class ConexionBBDD {
    
   //driver JDBC
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	//direccion de la BBDD MySQL
	private static final String URL = "jdbc:mysql://localhost:3306/ElectronicaWeb";
	//usuario y contraseoa de acceso a la BD
	private static final String USUARIO = "root";
	private static final String PASSWORD = "";

    	public Connection conectar() {
		Connection conexion = null;

		try {
			Class.forName(DRIVER);
			conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
			System.out.println("Conectado a la base de datos");

		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();

		} catch (SQLException e) {
			System.out.println("Error en la conexion");
			e.printStackTrace();
		}

		return conexion;
	}
	
	public void cerrarConexion(Connection conection){
		try {
			//Cierre de la conexion
			conection.close();
		} catch (SQLException e) {
			System.err.println("Se ha producido un error al cerrar la conexion");
			
		}
                
          /*
             
            public void insertData() throws SQLException{
		Connection conexion = conectar();
		
		try {
			
		//Datos a insertar
			String consultasInserccion = "INSERT INTO fabricante VALUES(11, 'Martin');";
			System.out.println(consultasInserccion);
		//Creaci�n del Statement para poder reqalizar la consulta
			Statement consul = conexion.createStatement();
		//Ejecuci�n de la consulta
			consul.executeUpdate(consultasInserccion);
			System.out.println("Datos insertados correctamente");
		//Cierre del Statement
			consul.close();
			}finally {
				//Cierre de la conexi�n
				cerrarConexion(conexion);
			}
	}
	
	public void getData() throws SQLException{
		Connection conexion = conectar();

		if(conexion!=null) {
			try {

				//Datos a consultar
				String consultasSeleccion = "SELECT * FROM producto";
				System.out.println(consultasSeleccion);
				Statement consul = conexion.createStatement();
				//Ejecuci�n de la consulta
					if(consul.execute(consultasSeleccion)) {
						ResultSet resultset = consul.getResultSet();
						while(resultset.next()) {
							Producto producto = new Producto (resultset.getInt("id"), resultset.getString("nombre"),
									resultset.getInt("precio"), resultset.getInt("id_fabricante") );
							System.out.println(producto.toString());
							
						}
						
						System.out.println("Datos recuperados correctamente");
					}
				//Cierre del Statement
					consul.close();
				
			}finally {
				//Cierre de la conexi�n
				cerrarConexion(conexion);
			}
		}
	}    
                
          */     
           
	}
}
