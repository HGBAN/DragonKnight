package dragonknight.powers;

import static dragonknight.DragonKnightMod.makeID;

import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
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

    // @Override
    // public void atStartOfTurn() {
    // addToBot(new RemoveSpecificPowerAction(owner, owner, this));

    // }

    // @Override
    // public void onInitialApplication() {
    // if (owner.hasPower(makeID("WhiteDragon"))) {
    // addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    // return;
    // }

    // if (owner.hasPower(makeID("PhantomDragonPower"))) {
    // PhantomDragonPower power = (PhantomDragonPower)
    // owner.getPower(makeID("PhantomDragonPower"));
    // if (!power.isUsed) {
    // if (owner.isPlayer) {
    // addToBot(new ApplyPowerAction(owner, owner, new NextCardFreePower(owner,
    // 1)));
    // power.isUsed = true;
    // }
    // }
    // }
    // }

    @Override
    protected void initializeTemplate() {
        // addToBot(new RemoveSpecificPowerAction(owner, owner, makeID("WhiteDragon")));
        if (owner.hasPower(makeID("WhiteDragon"))) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            return;
        }
        addToBot(new GainBlockAction(owner, 10));
        addToBot(new AbstractGameAction() {

            @Override
            public void update() {
                AbstractPlayer player = AbstractDungeon.player;

                List<AbstractCard> cards = player.hand.group.stream()
                        .filter(card -> !card.hasTag(DragonKnightMod.Enums.BRAND)).collect(Collectors.toList());
                if (cards.size() > 0) {
                    int index = AbstractDungeon.cardRng.random.nextInt(cards.size());
                    AbstractCard card = cards.get(index);
                    // card.exhaustOnUseOnce = true;
                    card.exhaust = true;
                    card.rawDescription += " NL " + GameDictionary.EXHAUST.NAMES[0] + " NL dragonknight:"
                            + DragonKnightMod.keywords.get("Brand").PROPER_NAME;

                    card.tags.add(DragonKnightMod.Enums.BRAND);
                    card.initializeDescription();
                    card.glowColor = Color.GOLD.cpy();
                }

                this.isDone = true;
            }

        });

        if (owner.hasPower(makeID("BlackBrandPower"))) {
            addToBot(new ApplyPowerAction(owner, owner,
                    new PlatedArmorPower(owner, owner.getPower(makeID("BlackBrandPower")).amount)));
        }
    }
}
