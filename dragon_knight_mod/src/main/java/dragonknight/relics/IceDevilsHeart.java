package dragonknight.relics;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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

    public void onEquip() {
        AbstractDungeon.player.masterHandSize += 2;
        AbstractDungeon.player.energy.energyMaster += 2;
    }

    public void onUnequip() {
        AbstractDungeon.player.masterHandSize -= 2;
        AbstractDungeon.player.energy.energyMaster -= 2;
    }

    @Override
    public void atTurnStart() {
        this.flash();
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new DrawBrandCardAction(1, (x) -> x.hasTag(DragonKnightMod.Enums.ANTI_BRAND)));
        addToBot(new ApplyPowerAction(p, p, new IceDevilsHeartPower(p, 1)));
        // addToBot(new GainEnergyAction(2));
        // addToBot(new DrawCardAction(2));
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(makeID("AbyssSeal"));
    }
}
