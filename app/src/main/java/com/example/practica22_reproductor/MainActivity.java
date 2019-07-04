package com.example.practica22_reproductor;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button play_pause, btn_repetir;
    MediaPlayer mp;
    ImageView iv;
    //Lavariable repetir nos ayuda a que pueda cambiar entre repetir y no repetir
    int repetir = 2, posicion = 0;

    MediaPlayer vector_mp[] = new MediaPlayer[3];//ESTE VECTOR GUARDA LAS PISTAS DE AUDIO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play_pause = (Button)findViewById(R.id.btn_play);
        btn_repetir = (Button)findViewById(R.id.btn_repetir);
        iv = (ImageView)findViewById(R.id.imageView);

        //introducir canciones dentro del vector, usando las rutas de las canciones
        vector_mp[0] = MediaPlayer.create(this, R.raw.barrio);
        vector_mp[1] = MediaPlayer.create(this, R.raw.desesperado);
        vector_mp[2] = MediaPlayer.create(this, R.raw.encerrado);
    }

    //Metodo para el boton PlayPause
    public void PlayPause(View view){
        //Verifica si la pista de audio que esta en la posicion tal, se esta reproducioendo
        if(vector_mp[posicion].isPlaying()){
            //Si la pista se esta reproducioendo y pulsan el boton debe detenerse
            vector_mp[posicion].pause();
            //Cambia la apariencia del boton
            play_pause.setBackgroundResource(R.drawable.reproducir);
            Toast.makeText(this, "Pause", Toast.LENGTH_SHORT).show();
        }else{
            vector_mp[posicion].start();
            //Cambia la apariencia del boton
            play_pause.setBackgroundResource(R.drawable.pausa);
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para el boton Stop
    public void Stop(View view){
        if(vector_mp[posicion] != null){//Verifica que exista una cancion reproduciendose
            vector_mp[posicion].stop();//Detiene la cancion que esta en curso

            //El metodo vacia la posicion, no sabra que canciones estaban y en que orden.
            //Entonces volvemos a determinar las canciones y posiciones de estas
            vector_mp[0] = MediaPlayer.create(this, R.raw.barrio);
            vector_mp[1] = MediaPlayer.create(this, R.raw.desesperado);
            vector_mp[2] = MediaPlayer.create(this, R.raw.encerrado);
            //tambien indicamos que nuestro indice(posicion) vuelva al inicio
            posicion = 0;
            //cambiamos la apariencia del boton
            play_pause.setBackgroundResource(R.drawable.reproducir);
            //cambiamos la portada a la de la primera cancion
            iv.setImageResource(R.drawable.portada1);
            Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para Repetir una pista
    public void RepetirPista(View view){
        if(repetir == 1){//
            //cambiamos la aparencia del boton
            btn_repetir.setBackgroundResource(R.drawable.norepetir);
            Toast.makeText(this,"No Repetir", Toast.LENGTH_SHORT).show();
            vector_mp[posicion].setLooping(false);
            repetir = 2;
        } else{
            //cambiamos la aparencia del boton
            btn_repetir.setBackgroundResource(R.drawable.repetir);
            Toast.makeText(this,"Repetir", Toast.LENGTH_SHORT).show();
            vector_mp[posicion].setLooping(false);
            repetir = 1;
        }
    }

    //Metodo para el boton SiguienteCancion
    public void SiguienteCancion(View view){
        //Verificamos si nuestro indice esta en la posicion 0
        if(posicion < vector_mp.length -1){//-1 evita el desbordamiento de memoria
            // Si nuestro indice es menor al ancho de nuestro vector, permite que nuestro indice siga recorriendo el vector
            //Verifica que nuestro vector en tal posicion este reprodcuiendo una cancion
            if(vector_mp[posicion].isPlaying()){
                //Detiene la pista que se esta reproduciendo para poder reproducir la siguiente
                vector_mp[posicion].stop();
                //Ayudamos a nuestro indice a recorrer el vector
                posicion++;
                //La siguiente posicion/cancion debe empezar a reproducirse
                vector_mp[posicion].start();

                //cambio de portada
                if(posicion == 0){
                    iv.setImageResource(R.drawable.portada1);;
                } else if(posicion == 1){
                    iv.setImageResource(R.drawable.portada2);
                } else if(posicion == 2){
                    iv.setImageResource(R.drawable.portada3);
                }

            } else {
                //Solo incrementa la posicion ya que al no estar reproduciendo no tiene caso detener
                posicion++;

                //cambio de portada
                if(posicion == 0){
                    iv.setImageResource(R.drawable.portada1);;
                } else if(posicion == 1){
                    iv.setImageResource(R.drawable.portada2);
                } else if(posicion == 2){
                    iv.setImageResource(R.drawable.portada3);
                }

            }

        } else{
            //Si nuestro inidice posicion ya recorrio el vector indicara que no hay mas canciones y asi evitar un desbordamiento de memoria
            Toast.makeText(this, "No hay mas canciones", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para el boton RegresarCancion
    public void RegresarSancion(View view){
        if(posicion >= 1){//Verificamos que no retroceda mas caciones de las que hay
            if(vector_mp[posicion].isPlaying()){//si la cancion se esta reproduciendo
                vector_mp[posicion].stop();//paramos la cancion

                //colocamos las canciones y posiciones ya que el metodo stop las vacio
                vector_mp[0] = MediaPlayer.create(this, R.raw.barrio);
                vector_mp[1] = MediaPlayer.create(this, R.raw.desesperado);
                vector_mp[2] = MediaPlayer.create(this, R.raw.encerrado);
                posicion--;//Decrementa una posicion para recorrer el vector hacia atras

                //Cambiamos las portadas
                if(posicion == 0){
                    iv.setImageResource(R.drawable.portada1);;
                } else if(posicion == 1){
                    iv.setImageResource(R.drawable.portada2);
                } else if(posicion == 2){
                    iv.setImageResource(R.drawable.portada3);
                }

                vector_mp[posicion].start();//Reproduce la cancion a donde llego
            } else{
                posicion--;//Decrementa para recorrer el vector

               //Cambiasmos las portadas
                if(posicion == 0){
                    iv.setImageResource(R.drawable.portada1);;
                } else if(posicion == 1){
                    iv.setImageResource(R.drawable.portada2);
                } else if(posicion == 2){
                    iv.setImageResource(R.drawable.portada3);
                }
            }

        } else{
            //Si nuestro inidice posicion ya recorrio el vector indicara que no hay mas canciones y asi evitar un desbordamiento de memoria
            Toast.makeText(this, "No hay mas canciones", Toast.LENGTH_SHORT).show();
        }
    }
}
