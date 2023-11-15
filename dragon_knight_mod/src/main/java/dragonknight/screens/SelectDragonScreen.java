package dragonknight.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

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
        whiteDragon.buttonText = "白龙";
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
    private void open(AbstractPlayer player) {
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;

        this.player = player;
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
