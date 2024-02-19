package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import dragonknight.DragonKnightMod;
import dragonknight.actions.DrawBrandCardAction;

public class DivineFlameFormPower extends BasePower {
    public static final String POWER_ID = makeID("DivineFlameFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean isUsed = false;

    public DivineFlameFormPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        isUsed = false;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        handle(card);
    }

    public void onDiscard(AbstractCard card) {
        handle(card);
    }

    private void handle(AbstractCard card) {
        if (!isUsed) {
            logger.info("11");
            if (card.hasTag(DragonKnightMod.Enums.ANTI_BRAND)) {
                logger.info(card.name);
                int tmp = 1;
                boolean flag = false;
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (flag)
                        break;
                    if (!m.isDead) {
                        for (AbstractPower p : m.powers) {
                            if (p.type.equals(PowerType.DEBUFF)) {
                                tmp += 2;
                                flag = true;
                                break;
                            }
                        }
                    }
                }
                flash();
                addToBot(new DrawBrandCardAction(tmp));
                isUsed = true;
            }
        }
    }

}
