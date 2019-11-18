package tp1.ezequiel_ramis;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.icu.text.AlphabeticIndex;
import android.icu.text.LocaleDisplayNames;
import android.media.MediaPlayer;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;

import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.layers.Layer;
import org.cocos2d.menus.Menu;
import org.cocos2d.menus.MenuItemLabel;
import org.cocos2d.nodes.CocosNode;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Label;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.opengl.Camera;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;
import org.cocos2d.types.CCTexParams;
import org.cocos2d.utils.CCFormatter;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class game {
    CCGLSurfaceView _view;
    CCSize _size;
    int points;
    int maxPoints;
    String[] tilesText = {
            "JAVA",
            "FRÍO",
            "CALOR",
            "SUEÑO",
            "ESTRÉS",
            "PRUEBA",
            "INFLACIÓN",
            "NO COMPILA",
            "ANSIEDAD",
            "ANDROID STUDIO",
            "GUIDO",
            "DEPRE",
            "MIGRAÑA",
            "NULL POINTER",
            "RESFRÍO",
            "RECUPERAR",
            "HUMEDAD",
            "ANUNCIOS",
            "TRÁNSITO",
            "CHIVO",
            "YO",
            "JAVA DE VUELTA",
            "TAREA",
            "TRABAJO"
    };

    float labelHeight = 170*.75f;

    public game(CCGLSurfaceView view) {
        Log.d("Game", "Comienza el constructor de la clase");
        _view= view;
    }

    public void startGame() {
        Log.d("StartGame", "Comienza el juego");
        Director.sharedDirector().attachInView(_view);
        _size = Director.sharedDirector().displaySize();
        Log.d("StartGame", "Width: " + _size.getWidth() + ", Height: " + _size.getHeight());
        Log.d("StartGame", "Declarar e instanciar escena");
        Log.d("StartGame", "Director inicia escena");
        Director.sharedDirector().runWithScene(UI(0));
    }

    private Scene UI(int index) {
        Log.d("UI", "Comienza");
        Scene scene = Scene.node();
        Log.d("UI", "Agregar capa");
        uiLayer uiLayer = new uiLayer(index);
        scene.addChild(uiLayer);
        Log.d("UI", "Devuelvo escena");
        return scene;
    }

    private Scene MainScene() {
        Log.d("MainScene", "Comienza");
        Scene scene = Scene.node();
        Log.d("MainScene", "Agregar capa y sonido");
        gameLayer gamelayer = new gameLayer();
        scene.addChild(gamelayer);
        gamelayer.tenPoints = MediaPlayer.create(Director.sharedDirector().getActivity(), R.raw.ten_points);
        gamelayer.fiftyPoints = MediaPlayer.create(Director.sharedDirector().getActivity(), R.raw.fifty_points);
        gamelayer.slap = MediaPlayer.create(Director.sharedDirector().getActivity(), R.raw.slap);
        gamelayer.bgMusic = MediaPlayer.create(Director.sharedDirector().getActivity(), R.raw.un_problema_menos);
        gamelayer.bgMusic.setLooping(true);
        gamelayer.bgMusic.setVolume(.33f,.33f);
        gamelayer.bgMusic.start();
        Log.d("MainScene", "Devuelvo escena");
        return scene;
    }

    class uiLayer extends Layer {
        int index;
        Menu menu;
        MenuItemLabel menuItemLabel;
        Label bienvenido;
        Label puntaje;
        Label puntajeMax;
        public uiLayer(int index) {
            this.index = index;
            Label button;
            if (index == 0){
                button = Label.label("Jugar", "", labelHeight);
                bienvenido = Label.label("Bienvenido a superar problemas :D", "", labelHeight/2.5f);
                bienvenido.setPosition(_size.getWidth()/2, _size.getHeight()/2);
                bienvenido.setColor(new CCColor3B(255,255,255));
                super.addChild(bienvenido);
            }
            else {
                button = Label.label("Reintentar", "", labelHeight);
                puntaje = Label.label("Superados: " + points, "", labelHeight/2f);
                puntajeMax = Label.label("Superación máxima: " + maxPoints, "", labelHeight/2f);
                puntaje.setColor(new CCColor3B(255,255,255));
                puntajeMax.setColor(new CCColor3B(255,255,255));
                puntaje.setAnchorPoint(0, 0);
                puntajeMax.setAnchorPoint(0, 0);
                puntaje.setPosition(_size.getWidth()/4, _size.getHeight()/1.8f);
                puntajeMax.setPosition(_size.getWidth()/4, _size.getHeight()/2);
                super.addChild(puntaje);
                super.addChild(puntajeMax);
            }
            button.setColor(new CCColor3B(255,200,0));
            menuItemLabel = MenuItemLabel.item(button, this, "play");
            menu = Menu.menu(menuItemLabel);
            menu.setPosition(_size.getWidth()/2, _size.getHeight()/4);
            super.addChild(menu);
        }

        public void play() {
            points = 0;
            Director.sharedDirector().runWithScene(MainScene());
        }
    }

    class gameLayer extends Layer {
        boolean left = new Random().nextBoolean();

        CCPoint VEL;
        int indexLastTile = 0;

        Label player;
        int repetitionI = 25000;
        float velI = 15;
        float tick = 1.0005f;

        float cameraI = 3;

        boolean orientation;

        CCPoint delta;

        ArrayList<Label> enemies;
        int down = 0;

        float deltaPoints;
        float maxYlimit = 5 * labelHeight*1.5f;

        float gapX = 20;
        float gapY = 25;

        MediaPlayer tenPoints;
        MediaPlayer fiftyPoints;
        MediaPlayer slap;
        MediaPlayer bgMusic;
        int lastPoint = 0;
        Label pointsL;
        public gameLayer() {
            VEL = new CCPoint();
            VEL.x = velI;
            VEL.y = repetitionI;
            enemies = new ArrayList<>();
            delta = new CCPoint();
            setPlayer();
            super.schedule("moveCamera", 1 / 30);
            super.schedule("setTiles", 1/30);
            super.schedule("startCollisions", 1/30);
            setIsTouchEnabled(true);
            pointsL = Label.label("0", "", labelHeight*.75f);
            pointsL.setColor(new CCColor3B(255,200,0));
            pointsL.setPosition(_size.getWidth()-125, _size.getHeight()-75);
            super.addChild(pointsL);
            Log.d("Puntos", "Agrego puntaje");
            super.schedule("setPoints", 1/30);
        }

        void setPoints(float time) {
            pointsL.setString(points+"");
            Log.d("Puntos", points+"");
            if (points != 0 && lastPoint < points) {
                if (points % 50 == 0) {
                    fiftyPoints.start();
                    //cameraI*=1.1;
                }
                else if (points % 10 == 0) {
                    tenPoints.start();
                }
                lastPoint = points;
            }
        }

        void setTiles(float time) {
            if (indexLastTile * labelHeight <= _size.getHeight() + down) {
                AddTile addTile = new AddTile((indexLastTile + 5) * labelHeight*1.5f);
                addTile.run();
                indexLastTile++;
            }
        }

        void moveCamera(float time) {
            player.setPosition(player.getPositionX(), player.getPositionY()-cameraI);
            for (Label e:enemies) {
                e.setPosition(e.getPositionX(), e.getPositionY() - cameraI);
            }
            down += cameraI;
            maxYlimit -= cameraI;
            VEL.y /= tick + .00025f;
            VEL.x *= tick;
        }

        void startCollisions(float time) {
            for (Label enemy:enemies) {
                if (isIntersected(player, enemy) ||
                    right(player) < 0 ||
                    left(player) > _size.getWidth() ||
                    top(player) < 0 ||
                    bot(player) > _size.getHeight()) {
                    /*Perder*/
                    slap.start();
                    bgMusic.stop();
                    if (points > maxPoints) maxPoints = points;
                    super.removeAllChildren(true);
                    enemies.clear();
                    Director.sharedDirector().runWithScene(UI(1));
                }
            }
        }

        class AddTile extends TimerTask {
            float y;

            public AddTile(float y) {
                this.y = y;
            }

            @Override
            public void run() {
                if (new Random().nextInt(100) > 20) {
                    int indexText = new Random().nextInt(tilesText.length);
                    Label label = Label.label(tilesText[indexText], "", labelHeight);
                    //orientation = orientation ? false : true;
                    orientation = new Random().nextBoolean();     //Se complica demasiado
                    Timer timer = new Timer();
                    SetEnemy setEnemy = new SetEnemy(y, tilesText[indexText], orientation, VEL.x);
                    timer.schedule(setEnemy, new Random().nextInt(10000), Math.round(label.getWidth() / _size.getWidth() * VEL.y));
                }
                Log.d("Dificultad", "Repeticion: " + VEL.y + " Velocidad: " + String.format("%.1f", VEL.x));
            }
        }

        class SetEnemy extends TimerTask {
            float y;
            String text;
            boolean left;
            float velocity;

            public SetEnemy(float y, String text, boolean left, float velocity) {
                this.y = y;
                this.text = text;
                this.left = left;
                this.velocity = velocity;
            }

            @Override
            public void run() {
                setEnemy(y, text, left, velocity);
            }

            @Override
            public boolean cancel() {
                return true;
            }
        }

        class MoveEnemy extends TimerTask {
            Label enemy;
            float velocity;

            public MoveEnemy(Label enemy, float velocity) {
                this.enemy = enemy;
                this.velocity = velocity;
            }

            @Override
            public void run() {
                enemy.setPosition(enemy.getPositionX() + velocity, enemy.getPositionY());
                if ((velocity > 0 && enemy.getPositionX()-enemy.getWidth()/2 > _size.getWidth())
                ||  (velocity < 0 && enemy.getPositionX()+enemy.getWidth()/2 < 0)
                ||  (enemy.getPositionY() < - enemy.getHeight())) {
                    removeChild(enemy, true);
                    enemies.remove(enemy);
                    super.cancel();
                }
            }
        }

        public void setEnemy(float y, String text, boolean left, float velN) {
            double velocity;
            float x;
            Label enemy = Label.label(text, "", labelHeight);
            x = left ? -enemy.getWidth() : _size.getWidth() + enemy.getWidth();
            enemy.setColor(new CCColor3B(255, 255, 255));
            enemy.setPosition(x, y - down);
            if (left) {
                velocity = velN * sigmoid(enemy.getWidth());
            } else {
                velocity = -velN * sigmoid(enemy.getWidth());
            }
            super.addChild(enemy);
            enemies.add(enemy);
            //Log.d("Enemy", enemy.getWidth()+"");
            Timer timer = new Timer();
            MoveEnemy moveEnemy = new MoveEnemy(enemy, (float) velocity);
            timer.schedule(moveEnemy, 0, 1000 / 30);
        }

        public void setPlayer() {
            player = Label.label("YO", "", labelHeight*.75f);
            player.setColor(new CCColor3B(255, 255, 255));
            player.setPosition(_size.getWidth() / 2, _size.getHeight()/2);
            super.addChild(player);
        }

        boolean isIntersected(Label i0, Label i1) {
            return !(left(i0) + gapX > right(i1) - gapX ||
                    right(i0) - gapX < left(i1) + gapX ||
                    top(i0) - gapY < bot(i1) + gapY ||
                    bot(i0) + gapY > top(i1) - gapY);
        }

        @Override
        public boolean ccTouchesBegan(MotionEvent event) {
            CCPoint goTo = CCPoint.ccp(event.getX(), _size.getHeight()-event.getY());
            delta.x = player.getPositionX() - goTo.x;
            delta.y = player.getPositionY() - goTo.y;
            deltaPoints=player.getPositionY()-maxYlimit;
            return true;
        }

        @Override
        public boolean ccTouchesMoved(MotionEvent event) {
            CCPoint goTo = CCPoint.ccp(event.getX(), _size.getHeight()-event.getY());
            player.setPosition(goTo.x + delta.x, goTo.y + delta.y);
            if (player.getPositionY() - maxYlimit > deltaPoints) {
                deltaPoints=player.getPositionY()-maxYlimit;
                if (deltaPoints/labelHeight > points) points = Math.round(deltaPoints/labelHeight*0.5f);
            }
            return true;
        }

        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
            delta.x = 0;
            delta.y = 0;
            return true;
        }
    }

    Double sigmoid(float w) {
        return 5/(1 + Math.pow(Math.E, .01*w)) + .5;
    }

    float left(Label label) {
        return label.getPositionX() - label.getWidth() / 2;
    }

    float right(Label label) {
        return label.getPositionX() + label.getWidth() / 2;
    }

    float top(Label label) {
        return label.getPositionY() + label.getHeight() / 2;
    }

    float bot(Label label) {
        return label.getPositionY() - label.getHeight() / 2;
    }


}
