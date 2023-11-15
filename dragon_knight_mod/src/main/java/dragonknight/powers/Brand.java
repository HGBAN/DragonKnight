package dragonknight.powers;

import static dragonknight.DragonKnightMod.makeID;

import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import dragonknight.DragonKnightMod;

public class Brand extends AbstractPower {
    public static final String POWER_ID = makeID("Brand");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    // public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // public AbstractCard brandCard;
    public static int triggerCount = 1;

    public Brand(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        description = "";
        // brandCard = card;
        priority = 4;

        loadRegion("poison");
    }

    private void updateDes() {
        StringBuilder builder = new StringBuilder();

        builder.append(DESCRIPTIONS[0]).append(" NL ");
        builder.append(DESCRIPTIONS[1]).append(" NL ");

        Iterator<AbstractCard> iterator = DragonKnightMod.brandCards.iterator();

        while (iterator.hasNext()) {
            AbstractCard brandCard = iterator.next();
            builder.append(brandCard.name);
            if(iterator.hasNext())
                builder.append(" NL ");
        }
        // for (AbstractCard brandCard : DragonKnightMod.brandCards) {
        // builder.append(brandCard.name).append(" NL ");
        // }
        description = builder.toString();
        // this.description = DESCRIPTIONS[0] + " NL 下面的牌将在回合结束时打出： NL " +
        // brandCard.name;
    }

    @Override
    public void onInitialApplication() {
        updateDes();
        // this.description = DESCRIPTIONS[0] + " NL 下面的牌将在回合结束时打出： NL " +
        // brandCard.name;
    }

    @Override
    public void stackPower(int stackAmount) {
        updateDes();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer)
            return;

        for (AbstractCard brandCard : DragonKnightMod.brandCards) {
            for (int i = 0; i < triggerCount; i++) {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        brandCard.use(AbstractDungeon.player,
                                AbstractDungeon.getMonsters().getRandomMonster());

                        this.isDone = true;
                    }

                });
            }
        }
        // if (brandCard.target.equals(CardTarget.SELF)) {
        // AbstractDungeon.actionManager
        // .addToBottom(new UseCardAction(brandCard, AbstractDungeon.player));
        // } else
        // AbstractDungeon.actionManager
        // .addToBottom(new UseCardAction(brandCard,
        // AbstractDungeon.getMonsters().getRandomMonster()));
        // com.megacrit.cardcrawl.actions.utility.ExhaustToHandAction()

        // addToBot(new UseCardAction(brandCard,));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

}
