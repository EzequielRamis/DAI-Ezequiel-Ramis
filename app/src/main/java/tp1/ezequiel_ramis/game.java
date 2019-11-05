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

public class game {
    CCGLSurfaceView _view;
    CCSize _size;
    ArrayList<tile> tiles;
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
            setTiles();
            super.schedule("moveTiles",  1/60);
            setIsTouchEnabled(true);
        }

        void setTiles() {
            /*for (int i = 0; i < 12; i++) {
                _images[i] = Sprite.sprite("PNG/SPRITE_" + i + ".png");
                super.addChild(_images[i]);
            }*/
            /*Log.d("SetImages", "Posiciono imagenes");
            do {
                setRandPosition(_images[0], 0);
                setRandPosition(_images[1], 1);
            } while (isIntersected(_images[0], _images[1]));*/
            tiles = new ArrayList<tile>();
            for(int i = 0; i < 10; i++) {
                tiles.add(new tile("CALOR", CCPoint.ccp(500, i), true, 1.0));
                super.addChild(tiles.get(i).getLabel(0));
            }

            //super.addChild(tile);
        }

        void moveTiles(float time) {
            for(tile tile:tiles) {
                tile.moveX();
            }
        }

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
