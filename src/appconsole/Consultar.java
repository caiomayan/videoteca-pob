package appconsole;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import modelo.Genero;
import modelo.Video;
import util.Util;

public class Consultar {
    private EntityManager manager;

    public Consultar() {
    	try {
	        Util.conectar();
	        manager = Util.getManager();
	
	        // 1ª consulta - - Quais os vídeos de X?
	
	        // Declaração da variável X de consulta a ser restringida
	        int x = 4;
	
	        String textoConsulta1 = "__________ 1ª consulta __________\n- Quais os vídeos de " + x + " estrelas?";
	
	        System.out.println(textoConsulta1);
	
	        TypedQuery<Video> q1 = manager.createQuery("select v from Video v where v.classificacao = :x", Video.class);
	        q1.setParameter("x", x);
	        List<Video> videos = q1.getResultList();
	        if (videos.isEmpty()) {
	            System.out.println("Nenhum vídeo encontrado");
	        }
	        for (Video video : videos) {
	            System.out.println(video);
	        }
	
	        // 2ª consulta - Quais vídeos do gênero de nome X?
	
	        String y = "Ação";
	
	        String textoConsulta2 = "\n__________ 2ª consulta __________\n- Quais vídeos do gênero de nome " + y + "?";
	
	        System.out.println(textoConsulta2);
	
	        TypedQuery<Video> q2 = manager.createQuery("select v from Video v join v.listaGeneros g where g.nome = :y", Video.class);
	        q2.setParameter("y", y);
	        videos = q2.getResultList();
	        if (videos.isEmpty()) {
	            System.out.println("Nenhum vídeo encontrado");
	        }
	        for (Video video : videos) {
	            System.out.println(video);
	        }
	
	        // 3ª consulta - Quais os gêneros que tem mais de N vídeos com classificação X?
	
	        // Declaração variável Z do número da classificação do vídeo
	        int z = 4;
	        // Declarando variável N da quantidade de ocorrências da consulta
	        int n = 2;
	
	        String textoConsulta3 = "\n__________ 3ª consulta __________\n- Quais os gêneros que tem mais de " + n
	                + " vídeo/vídeos com classificação de " + z + " estrelas?\n";
	
	        System.out.println(textoConsulta3);
	
	        TypedQuery<Genero> q3 = manager.createQuery("select g from Genero g join g.listaVideos v where v.classificacao = :z group by g.id, g.nome having count(v) > :n", Genero.class);
	        q3.setParameter("z", z);
	        q3.setParameter("n", n);
	        List<Genero> generos = q3.getResultList();
	        if (generos.isEmpty()) {
	            System.out.println("Nenhum gênero encontrado");
	        }
	        for (Genero genero : generos) {
	            System.out.println(genero);
	        }
	
	        // Fim
    	} catch (Exception e) {
			if (manager != null && manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
    		System.out.println(e.getMessage());
    	} finally {
            Util.desconectar();
    	}
        System.out.println("\nFim da consulta.");
    }

    public static void main(String[] args) {
        new Consultar();
    }
}