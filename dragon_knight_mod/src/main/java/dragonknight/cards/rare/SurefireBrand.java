package dragonknight.cards.rare;

import static dragonknight.DragonKnightMod.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;
import dragonknight.powers.SurefirePower;

public class SurefireBrand extends CustomCard {
    public static final String ID = makeID("SurefireBrand");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SurefireBrand() {
        super(ID, NAME, imagePath("cards/skill/SurefireBrand.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(DragonKnightMod.Enums.EXHAUST);
        this.tags.add(DragonKnightMod.Enums.ANTI_BRAND);
        this.initializeDescription();
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
        for (AbstractCard card : p.hand.group) {
            if (!card.hasTag(DragonKnightMod.Enums.BRAND)
                    && !card.hasTag(DragonKnightMod.Enums.BRAND2) && !card.hasTag(DragonKnightMod.Enums.ANTI_BRAND)
                    && canUseCard(card)) {
                card.tags.add(DragonKnightMod.Enums.BRAND);
            }
            if (!card.exhaust && !card.exhaustOnUseOnce && canUseCard(card)) {
                card.exhaust = true;
                card.tags.add(DragonKnightMod.Enums.EXHAUST);
            }
            if (!card.isEthereal) {
                card.isEthereal = true;
                card.tags.add(DragonKnightMod.Enums.ETHEREAL);
            }
            card.initializeDescription();
        }
        if (!p.hasPower(makeID("SurefirePower")))
            addToBot(new ApplyPowerAction(p, p, new SurefirePower(p)));
    }
}
