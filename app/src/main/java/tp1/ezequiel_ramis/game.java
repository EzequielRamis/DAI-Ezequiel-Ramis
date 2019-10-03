package tp1.ezequiel_ramis;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MotionEvent;

import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.RotateTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.actions.interval.ScaleTo;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Animation;
import org.cocos2d.nodes.CocosNode;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.ArrayList;
import java.util.Random;

public class game {
    CCGLSurfaceView _view;
    CCSize _size;
    Sprite[] _images = new Sprite[2];

    boolean touching = false;
    int imageTouched = -1;
    float dx, dy;

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
            Log.d("GameLayer", "Comienza el constructor");
            Log.d("GameLayer", "Agrego imagenes");
            //super.schedule("setImages", 1f);
            setImages();
            setIsTouchEnabled(true);
        }

        void setImages(/*float time*/) {
            _images[0] = Sprite.sprite("manaos.jpg");
            _images[1] = Sprite.sprite("alimento.jpg");
            Log.d("SetImages", "Posiciono imagenes");
            do {
                setRandPosition(_images[0]);
                setRandPosition(_images[1]);
            } while (isIntersected(_images[0], _images[1]));
            Log.d("SetImages", "Los agrego a la capa");
            super.addChild(_images[0]);
            super.addChild(_images[1]);
        }

        public void setRandPosition(Sprite image) {
            image.setPosition(
                    new Random().nextInt((int)(_size.getWidth() - image.getWidth())) + image.getWidth()/2,
                    new Random().nextInt((int)(_size.getHeight() - image.getHeight())) + image.getHeight()/2
            );
        }

        public boolean isIntersected(Sprite i0, Sprite i1) {
            float left0, left1, right0, right1, top0, top1, bot0, bot1;
            left0 = i0.getPositionX() - i0.getWidth()/2;
            right0 = i0.getPositionX() + i0.getWidth()/2;
            top0 = i0.getPositionY() + i0.getHeight()/2;
            bot0 = i0.getPositionY() - i0.getHeight()/2;
            left1 = i1.getPositionX() - i1.getWidth()/2;
            right1 = i1.getPositionX() + i1.getWidth()/2;
            top1 = i1.getPositionY() + i1.getHeight()/2;
            bot1 = i1.getPositionY() - i1.getHeight()/2;
            return !(left0 > right1 || right0 < left1 || top0 < bot1 || bot0 > top1);
        }

        public boolean isInside(Sprite i, float x, float y) {
            return     (x < i.getPositionX() + i.getWidth()/2)
                    && (x > i.getPositionX() - i.getWidth()/2)
                    && (y < i.getPositionY() + i.getHeight()/2)
                    && (y > i.getPositionY() - i.getHeight()/2);
        }


        void moveImage(Sprite i, float x, float y) {
            i.setPosition(x, y);
        }

        @Override
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
            float x, y, xTo, yTo;
            x = event.getX();
            y = _size.getHeight() - event.getY();
            Log.d("Touch", "X: " + x + " Y: " + y);
            if (touching) {
                //if (_images[imageTouched].getPositionX() + _images[imageTouched].getWidth()/2 >= _size.getWidth() ... ) Colision en las paredes
                /*moveImage(_images[imageTouched],
                          x + dx,
                          y + dy); TO DO */
            }
            return true;
        }

        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
            float x, y;
            x = event.getX();
            y = _size.getHeight() - event.getY();
            Log.d("Touch", "X: " + x + " Y: " + y);
            touching = false;
            imageTouched = -1;
            dx = dy = 0;
            return true;
        }
    }
}
