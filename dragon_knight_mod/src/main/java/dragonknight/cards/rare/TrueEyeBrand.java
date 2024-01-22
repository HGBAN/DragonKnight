package dragonknight.cards.rare;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.character.DragonPrince;
import dragonknight.powers.TrueDragon;
import dragonknight.powers.WhiteDragon;

public class TrueEyeBrand extends CustomCard {
    public static final String ID = makeID("TrueEyeBrand");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TrueEyeBrand() {
        super(ID, NAME, imagePath("cards/power/default.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower(makeID("WhiteDragon")) && !p.hasPower(makeID("BlackDragon"))) {
            addToBot(new ApplyPowerAction(p, p, new WhiteDragon(p)));
        } else {
            addToBot(new ApplyPowerAction(p, p, new TrueDragon(p)));
        }
    }
}
