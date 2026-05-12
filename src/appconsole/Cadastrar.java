package appconsole;


// Import das classes de negócio

import jakarta.persistence.EntityManager;
import modelo.Genero;
import modelo.Video;
import util.Util;

public class Cadastrar {
    // Declaração da variável do manager do PostgreSQL para persistir os objetos
    private EntityManager manager;

    public Cadastrar() {
    	try {
	        // Conectar ao PostgreSQL
	        Util.conectar();
	
	        // Variável de manipulação dos objetos no banco
	        manager = Util.getManager();
	
	        System.out.println("Cadastrando...");
	        manager.getTransaction().begin();
	        
	        // Instanciando gêneros e vídeos
	        Genero documentario = new Genero("Documentário");
	        Genero acao = new Genero("Ação");
	        Genero games = new Genero("Games");
	        Genero publicidade = new Genero("Publicidade");
	        Genero comedia = new Genero("Comédia");
	        Genero futebol = new Genero("Futebol");
	        Genero jornalismo = new Genero("Jornalismo");
	        // Gênero que não está relacionado a nenhum vídeo
	        Genero tutorial = new Genero("Tutorial");
	
	        
	        
	        Video ad_cocacola = new Video("Coca-Cola | Holidays Are Coming", "2025-11-03",
	                "https://www.youtube.com/watch?v=Yy6fByUmPuE", 5);
	        Video como_jogar_cs = new Video("Dicas de como iniciar e aprender a jogar o CS2 (Counter Strike 2)",
	                "2023-09-28", "https://www.youtube.com/watch?v=INAt-aIZ28M", 4);
	        Video invasao_ucrania = new Video("Especial: Ucrania - Arquivos de Guerra", "2023-02-24",
	                "https://www.youtube.com/watch?v=SS6hLG4smf0", 4);
	        Video standup = new Video("ALMA DE BRASILEIRO - Paul Cabannes (stand up - show completo)",
	                "2026-03-26", "https://www.youtube.com/watch?v=BdTVDYUrCNM", 2);
	        Video geopolitica_eua_x_ira = new Video("Entenda o conflito entre IRÃ e ESTADOS UNIDOS de uma FORMA SIMPLES",
	                "2025-10-08", "https://www.youtube.com/watch?v=3D_vS0XlC1w", 4);
	        Video irl_motovlog = new Video("SUSTOS DE MOTO (EP. 249)", "2026-03-23",
	                "https://www.youtube.com/watch?v=m5iNeGIkTKo", 3);
	        Video brasil_x_franca = new Video("BRASIL 1 X 2 FRANÇA | MELHORES MOMENTOS | AMISTOSO INTERNACIONAL | ge tv",
	                "2026-03-26", "https://www.youtube.com/watch?v=MXFDz0uOSxM", 4);
	        Video governador_rj_renuncia_cargo = new Video(
	                "JN: Governador do Rio de Janeiro, Cláudio Castro, do PL, renuncia ao cargo", "2026-03-23",
	                "https://www.youtube.com/watch?v=W5iRrg8NpEg", 3);
	
	        // Novos cadastros fictícios: poucos, mas cobrindo cenários diferentes
	        Video bastidores_copa = new Video("Bastidores da Copa: preparação e estratégia", "2026-04-01",
	                "https://www.youtube.com/watch?v=exemplo1", 4);
	        Video analise_lancamento_game = new Video("Análise de lançamento: RPG de mundo aberto", "2026-02-18",
	                "https://www.youtube.com/watch?v=exemplo2", 5);
	        Video reportagem_mobilidade = new Video("Reportagem especial: mobilidade urbana no Brasil", "2026-01-12",
	                "https://www.youtube.com/watch?v=exemplo3", 3);
	        Video humor_sketch = new Video("Sketch: reunião de condomínio", "2025-12-05",
	                "https://www.youtube.com/watch?v=exemplo4", 2);
	
	        Video desconhecido = new Video("Desconhecido");
	
	        // adicionando gêneros aos vídeos, dando store em cada alteração para o commit final
	
	        governador_rj_renuncia_cargo.addGenero(jornalismo);
	        manager.persist(governador_rj_renuncia_cargo);
	
	        brasil_x_franca.addGenero(futebol);
	        manager.persist(brasil_x_franca);
	
	        ad_cocacola.addGenero(publicidade);
	        manager.persist(ad_cocacola);
	
	        como_jogar_cs.addGenero(games);
	        como_jogar_cs.addGenero(acao);
	        manager.persist(como_jogar_cs);
	
	        invasao_ucrania.addGenero(documentario);
	        invasao_ucrania.addGenero(acao);
	        manager.persist(invasao_ucrania);
	
	        standup.addGenero(comedia);
	        manager.persist(standup);
	
	        geopolitica_eua_x_ira.addGenero(documentario);
	        manager.persist(geopolitica_eua_x_ira);
	
	        irl_motovlog.addGenero(acao);
	        manager.persist(irl_motovlog);
	
	        bastidores_copa.addGenero(futebol);
	        bastidores_copa.addGenero(documentario);
	        manager.persist(bastidores_copa);
	
	        // teste duplicidade
	        analise_lancamento_game.addGenero(games);
	        analise_lancamento_game.addGenero(acao);
	        analise_lancamento_game.addGenero(games);
	        manager.persist(analise_lancamento_game);
	
	        reportagem_mobilidade.addGenero(jornalismo);
	        reportagem_mobilidade.addGenero(documentario);
	        manager.persist(reportagem_mobilidade);
	
	        humor_sketch.addGenero(comedia);
	        manager.persist(humor_sketch);
	
	        // Guardando objetos órfãos
	        manager.persist(desconhecido);
	
	        manager.persist(tutorial);
	
	        // commit de todas as inserções
			manager.getTransaction().commit();

			System.out.println("\nTérmino do cadastro!");

	    } catch(Exception e) {
			System.out.println(e.getMessage());
	    } finally {
			Util.desconectar();
		}
    }

    public static void main(String[] args) {
        // Execução da aplicação
        new Cadastrar();
    }
}
