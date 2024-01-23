package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.actions.HandToDrawPileAction;
import dragonknight.cards.common.EtudeOfTheNadir;
import dragonknight.character.DragonPrince;

public class HeavenlyEtudeOfTheNadir extends CustomCard {
    public static final String ID = makeID("HeavenlyEtudeOfTheNadir");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public HeavenlyEtudeOfTheNadir() {
        super(ID, NAME, imagePath("cards/skill/HeavenlyEtudeOfTheNadir.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.cardsToPreview = new EtudeOfTheNadir();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int handSize = p.hand.size();
        this.addToBot(new HandToDrawPileAction());
        this.addToBot(new MakeTempCardInHandAction(new EtudeOfTheNadir(), handSize));

    }

}
