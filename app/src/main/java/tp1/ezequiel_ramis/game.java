package tp1.ezequiel_ramis;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.icu.text.AlphabeticIndex;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;

import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Label;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;
import org.cocos2d.types.CCTexParams;
import org.cocos2d.utils.CCFormatter;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class game {
    CCGLSurfaceView _view;
    CCSize _size;
    boolean left = new Random().nextBoolean();
    String[] tilesText = {
            "JAVA",
            "FRIO",
            "CALOR",
            "SUEÑO",
            "ESTRES",
            "PRUEBAS",
            "INFLACIÓN",
            "NO COMPILA",
            "ANSIEDAD",
            "ANDROID STUDIO"
    };
    CCPoint VEL;
    int indexLastTile = 0;

    Label player;
    int repetitionI = 10000;
    int repetitionF = 4000;
    int repetitionTick = 50;

    float velI = 20;
    float velF = 40;
    float velTick = .24f;

    float cameraI = 1;
    float cameraF = 4;
    float cameraTick = .01f;

    boolean orientation = true;

    CCPoint delta;

    float labelHeight = 170*.75f;

    ArrayList<Label> enemies;
    int down = 0;

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
        Scene scene = MainScene();
        Log.d("StartGame", "Director inicia escena");
        Director.sharedDirector().runWithScene(scene);
    }

    private Scene MainScene() {
        Log.d("MainScene", "Comienza");
        Scene scene = Scene.node();
        Log.d("MainScene", "Agregar capa");
        gameLayer layer = new gameLayer();
        scene.addChild(layer);
        Log.d("MainScene", "Devuelvo escena");
        return scene;
    }

    class gameLayer extends Layer {
        public gameLayer() {
            VEL = new CCPoint();
            VEL.x = velI;
            enemies = new ArrayList<>();
            delta = new CCPoint();
            setPlayer();
            super.schedule("setTiles", 1/30);
            super.schedule("moveCamera", 1 / 30);
            super.schedule("startCollisions", 1/30);
            setIsTouchEnabled(true);
        }

        void setTiles(float time) {
            if (indexLastTile * 100 <= _size.getHeight() + down) {
                AddTile addTile = new AddTile((indexLastTile + 3) * labelHeight*1.25f);
                addTile.run();
                indexLastTile++;
            }
        }

        void moveCamera(float time) {
            float vel = cameraI;
            player.setPosition(player.getPositionX(), player.getPositionY()-vel);
            for (Label e:enemies) {
                e.setPosition(e.getPositionX(), e.getPositionY()-vel);
            }
            down += vel;
        }

        void startCollisions(float time) {
            for (Label enemy:enemies) {
                if (isIntersected(player, enemy)) {
                    /*Perder*/
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
                int indexText = new Random().nextInt(tilesText.length);
                Label label = Label.label(tilesText[indexText], "", labelHeight);
                Log.d("Size", label.getHeight()+"");
                //orientation = orientation? false:true;
                orientation = new Random().nextBoolean();
                Timer timer = new Timer();
                SetEnemy setEnemy = new SetEnemy(y, tilesText[indexText], orientation, VEL.x);
                timer.schedule(setEnemy, new Random().nextInt(10000), Math.round(label.getWidth() / _size.getWidth() * repetitionI));
                if (repetitionI > repetitionF) {
                    repetitionI -= repetitionTick;
                }
                if (VEL.x < velF) {
                    VEL.x += velTick;
                }
                if (cameraI < cameraF) {
                    cameraI+= cameraTick;
                }
                Log.d("Avance", repetitionI + " " + VEL.x + " " + cameraI);
            }

            @Override
            public boolean cancel() {
                return true;
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
                setEnemy(y /*- down*/, text, left, velocity);
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
                    super.cancel();
                }
            }

            @Override
            public boolean cancel() {
                removeChild(enemy, true);
                enemies.remove(enemy);
                return true;
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
            //enemiesTile.add(enemy);
            super.addChild(enemy);
            enemies.add(enemy);
            Timer timer = new Timer();
            MoveEnemy moveEnemy = new MoveEnemy(enemy, (float) velocity);
            timer.schedule(moveEnemy, 0, 1000 / 30);
        }

        public void setPlayer() {
            player = Label.label("YO", "", labelHeight*.75f);
            player.setColor(new CCColor3B(255, 255, 255));
            player.setPosition(_size.getWidth() / 2, 100);
            super.addChild(player);
        }

        boolean isIntersected(Label i0, Label i1) {
            return !(left(i0) > right(i1) ||
                    right(i0) < left(i1) ||
                    top(i0) < bot(i1) ||
                    bot(i0) > top(i1));
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

        @Override
        public boolean ccTouchesBegan(MotionEvent event) {
            CCPoint goTo = CCPoint.ccp(event.getX(), _size.getHeight()-event.getY());
            delta.x = player.getPositionX() - goTo.x;
            delta.y = player.getPositionY() - goTo.y;
            return true;
        }

        @Override
        public boolean ccTouchesMoved(MotionEvent event) {
            CCPoint goTo = CCPoint.ccp(event.getX(), _size.getHeight()-event.getY());
            player.setPosition(goTo.x + delta.x, goTo.y + delta.y);
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
        return 2/(1 + Math.pow(Math.E, .01*w)) + .5;
    }

    /*public Direction getDirection(float x1, float y1, float x2, float y2){
        double angle = getAngle(x1, y1, x2, y2);
        return Direction.fromAngle(angle);
    }*/

    /*public double getAngle(float x1, float y1, float x2, float y2) {

        double rad = Math.atan2(y1-y2,x2-x1) + Math.PI;
        return (rad*180/Math.PI + 180)%360;
    }*/

    /*public enum Direction{
        up,
        down,
        left,
        right;
        public static Direction fromAngle(double angle){
            if(inRange(angle, 45, 135)){
                return Direction.up;
            }
            else if(inRange(angle, 0,45) || inRange(angle, 315, 360)){
                return Direction.right;
            }
            else if(inRange(angle, 225, 315)){
                return Direction.down;
            }
            else{
                return Direction.left;
            }
        }
        private static boolean inRange(double angle, float init, float end){
            return (angle >= init) && (angle < end);
        }
    }*/

}
