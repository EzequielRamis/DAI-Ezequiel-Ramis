package tp1.ezequiel_ramis;

import android.support.design.widget.Snackbar;
import android.util.Log;

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
import org.cocos2d.types.CCSize;

import java.util.Random;

public class game {
    CCGLSurfaceView _view;
    CCSize _size;
    Sprite _image;
    int imageFrame = 0;

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
            Log.d("GameLayer", "Agrego imagen");
            super.schedule("setImage", 3f);
        }

        void setImage(float time) {
            Log.d("SetImage", "Creo animacion y la agrego al sprite");
            Animation animation = new Animation("run", 1f);
            for (int i = 0; i <= 20; i++) {
                animation.addFrame("image/frame_" + i + ".jpg");
            }
            _image = Sprite.sprite("image.gif");
            _image.addAnimation(animation);
            super.schedule("updateImage", .06f);
            float posX = new Random().nextInt((int)(_size.getWidth() - _image.getWidth())) + _image.getWidth()/2;
            float posY = new Random().nextInt((int)(_size.getHeight() - _image.getHeight())) + _image.getHeight()/2;
            Log.d("SetImage", "Posiciono imagen");
            _image.setPosition(posX, posY);
            _image.runAction(MoveTo.action(3f, getNearestX(posX), getNearestY(posY)));
            Log.d("SetImage", "Lo agrego a la capa");
            super.addChild(_image);
        }

        public void updateImage(float time) {
            _image.setDisplayFrame("run", imageFrame);
            if (imageFrame == 20) imageFrame = -1;
            imageFrame++;
        }

        public float getNearestX(float posX) {
            if (_size.getWidth() - posX > _size.getWidth()/2)
                return _size.getWidth();
            return 0;
        }

        public float getNearestY(float posY) {
            if (_size.getHeight() - posY > _size.getHeight()/2)
                return _size.getHeight();
            return 0;
        }
    }
}
