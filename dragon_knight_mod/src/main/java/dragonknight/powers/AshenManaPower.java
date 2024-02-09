package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import dragonknight.DragonKnightMod;
import dragonknight.patch.CardPatch;

public class AshenManaPower extends BasePower {
    public static final String POWER_ID = makeID("AshenManaPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AshenManaPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (!owner.isPlayer)
            return;
        this.addToBot(new AbstractGameAction() {

            @Override
            public void update() {
                AbstractPlayer player = AbstractDungeon.player;
                List<AbstractCard> cards = player.hand.group.stream()
                        .filter(x -> (x.hasTag(DragonKnightMod.Enums.BRAND) || x.hasTag(DragonKnightMod.Enums.BRAND2)
                                || x.hasTag(DragonKnightMod.Enums.TEMP_BRAND)) && !CardPatch.Field.reduceEnergy.get(x))
                        .collect(Collectors.toList());
                if (cards.size() > 0) {
                    AbstractCard card = cards.get(AbstractDungeon.cardRng.random(cards.size() - 1));
                    if (card.cost > 0) {
                        card.cost--;
                        card.setCostForTurn(card.cost);
                    }
                    card.tags.add(DragonKnightMod.Enums.DRAW_CARD);
                    card.initializeDescription();
                    CardPatch.Field.reduceEnergy.set(card, true);
                    flash();
                }

                isDone = true;
            }

        });

    }

}
