package hm.twentydown;

import hm.twentydown.card.Card;
import hm.twentydown.card.Kind;
import hm.twentydown.card.Suit;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class FixedOrderDeckTest {
    private FixedOrderDeck deck;

    @Before
    public void setUp() throws Exception {
        deck = new FixedOrderDeck();
    }

    @Test
    public void startsWith32Cards() {
        assertEquals(32, deck.getSize());
    }

    @Test
    public void afterTakingACard_itHas31() {
        deck.take();
        assertEquals(31, deck.getSize());
    }

    @Test
    public void makingNewAfterTakingACard_itHas32Again() {
        deck.take();
        deck = (FixedOrderDeck) deck.makeNew();
        assertEquals(32, deck.getSize());
    }

    @Test
    public void fixedCardOrder() {
        assertNextCardsOfSuit(Suit.SPADES);
        assertNextCardsOfSuit(Suit.CLUBS);
        assertNextCardsOfSuit(Suit.DIAMONDS);
        assertNextCardsOfSuit(Suit.HEARTS);
    }

    private void assertNextCardsOfSuit(Suit suit) {
        assertNextCard(suit, Kind.ACE);
        assertNextCard(suit, Kind.KING);
        assertNextCard(suit, Kind.QUEEN);
        assertNextCard(suit, Kind.JACK);
        assertNextCard(suit, Kind.TEN);
        assertNextCard(suit, Kind.NINE);
        assertNextCard(suit, Kind.EIGHT);
        assertNextCard(suit, Kind.SEVEN);
    }

    private void assertNextCard(Suit spades, Kind kind) {
        assertEquals(new Card(spades, kind), deck.take());
    }
}
