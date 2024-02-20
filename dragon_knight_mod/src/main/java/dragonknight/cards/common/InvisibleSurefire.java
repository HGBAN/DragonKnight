package dragonknight.cards.common;

import static dragonknight.DragonKnightMod.*;

import java.util.List;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.actions.DiscardAntiBrandCardAction;
import dragonknight.character.DragonPrince;
import dragonknight.powers.ScorchPower;

public class InvisibleSurefire extends CustomCard {
    public static final String ID = makeID("InvisibleSurefire");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public InvisibleSurefire() {
        super(ID, NAME, imagePath("cards/skill/InvisibleSurefire.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.baseMagicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    // @Override
    // public void applyPowers() {
    // super.applyPowers();
    // this.magicNumber = this.baseMagicNumber * (checkHand() + 1);
    // }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int tmp = this.baseMagicNumber;
        // ArrayList<AbstractCard> cards = new ArrayList<>();
        int count = checkHand();
        if (count >= 2) {
            // for (AbstractCard c : cards) {
            // addToBot(new DiscardSpecificCardAction(c));
            // }
            addToBot(new DiscardAntiBrandCardAction());
        }
        tmp += count * this.baseMagicNumber;
        addToBot(new ApplyPowerAction(m, p, new ScorchPower(m, tmp)));
    }

    @Override
    public void triggerOnGlowCheck() {
        if (checkHand() >= 2)
            this.glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        else
            this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
    }

    private int checkHand(List<AbstractCard> cards) {
        AbstractPlayer p = AbstractDungeon.player;
        int count = 0;
        for (AbstractCard c : p.hand.group) {
            if (c.hasTag(DragonKnightMod.Enums.ANTI_BRAND) && !c.equals(this)) {
                count++;
                if (cards != null)
                    cards.add(c);
            }
        }
        return count;
    }

    private int checkHand() {
        return checkHand(null);
    }
}
