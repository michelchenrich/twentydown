package hm.twentydown.trick;

import hm.twentydown.card.Cards;
import hm.twentydown.card.Suit;
import hm.twentydown.player.Player;

public class TrickCards extends Cards<TrickCard> {
    public boolean isWinner(Player player, Suit trumpSuit) {
        if (hasSuit(trumpSuit))
            return isPlayerOfHighestCard(trumpSuit, player);
        else
            return isPlayerOfHighestCard(getFollowSuit(), player);
    }

    private boolean isPlayerOfHighestCard(Suit suit, Player player) {
        return filterPlays(suit).stream().sorted().findFirst().get().isPlayedBy(player);
    }

    public Suit getFollowSuit() {
        return get(0).getSuit();
    }
}
