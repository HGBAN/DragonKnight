package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;
import dragonknight.powers.AshPower;

public class AshenScorch extends CustomCard {
    public static final String ID = makeID("AshenScorch");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public AshenScorch() {
        super(ID, NAME, imagePath("cards/skill/default.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.baseMagicNumber = 1;
        this.tags.add(DragonKnightMod.Enums.ANTI_BRAND);
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, p, new AshPower(m, p, this.baseMagicNumber)));
    }

}
