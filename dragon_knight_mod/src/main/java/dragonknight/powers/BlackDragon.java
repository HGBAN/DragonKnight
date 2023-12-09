package dragonknight.powers;

import static dragonknight.DragonKnightMod.addBrandToCard;
import static dragonknight.DragonKnightMod.makeID;

import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import dragonknight.DragonKnightMod;

public class BlackDragon extends BeDragonPower {
    public static final String POWER_ID = makeID("BlackDragon");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BlackDragon(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    protected void initializeTemplate() {
        // addToBot(new RemoveSpecificPowerAction(owner, owner, makeID("WhiteDragon")));
        if (owner.hasPower(makeID("WhiteDragon"))) {
            addToBot(new GainEnergyAction(1));
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            done = true;
            return;
        }
        addToBot(new GainBlockAction(owner, 10));
        addToBot(new AbstractGameAction() {

            @Override
            public void update() {
                AbstractPlayer player = AbstractDungeon.player;

                List<AbstractCard> cards = player.hand.group.stream()
                        .filter(card -> !card.hasTag(DragonKnightMod.Enums.BRAND)
                                && !card.hasTag(DragonKnightMod.Enums.BRAND2)
                                && !card.hasTag(DragonKnightMod.Enums.ANTI_BRAND)
                                && card.canUse(null, null))
                        .collect(Collectors.toList());
                if (cards.size() > 0) {
                    int index = AbstractDungeon.cardRng.random(cards.size() - 1);
                    AbstractCard card = cards.get(index);
                    // card.exhaustOnUseOnce = true;
                    if (!card.exhaust) {
                        card.exhaust = true;
                        card.tags.add(DragonKnightMod.Enums.EXHAUST);
                        // card.rawDescription += " NL " + GameDictionary.EXHAUST.NAMES[0];
                    }
                    addBrandToCard(card);

                    card.initializeDescription();
                    card.glowColor = Color.GOLD.cpy();
                }

                this.isDone = true;
            }

        });

        if (owner.hasPower(makeID("BlackBrandPower"))) {
            owner.getPower(makeID("BlackBrandPower")).flash();
            addToBot(new ApplyPowerAction(owner, owner,
                    new PlatedArmorPower(owner, owner.getPower(makeID("BlackBrandPower")).amount)));
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        addToBot(new GainEnergyAction(1));
    }
}
