package programa;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pelicula {

	private String title;
    private int year;
    private List<String> cast; // Lista de actores
    private List<String> genres; // Lista de géneros
    private String href;
    private String extract;
    private String thumbnail;
    private int thumbnail_width; 
    private int thumbnail_height;
    
    @Override
    public String toString() {
        return "Pelicula{" +
                "titulo='" + title + '\'' +
                ", año=" + year +
                ", cast=" + cast +
                ", generos=" + genres +
                ", href='" + href + '\'' +
                ", resumen='" + extract + '\'' +
                ", imagen='" + thumbnail + '\'' +
                ", ancho=" + thumbnail_width +
                ", alto=" + thumbnail_height +
                '}';
    }
}
