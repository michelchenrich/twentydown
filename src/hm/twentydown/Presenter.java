package hm.twentydown;

import hm.twentydown.card.Suit;
import hm.twentydown.player.Player;

public interface Presenter {
    void presentScores(Iterable<Player> players);
    void presentTrumpSuit(Suit trumpSuit);
    void askForPlayers();
    void askForTrumpSuit(Player currentPlayer);
    void askForCard(Player currentPlayer);
}
