package hm.twentydown;

import hm.twentydown.card.Card;
import hm.twentydown.card.Kind;
import hm.twentydown.card.Suit;

import java.util.ArrayList;
import java.util.List;

public class FixedOrderDeck implements Deck {
    private List<Card> cards;

    public FixedOrderDeck() {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values())
            for (Kind kind : Kind.values())
                cards.add(new Card(suit, kind));
    }

    public FixedOrderDeck(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public Card take() {
        return cards.remove(0);
    }

    public Deck makeNew() {
        return new FixedOrderDeck();
    }

    public int getSize() {
        return cards.size();
    }
}
