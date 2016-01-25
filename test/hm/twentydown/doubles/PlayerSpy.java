package hm.twentydown.doubles;

import hm.twentydown.player.Player;
import hm.twentydown.trick.Trick;

public class PlayerSpy extends Player {
    public Trick trickForCardsUpdate;
    public Iterable<Trick> tricksForScoreUpdate;

    public PlayerSpy(String name) {
        super(name);
    }

    public void updateCards(Trick trick) {
        trickForCardsUpdate = trick;
    }

    public void updateScore(Iterable<Trick> tricks) {
        tricksForScoreUpdate = tricks;
    }
}
