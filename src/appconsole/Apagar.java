package appconsole;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import modelo.Genero;
import modelo.Video;
import util.Util;

public class Apagar {
    private EntityManager manager;

    public Apagar() {
    	try {
	        Util.conectar();
	        manager = Util.getManager();
	
	        // Removendo OBJETO vídeo da Coca-Cola pelo ID, com relacionamento de gênero para Publicidade
	
	        manager.getTransaction().begin();
	        
	        TypedQuery<Video> q = manager.createQuery("select v from Video v where v.id = :id", Video.class);
	        q.setParameter("id", 3);
	        Video video = q.getSingleResult();
	        
            List<Genero> generos = new ArrayList<>(video.getListaGeneros());
        	for (Genero g : generos) {
    			g.removerVideo(video);
	        }
	
            manager.remove(video);
            manager.getTransaction().commit();
            System.out.println("Vídeo '" + video.getTitulo() + "' apagado com sucesso");
    	} catch (NoResultException e) {
    		System.out.println("O vídeo a ser apagado não existe.");
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	} finally {
            Util.desconectar();
    	}
    }


public static void main(String[] args) { new Apagar(); }
}