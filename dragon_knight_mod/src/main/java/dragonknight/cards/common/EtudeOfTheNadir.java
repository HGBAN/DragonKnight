package dragonknight.cards.common;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;

public class EtudeOfTheNadir extends CustomCard {
    public static final String ID = makeID("EtudeOfTheNadir");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public EtudeOfTheNadir() {
        super(ID, NAME, imagePath("cards/skill/EtudeOfTheNadir.png"), COST, DESCRIPTION, TYPE, DragonPrince.Enums.CARD_COLOR,
                RARITY,
                TARGET);
        this.exhaust = true;
        this.tags.add(DragonKnightMod.Enums.EXHAUST);
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DrawCardAction(1));
        if (this.upgraded)
            this.addToBot(new GainEnergyAction(1));
    }
}
