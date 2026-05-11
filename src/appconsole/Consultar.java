package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import modelo.Genero;
import modelo.Video;
import util.Util;

public class Consultar {
    private ObjectContainer manager;

    public Consultar() {
        Util.conectar();
        manager = Util.getManager();

        // 1ª consulta - - Quais os vídeos de classificação X?

        // Declaração da variável X de consulta a ser restringida
        int x = 4;

        String textoConsulta1 = "__________ 1ª consulta __________\n- Quais os vídeos de classificação " + x + " estrelas?";

        System.out.println(textoConsulta1);

        Query q = manager.query();
        q.constrain(Video.class);
        q.descend("classificacao").constrain(x);
        List<Video> videos = q.execute();
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

        q = manager.query();
        q.constrain(Video.class);
        q.descend("listaGeneros").descend("nome").constrain(y);
        videos = q.execute();
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

        q = manager.query();
        q.constrain(Genero.class);

        // Uso de filtro implementando classe Evaluation do SODA - Filtro customizado

        q.constrain(new Filtro1(z, n)); // Construtor necessário para passar os parâmetros de ocorrência N e a classificação desejada Y

        List<Genero> generos = q.execute();
        if (generos.isEmpty()) {
            System.out.println("Nenhum gênero encontrado");
        }
        for (Genero genero : generos) {
            System.out.println(genero);
        }

        // Fim

        Util.desconectar();
        System.out.println("\nFim da consulta.");
    }

    public static void main(String[] args) {
        new Consultar();
    }
}

class Filtro1 implements Evaluation {
    private final int classificacao;
    private final int minimo;

    public Filtro1(int classificacao, int minimo) {
        this.classificacao = classificacao;
        this.minimo = minimo;
    }

    @Override
    public void evaluate(Candidate candidate) {
        Genero genero = (Genero) candidate.getObject();
        int i = 0;

        for (Video video : genero.getListaVideos()) {
            if (video.getClassificacao() == classificacao) {
                i++;
            }
        }
        candidate.include(i > minimo);
    }
}