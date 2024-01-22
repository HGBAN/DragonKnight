package dragonknight.powers;

import static dragonknight.DragonKnightMod.*;

import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import dragonknight.DragonKnightMod;
import dragonknight.cards.IBrandDifferentCard;

public class Brand extends BasePower {
    public static final String POWER_ID = makeID("Brand");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    // public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // public AbstractCard brandCard;
    public static int triggerCount = 1;
    private int currentIndex = 0;

    public Brand(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner, owner, -1);
        priority = 4;
    }

    @Override
    public void updateDescription() {
        // this.description = DESCRIPTIONS[0];
        updateDes();
    }

    private void updateDes() {
        StringBuilder builder = new StringBuilder();

        builder.append(DESCRIPTIONS[0]).append(" NL ");
        if (DragonKnightMod.brandCards.size() > 0) {
            builder.append(DESCRIPTIONS[1]).append(" NL ");

            Iterator<AbstractCard> iterator = DragonKnightMod.brandCards.iterator();

            while (iterator.hasNext()) {
                AbstractCard brandCard = iterator.next();
                builder.append(brandCard.name);
                if (iterator.hasNext())
                    builder.append(" NL ");
            }
            // for (AbstractCard brandCard : DragonKnightMod.brandCards) {
            // builder.append(brandCard.name).append(" NL ");
            // }
        }
        description = builder.toString();
        // this.description = DESCRIPTIONS[0] + " NL 下面的牌将在回合结束时打出： NL " +
        // brandCard.name;
    }

    @Override
    public void onInitialApplication() {
        updateDes();
        // this.description = DESCRIPTIONS[0] + " NL 下面的牌将在回合结束时打出： NL " +
        // brandCard.name;
    }

    @Override
    public void stackPower(int stackAmount) {
        updateDes();
    }

    private void handleNextCard() {
        if (currentIndex >= DragonKnightMod.brandCards.size()) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            return;
        }
        AbstractCard brandCard = DragonKnightMod.brandCards.get(currentIndex++);
        // DragonKnightMod.brandCards.remove(0);
        if (brandCard.hasTag(DragonKnightMod.Enums.NO_BRAND) || brandCard.cost == -2) {

            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    DragonKnightMod.onRemoveBrandCards();
                    handleNextCard();
                    isDone = true;
                }
            });
            return;
        }
        addToBot(new AbstractGameAction() {
            {
                this.duration = 0.7f;
            }

            private boolean fade = false;

            @Override
            public void update() {
                if (this.duration != 0.7f) {
                    brandCard.update();
                    if (this.duration < 0.3f && !fade) {
                        fade = true;
                        brandCard.fadingOut = true;
                    }
                    this.tickDuration();
                    if (this.isDone) {
                        handleNextCard();
                    }
                    return;
                }
                DragonKnightMod.onRemoveBrandCards();
                for (int i = 0; i < triggerCount; i++) {

                    AbstractMonster monster = AbstractDungeon.getMonsters().getRandomMonster(true);

                    brandCard.calculateCardDamage(monster);
                    brandCard.freeToPlayOnce = true;
                    brandCard.use(AbstractDungeon.player, monster);
                    AbstractDungeon.actionManager.addToBottom(new UseCardAction(brandCard, monster) {
                        @Override
                        public void update() {
                            if (this.duration == 0.15F) {
                                Iterator<AbstractPower> var1 = AbstractDungeon.player.powers.iterator();

                                while (var1.hasNext()) {
                                    AbstractPower p = (AbstractPower) var1.next();
                                    if (!brandCard.dontTriggerOnUseCard) {
                                        p.onAfterUseCard(brandCard, this);
                                    }
                                }

                                Iterator<AbstractMonster> var2 = AbstractDungeon.getMonsters().monsters.iterator();

                                while (var2.hasNext()) {
                                    AbstractMonster m = (AbstractMonster) var2.next();
                                    Iterator<AbstractPower> var3 = m.powers.iterator();

                                    while (var3.hasNext()) {
                                        AbstractPower p = (AbstractPower) var3.next();
                                        if (!brandCard.dontTriggerOnUseCard) {
                                            p.onAfterUseCard(brandCard, this);
                                        }
                                    }
                                }

                                brandCard.freeToPlayOnce = false;
                                brandCard.isInAutoplay = false;
                                if (brandCard.purgeOnUse) {
                                    this.addToTop(new ShowCardAndPoofAction(brandCard));
                                    this.isDone = true;
                                    AbstractDungeon.player.cardInUse = null;
                                    return;
                                }

                                if (brandCard.type == CardType.POWER) {
                                    this.addToTop(new ShowCardAction(brandCard));
                                    if (Settings.FAST_MODE) {
                                        this.addToTop(new WaitAction(0.1F));
                                    } else {
                                        this.addToTop(new WaitAction(0.7F));
                                    }

                                    AbstractDungeon.player.hand.empower(brandCard);
                                    this.isDone = true;
                                    AbstractDungeon.player.hand.applyPowers();
                                    AbstractDungeon.player.hand.glowCheck();
                                    AbstractDungeon.player.cardInUse = null;
                                    return;
                                }

                                AbstractDungeon.player.cardInUse = null;

                                brandCard.exhaustOnUseOnce = false;
                                brandCard.dontTriggerOnUseCard = false;

                                this.addToBot(new HandCheckAction());
                            }

                            this.tickDuration();
                        }
                    });
                }
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {

                    @Override
                    public void update() {
                        if (brandCard instanceof IBrandDifferentCard) {
                            ((IBrandDifferentCard) brandCard).setBranded(false);
                        }
                        isDone = true;
                    }

                });
                AbstractDungeon.player.cardInUse = brandCard;
                brandCard.current_x = brandCard.current_y = 0;
                brandCard.target_x = (float) (Settings.WIDTH / 2);
                brandCard.target_y = (float) (Settings.HEIGHT / 2);
                brandCard.targetTransparency = 1;
                brandCard.transparency = 1;
                brandCard.fadingOut = false;

                // this.isDone = true;

                this.tickDuration();
            }

        });
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (!isPlayer)
            return;

        this.flash();
        AbstractPlayer player = AbstractDungeon.player;
        if (player.hasPower(makeID("WhiteRealmPower"))) {
            AbstractPower power = player.getPower(makeID("WhiteRealmPower"));
            power.flash();
            // power.amount--;
            // if (power.amount == 0) {
            // Brand.triggerCount--;
            // addToBot(new RemoveSpecificPowerAction(owner, owner, power));
            // }
        }
        addToBot(new AbstractGameAction() {

            @Override
            public void update() {
                isDone = true;
                handleNextCard();
            }

        });

    }
}
