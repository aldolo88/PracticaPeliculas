package peliculas.datos;

import java.io.*;
import java.util.*;
import peliculas.domain.Pelicula;
import peliculas.excepciones.*;

public class AccesoDatosImpl implements IAccesoDatos {

    @Override
    public boolean existe(String nombreRecurso){
        var archivo = new File(nombreRecurso);
        return archivo.exists();
    }

    @Override
    public List<Pelicula> listar(String nombreRecurso) throws LecturaDatosEx {
        var archivo = new File(nombreRecurso);
        List<Pelicula> peliculas = new ArrayList<>();
        try {
            var entrada = new BufferedReader(new FileReader(archivo));
            String linea = null;
            linea = entrada.readLine();
            while (linea != null) {
                Pelicula pelicula = new Pelicula(linea);
                peliculas.add(pelicula);
                linea = entrada.readLine();
            }
            entrada.close();
        } catch (FileNotFoundException ex) {
            throw new LecturaDatosEx("Excepción al listar películas: " + ex.getMessage());
        } catch (IOException ex) {
            throw new LecturaDatosEx("Excepción al listar películas: " + ex.getMessage());
        }
        return peliculas;
    }

    @Override
    public void escribir(Pelicula pelicula, String nombreRecurso, boolean anexar) throws EscrituraDatosEx {
        var archivo = new File(nombreRecurso);
        try {
            var salida = new PrintWriter(new FileWriter(archivo, anexar));
            salida.println(pelicula.toString());
            salida.close();
            System.out.println("Se ha escrito información al archivo: " + pelicula);
        } catch (IOException ex) {
            throw new EscrituraDatosEx("Excepción al escribir películas: " + ex.getMessage());
        }
    }

    @Override
    public String buscar(String nombreRecurso, String buscar) throws LecturaDatosEx {
        var archivo = new File(nombreRecurso);
        String resultado = null;
        try {
            var entrada = new BufferedReader(new FileReader(archivo));
            String linea = null;
            linea = entrada.readLine();
            int indice = 1;
            while (linea != null) {
                if (buscar != null && buscar.equalsIgnoreCase(linea)) {
                    resultado = "Pelicula " + linea + " encontrada en el indice " + indice;
                    break;
                }
                linea = entrada.readLine();
                indice++;
            }
            entrada.close();
        } catch (FileNotFoundException ex) {
            throw new LecturaDatosEx("Excepción al buscar películas: " + ex.getMessage());
        } catch (IOException ex) {
            throw new LecturaDatosEx("Excepción al buscar películas: " + ex.getMessage());
        }
        return resultado;
    }

    @Override
    public void crear(String nombreRecurso) throws AccesoDatosEx {
        var archivo = new File(nombreRecurso);
        try {
            var salida = new PrintWriter(new FileWriter(archivo));
            salida.close();
            System.out.println("Se ha creado el archivo.");
        } catch (IOException ex) {
            throw new AccesoDatosEx("Excepción al crear archivo: " + ex.getMessage());
        }
    }

    @Override
    public void borrar(String nombreRecurso){
        var archivo = new File(nombreRecurso);
        if (archivo.exists()){
            archivo.delete();
            System.out.println("Se ha borrado el archivo.");
        }
    }
}
