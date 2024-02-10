package dragonknight.relics;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import dragonknight.character.DragonPrince;

public class DivineFlameRing extends BaseRelic {
    public static final String ID = makeID("DivineFlameRing");

    public DivineFlameRing() {
        super(ID, "DivineFlameRing", RelicTier.COMMON, LandingSound.MAGICAL);
        pool = DragonPrince.Enums.CARD_COLOR;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onAddPower() {
        AbstractPlayer p = AbstractDungeon.player;
        this.flash();
        addToBot(new GainEnergyAction(1));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1)));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 1)));
    }
}
