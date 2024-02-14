package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import dragonknight.DragonKnightMod;

public class IceDevilErosionPower extends BasePower {
    public static final String POWER_ID = makeID("IceDevilErosionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public IceDevilErosionPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new AbstractGameAction() {

            @Override
            public void update() {
                boolean flashed = false;
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c.hasTag(DragonKnightMod.Enums.ANTI_BRAND) && c.type.equals(CardType.ATTACK)) {
                        if (!flashed) {
                            flash();
                            flashed = true;
                        }
                        c.baseDamage += 2;
                    }
                }
                isDone = true;
            }

        });

    }
}
