package dragonknight.relics;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import dragonknight.actions.DrawBrandCardAction;
import dragonknight.character.DragonPrince;
import dragonknight.powers.BrandsCallPower;

public class BrandsCall extends BaseRelic {
    public static final String ID = makeID("BrandsCall");

    public BrandsCall() {
        super(ID, "BrandsCall", RelicTier.COMMON, LandingSound.MAGICAL);
        pool = DragonPrince.Enums.CARD_COLOR;
    }

    // public void onEquip() {
    //     AbstractDungeon.player.masterHandSize++;
    // }

    // public void onUnequip() {
    //     AbstractDungeon.player.masterHandSize--;
    // }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        this.flash();
        addToBot(new DrawBrandCardAction(1));
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new BrandsCallPower(p, 1)));
    }

}
