package hm.twentydown.card;

import static hm.twentydown.card.Kind.*;
import static hm.twentydown.card.Suit.CLUBS;
import static hm.twentydown.card.Suit.SPADES;
import static org.junit.Assert.*;
import org.junit.Test;

public class CardTest {
    private void assertPositive(int compareResult) {
        assertTrue(compareResult > 0);
    }

    private void assertNegative(int compareResult) {
        assertTrue(compareResult < 0);
    }

    @Test
    public void kind() {
        assertEquals(ACE, new Card(SPADES, ACE).getKind());
        assertEquals(KING, new Card(CLUBS, KING).getKind());
    }

    @Test
    public void suit() {
        assertEquals(SPADES, new Card(SPADES, ACE).getSuit());
        assertEquals(CLUBS, new Card(CLUBS, KING).getSuit());
    }

    @Test
    public void sameKindAndSuitAreEqual() {
        assertEquals(new Card(SPADES, ACE), new Card(SPADES, ACE));
    }

    @Test
    public void differentKindsAreNotEqual() {
        assertNotEquals(new Card(SPADES, ACE), new Card(SPADES, KING));
    }

    @Test
    public void differentSuitsAreNotEqual() {
        assertNotEquals(new Card(SPADES, ACE), new Card(CLUBS, ACE));
    }

    @Test
    public void equalCardsAlsoReturnZeroWhenCompared() {
        assertEquals(0, new Card(SPADES, ACE).compareTo(new Card(SPADES, ACE)));
    }

    @Test
    public void differentCardsReturnDifferentThanZeroWhenCompared() {
        assertNotEquals(0, new Card(SPADES, ACE).compareTo(new Card(SPADES, KING)));
    }

    @Test(expected = IllegalComparisonException.class)
    public void comparingDifferentSuitsIsNotPossible() {
        new Card(SPADES, ACE).compareTo(new Card(CLUBS, ACE));
    }

    @Test
    public void highestCardsComeFirst_theReturnIsNegative() {
        assertNegative(new Card(SPADES, ACE).compareTo(new Card(SPADES, KING)));
        assertNegative(new Card(SPADES, KING).compareTo(new Card(SPADES, QUEEN)));
        assertNegative(new Card(SPADES, QUEEN).compareTo(new Card(SPADES, JACK)));
        assertNegative(new Card(SPADES, JACK).compareTo(new Card(SPADES, TEN)));
        assertNegative(new Card(SPADES, TEN).compareTo(new Card(SPADES, NINE)));
        assertNegative(new Card(SPADES, NINE).compareTo(new Card(SPADES, EIGHT)));
        assertNegative(new Card(SPADES, EIGHT).compareTo(new Card(SPADES, SEVEN)));
    }

    @Test
    public void lowestCardsComeLast_theReturnIsPositive() {
        assertPositive(new Card(SPADES, KING).compareTo(new Card(SPADES, ACE)));
        assertPositive(new Card(SPADES, QUEEN).compareTo(new Card(SPADES, KING)));
        assertPositive(new Card(SPADES, JACK).compareTo(new Card(SPADES, QUEEN)));
        assertPositive(new Card(SPADES, TEN).compareTo(new Card(SPADES, JACK)));
        assertPositive(new Card(SPADES, NINE).compareTo(new Card(SPADES, TEN)));
        assertPositive(new Card(SPADES, EIGHT).compareTo(new Card(SPADES, NINE)));
        assertPositive(new Card(SPADES, SEVEN).compareTo(new Card(SPADES, EIGHT)));
    }

    @Test
    public void hasSuit() {
        assertTrue(new Card(SPADES, ACE).hasSuit(SPADES));
    }

    @Test
    public void doesNotHaveSuit() {
        assertFalse(new Card(SPADES, ACE).hasSuit(CLUBS));
    }
}
