package com.team.seeders;

import com.team.Repository.PlayerRepository;
import com.team.models.Player;
import org.springframework.boot.CommandLineRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayeurSeeder implements CommandLineRunner {

  private final PlayerRepository playerRepository;

  public PlayeurSeeder(PlayerRepository playerRepository){
      this.playerRepository =playerRepository;
  }
    @Override
    public void run(String... args) throws Exception {
        if (playerRepository.count() == 0) {
            List<Player> joueurs = new ArrayList<>();
            Random random = new Random();

            // Générer 100 joueurs aléatoires
            for (int i = 1; i <= 100; i++) {
                 Player joueur = new Player();
                 joueur.setName("joueur "+i);
                joueur.setPosition(genererPosteAleatoire());
                joueurs.add(joueur);
            }

            // Enregistrer les joueurs dans la base de données
            this.playerRepository.saveAll(joueurs);

            System.out.println("100 joueurs ont été insérés dans la base de données !");
        } else {
            System.out.println("La table Joueur contient déjà des données. Aucun joueur ajouté.");
        }



    }


    private String genererPosteAleatoire() {
        String[] postes = {"Gardien", "Défenseur", "Milieu", "Attaquant"};
        Random random = new Random();
        return postes[random.nextInt(postes.length)];
    }


}
