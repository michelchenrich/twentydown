package hm.twentydown.player;

import hm.twentydown.Deck;
import hm.twentydown.trick.Trick;

import java.util.ArrayList;
import java.util.Collection;

public class Players extends ArrayList<Player> {
    public Players() {
    }

    public Players(Collection<Player> players) {
        this.addAll(players);
    }

    public Players(Player... players) {
        for (Player player : players)
            add(player);
    }

    public Players rotate() {
        return new Players(this).shiftLeft();
    }

    private Players shiftLeft() {
        if (!isEmpty())
            add(remove(0));
        return this;
    }

    public Players rotateUntil(Player player) {
        Players rotated = this;
        while (!rotated.isFirst(player))
            rotated = rotated.rotate();
        return rotated;
    }

    private boolean isFirst(Player player) {
        return getFirst().equals(player);
    }

    public void drawFrom(Deck deck) {
        forEach(player -> player.drawFrom(deck));
    }

    public void updateCards(Trick trick) {
        forEach(player -> player.updateCards(trick));
    }

    public void updateScore(Iterable<Trick> tricks) {
        forEach(player -> player.updateScore(tricks));
    }

    public Player getFirst() {
        return get(0);
    }
}
