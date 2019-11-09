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
    ArrayList<ArrayList<gameLabel>> gridLabels;

    Label player;
    CCPoint touch;


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
            setPlayer();
            //super.schedule("setLabel", VEL.x);
            setIsTouchEnabled(true);
        }

        public void setPlayer(){
            player = Label.label("YO", "", 85);
            player.setColor(new CCColor3B(255,255,255));
            player.setPosition(_size.getWidth()/2, 100);
            super.addChild(player);
        }

        /*public void setLabel(float time) {
            gameLabel label = new gameLabel(tilesText[0], _size, 0);
            if (left) {
                label.set_velocity(VEL.x*sigmoid(label.getLabel().getWidth()));
                label.moveToLeft();
            }
            else {
                label.set_velocity(-VEL.x*sigmoid(label.getLabel().getWidth()));
                label.moveToRight();
            }
            label.moveToY(500f);
            super.addChild(label.getLabel());
            moveLabel moveLabel = new moveLabel(label, label.velocity);
            Log.d("Velocity", label.velocity+"");
            new Timer().schedule(moveLabel, 0, 1000/60);
        }

        class moveLabel extends TimerTask {
            double velocity;
            gameLabel label;

            public moveLabel(gameLabel label, double velocity) {
                this.label = label;
                this.velocity = velocity;
            }

            @Override
            public void run(){
                label.moveBy((float)velocity, 0);
                Log.d("Label", label.point.x  + " " + label.point.y);
            }
        }*/

        float left(Label label) {
            return label.getPositionX() - label.getWidth()/2;
        }

        float right(Label label) {
            return label.getPositionX() + label.getWidth()/2;
        }

        float top(Label label) {
            return label.getPositionY() + label.getHeight()/2;
        }

        float bot(Label label) {
            return label.getPositionY() - label.getHeight()/2;
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
            float time = 0.1f;
            if (Math.abs(touch.x - event.getX()) < 50 && Math.abs(touch.y - event.getY()) < 50){
                player.runAction(MoveBy.action(time, 0, 100));
            }
            else {
                Direction direction = getDirection(touch.x, touch.y, event.getX(), event.getY());
                Log.d("Touch", direction.toString());
                switch (direction) {
                    case up:
                        player.runAction(MoveBy.action(time, 0, 100));
                        break;
                    case down:
                        player.runAction(MoveBy.action(time, 0, -100));
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
