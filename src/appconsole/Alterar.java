package appconsole;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import modelo.Genero;
import modelo.Video;
import util.Util;

public class Alterar {
    private EntityManager manager;

    public Alterar() {
    	try {
	        Util.conectar();
	        manager = Util.getManager();
	
	        // Remover relacionamento entre dois objetos - Remover um gênero de vídeo e um vídeo de gênero
	
	        String x = "JN: Governador do Rio de Janeiro, Cláudio Castro, do PL, renuncia ao cargo"; // Vídeo
	        String n = "Jornalismo"; // Gênero
	
	        manager.getTransaction().begin();
	        
	        TypedQuery<Video> q = manager.createQuery("select v from Video v where v.titulo = :titulo", Video.class);
	        q.setParameter("titulo", x);
	        Video video = q.getSingleResult();
	
	        boolean encontrou = false;
	        for (Genero g : video.getListaGeneros()) {
	            if (g.getNome().equals(n)) {
	                video.removerGenero(g);
	                System.out.println("Gênero \n'" + g.getNome() + "' \nremovido do vídeo \n'" + video.getTitulo() + "' \ncom sucesso.\nRelacionamento alterado com sucesso.");
	                
	                encontrou = true;
	                break;
	            }
	        }
	
	        if (!encontrou) {
	            System.out.println("Esse vídeo não tem o gênero especificado.");
	        }
	        
	        manager.getTransaction().commit();
    	} catch (NoResultException e) {
    		System.out.println("Não existe nenhum vídeo com o nome especificado.");
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	} finally {
            Util.desconectar();
    	}
    }


public static void main(String[] args) { new Alterar(); }
}