package hm.twentydown.doubles;

import hm.twentydown.card.Suit;
import hm.twentydown.player.Player;
import hm.twentydown.trick.Trick;

public class TrickStub extends Trick {
    private Player winner;
    private Suit trumpSuit;
    private Suit followSuit;

    public TrickStub() {
        super(null, null);
    }

    public void setTrumpSuit(Suit suit) {
        trumpSuit = suit;
    }

    public void setFollowSuit(Suit suit) {
        followSuit = suit;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public boolean isWinner(Player player) {
        return player.equals(winner);
    }

    public Suit getTrumpSuit() {
        return trumpSuit;
    }

    public Suit getFollowSuit() {
        return followSuit;
    }

    public boolean hasFollowSuit() {
        return followSuit != null;
    }
}
