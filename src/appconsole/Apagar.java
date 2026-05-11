package appconsole;

import java.util.ArrayList;
import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Genero;
import modelo.Video;
import util.Util;

public class Apagar {
    private ObjectContainer manager;

    public Apagar() {
        Util.conectar();
        manager = Util.getManager();

        // Removendo OBJETO vídeo da Coca-Cola pelo título, com relacionamento de gênero para Publicidade

        Query q = manager.query();
        q.constrain(Video.class);
        q.descend("titulo").constrain("Coca-Cola | Holidays Are Coming");
        // Ou por ID:
        // q.descend("id").constrain("3");

        List<Video> lista = q.execute();

        if (lista.isEmpty()) {
            System.out.println("O vídeo a ser apagado não existe.");
            Util.desconectar();
            return;
        } else {
        	Video video = lista.getFirst();
            List<Genero> generos = new ArrayList<>(video.getListaGeneros());
        	for (Genero g : generos) {
    			g.removerVideo(video);
    			manager.store(g);
	        }

        	// Cascata desligada.

            manager.delete(video);
            manager.commit();
            System.out.println("Vídeo '" + video.getTitulo() + "' apagado com sucesso");
        }
 
        Util.desconectar();
    }


public static void main(String[] args) { new Apagar(); }
}