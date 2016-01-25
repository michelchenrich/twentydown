package hm.twentydown.trick;

import hm.twentydown.card.Cards;
import hm.twentydown.card.Suit;
import hm.twentydown.player.Player;

public class TrickCards extends Cards<TrickCard> {
    private Suit trumpSuit;

    public TrickCards(Suit trumpSuit) {
        this.trumpSuit = trumpSuit;
    }

    public boolean isWinner(Player player) {
        return getWinningCard().isPlayedBy(player);
    }

    public Player getWinner() {
        return getWinningCard().getPlayer();
    }

    private TrickCard getWinningCard() {
        if (hasSuit(trumpSuit))
            return getPlayerOfHighestCard(trumpSuit);
        else
            return getPlayerOfHighestCard(getFollowSuit());
    }

    private TrickCard getPlayerOfHighestCard(Suit suit) {
        return filterBySuit(suit).stream().sorted().findFirst().get();
    }

    public Suit getFollowSuit() {
        return getFirst().getSuit();
    }
}
