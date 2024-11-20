/*******
 * ALIMENTA LA BASE DE DATOS CON LOS GENERO EN UN STRING
 * ***/
package programa;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

public class Conexion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		ArrayList<Pelicula> peliculas = null;

		//Leemos el Json
		try (FileReader reader = new FileReader("movies.json")) {
            // Tipo de la lista de Peliculas
            Type movieListType = new TypeToken<ArrayList<Pelicula>>() {}.getType();
            
            // Leemos el archivo JSON y lo mapeamos a una lista de objetos Pelicula
            peliculas = gson.fromJson(reader, movieListType);

        } catch (IOException e) {
            e.printStackTrace();
        }
		AlimentaPelicula(peliculas);
	}

	/******
	 * Metodo que añade el ArrayList con las peliculas a la BBDD
	 * @param lista: ArrayList con una lista de peliculas
	 ****/
	public static void AlimentaPelicula(ArrayList<Pelicula> lista) {
		String jdbcURL = "jdbc:oracle:thin:@localhost:1521/xepdb1"; 
        String username = "PRUEBADAM4";
        String password = "PRUEBADAM4";
        try {
        	Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        	System.out.println("Conexión establecida");
        	String sql = "INSERT INTO PELICULA (titulo, imagen, fechaEstreno, genero, reparto, resumen, altoImagen, anchoImagen) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        	PreparedStatement statement = connection.prepareStatement(sql);
            
        	// Iteramos sobre las películas
            for (Pelicula pelicula : lista) {

                // Introducimos los valores de la Pelicula en el statement
                statement.setString(1, pelicula.getTitle());
                statement.setString(2, pelicula.getThumbnail());
                statement.setInt(3, pelicula.getYear());
                statement.setString(4, String.join(", ", pelicula.getGenres())); // String con todos los generos
                statement.setString(5, String.join(", ", pelicula.getCast())); // Junta los nombres del reparto en un solo String
                statement.setString(6, pelicula.getExtract());
                statement.setInt(7, pelicula.getThumbnail_height());
                statement.setInt(8, pelicula.getThumbnail_width());
                

                // Ejecutar la inserción
                statement.executeUpdate();
                System.out.println("Pelicula añadida");
            }

        } catch (SQLException e) {
        	System.err.println("Error al conectar a la base de datos.");
            System.err.println("Código de error SQL: " + e.getErrorCode());
            System.err.println("Estado SQL: " + e.getSQLState());
            e.printStackTrace();
        }
	}
}
