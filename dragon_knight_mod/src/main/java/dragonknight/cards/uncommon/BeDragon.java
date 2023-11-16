package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.imagePath;
import static dragonknight.DragonKnightMod.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import dragonknight.character.DragonPrince;
import dragonknight.screens.SelectDragonScreen;

public class BeDragon extends CustomCard {
    public static final String ID = makeID("BeDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public BeDragon() {
        super(ID, NAME, imagePath("cards/skill/BeDragon.png"), COST, DESCRIPTION, TYPE, DragonPrince.Enums.CARD_COLOR,
                RARITY,
                TARGET);
        // tags.add(DragonKnightMod.Enums.BE_DRAGON);
        // com.megacrit.cardcrawl.cards.green.PoisonedStab
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                BaseMod.openCustomScreen(SelectDragonScreen.Enum.SELECT_DRAGON_SCREEN, p);
                isDone = true;
            }
        });
    }

}
