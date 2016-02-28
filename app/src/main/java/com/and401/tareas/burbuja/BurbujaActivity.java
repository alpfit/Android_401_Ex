package com.and401.tareas.burbuja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

// TODO Revisar este link para ver el comportamiento https://www.youtube.com/watch?v=mEvyTbJO8eA

public class BurbujaActivity extends BurbujaBaseActivity {

    // La vista principal
    private RelativeLayout mFrame;

    // El bitmap para la burbuja
    private Bitmap mBitmap;

    // La dimension de la vista
    private int mDisplayWidth, mDisplayHeight;

    // Administrador del Audio
    private AudioManager mAudioManager;

    // Pool de sonido
    private SoundPool mSoundPool;

    // ID para el sonido de pop de la burbuja
    private int mSoundID;

    // El volumen del audio
    private float mStreamVolume;

    // Detector de gestos tactiles
    private GestureDetector mGestureDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burbuja);

        // TODO asigna a mFrame la instanca del layout con id R.id.frame

        // Cargar el imagen basica de la burbuja, este imagen es transparente
        // y el color se toma del fondo
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b64);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Administrar el sonido de Pop de la burbuja
        // Utiiza el AudioManager.STREAM_MUSIC para el tipo de stream y setear el volumen
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        mStreamVolume = (float) mAudioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC)
                / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        // TODO - en mSoundPool crear un nuevo pool de sonido (SoundPool), que permite hasta 10 streams

        // TODO - establece el SoundPool OnLoadCompletedListener para llama el setupGestureDetector() si el status == 0
        // el soundpool es representado por mSoundPool. el setupGestureDetector determinara que action tactile el usuario ha hecho
        // eg swipe, fling, touch etc...

        // TODO - Cargar en mSoundID el sonido desde res/raw/bubble_pop.wav con una pririoridad 1
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Recuperar el tamaño ancho y alto del frame para conocer el borde de la vista
            mDisplayWidth = mFrame.getWidth();
            mDisplayHeight = mFrame.getHeight();
        }
    }

    // Este methodo va detectar la accion tactile realizada por el usuario
    // ejemplo swipe, touch, fling, zoom. etc..
    private void setupGestureDetector() {
        mGestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    // Si un gesto de tipo fling fue iniciado sobre un BurbujaView entonces debemos cambiar la velocidad
                    // del BurbujaView

                    @Override
                    public boolean onFling(MotionEvent event1, MotionEvent event2,
                                           float velocityX, float velocityY) {

                        // Implementa solamante lo que falta del onFling.
                        // TODO define una variable mViewCount y recupera la cantidad de burbujas que existe en el frame
                        // se puede recuperar las Vistas en mFrame utilizando el methodo getChildCount() del frame
                        int mViewCount = mFrame.getChildCount();
                        // TODO define una BurbujaView en mbView

                        View mView;
                        for (int i= 0; i< mViewCount; i++) {
                            // TODO recuperar la vista en mView se puede utilizar getChildAt del frame para recuperar la vista
                            if (mView instanceof BurbujaView ) {
                                mbView = (BurbujaView) mView;
                                // TODO determina si la posicion donde el usuario ha tocado la pantalla intersecta con la burbuja
                                // utiliza el metodo intersects de la vista y el X y Y del event1
                                if () {
                                    mbView.deflect(velocityX, velocityY);
                                    break;
                                }
                            }
                        }
                        return true;
                    }

                    // Si el usuario ha hecho un Tap sobre una burbuja, este evento deberia reventar la burbuja
                    // caso contrario, se creara un nuevo burbuja en la ubicacion donde el usuario ha hecho el Tap
                    // sobre el mFrame.

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent event) {
                        boolean vPopped = false;
                        BurbujaView mbView;
                        View mView;
                        // TODO - Complete onSingleTapConfirmed actions. utilizando una logica similar que ya se implemento en onFling
                        for (int i = 0; i< vChildCount; i++){
                            // TODO determina si la vista es un BurbujaView
                            if (){
                                mbView = (BurbujaView)mView;
                                // TODO determina si la posicion donde el usuario ha tocado la pantalla intersecta con la burbuja
                                // utiliza el metodo intersects de la vista y el X y Y del event
                                if (mbView.intersects(event.getX(), event.getY())) {
                                    vPopped = true;
                                    mbView.stop(vPopped);
                                    mFrame.removeView(mbView);
                                }
                            }
                        }
                        if (!vPopped) {
                            // TODO - Crear una nueva instance del BurbujaView en mbView, despues agregue la vista a mFrame y
                            // finalmente inicie la animacion de la vista llamando a su metodo start()
                        }
                        return true;
                    }
                });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO - Complete el codigo para delegar el action touch al gestureDetector
        return mGestureDetector.....;

    }

    @Override
    protected void onPause() {
        // TODO - Descarga y libera todos los recursos del SoundPool
        mSoundPool...;   // para este methodo hay que pasar el mSoundID
        mSoundPool...;
        super.onPause();
    }

    // BurbujaView es una clase interna para crear y desplegar una Burbuja.
    // Esta clase maneja ciertos actions com animation, dibujo, y reventar una burbuja.
    // Para cada instancia de esta clase una nueva BurbujaView es creada sobre la pantalla

    public class BurbujaView extends View {

        private static final int BITMAP_SIZE = 64;
        private static final int REFRESH_RATE = 40;
        private final Paint mPainter = new Paint();
        private ScheduledFuture<?> mMoverFuture;
        private int mScaledBitmapWidth;
        private Bitmap mScaledBitmap;

        // Ubicacion, velocidad y la direccion de la Burbuja
        private float mXPos, mYPos, mDx, mDy, mRadius, mRadiusSquared;
        private long mRotate, mDRotate;

        BurbujaView(Context context, float x, float y) {
            super(context);

            // Crear un nuevo numero aleatorio para generar un
            // tamaño, rotation, velocidad y direccion de la burbuja
            Random r = new Random();

            // Crear el bitmap para la Burbuja para esta instancia del BurbujaView
            // este metodo tambien establece el valor de mScaledBitmapWidth
            createScaledBitmap(r);

            // Cacluilar el rayo del Bitmap
            mRadius = mScaledBitmapWidth / 2;
            mRadiusSquared = mRadius * mRadius;

            // Adjustar la posicion de desplazamiento (delta) en la burbuja al centro entre el rayo y el dedo del usuario
            mXPos = x - mRadius;
            mYPos = y - mRadius;

            // Establece la velocidad y direccion inicial de la the Burbuja
            setSpeedAndDirection(r);

            // Establece la rotation inicial de la burbuja
            setRotation(r);

            // suavizar el borde del bitmap
            mPainter.setAntiAlias(true);

        }

        private void setRotation(Random r) {

            if (tipoVelocidad == ALEATORIO) {
                // TODO - establecer en mDRotate la rotation en el rango [1..3] se puede utilizar la formula
                // valor minimo del rango + r.nextInt(valor maximo del rango - valor minimo del rango + 1)
            } else {
                mDRotate = 0;
            }
        }

        private void setSpeedAndDirection(Random r) {

            // Para probar
            switch (tipoVelocidad) {
                case UNICA: {
                    mDx = 20;
                    mDy = 20;
                    break;
                }
                case ESTATICO: {
                    // Velocidad 0
                    mDx = 0;
                    mDy = 0;
                    break;
                }
                default:
                    // TODO - Establecer en mDx y mDy los valores aleatorios de la direccion del movimiento
                    // Limita el movimiento en X y Y entre [-3..3] pixels por movimiento.
                    // valor minimo del rango + r.nextInt(valor maximo del rango - valor minimo del rango + 1)
            }
        }

        // Crear una copia del Bitmap original escalando su tamaño (mas grande o mas pequeño)
        private void createScaledBitmap(Random r) {

            if (tipoVelocidad != ALEATORIO) {
                mScaledBitmapWidth = BITMAP_SIZE * 3; // valor por defecto a 3 veces el tamaño original del imagen

            } else {
                //TODO - establezca el tamaño del scaled bitmap mScaledBitmapWidth en el rango de in range [1..3] * BITMAP_SIZE
            }

            // TODO - crear en mScaledBitmap el scaled bitmap filtrado utilizando el tamaño previamente definido
            // utiliza Bitmap.createScaledBitmap(mBitmap, mScaledBitmapWidth, mScaledBitmapWidth,....
        }

        // Empieza a mover la Burbuja y actualiza el frame
        private void start() {

            // Crear un WorkerThread
            ScheduledExecutorService executor = Executors
                    .newScheduledThreadPool(1);

            // Ejecutar y run() en el Worker Thread cada REFRESH_RATE
            // milliseconds

            mMoverFuture = executor.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {

                    // TODO - implementa la logica para mover las burbujas.
                    // Cada vez que este metodo se ejecuta la Burbuja debera mover un paso.
                    // Si la Burbuja sale de la pantalla,
                    // debera parar el Worker Thread de la Burbuja.
                    // caso contrario, solicita que la Burbuja se redibuja.
                    // para verificar si la burbuja sige visible en pantalla utiliza moveWhileOnScreen
                    // tambiem utiliza postInvalidate() para refrescar la pantalla
                }
            }, 0, REFRESH_RATE, TimeUnit.MILLISECONDS);
        }

        // Devuelve true is la burbuja se intersecta con la psoicion (x,y)
        private synchronized boolean intersects(float x, float y) {
            boolean intersect = false;
            // TODO - determina la condicion para verificar si la posicion del dedo esta dentro de la burbuja
            //  utiliza estos propiedades o variables mXPos, mScaledBitmapWidth, mYPos,  mScaledBitmapWidth
            if () {
                intersect = true;
            }
            return intersect;
        }

        // Cancelar el movimiento de la burbuja y removerla dekl mFrame
        // Toca el sonido del Popsi la burbuja fue destruida

        private void stop(final boolean wasPopped) {

            if (null != mMoverFuture && !mMoverFuture.isDone()) {
                mMoverFuture.cancel(true);
            }

            // Realizado sobre el hilo de UI
            mFrame.post(new Runnable() {
                @Override
                public void run() {

                    // TODO - Remover la Burbuja actual del from mFrame

                    // TODO - Si la burbuja fue destruida
                    // toca el sonido, utiliza estos valores Volumen izquierda y Derecha = 0.5f
                    // priorida = 0, loop = 0, tasa =1.0f
                    if (wasPopped) {
                    }
                }
            });
        }

        // Cambia la velocidad y direccion de la Burbuja
        private synchronized void deflect(float velocityX, float velocityY) {

            //TODO - Establece mDx y mDy a la nueva velocidad dividido por REFRESH_RATE
        }

        // Dibuja la Burbuja en su mnueva ubicacion
        @Override
        protected synchronized void onDraw(Canvas canvas) {

            // TODO - grabar el canvas

            // TODO - incrementar la rotacion del imagen original mRotate por mDRotate
            mRotate += mDRotate;

            // TODO Rotar el canvas por la rotacion actual mRotate
            // Hint - Rotar alrededor del centro de la iy no su posicion utiliza mXPos + mScaledBitmapWidth/2, mYPos + mScaledBitmapWidth/2

            // TODO - dibujar el bitmap escalada sobre el canvas en su nueva ubicacion mXPos, mYPos y mPainter

            // TODO - restorar el the canvas, para que solamante la burbuja se mantienen en su posicion rotada

        }

        // Devuelve True si la Burbuja esta todavia visible en la pantalla despues de moverlo
        private synchronized boolean moveWhileOnScreen() {

            // TODO - Mueve la Burbuja. Solamente es necesario incrementa su posicion mXPos mYPos por su valor delta
            // mDx y mDy respectivamente
            return !isOutOfView();
        }

        // Devuelve True is la burbuja esta fuera de la pantalla despues de moverla
        private boolean isOutOfView() {
            boolean offScreen = false;
            if (mXPos > mDisplayWidth || mXPos + mScaledBitmapWidth <= 0 ) {
                offScreen = true;
            }
            else if (mYPos > mDisplayHeight || mYPos + mScaledBitmapWidth <= 0 ) {
                offScreen = true;
            }
            return offScreen;
        }
    }
}

