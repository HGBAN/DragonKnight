package dragonknight.cards.common;

import static dragonknight.DragonKnightMod.*;

import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import basemod.abstracts.CustomCard;
import dragonknight.DragonKnightMod;
import dragonknight.character.DragonPrince;

public class TemporalBrand extends CustomCard {
    public static final String ID = makeID("TemporalBrand");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private int brandNumber = 1;

    public TemporalBrand() {
        super(ID, NAME, imagePath("cards/skill/default.png"), COST, DESCRIPTION, TYPE, DragonPrince.Enums.CARD_COLOR,
                RARITY,
                TARGET);
        this.baseMagicNumber = 3;
        this.tags.add(DragonKnightMod.Enums.BRAND);
        this.rawDescription = String.format(DESCRIPTION, brandNumber);
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
            brandNumber++;
            this.rawDescription = String.format(DESCRIPTION, brandNumber);
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, baseMagicNumber)));
        this.addToBot(new AbstractGameAction() {

            @Override
            public void update() {
                List<AbstractCard> cards = p.hand.group.stream().filter(x -> canBrand(x)).collect(Collectors.toList());
                for (int i = 0; i < brandNumber; i++) {
                    if (cards.size() <= 0)
                        break;
                    AbstractCard card = cards.get(AbstractDungeon.cardRng.random(cards.size() - 1));
                    card.tags.add(DragonKnightMod.Enums.BRAND);
                    card.initializeDescription();
                    cards.remove(card);
                }
                isDone = true;
            }

        });
    }

}
