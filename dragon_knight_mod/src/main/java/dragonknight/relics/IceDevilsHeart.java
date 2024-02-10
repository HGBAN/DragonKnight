package dragonknight.relics;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import dragonknight.DragonKnightMod;
import dragonknight.actions.DrawBrandCardAction;
import dragonknight.character.DragonPrince;
import dragonknight.powers.IceDevilsHeartPower;

public class IceDevilsHeart extends BaseRelic {
    public static final String ID = makeID("IceDevilsHeart");

    public IceDevilsHeart() {
        super(ID, "IceDevilsHeart", RelicTier.BOSS, LandingSound.MAGICAL);
        pool = DragonPrince.Enums.CARD_COLOR;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStartPostDraw() {
        this.flash();
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new DrawBrandCardAction(1, (x) -> x.hasTag(DragonKnightMod.Enums.ANTI_BRAND)));
        addToBot(new ApplyPowerAction(p, p, new IceDevilsHeartPower(p, 1)));
        addToBot(new GainEnergyAction(2));
        addToBot(new DrawCardAction(2));
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(makeID("AbyssSeal"));
    }
}
