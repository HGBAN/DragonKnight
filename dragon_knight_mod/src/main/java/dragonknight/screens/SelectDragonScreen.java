package dragonknight.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;

import basemod.abstracts.CustomScreen;
import dragonknight.powers.BlackDragon;
import dragonknight.powers.WhiteDragon;
import dragonknight.ui.Button1;

public class SelectDragonScreen extends CustomScreen {
    private Button1 blackDragon = new Button1(Settings.WIDTH / 2 - 422, Settings.HEIGHT / 2);
    private Button1 whiteDragon = new Button1(Settings.WIDTH / 2 + 178, Settings.HEIGHT / 2);
    private AbstractPlayer player;

    public SelectDragonScreen() {
        blackDragon.buttonText = "黑龙";
        blackDragon.tipHeader = "黑龙形态";
        blackDragon.tipText = "化身黑龙，获得10点护甲，并随机使一张手牌获得消耗和烙印。";
        whiteDragon.buttonText = "白龙";
        whiteDragon.tipHeader = "白龙形态";
        whiteDragon.tipText = "化身白龙，复制一张手牌并使其获得烙印。";
    }

    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen SELECT_DRAGON_SCREEN;
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        // com.megacrit.cardcrawl.ui.buttons.Button

        return Enum.SELECT_DRAGON_SCREEN;
    }

    @Override
    public void close() {
        AbstractDungeon.overlayMenu.hideBlackScreen();
        AbstractDungeon.isScreenUp = false;
    }

    @SuppressWarnings("unused")
    private void open() {
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;

        this.player = AbstractDungeon.player;
        reopen();
    }

    @Override
    public void openingSettings() {
        AbstractDungeon.previousScreen = curScreen();
    }

    @Override
    public void render(SpriteBatch sb) {
        blackDragon.render(sb);
        whiteDragon.render(sb);
        FontHelper.charDescFont.draw(sb,
                "一回合中进入一种形态后就不能再进入另一种形态。形态会在下一回合开始时结束。已经有烙印的牌不能被复制。如果化龙黑龙失败，则加一费；如果化龙白龙失败，则抽一张牌。",
                Settings.WIDTH / 2 - 300, Settings.HEIGHT / 2 - 200, 600, 0, true);
    }

    @Override
    public void reopen() {
        AbstractDungeon.overlayMenu.showBlackScreen();
        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;
    }

    @Override
    public void update() {
        blackDragon.update();
        if (blackDragon.pressed) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new BlackDragon(player), 0));

            AbstractDungeon.closeCurrentScreen();
            blackDragon.pressed = false;
        }
        whiteDragon.update();
        if (whiteDragon.pressed) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new WhiteDragon(player), 0));

            AbstractDungeon.closeCurrentScreen();
            whiteDragon.pressed = false;
        }
    }
}
