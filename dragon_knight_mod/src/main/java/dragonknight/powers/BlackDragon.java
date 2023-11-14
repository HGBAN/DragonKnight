package dragonknight.powers;

import static dragonknight.DragonKnightMod.makeID;

import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import dragonknight.DragonKnightMod;

public class BlackDragon extends AbstractPower {
    public static final String POWER_ID = makeID("BlackDragon");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BlackDragon(AbstractCreature owner) {
        // com.megacrit.cardcrawl.powers.NoDrawPower
        // logger.info(owner == null ? "null" : "exist");
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.description = DESCRIPTIONS[0];

        loadRegion("accuracy");

        // ApplyPowerAction
        // addToBot(new Hand);
        // import com.megacrit.cardcrawl.actions.common.DamageAction
    }

    // @Override
    // public void stackPower(int stackAmount) {
    // logger.info("stack");
    // }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        // com.megacrit.cardcrawl.powers.NoDrawPower
    }

    @Override
    public void onInitialApplication() {
        List<AbstractPower> whitePower = AbstractDungeon.player.powers.stream()
                .filter(power -> power.ID.equals(makeID("WhiteDragon")))
                .collect(Collectors.toList());
        if (whitePower.size() > 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            return;
        }

        // addToBot(new RemoveSpecificPowerAction(owner, owner, makeID("WhiteDragon")));
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
                    card.exhaustOnUseOnce = true;
                    card.rawDescription += " NL 消耗 NL 烙印";

                    card.tags.add(DragonKnightMod.Enums.BRAND);
                    card.initializeDescription();
                    card.glowColor = Color.GOLD.cpy();
                }
                // card.beginGlowing();

                // card.render(null);
                this.isDone = true;
            }

        });
    }

    // @Override
    // public void onApplyPower(AbstractPower power, AbstractCreature target,
    // AbstractCreature source) {

    // }

    // @Override
    // public void duringTurn() {
    // logger.info("during turn");
    // }
}
