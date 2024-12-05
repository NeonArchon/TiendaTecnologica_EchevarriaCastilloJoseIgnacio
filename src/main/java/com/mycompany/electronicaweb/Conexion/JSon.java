/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.electronicaweb.Conexion;


import org.json.simple.parser.JSONParser;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


/**
 *
 * @author alumno
 */
public class JSon {
    
           //funciones para guardad los datos del JSON a la base de datos
        
    private static void guardarCaracteristicas(JSONObject jsonObject, Connection connection) throws SQLException {
        JSONObject tienda = (JSONObject) jsonObject.get("tienda");
        JSONArray productos = (JSONArray) tienda.get("productos");

        String query = "INSERT INTO caracteristicas (nombre, producto_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Object prod : productos) {
                JSONObject producto = (JSONObject) prod;
                Long productoId = (Long) producto.get("id");

                JSONArray caracteristicas = (JSONArray) producto.get("caracteristicas");
                for (Object car : caracteristicas) {
                    stmt.setString(1, (String) car);
                    stmt.setLong(2, productoId);
                    stmt.executeUpdate();
                }
            }
        }
    }

        private static void guardarImagenes(JSONObject jsonObject, Connection connection) throws SQLException {
        JSONObject tienda = (JSONObject) jsonObject.get("tienda");
        JSONArray productos = (JSONArray) tienda.get("productos");

        String query = "INSERT INTO imagen (url, producto_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Object prod : productos) {
                JSONObject producto = (JSONObject) prod;
                Long productoId = (Long) producto.get("id");

                JSONArray imagenes = (JSONArray) producto.get("imagenes");
                for (Object img : imagenes) {
                    stmt.setString(1, (String) img);
                    stmt.setLong(2, productoId);
                    stmt.executeUpdate();
                }
            }
        }
     }

    private static void guardarUsuarios(JSONObject jsonObject, Connection connection) throws SQLException {
        JSONObject tienda = (JSONObject) jsonObject.get("tienda");
        JSONArray usuarios = (JSONArray) tienda.get("usuarios");

        String queryUsuario = "INSERT INTO usuario (nombre, email) VALUES (?, ?)";
        String queryDireccion = "INSERT INTO direccion (calle, numero, ciudad, pais, usuario_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmtUsuario = connection.prepareStatement(queryUsuario, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtDireccion = connection.prepareStatement(queryDireccion)) {

            for (Object user : usuarios) {
                JSONObject usuario = (JSONObject) user;
                stmtUsuario.setString(1, (String) usuario.get("nombre"));
                stmtUsuario.setString(2, (String) usuario.get("email"));
                stmtUsuario.executeUpdate();

                // Obtener el ID generado
                ResultSet rs = stmtUsuario.getGeneratedKeys();
                if (rs.next()) {
                    int usuarioId = rs.getInt(1);

                    // Insertar la dirección
                    JSONObject direccion = (JSONObject) usuario.get("direccion");
                    stmtDireccion.setString(1, (String) direccion.get("calle"));
                    stmtDireccion.setString(2, (String) direccion.get("numero"));
                    stmtDireccion.setString(3, (String) direccion.get("ciudad"));
                    stmtDireccion.setString(4, (String) direccion.get("pais"));
                    stmtDireccion.setInt(5, usuarioId);
                    stmtDireccion.executeUpdate();
                }
            }
        }
    }
    
    
    
    public static void LeerJson() throws ParseException{
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/resources/JSonTienda.json")){
            Object obj = parser.parse(reader);

            
            JSONObject jsonObject = (JSONObject) obj;

            System.out.println(jsonObject);

            
            //este pedazo del cdigo llama a las funciones requetidas para guarar los datos del JSOn a la base de datos
            
            ConexionBBDD connection = new ConexionBBDD();
            Connection conn = connection.conectar();
            if (conn != null) {
                // Insertar datos en la base de datos
                guardarCategorias(jsonObject, conn);
                guardarProductos(jsonObject, conn);
                guardarUsuarios(jsonObject, conn);
                connection.cerrarConexion(conn);
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException ex) {

        }
    }
        
    private static void mostrarJson(JSONObject jsonobject) {
        //PRIMERA MITAD DEL JSON (LOS OBJETOS DE LA TIENDA)
        
        //Object de la tienda de la tienda del JSON
        JSONObject JTienda = (JSONObject) jsonobject.get("tienda");
        String tienda = (String) JTienda.get("ElectronicaWeb");
        System.out.println("Nombre: " + tienda);
        
        //Objetct del nombre
        
        //Array de la categorias
        JSONArray EwArray = (JSONArray) JTienda.get("categorias");
        for (Object cat : EwArray) {
            JSONObject c = (JSONObject) cat;
            System.out.println("Categorias: " + c.get("categorias"));
             
        //Object para el id del producto
        JSONObject id = (JSONObject) jsonobject.get("id");
        String myid = (String) id.get("id");
        System.out.println("id: " + myid);
               
        //Object del nombre del id del producto
        JSONObject nProducto = (JSONObject) jsonobject.get("nombre");
        System.out.println("Nombre: " + nProducto);
        
        //Array de Productos (esta uncluya mas variables a dentro de ella
        JSONArray ProductosArray = (JSONArray) JTienda.get("productos");
        for (Object pa : ProductosArray) {
            JSONObject p = (JSONObject) pa;
            System.out.println("Productos: " + p.get("productos"));
            //Object de la descripcion del producto
            JSONObject Jdesc = (JSONObject) jsonobject.get("decripcion");
            String desc = (String) Jdesc.get ("descripcion");
            System.out.println("Descripcion: " + desc);
        //Arrady de las caracteristicas del producto
        JSONArray JCaracteristicas = (JSONArray) JTienda.get("caracteristicas");
        for (Object JCa : JCaracteristicas) {
            JSONObject JC = (JSONObject) JCa;
            System.out.println("Caracteristicas: " + JC.get("caracteristicas"));
        //Array de imagenes de productos
        JSONArray JImagenes = (JSONArray) JTienda.get("imagenes");
        for (Object Jim : JImagenes){
            JSONObject Jimg = (JSONObject) Jim;
            System.out.println("Imagenes: " + Jimg.get("imagenes"));
        }
        //Object del inventario de productos
        JSONObject JInventario = (JSONObject) jsonobject.get("inventario");
        String inventario = (String) JInventario.get("inventario");
        System.out.println("Inventario: " + inventario);
         
         
        //SEGUNDA MITAD DE LA TIENDA (LOS USUARIOS)
        //Array de los usuarios
        JSONArray JUsuarios = (JSONArray) JTienda.get("usuarios");
        for (Object Jusers : JUsuarios){
            JSONObject users = (JSONObject) Jusers;
            System.out.println("Usuarios: " + users);
        //Object del nombre los usuarios
        JSONObject JNombres = (JSONObject) jsonobject.get("usuarios");
        String Nombres = (String) JNombres.get("usuarios");
        System.out.println("Nombres: " + Nombres);
        //Object con el email de los usuarios
        JSONObject JEmail = (JSONObject) jsonobject.get("email");
        String Email = (String)JEmail.get("email");
        System.out.println("Email" + Email);
        //Array de la direccion de los usuarios
        JSONArray JDireccion =(JSONArray) JTienda.get("direccion");
        for (Object JDir : JDireccion){
            JSONObject JD = (JSONObject) JDir;
            System.out.println("Direccion: " + JD.get("direccion"));
        //Elementos dentro del array de Direccion
        //Object de la calle de los usuarios
        JSONObject JCalle = (JSONObject) jsonobject.get("calle");
        String Direccion = (String)JCalle.get("calle");
        System.out.println("Calle" + Direccion);
        //Object del numero de calle
        JSONObject JNumero = (JSONObject) jsonobject.get("numero");
        String Numero = (String)JNumero.get("numero");
        System.out.println("Numero " + Numero);
        //Object de la cuidad de los usuarios
        JSONObject JCiudad = (JSONObject) jsonobject.get("cuidad");
        String Ciudad = (String)JCiudad.get("cuidad");
        System.out.println("Ciudad" + Ciudad);
        //Object dl pais de los usuarios
        JSONObject JPais = (JSONObject) jsonobject.get("pais");
        String Pais = (String)JPais.get("pais");
        System.out.println("Ciudad" + Pais);
        //array del historial de compras de cada usuario
        JSONArray JHistorial = (JSONArray) JTienda.get("historialCompras");
        for (Object Hist : JHistorial){
            JSONObject H = (JSONObject) Hist;
            System.out.println("Historial" + H.get("historialCompras"));
            //Objeto del id del producto
            JSONObject JroductoId = (JSONObject) jsonobject.get("id");
            String ProdId = (String)JroductoId.get("id");
            System.out.println("Id Producto " + ProdId);
            //Objest de la cantidad
            JSONObject JCantidad = (JSONObject) jsonobject.get("cantidad");
            String JCan = (String)JCantidad.get("cantidad");
            System.out.println("Cantidad " + JCan);
            //Object de la fecha de compra del producto
            JSONObject JFecha = (JSONObject) jsonobject.get("fecha");
            String JF = (String)JFecha.get("fecha");
            System.out.println("Fecha " + JF);
            
        }
        
        }
        
        }
            
        }
               
        }
        
        }
    }
 


//otras funiones

    //funcion para buscar un usuario a travez de su id
    public String mostrarDatosUsuario(Connection conexion, int usuarioId) {
        String consultaSQL = "SELECT email, calle, numero, ciudad, pais FROM direccion d JOIN usuario u ON d.usuario_id = u.id WHERE u.id = ?";
        StringBuilder resultado = new StringBuilder();

        try (PreparedStatement stmt = conexion.prepareStatement(consultaSQL)) {
            stmt.setInt(1, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    resultado.append("Email: ").append(rs.getString("email")).append("\n");
                    resultado.append("Calle: ").append(rs.getString("calle")).append("\n");
                    resultado.append("Número: ").append(rs.getString("numero")).append("\n");
                    resultado.append("Ciudad: ").append(rs.getString("ciudad")).append("\n");
                    resultado.append("País: ").append(rs.getString("pais")).append("\n");
                } else {
                    return "El usuario " + usuarioId + " no fue encontrado.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la consulta.";
        }

        return resultado.toString();
    }

    //funcion para buscar un producto pro su categoria
public String BuscarProductoPorCategoriaOId(Connection conexion, String nombreCategoria, Integer idProducto) {
    String consultaSQL = """
        SELECT p.id, p.nombre, p.descripcion, p.inventario
        FROM producto p
        JOIN categoria c ON p.categoria_id = c.id
        WHERE (c.nombre = ? AND idProducto IS NULL) OR idProducto = ?
    """;
    StringBuilder resultado = new StringBuilder();

    try (PreparedStatement stmt = conexion.prepareStatement(consultaSQL)) {
        stmt.setString(1, nombreCategoria);
        if (idProducto != null) {
            stmt.setInt(2, idProducto);
        }

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                int inventario = rs.getInt("inventario");

                resultado.append("ID: ").append(id).append("\n");
                resultado.append("Nombre: ").append(nombre).append("\n");
                resultado.append("Descripción: ").append(descripcion).append("\n");
                resultado.append("Inventario: ").append(inventario).append("\n");
                resultado.append("-------------\n");
            } else if (idProducto != null) {
                resultado.append("El producto " + idProducto + " no fue encontrado.");
            } else {
                resultado.append("No se encontraron productos para la categoría: " + nombreCategoria);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return "Error al realizar la consulta.";
    }

    return resultado.toString();
}
     
    
    //funcion para mostrar el historial de todas las compras realizadas
    public String MostrarHistorialCompleto(Connection conexion) {
    String consultaSQL = "SELECT hc.id AS id_compra, u.nombre AS cliente, p.nombre AS producto, hc.cantidad, hc.fecha " +
                         "FROM historial_compras hc " +
                         "JOIN usuario u ON hc.usuario_id = u.id " +
                         "JOIN producto p ON hc.producto_id = p.id";
    StringBuilder resultado = new StringBuilder();

    try (PreparedStatement stmt = conexion.prepareStatement(consultaSQL);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            resultado.append("ID Compra: ").append(rs.getInt("id_compra")).append("\n");
            resultado.append("Cliente: ").append(rs.getString("cliente")).append("\n");
            resultado.append("Producto: ").append(rs.getString("producto")).append("\n");
            resultado.append("Cantidad: ").append(rs.getInt("cantidad")).append("\n");
            resultado.append("Fecha: ").append(rs.getDate("fecha")).append("\n");
            resultado.append("-------------\n");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return "Error al consultar el historial completo.";
    }

    if (resultado.length() == 0) {
        return "No se encontraron compras en el historial.";
    }

    return resultado.toString();
}

    //funcion para realizar un historial de compras de la fecha establecidas
    public String MostrarHistorialPorFecha(Connection conexion, String fecha) {
        String consultaSQL = "SELECT hc.id AS id_compra, u.nombre AS cliente, p.nombre AS producto, hc.cantidad, hc.fecha " +
                             "FROM historial_compras hc " +
                             "JOIN usuario u ON hc.usuario_id = u.id " +
                             "JOIN producto p ON hc.producto_id = p.id " +
                             "WHERE hc.fecha = ?";
        StringBuilder resultado = new StringBuilder();

        try (PreparedStatement stmt = conexion.prepareStatement(consultaSQL)) {
            stmt.setString(1, fecha);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultado.append("ID Compra: ").append(rs.getInt("id_compra")).append("\n");
                    resultado.append("Cliente: ").append(rs.getString("cliente")).append("\n");
                    resultado.append("Producto: ").append(rs.getString("producto")).append("\n");
                    resultado.append("Cantidad: ").append(rs.getInt("cantidad")).append("\n");
                    resultado.append("Fecha: ").append(rs.getDate("fecha")).append("\n");
                    resultado.append("-------------\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al consultar el historial por fecha.";
        }

        if (resultado.length() == 0) {
            return "No se encontraron compras para la fecha: " + fecha;
        }

        return resultado.toString();
    }
    
    //funcion para hacer una compra
     public static boolean RealizarCompra(Connection conexion, int idProducto, int idUsuario, int cantidad) {
        try {
            // Transacción para asegurar la consistencia de los datos
            conexion.setAutoCommit(false);

            // 1. Actualizar inventario del producto
            String actualizarInventario = "UPDATE producto SET inventario = inventario - ? WHERE id = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(actualizarInventario)) {
                stmt.setInt(1, cantidad);
                stmt.setInt(2, idProducto);
                stmt.executeUpdate();
            }

            // 2. Insertar registro en el historial de compras
            String insertarCompra = "INSERT INTO historial_compras (producto_id, usuario_id, cantidad, fecha_compra) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conexion.prepareStatement(insertarCompra)) {
                stmt.setInt(1, idProducto);
                stmt.setInt(2, idUsuario);
                stmt.setInt(3, cantidad);
                stmt.setTimestamp(4, new java.sql.Timestamp(new Date().getTime()));
                stmt.executeUpdate();
            }

            // Confirmar la transacción
            conexion.commit();
            return true;
        } catch (SQLException e) {
            try {
                // Revertir la transacción en caso de error
                conexion.rollback();
            } catch (SQLException ex) {
                //Error de rollback
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                // Manejar el error de restaurar el autocommit
            }
        }
    }
}