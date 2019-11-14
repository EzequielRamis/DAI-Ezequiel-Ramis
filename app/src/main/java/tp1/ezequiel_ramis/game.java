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
            "ELECCIONES",
            "NO COMPILA",
            "ANDROID STUDIO",
    };
    CCPoint VEL;
    int indexLastTile = 0;
//    ArrayList<ArrayList<gameLabel>> gridLabels;

    Label player;
    CCPoint touch;
    int repetitionVal = 20000;
    boolean orientation = true;

    ArrayList<Label> entities;
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
            touch = new CCPoint();
            VEL.x = 5;
            entities = new ArrayList<>();
            setPlayer();
            super.schedule("setTiles", 1 / 60);
            super.schedule("moveCamera", 1 / 60);
            setIsTouchEnabled(true);
        }

        void setTiles(float time) {
            if (indexLastTile * 100 <= _size.getHeight()) {
                AddTile addTile = new AddTile((indexLastTile + 3) * 100);
                addTile.run();
                indexLastTile++;
            }
        }

        void moveCamera(float time) {
            for (Label entity:entities) {
                entity.setPosition(entity.getPositionX(), entity.getPositionY()-2);
            }
            down += 2;
        }

        class AddTile extends TimerTask {
            float y;

            public AddTile(float y) {
                this.y = y;
            }

            @Override
            public void run() {
                int indexText = new Random().nextInt(tilesText.length);
                Label label = Label.label(tilesText[indexText], "", 85);
                orientation = orientation? false:true;
                Timer timer = new Timer();
                SetEnemy setEnemy = new SetEnemy(y, tilesText[indexText], orientation);
                timer.schedule(setEnemy, /*new Random().nextInt(5000)*/0, Math.round(label.getWidth() / _size.getWidth() * repetitionVal));
                if (repetitionVal > 10000) repetitionVal-=200;
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

            public SetEnemy(float y, String text, boolean left) {
                this.y = y;
                this.text = text;
                this.left = left;
            }

            @Override
            public void run() {
                setEnemy(y /*- down*/, text, left);
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
                ||  (velocity < 0 && enemy.getPositionX()+enemy.getWidth()/2 < 0)) {
                    removeChild(enemy, true);
                }
            }

            @Override
            public boolean cancel() {
                return true;
            }
        }

        public void setEnemy(float y, String text, boolean left) {
            double velocity;
            float x;
            Label enemy = Label.label(text, "", 85);
            x = left ? -enemy.getWidth() : _size.getWidth() + enemy.getWidth();
            enemy.setColor(new CCColor3B(255, 255, 255));
            enemy.setPosition(x, y - down);
            if (left) {
                velocity = VEL.x * sigmoid(enemy.getWidth());
            } else {
                velocity = -VEL.x * sigmoid(enemy.getWidth());
            }
            //enemiesTile.add(enemy);
            super.addChild(enemy);
            entities.add(enemy);
            Timer timer = new Timer();
            MoveEnemy moveEnemy = new MoveEnemy(enemy, (float) velocity);
            timer.schedule(moveEnemy, 0, 1000 / 60);
        }

        public void setPlayer() {
            player = Label.label("YO", "", 85);
            player.setColor(new CCColor3B(255, 255, 255));
            player.setPosition(_size.getWidth() / 2, 100);
            super.addChild(player);
            entities.add(player);
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
            touch.x = event.getX();
            touch.y = event.getY();
            return true;
        }

        @Override
        public boolean ccTouchesMoved(MotionEvent event) {
            return true;
        }

        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
            float time = 1/6f;
            if (Math.abs(touch.x - event.getX()) < 50 && Math.abs(touch.y - event.getY()) < 50) {
                player.runAction(MoveBy.action(time, 0, 100));
            } else {
                Direction direction = getDirection(touch.x, touch.y, event.getX(), event.getY());
                Log.d("Touch", direction.toString());
                switch (direction) {
                    case up:
                        player.runAction(MoveBy.action(time, 0, 90 + 90*time/10));
                        break;
                    case down:
                        player.runAction(MoveBy.action(time, 0, -90 - 90*time/10));
                        break;
                    case left:
                        player.runAction(MoveBy.action(time, -player.getWidth(), 0));
                        break;
                    case right:
                        player.runAction(MoveBy.action(time, player.getWidth(), 0));
                }
            }
            return true;
        }
    }

    Double sigmoid(float w) {
        return 2/(1 + Math.pow(Math.E, .01*w)) + .5;
    }

    public Direction getDirection(float x1, float y1, float x2, float y2){
        double angle = getAngle(x1, y1, x2, y2);
        return Direction.fromAngle(angle);
    }

    public double getAngle(float x1, float y1, float x2, float y2) {

        double rad = Math.atan2(y1-y2,x2-x1) + Math.PI;
        return (rad*180/Math.PI + 180)%360;
    }

    public enum Direction{
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
    }

}
