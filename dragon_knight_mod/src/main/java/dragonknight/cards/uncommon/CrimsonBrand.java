package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import dragonknight.actions.DrawBrandCardAction;
import dragonknight.cards.BrandCopyCard;
import dragonknight.character.DragonPrince;

public class CrimsonBrand extends BrandCopyCard {
    public static final String ID = makeID("CrimsonBrand");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public CrimsonBrand() {
        super(ID, NAME, imagePath("cards/skill/CrimsonBrand.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY,
                TARGET);
        this.baseMagicNumber = 2;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DrawBrandCardAction(1));
        if (p.exhaustPile.group.size() < 10) {
            this.addToBot(new DrawCardAction(this.baseMagicNumber));
            if (p.exhaustPile.group.size() < 5) {
                this.addToBot(new GainEnergyAction(this.baseMagicNumber));
            }
        }
    }

    @Override
    public void brandExhaust() {
        AbstractCard newCard = this.makeCopy();
        if (this.upgraded)
            newCard.upgrade();
        this.addToBot(new MakeTempCardInDiscardAction(newCard, 1));
    }
}
