package hm.twentydown.player;

import hm.twentydown.card.Cards;
import hm.twentydown.trick.Trick;

import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayerCards extends Cards<PlayerCard> {
    public PlayerCards update(Trick trick) {
        if (trick.hasFollowSuit() && hasSuit(trick.getFollowSuit()))
            return map(card -> {
                if (card.hasSuit(trick.getFollowSuit()))
                    return PlayerCard.makePlayable(card);
                else
                    return PlayerCard.makeNotPlayable(card);
            });
        else
            return map(PlayerCard::makePlayable);
    }

    public PlayerCards map(Function<PlayerCard, PlayerCard> mapper) {
        return stream().map(mapper).collect(Collectors.toCollection(PlayerCards::new));
    }
}
