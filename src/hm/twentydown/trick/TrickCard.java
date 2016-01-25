package hm.twentydown.trick;

import hm.twentydown.card.Card;
import hm.twentydown.player.Player;

public class TrickCard extends Card {
    private Player player;

    public TrickCard(Player player, Card card) {
        super(card);
        this.player = player;
    }

    public boolean isPlayedBy(Player player) {
        return this.player.equals(player);
    }

    public Player getPlayer() {
        return player;
    }
}
