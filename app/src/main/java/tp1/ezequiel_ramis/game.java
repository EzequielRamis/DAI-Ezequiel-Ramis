package tp1.ezequiel_ramis;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.icu.text.AlphabeticIndex;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
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
    ArrayList<tile> tiles = new ArrayList<tile>();
    boolean left = new Random().nextBoolean();
    int indexLastTile = 0;
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
    int VEL = 10;

    ArrayList<ArrayList<gameLabel>> gridLabels;

    //Sprite[] _images = new Sprite[12];

    /*boolean touching = false;
    int imageTouched = -1;
    float dx, dy;
    float[] xTo, yTo;*/

    /*VELOCIDAD*/
    // [2/(1+e^.01x)]+.5


    public game(CCGLSurfaceView view) {
        Log.d("Game", "Comienza el constructor de la clase");
        _view= view;
    }

    public void startGame() {
        /*xTo = new float[2];
        yTo = new float[2];*/
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
            Log.d("GameLayer", "Comienza el constructor");
            Log.d("GameLayer", "Agrego imagenes");
            //super.schedule("setImages", 1f);
            super.schedule("setTiles", 1/60);
            //super.schedule("addLabel", 3f);
            //super.schedule("moveLabels", 1/60);
            setIsTouchEnabled(true);
        }

        void setTiles(float time) {
            if (indexLastTile*100 <= _size.getHeight()) {
                int textIndex = new Random().nextInt(tilesText.length);
                left = left ? false : true;
                double velocity;
                float position = left ? 0 : _size.getWidth();
                gridLabels.get(indexLastTile).add(new gameLabel(tilesText[textIndex], _size, 0));
                if (left) {
                    velocity =  VEL*sigmoid(gridLabels.get(indexLastTile).get(0).getLabel().getWidth());
                } else {
                    velocity =  -VEL*sigmoid(gridLabels.get(indexLastTile).get(0).getLabel().getWidth());
                }

                indexLastTile ++;
            }
        }

        /*void setTiles(float time) {
            if (indexLastTile*100 <= _size.getHeight()) {
                int textIndex = new Random().nextInt(tilesText.length);
                left = left ? false : true;
                double velocity;
                float position = left ? 0 : _size.getWidth();
                tiles.add(new tile(tilesText[textIndex], indexLastTile + 3, _size, super.getParent()));
                if (left) {
                    velocity =  VEL*sigmoid(tiles.get(indexLastTile).getGameLabelAtrr().getLabel().getWidth());
                } else {
                    velocity =  -VEL*sigmoid(tiles.get(indexLastTile).getGameLabelAtrr().getLabel().getWidth());
                }
                tiles.get(indexLastTile).set_velocity((float) velocity);
                tiles.get(indexLastTile).addGameLabels(Math.abs(velocity));
                for (gameLabel label: tiles.get(indexLastTile).getGameLabels()) {
                    super.addChild(label.getLabel());
                }
                //super.addChild(tiles.get(lastTile).getLabel(0));
                //super.schedule("setLabels",  1/60);
                //super.schedule("addLabel", 0.1f);
                indexLastTile ++;
            }
        }*/

        Double sigmoid(float w) {
            return 2/(1 + Math.pow(Math.E, .01*w)) + .5;
        }

        /*void addLabel(float time) {
            gameLabels.add(new gameLabel(
                    "Hello world",
                    true,
                    _size,
                    10
            ));
            gameLabel lastLabel = gameLabels.get(indexLastLabel);
            super.addChild(lastLabel.getLabel());
            Log.d("Position", lastLabel.getLabel().getPositionX() + " " + lastLabel.getLabel().getPositionY());
            indexLastLabel++;
        }

        void moveLabels(float time) {
            for(gameLabel label:gameLabels) {
                label.move(500);
            }
        }*/

        boolean isIntersected(Sprite i0, Sprite i1) {
            return !(left(i0) > right(i1) ||
                     right(i0) < left(i1) ||
                     top(i0) < bot(i1) ||
                     bot(i0) > top(i1));
        }

        boolean isInside(Sprite i, float x, float y) {
            return (x < right(i)) &&
                   (x > left(i)) &&
                   (y < top(i)) &&
                   (y > bot(i));
        }


        void moveImage(Sprite i, float x, float y) {
            i.setPosition(x, y);
        }

        float left(Sprite sprite) {
            return sprite.getPositionX() - sprite.getWidth()/2;
        }

        float right(Sprite sprite) {
            return sprite.getPositionX() + sprite.getWidth()/2;
        }

        float top(Sprite sprite) {
            return sprite.getPositionY() + sprite.getHeight()/2;
        }

        float bot(Sprite sprite) {
            return sprite.getPositionY() - sprite.getHeight()/2;
        }

        float dist(float a, float b) {
            if (a > b) return a - b;
            else return b - a;
        }

        /*@Override
        public boolean ccTouchesBegan(MotionEvent event) {
            float x, y;
            x = event.getX();
            y = _size.getHeight() - event.getY();
            Log.d("Touch", "X: " + x + " Y: " + y);
            for (int i = 0; i < _images.length; i++) {
                if (isInside(_images[i], x, y)) {
                    touching = true;
                    imageTouched = i;
                    dx = _images[i].getPositionX() - x;
                    dy = _images[i].getPositionY() - y;
                    break;
                }
            }
            return true;
        }

        @Override
        public boolean ccTouchesMoved(MotionEvent event) {
            float x, y;
            x = event.getX();
            y = _size.getHeight() - event.getY();
            Log.d("Touch", "X: " + x + " Y: " + y);
            for (int i = 0; i < _images.length; i++) {
                if (touching && i == imageTouched) {
                    xTo[i] = x + dx;
                    yTo[i] = y + dy;
                    moveImage(_images[i],
                            xTo[i],
                            yTo[i]);
                }
            }
            return true;
        }

        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
            touching = false;
            imageTouched = -1;
            dx = dy = 0;
            return true;
        }*/
    }
}
