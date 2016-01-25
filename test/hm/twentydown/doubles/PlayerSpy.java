package hm.twentydown.doubles;

import hm.twentydown.player.Player;
import hm.twentydown.trick.Trick;

public class PlayerSpy extends Player {
    public Trick visitedTrick;

    public PlayerSpy(String name) {
        super(name);
    }

    public void updateCards(Trick trick) {
        this.visitedTrick = trick;
    }
}
