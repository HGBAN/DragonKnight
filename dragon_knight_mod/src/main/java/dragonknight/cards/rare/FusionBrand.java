package dragonknight.cards.rare;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import dragonknight.DragonKnightMod;
import dragonknight.cards.BrandCopyCard;
import dragonknight.character.DragonPrince;

public class FusionBrand extends BrandCopyCard {
    public static final String ID = makeID("FusionBrand");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public FusionBrand() {
        super(ID, NAME, imagePath("cards/skill/FusionBrand.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY,
                TARGET);

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // this.addToBot(new GainEnergyAction(DragonKnightMod.brandCountLastTurn));
        // DrawCardAction.drawnCards.clear();

        this.addToBot(new DrawCardAction(3, new AbstractGameAction() {

            @Override
            public void update() {
                boolean brandCardExist = false;
                for (AbstractCard c : DrawCardAction.drawnCards) {
                    c.setCostForTurn(c.costForTurn - 1);
                    if (c.hasTag(DragonKnightMod.Enums.BRAND)
                            || c.name.contains(DragonKnightMod.cardNameKeywords.TEXT_DICT.get("Brand"))) {
                        brandCardExist = true;
                    }
                }
                if (brandCardExist) {
                    addToBot(new GainEnergyAction(2));
                }
                isDone = true;
            }

        }));
    }

    @Override
    public void brandExhaust() {
        AbstractCard c = this.makeCopy();
        if (upgraded)
            c.upgrade();
        this.addToBot(new MakeTempCardInDiscardAction(c, 1));
    }
}
