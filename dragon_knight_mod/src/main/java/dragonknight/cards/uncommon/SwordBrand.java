package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.*;

import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import dragonknight.DragonKnightMod;
import dragonknight.cards.BrandCopyCard;
import dragonknight.character.DragonPrince;

public class SwordBrand extends BrandCopyCard {
    public static final String ID = makeID("SwordBrand");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SwordBrand() {
        super(ID, NAME, imagePath("cards/skill/SwordBrand.png"), COST, DESCRIPTION, TYPE,
                DragonPrince.Enums.CARD_COLOR,
                RARITY, TARGET);
        this.tags.add(DragonKnightMod.Enums.BRAND);
        this.initializeDescription();
        this.baseMagicNumber = 1;
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
        addToBot(new AbstractGameAction() {

            @Override
            public void update() {
                List<AbstractCard> cards = p.hand.group.stream()
                        .filter(card -> !card.hasTag(DragonKnightMod.Enums.BRAND)
                                && !card.hasTag(DragonKnightMod.Enums.BRAND2)
                                && !card.hasTag(DragonKnightMod.Enums.ANTI_BRAND) && canUseCard(card)
                                && card.type.equals(CardType.SKILL))
                        .collect(Collectors.toList());
                // cards.remove(SwordBrand.this);

                for (AbstractCard c:cards) {
                    // if (cards.size() <= 0)
                    //     break;
                    // int index = AbstractDungeon.cardRng.random(cards.size() - 1);
                    // AbstractCard randomCard = cards.get(index);
                    // cards.remove(index);

                    c.tags.add(DragonKnightMod.Enums.BRAND);
                    c.tags.add(DragonKnightMod.Enums.TEMP_BRAND);
                    DragonKnightMod.tempBrandCards.add(c);
                    // randomCard.rawDescription += " NL dragonknight:"
                    // + DragonKnightMod.keywords.get("TempBrand").PROPER_NAME;
                    c.initializeDescription();
                }

                isDone = true;
            }
        });

    }

    @Override
    public void brandExhaust() {
        AbstractCard newCard = this.makeCopy();
        if (this.upgraded)
            newCard.upgrade();
        addToBot(new MakeTempCardInDiscardAction(newCard, 1));
    }
}
