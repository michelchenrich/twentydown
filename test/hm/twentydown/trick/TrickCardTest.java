package hm.twentydown.trick;

import hm.twentydown.card.Card;
import hm.twentydown.card.Kind;
import hm.twentydown.card.Suit;
import hm.twentydown.player.Player;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TrickCardTest {
    private Card card = new Card(Suit.SPADES, Kind.ACE);

    @Test
    public void isACard() {
        assertIsCard(new TrickCard(new Player("A"), card));
    }

    @Test
    public void isPlayedBySamePlayer() {
        Player player = new Player("A");
        assertTrue(new TrickCard(player, card).isPlayedBy(player));
    }

    @Test
    public void isPlayedByEqualPlayer() {
        assertTrue(new TrickCard(new Player("A"), card).isPlayedBy(new Player("A")));
    }

    @Test
    public void isNotPlayedByDifferentPlayer() {
        assertFalse(new TrickCard(new Player("A"), card).isPlayedBy(new Player("B")));
    }

    private void assertIsCard(Object object) {
        assertTrue(object instanceof Card);
    }
}
