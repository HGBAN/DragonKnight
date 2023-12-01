package dragonknight.cards.uncommon;

import static dragonknight.DragonKnightMod.imagePath;
import static dragonknight.DragonKnightMod.makeID;

import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;

public class SwordBrand extends CustomCard {
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
        this.baseMagicNumber = 1;
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
        addToBot(new AbstractGameAction() {

            @Override
            public void update() {
                List<AbstractCard> cards = p.hand.group.stream()
                        .filter(card -> !card.hasTag(DragonKnightMod.Enums.BRAND)
                                && !card.hasTag(DragonKnightMod.Enums.BRAND2))
                        .collect(Collectors.toList());

                for (int i = 0; i < SwordBrand.this.baseMagicNumber; i++) {
                    if (cards.size() <= 0)
                        break;
                    int index = AbstractDungeon.cardRng.random(cards.size());
                    AbstractCard randomCard = cards.get(index);
                    cards.remove(index);

                    randomCard.tags.add(DragonKnightMod.Enums.BRAND);
                    DragonKnightMod.tempBrandCards.add(randomCard);
                    randomCard.rawDescription += " NL dragonknight:"
                            + DragonKnightMod.keywords.get("TempBrand").PROPER_NAME;
                    randomCard.initializeDescription();
                }

                isDone = true;
            }
        });

    }

}
