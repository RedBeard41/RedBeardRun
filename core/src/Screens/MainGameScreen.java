package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.redbeard.RedBeardRun;

import java.util.Random;

import Helpers.Figures;
import Helpers.GameInput;

public class MainGameScreen implements Screen {
public static final String TAG = MainGameScreen.class.getSimpleName();

    private RedBeardRun game;
    private SpriteBatch batch;

    //box2d
    private World world;
    private Body body;
    private Body body2;
    private Vector2 gravitationalForces;
    private float random;

    private static final short GROUND = 2;
    private static final short PLAYER = 4;
    private static final short ENEMY = 8;

    //view
    private OrthographicCamera camera;
    private Viewport gameViewport;
    private Box2DDebugRenderer b2dr;

    //Controls
    private GameInput gameInput;





    public MainGameScreen(RedBeardRun game, SpriteBatch batch) {
        Gdx.app.log(TAG, "MainGame Constructor");
        this.game = game;
        this.batch = batch;

        camera = new OrthographicCamera();
        gameViewport = new FitViewport(Figures.VIRTUALWIDTH,Figures.VIRTUALHEIGHT,camera);
        camera.position.set(gameViewport.getWorldWidth()/2,gameViewport.getWorldHeight()/2,0);

        gameInput = new GameInput(gameViewport);


        /*gravitationalForces = new Vector2(0,-9.8f);

        world = new World(gravitationalForces,true);
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,16, 10);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);*/





    }







    @Override
    public void show() {
        Gdx.app.log(TAG, "MainGame Show");



      /*  for(int i = 3; i<6; i++) {
            random = MathUtils.random(1,5);
            body = createBody(new Vector2(i, camera.viewportHeight+10),
                    random, 1, BodyDef.BodyType.DynamicBody, 0, ENEMY, (short)(PLAYER));
        }

        body = createBody(new Vector2(camera.viewportWidth/2, camera.viewportHeight),
                random, 1, BodyDef.BodyType.DynamicBody, 0, PLAYER, (short)(GROUND|ENEMY));

        body2 = createBody(new Vector2(camera.viewportWidth/2,-camera.viewportHeight/2+1),camera.viewportWidth,
                0, BodyDef.BodyType.StaticBody, 1,GROUND, PLAYER);*/
    }

    public void movePlayer(){
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            body.setLinearVelocity(-1f,0f);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            body.setLinearVelocity(1f,0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            body.applyLinearImpulse(0f,5f,body.getPosition().x,body.getPosition().y,true);
            body.setAngularVelocity(0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            body.setLinearVelocity(0f,0f);
        }
    }

    @Override
    public void render(float delta) {
        movePlayer();
        camera.update();

      //  world.step(delta,6,2);


        Gdx.app.log(TAG, "MainGame Render");
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

       // b2dr.render(world,camera.combined);

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "MainGame resize");

        gameViewport.update(width, height);


    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "MainGame pause");
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "MainGame resume");
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "MainGame hide");

    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "MainGame dispose");
        b2dr.dispose();
        world.dispose();


    }
}
