package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.redbeard.RedBeardRun;

public class MainMenuScreen implements Screen {
    private static final String TAG = MainMenuScreen.class.getSimpleName();

    private SpriteBatch batch;
    private RedBeardRun game;
    private Texture img;
    private float timeToWait = 2f;

    public MainMenuScreen(RedBeardRun game, SpriteBatch batch) {
        this.batch = batch;
        this.game = game;
        Gdx.app.log(TAG, "MainMenu Constructor");
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "MainMenu SHOW");
    }

    @Override
    public void render(float delta) {
        Gdx.app.log(TAG, "MainMenu Render");
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timeToWait-=delta;
        if(timeToWait<=0){
            game.setScreen(RedBeardRun.SCREENTYPE.GAME);
            timeToWait = 2f;
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "MainMenu Resize");
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "MainMenu Pause");
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "MainMenu Resume");
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "MainMenu HIDE");
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "MainMenu Dispose");

    }
}
