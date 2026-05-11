package appconsole;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import modelo.Genero;
import modelo.Video;
import util.Util;

import java.util.List;

public class Listar {
    private ObjectContainer manager;

    public Listar() {
        Util.conectar();

        manager = Util.getManager();

        System.out.println("__________ Lista de gêneros __________ ");
        Query q = manager.query();
        q.constrain(Genero.class);
        List<Genero> listaGeneros = q.execute();
        if(listaGeneros.isEmpty()) {
            System.out.println("Nenhum gênero está cadastrado.");
        }
        for (Genero genero : listaGeneros) {
            System.out.println(genero);
        }

        System.out.println("\n__________ Lista de vídeos __________ ");
        q = manager.query();
        q.constrain(Video.class);
        List<Video> listaVideos = q.execute();
        if (listaVideos.isEmpty()) {
            System.out.println("Nenhum vídeo está cadastrado.");
        }
        for (Video video : listaVideos) {
            System.out.println(video);
        }
        Util.desconectar();
    }
    

    public static void main(String[] args) {
        new Listar();
    }
}
