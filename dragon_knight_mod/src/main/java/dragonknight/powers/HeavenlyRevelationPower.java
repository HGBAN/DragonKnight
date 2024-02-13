package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import dragonknight.DragonKnightMod;
import dragonknight.actions.DrawBrandCardAction;

public class HeavenlyRevelationPower extends BasePower {
    public static final String POWER_ID = makeID("HeavenlyRevelationPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int useCount = 0;

    public HeavenlyRevelationPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, true, owner, owner, 1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurn() {
        useCount = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!owner.isPlayer)
            return;
        useCount++;
        AbstractPlayer player = AbstractDungeon.player;
        if ((card.hasTag(DragonKnightMod.Enums.BRAND) || card.hasTag(DragonKnightMod.Enums.BRAND2)
                || card.hasTag(DragonKnightMod.Enums.TEMP_BRAND)) && useCount == 2) {
            this.flash();
            addToBot(new DrawBrandCardAction(amount,
                    c -> c.name.contains(DragonKnightMod.cardNameKeywords.TEXT_DICT.get("Brand"))));
            // addToBot(new DiscardPileToDrawPileAction(amount));
            List<AbstractCard> attackCards = player.exhaustPile.group.stream()
                    .filter(x -> x.type.equals(CardType.ATTACK)).collect(Collectors.toList());

            for (int i = 0; i < amount; i++) {
                if (attackCards.size() <= 0) {
                    break;
                }
                AbstractCard c = attackCards.remove(AbstractDungeon.cardRng.random(attackCards.size()));
                addToBot(new MakeTempCardInDrawPileAction(c, 1, true, true));
            }

        }
    }

}
