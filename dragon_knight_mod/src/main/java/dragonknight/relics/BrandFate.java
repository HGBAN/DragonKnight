package dragonknight.relics;

import static dragonknight.DragonKnightMod.makeID;

import java.util.ArrayList;

import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import dragonknight.character.DragonPrince;
import dragonknight.ui.AntiBrandOption;

public class BrandFate extends BaseRelic {
    public static final String ID = makeID("BrandFate");

    public boolean isUsed = false;

    public BrandFate() {
        super(ID, "BrandFate", RelicTier.COMMON, LandingSound.MAGICAL);
        pool = DragonPrince.Enums.CARD_COLOR;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        options.add(new AntiBrandOption());
    }
    
}
