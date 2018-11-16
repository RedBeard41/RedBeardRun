package Screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

import Components.BodyComponent;
import Helpers.Figures;
import Helpers.GameInput;
import Helpers.LevelCollisionGenerator;
import Managers.CollisionManager;
import Managers.EntityManager;
import Systems.PhysicsDebugSystem;
import Systems.PhysicsSystem;
import Systems.PlayerControlSystem;

public class MainGameScreen implements Screen {
public static final String TAG = MainGameScreen.class.getSimpleName();

    private RedBeardRun game;
    private SpriteBatch batch;

    //box2d
    private World world;
    private Body body;
    private Body body2;
    private CollisionManager collisionManager;
    
    private float random;



    //view
    private OrthographicCamera camera;
    private Viewport gameViewport;


    //Controls
    private GameInput gameInput;

    //Ashley
    private PooledEngine engine;
    private PhysicsSystem physicsSystem;
    private PhysicsDebugSystem physicsDebugSystem;
    private PlayerControlSystem playerControlSystem;

    //Entity Manager
    private EntityManager entityManager;
    private Entity player;

    //level generator
    private LevelCollisionGenerator levelCollisionGenerator;
    private Entity ground;
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap map;

    //temp variables for optimization
    private Vector2 tempPosition;
    private Vector2 tempDimensions;





    public MainGameScreen(RedBeardRun game, SpriteBatch batch) {
        Gdx.app.log(TAG, "MainGame Constructor");
        this.game = game;
        this.batch = batch;

        tempDimensions = new Vector2(Vector2.Zero);
        tempPosition = new Vector2(Vector2.Zero);

        camera = new OrthographicCamera();
        gameViewport = new FitViewport(Figures.VIRTUALWIDTH,Figures.VIRTUALHEIGHT,camera);
        camera.position.set(gameViewport.getWorldWidth()/2,gameViewport.getWorldHeight()/2,0);

        gameInput = new GameInput(gameViewport);

        engine = new PooledEngine(100, 500, 300,
                1000);
        world = new World(Figures.GRAVITATIONAL_FORCES,true);
        collisionManager = new CollisionManager();
        world.setContactListener(collisionManager);


        initAshleySystems();
        entityManager = new EntityManager(game, world, this.batch, engine);
        levelCollisionGenerator = new LevelCollisionGenerator(world, engine);

        //todo need to change how map is loaded when implementing asset management
        map = new TmxMapLoader().load("TestMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, this.batch);

        levelCollisionGenerator.createCollisionLevel(map);

        /*gravitationalForces = new Vector2(0,-9.8f);


        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,16, 10);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);*/





    }

    public void initAshleySystems(){
        physicsSystem = new PhysicsSystem(world);
        physicsDebugSystem = new PhysicsDebugSystem(world, camera);
        playerControlSystem = new PlayerControlSystem(gameInput);

        engine.addSystem(physicsSystem);
        engine.addSystem(physicsDebugSystem);
        engine.addSystem(playerControlSystem);






    }







    @Override
    public void show() {
        Gdx.app.log(TAG, "MainGame Show");
        Gdx.input.setInputProcessor(gameInput);

        player = entityManager.spawnEntity("Player",8,5);

        //temp test of level generation
        tempPosition.x = 0;
        tempPosition.y = 1;
        tempDimensions.x = gameViewport.getWorldWidth();
        tempDimensions.y = 1;





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



    @Override
    public void render(float delta) {
        //camera.update();




        Gdx.app.log(TAG, "MainGame Render");
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();
        engine.update(delta);



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
        world.dispose();


    }
}
