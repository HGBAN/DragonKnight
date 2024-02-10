package dragonknight.relics;

import static dragonknight.DragonKnightMod.*;

import dragonknight.character.DragonPrince;

public class KingsInsight extends BaseRelic {
    public static final String ID = makeID("KingsInsight");

    public KingsInsight() {
        super(ID, "KingsInsight", RelicTier.COMMON, LandingSound.MAGICAL);
        pool = DragonPrince.Enums.CARD_COLOR;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    

}
