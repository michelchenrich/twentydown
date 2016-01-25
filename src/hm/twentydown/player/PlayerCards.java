package hm.twentydown.player;

import hm.twentydown.card.Cards;
import static hm.twentydown.player.PlayerCard.makeNotPlayable;
import static hm.twentydown.player.PlayerCard.makePlayable;
import hm.twentydown.trick.Trick;

import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayerCards extends Cards<PlayerCard> {
    public PlayerCards update(Trick trick) {
        if (canOnlyFollowSuit(trick))
            return convertWith(card -> {
                if (card.hasSuit(trick.getFollowSuit()))
                    return makePlayable(card);
                else
                    return makeNotPlayable(card);
            });
        else
            return convertWith(PlayerCard::makePlayable);
    }

    private boolean canOnlyFollowSuit(Trick trick) {
        return trick.hasFollowSuit() && hasSuit(trick.getFollowSuit());
    }

    private PlayerCards convertWith(Function<PlayerCard, PlayerCard> mapper) {
        return stream().map(mapper).collect(Collectors.toCollection(PlayerCards::new));
    }
}
