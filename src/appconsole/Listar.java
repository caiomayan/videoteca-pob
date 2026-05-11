package appconsole;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Genero;
import modelo.Video;
import util.Util;

public class Listar {
    private EntityManager manager;

    public Listar() { 
    	try {
	        Util.conectar();
	
	        manager = Util.getManager();
	
	        System.out.println("__________ Lista de gêneros __________ ");
			TypedQuery<Genero> queryGenero = manager.createQuery("select g from Genero g", Genero.class); 
			List<Genero> listaGeneros = queryGenero.getResultList();
	        if(listaGeneros.isEmpty()) {
	            System.out.println("Nenhum gênero está cadastrado.");
	        }
	        for (Genero genero : listaGeneros) {
	            System.out.println(genero);
	        }
	
	        System.out.println("\n__________ Lista de vídeos __________ ");
			TypedQuery<Video> queryVideo = manager.createQuery("select v from Video v", Video.class); 
			List<Video> listaVideos = queryVideo.getResultList();
	        if (listaVideos.isEmpty()) {
	            System.out.println("Nenhum vídeo está cadastrado.");
	        }
	        for (Video video : listaVideos) {
	            System.out.println(video);
	        }
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    }
        Util.desconectar();

    }
    

    public static void main(String[] args) {
        new Listar();
    }
}
