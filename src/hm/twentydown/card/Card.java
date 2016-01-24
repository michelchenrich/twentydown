package hm.twentydown.card;

public class Card implements Comparable<Card> {
    private Kind kind;
    private Suit suit;

    public Card(Suit suit, Kind kind) {
        this.suit = suit;
        this.kind = kind;
    }

    protected Card(Card card) {
        this.suit = card.suit;
        this.kind = card.kind;
    }

    public Kind getKind() {
        return kind;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean hasSuit(Suit suit) {
        return this.suit.equals(suit);
    }

    public boolean equals(Object other) {
        return other instanceof Card && equalsCard((Card) other);
    }

    protected boolean equalsCard(Card other) {
        return kind.equals(other.kind) && suit.equals(other.suit);
    }

    public int compareTo(Card card) {
        if (equals(card))
            return 0;
        else
            return compareToDifferent(card);
    }

    private int compareToDifferent(Card other) {
        if (suit.equals(other.suit))
            return kind.compareTo(other.kind);
        else
            throw new IllegalComparisonException();
    }
}
