package hm.twentydown.player;

import hm.twentydown.card.Card;

public class PlayerCard extends Card {
    private boolean playable;

    public static PlayerCard makePlayable(Card card) {
        return new PlayerCard(card, true);
    }

    public static PlayerCard makeNotPlayable(Card card) {
        return new PlayerCard(card, false);
    }

    private PlayerCard(Card card, boolean playable) {
        super(card);
        this.playable = playable;
    }

    public boolean isPlayable() {
        return playable;
    }
}
