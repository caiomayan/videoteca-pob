package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Genero;
import modelo.Video;
import util.Util;

public class Alterar {
    private ObjectContainer manager;

    public Alterar() {
        Util.conectar();
        manager = Util.getManager();

        // Remover relacionamento entre dois objetos - Remover um gênero de vídeo e um vídeo de gênero

        String x = "JN: Governador do Rio de Janeiro, Cláudio Castro, do PL, renuncia ao cargo"; // Vídeo
        String n = "Jornalismo"; // Gênero

        Query q = manager.query();
        q.constrain(Video.class);
        q.descend("titulo").constrain(x);
        List<Video> videos = q.execute();

        if (videos.isEmpty()) {
            System.out.println("Não existe nenhum vídeo com o nome especificado.");
            Util.desconectar();
            return;
        }

        Video video = videos.getFirst();

        for (Genero g : video.getListaGeneros()) {
            if (g.getNome().equals(n)) {
                video.removerGenero(g);
                System.out.println("Gênero \n'" + g.getNome() + "' \nremovido do vídeo \n'" + video.getTitulo() + "' \ncom sucesso.\nRelacionamento alterado com sucesso.");

                manager.store(video);
                manager.store(g);
                manager.commit();

                Util.desconectar();
                return;
            }
        }

        System.out.println("Esse vídeo não tem o gênero especificado.");
        Util.desconectar();
    }


public static void main(String[] args) { new Alterar(); }
}