package dragonknight.relics;

import static dragonknight.DragonKnightMod.*;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;

import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;

public class HeavenlyFate extends BaseRelic {
    public static final String ID = makeID("HeavenlyFate");

    public HeavenlyFate() {
        super(ID, "HeavenlyFate", RelicTier.SHOP, LandingSound.MAGICAL);
        pool = DragonPrince.Enums.CARD_COLOR;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStartPostDraw() {
        addToBot(new SelectCardsInHandAction(1, DragonKnightMod.selectCardTips.TEXT_DICT.get("HeavenlyFate"), true,
                true, x -> x.type.equals(CardType.ATTACK), cards -> {
                    for (AbstractCard c : cards) {
                        c.exhaust = true;
                        c.isEthereal = true;
                        c.tags.add(DragonKnightMod.Enums.EXHAUST);
                        c.tags.add(DragonKnightMod.Enums.ETHEREAL);
                        c.initializeDescription();
                    }
                }));
    }

}
