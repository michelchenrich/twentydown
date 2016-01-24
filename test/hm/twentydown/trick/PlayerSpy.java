package hm.twentydown.trick;

import hm.twentydown.player.Player;

public class PlayerSpy extends Player {
    public Trick visitedTrick;

    public PlayerSpy(String name) {
        super(name);
    }

    public void visit(Trick trick) {
        this.visitedTrick = trick;
    }
}
