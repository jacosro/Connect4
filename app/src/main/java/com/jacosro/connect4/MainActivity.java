package com.jacosro.connect4;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private boolean game = true,              //determina si ha acabado el juego
					j1 = true;            	  //dice si es el turno del j1 o del j2
	protected int[] id =
		{
			R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7,
			R.id.b8, R.id.b9 ,R.id.b10,R.id.b11,R.id.b12,R.id.b13,R.id.b14,
			R.id.b15,R.id.b16,R.id.b17,R.id.b18,R.id.b19,R.id.b20,R.id.b21,
			R.id.b22,R.id.b23,R.id.b24,R.id.b25,R.id.b26,R.id.b27,R.id.b28,
			R.id.b29,R.id.b30,R.id.b31,R.id.b32,R.id.b33,R.id.b34,R.id.b35,
			R.id.b36,R.id.b37,R.id.b38,R.id.b39,R.id.b40,R.id.b41,R.id.b42,
			R.id.b43,R.id.b44,R.id.b45,R.id.b46,R.id.b47,R.id.b48,R.id.b49
		};
	private byte[] c = new byte[id.length];
	private short blue = 0, //numero de dots azules que hay en total
				red = 0; //numero de dots rojos que hay en total
	private final byte SPACE = 1,
						BLUE = 2,
						RED = 3;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		for(int i = 0; i < id.length ; i++) {
			c[i] = SPACE;
			View v = findViewById(id[i]);
			v.setOnClickListener
			(
				new OnClickListener() {
					public void onClick(View v) {
						Start(v);
					}
				}
			);
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.idml.

		int id = item.getItemId();
		if(id == R.id.action_settings) return true;
		else if(id == R.id.clear) {
			clear();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void Start(View v) {
		ImageButton i = (ImageButton) v;
		if(game && i.getTag() == null) {
			if(j1) {
				i.setImageResource(R.drawable.c4_blue_button);
				i.setTag("blue");//
			} else {
				i.setImageResource(R.drawable.c4_red_button);
				i.setTag("red");
			}
			game = false;   //Se para momentaneamente el juego
			if(fin(v)) end(); //Comprobamos si existen 4 dots del mismo color seguidos, que significaria el fin del juego
			else {
				j1 = !j1; //Si no es el fin del juego, se cambia el turno
				game = true; //Si no es el fin del juego se activa otra vez
			}
		}
	}
	private boolean fin(View v) {
		int i=0;
		while(id[i] != v.getId()) i++;  //Busca el dot pulsado
		if(j1) {
			c[i] = BLUE;
			blue++;
		} else {
			c[i] = RED;
			red++;
		}
		if(blue>3 || red>3) {
			byte rojooazul = j1 ? BLUE : RED;

			i=0;
			while(id[i] != v.getId()) i++; //Manda como parametro posicion del dot pulsado(donde se tiene que empezar a buscar)
			return busca(i, rojooazul, 1, -55); //parametros: la id del dot, el color del dot, el entero de control, la posicion 
												// del dot anterior (-55 representa que no tiene anterior)
		}
		return false;
	}
	private boolean busca(int i, byte ch, int control, int anterior) {
		if(c[i] == ch) // Si el color de esa posicion es el que estamos buscando
		{
			if(anterior == -55) // Si el dot no tiene anterior
			{
				boolean b = false;
				if( 	i == 8  || i == 9  || i == 10 || i == 11 || i == 12 ||
						i == 15 || i == 16 || i == 17 || i == 18 || i == 19 ||   // Si esta en las posiciones centrales tendra que
						i == 22 || i == 23 || i == 24 || i == 25 || i == 26 ||   // buscar en todas direcciones 
						i == 29 || i == 30 || i == 31 || i == 32 || i == 33 ||
						i == 36 || i == 37 || i == 38 || i == 39 || i == 40 )
				{
					if(!b) b = busca(i+8, ch, control, i);
					if(!b) b = busca(i+7, ch, control, i);
					if(!b) b = busca(i+6, ch, control, i);
					if(!b) b = busca(i+1, ch, control, i);
					if(!b) b = busca(i-1, ch, control, i);
					if(!b) b = busca(i-6, ch, control, i);
					if(!b) b = busca(i-7, ch, control, i);
					if(!b) b = busca(i-8, ch, control, i);
					if( i == 16 || i == 17 || i == 18 ||
						i == 23 || i == 24 || i == 25 ||
						i == 30 || i == 31 || i == 32  )
					{
						if(!b) b = busca(i-16, ch, 0, i-24);
						if(!b) b = busca(i-14, ch, 0, i-21);
						if(!b) b = busca(i-12, ch, 0, i-18);
						if(!b) b = busca(i-2 , ch, 0, i-3);
						if(!b) b = busca(i+2 , ch, 0, i+3);
						if(!b) b = busca(i+12, ch, 0, i+18);
						if(!b) b = busca(i+14, ch, 0, i+21);
						if(!b) b = busca(i+16, ch, 0, i+24);
					}
					else if( i == 8 || i == 9 || i == 10 || i == 11 || i == 12)
					{
						if(!b && i!=8)  b = busca(i-2 , ch, 0, i-3);
						if(!b && i!=40) b = busca(i+2 , ch, 0, i+3);
						if(!b && i!=8)  b = busca(i+12, ch, 0, i+18);
						if(!b) 			b = busca(i+14, ch, 0, i+21);
						if(!b && i!=12) b = busca(i+16, ch, 0, i+24);
					}
					else if( i == 15 || i == 22 || i == 29 )
					{
						if(!b) b = busca(i-14, ch, 0, i-21);
						if(!b) b = busca(i-12, ch, 0, i-18);
						if(!b) b = busca(i+2 , ch, 0, i+3);
						if(!b) b = busca(i+14, ch, 0, i+21);
						if(!b) b = busca(i+16, ch, 0, i+24);
					}
					else if( i == 36 || i == 37 || i == 38 || i == 39 || i == 40)
					{
						if(!b && i!=36) b = busca(i-2 , ch, 0, i-3);
						if(!b && i!=40) b = busca(i+2 , ch, 0, i+3);
						if(!b && i!=36) b = busca(i-12, ch, 0, i-18);
						if(!b) 			b = busca(i-14, ch, 0, i-21);
						if(!b && i!=40) b = busca(i-16, ch, 0, i-24);
					}
					else if( i == 19 || i == 26 || i == 33 )
					{
						if(!b) b = busca(i-16, ch, 0, i-24);
						if(!b) b = busca(i-14, ch, 0, i-21);
						if(!b) b = busca(i-2 , ch, 0, i-3);
						if(!b) b = busca(i+14, ch, 0, i+21);
						if(!b) b = busca(i+12, ch, 0, i+18);
					}
				}
				else if(i % 7 == 0 && i!=0 && i!=42)
				{
					if(!b) b = busca(i+8, ch, control, i);
					if(!b) b = busca(i+7, ch, control, i);
					if(!b) b = busca(i+1, ch, control, i);
					if(!b) b = busca(i-6, ch, control, i);
					if(!b) b = busca(i-7, ch, control, i);
					if(!b && i!=7 ) b = busca(i-14, ch, 0, i-21);
					if(!b && i!=35) b = busca(i+14, ch, 0, i+21);
				}
				else if(i == 13 || i == 20 || i == 27 || i == 34 || i == 41)
				{
					if(!b) b = busca(i-8, ch, control, i);
					if(!b) b = busca(i-7, ch, control, i);
					if(!b) b = busca(i-1, ch, control, i);
					if(!b) b = busca(i+6, ch, control, i);
					if(!b) b = busca(i+7, ch, control, i);
					if(!b && i!=13) b = busca(i-14, ch, 0, i-21);
					if(!b && i!=41) b = busca(i+14, ch, 0, i+21);
				}
				else if(i == 43 || i == 44 || i == 45 || i == 46 || i == 47)
				{
					if(!b) b = busca(i-8, ch, control, i);
					if(!b) b = busca(i-7, ch, control, i);
					if(!b) b = busca(i-6, ch, control, i);
					if(!b) b = busca(i-1, ch, control, i);
					if(!b) b = busca(i+1, ch, control, i);
					if(!b && i!=43) b = busca(i-2, ch, 0, i-3);
					if(!b && i!=47) b = busca(i+2, ch, 0, i+3);
				}
				else if(i == 1 || i == 2 || i == 3 || i == 4 || i == 5)
				{
					if(!b) b = busca(i+8, ch, control, i);
					if(!b) b = busca(i+7, ch, control, i);
					if(!b) b = busca(i+6, ch, control, i);
					if(!b) b = busca(i-1, ch, control, i);
					if(!b) b = busca(i+1, ch, control, i);
					if(!b && i!=1) b = busca(i-2, ch, 0, i-3);
					if(!b && i!=5) b = busca(i+2, ch, 0, i+3);
				}
				else if(i == 0)
				{
					if(!b) b = busca(i+1, ch, control, i);
					if(!b) b = busca(i+8, ch, control, i);
					if(!b) b = busca(i+7, ch, control, i);
				}
				else if(i == 42)
				{
					if(!b) b = busca(i+1, ch, control, i);
					if(!b) b = busca(i-6, ch, control, i);
					if(!b) b = busca(i-7, ch, control, i);
				}
				else if(i == 6)
				{
					if(!b) b = busca(i-1, ch, control, i);
					if(!b) b = busca(i+6, ch, control, i);
					if(!b) b = busca(i+7, ch, control, i);
				}
				else if(i == 48)
				{
					if(!b) b = busca(i-1, ch, control, i);
					if(!b) b = busca(i-8, ch, control, i);
					if(!b) b = busca(i-7, ch, control, i);
				}
				return b;
			}
			else
			{
				control++;
				if( i == 8  || i == 9  || i == 10 || i == 11 || i == 12 ||
					i == 15 || i == 16 || i == 17 || i == 18 || i == 19 ||
					i == 22 || i == 23 || i == 24 || i == 25 || i == 26 ||
					i == 29 || i == 30 || i == 31 || i == 32 || i == 33 ||
					i == 36 || i == 37 || i == 38 || i == 39 || i == 40 )
				{
					if	   (i-8 == anterior) return busca(i+8, ch, control, i);
					else if(i-7 == anterior) return busca(i+7, ch, control, i);
					else if(i-6 == anterior) return busca(i+6, ch, control, i);
					else if(i-1 == anterior) return busca(i+1, ch, control, i);
					else if(i+1 == anterior) return busca(i-1, ch, control, i);
					else if(i+6 == anterior) return busca(i-6, ch, control, i);
					else if(i+7 == anterior) return busca(i-7, ch, control, i);
					else if(i+8 == anterior) return busca(i-8, ch, control, i);
				}
				else if(i % 7 == 0 && i!=0 && i!=42)
				{
					if(i==7 || i ==14)
					{
						if(i-1 == anterior) 	 return busca(i+1, ch, control, i);
						else if(i-8 == anterior) return busca(i+8, ch, control, i);
						else if(i-7 == anterior) return busca(i+7, ch, control, i);
						else if(i+7 == anterior) return busca(i-7, ch, control, i);
					}
					else if(i==21)
					{
						if(i-1 == anterior) 	 return busca(i+1, ch, control, i);
						else if(i-8 == anterior) return busca(i+8, ch, control, i);
						else if(i+6 == anterior) return busca(i-6, ch, control, i);
						else if(i-7 == anterior) return busca(i+7, ch, control, i);
						else if(i+7 == anterior) return busca(i-7, ch, control, i);
					}
					else if(i==28 || i==35)
					{
						if(i-1 == anterior) 	 return busca(i+1, ch, control, i);
						else if(i+6 == anterior) return busca(i-6, ch, control, i);
						else if(i-7 == anterior) return busca(i+7, ch, control, i);
						else if(i+7 == anterior) return busca(i-7, ch, control, i);
					}
				}
				else if(i == 13 || i == 20 || i == 27 || i == 34 || i == 41)
				{
					if(i==13 || i ==20)
					{
						if(i+1 == anterior) 	 return busca(i-1, ch, control, i);
						else if(i-6 == anterior) return busca(i+6, ch, control, i);
						else if(i-7 == anterior) return busca(i+7, ch, control, i);
						else if(i+7 == anterior) return busca(i-7, ch, control, i);
					}
					else if(i==27)
					{
						if(i+1 == anterior) 	 return busca(i-1, ch, control, i);
						else if(i+8 == anterior) return busca(i-8, ch, control, i);
						else if(i-6 == anterior) return busca(i+6, ch, control, i);
						else if(i-7 == anterior) return busca(i+7, ch, control, i);
						else if(i+7 == anterior) return busca(i-7, ch, control, i);
					}
					else if(i==34 || i==41)
					{
						if(i+1 == anterior) 	 return busca(i-1, ch, control, i);
						else if(i+8 == anterior) return busca(i-8, ch, control, i);
						else if(i-7 == anterior) return busca(i+7, ch, control, i);
						else if(i+7 == anterior) return busca(i-7, ch, control, i);
					}
				}
				else if(i == 43 || i == 44 || i == 45 || i == 46 || i == 47)
				{
					if(i==43 || i ==44)
					{
						if(i+6 == anterior) 	 return busca(i-6, ch, control, i);
						else if(i+7 == anterior) return busca(i-7, ch, control, i);
						else if(i+1 == anterior) return busca(i-1, ch, control, i);
						else if(i-1 == anterior) return busca(i+1, ch, control, i);
					}
					else if(i==45)
					{
						if(i+6 == anterior) 	 return busca(i-6, ch, control, i);
						else if(i+8 == anterior) return busca(i-8, ch, control, i);
						else if(i+7 == anterior) return busca(i-7, ch, control, i);
						else if(i+1 == anterior) return busca(i-1, ch, control, i);
						else if(i-1 == anterior) return busca(i+1, ch, control, i);
					}
					else if(i==46 || i==47)
					{
						if(i+8 == anterior) 	 return busca(i-8, ch, control, i);
						else if(i+7 == anterior) return busca(i-7, ch, control, i);
						else if(i+1 == anterior) return busca(i-1, ch, control, i);
						else if(i-1 == anterior) return busca(i+1, ch, control, i);
					}
				}
				else if(i == 1 || i == 2 || i == 3 || i == 4 || i == 5)
				{
					if(i==1 || i ==2)
					{
						if(i-8 == anterior) 	 return busca(i+8, ch, control, i);
						else if(i-7 == anterior) return busca(i+7, ch, control, i);
						else if(i+1 == anterior) return busca(i-1, ch, control, i);
						else if(i-1 == anterior) return busca(i+1, ch, control, i);
					}
					else if(i==3)
					{
						if(i-6 == anterior) 	 return busca(i+6, ch, control, i);
						else if(i-8 == anterior) return busca(i+8, ch, control, i);
						else if(i-7 == anterior) return busca(i+7, ch, control, i);
						else if(i+1 == anterior) return busca(i-1, ch, control, i);
						else if(i-1 == anterior) return busca(i+1, ch, control, i);
					}
					else if(i==4 || i==5)
					{
						if(i-6 == anterior) 	 return busca(i+6, ch, control, i);
						else if(i-7 == anterior) return busca(i+7, ch, control, i);
						else if(i+1 == anterior) return busca(i-1, ch, control, i);
						else if(i-1 == anterior) return busca(i+1, ch, control, i);
					}
				}
				else if(i==0)
				{
					if(i-8 == anterior) 	return busca(i+8, ch, control, i);
					else if(i-7 == anterior) return busca(i+7, ch, control, i);
					else if(i-1 == anterior) return busca(i+1, ch, control, i);
				}
				else if(i==6)
				{
					if(i-6 == anterior) 	return busca(i+6, ch, control, i);
					else if(i-7 == anterior) return busca(i+7, ch, control, i);
					else if(i+1 == anterior) return busca(i-1, ch, control, i);
				}
				else if(i==42)
				{
					if(i+6 == anterior) 	return busca(i-6, ch, control, i);
					else if(i+7 == anterior) return busca(i-7, ch, control, i);
					else if(i-1 == anterior) return busca(i+1, ch, control, i);
					}
				else if(i==48)
				{
					if(i+8 == anterior) 	return busca(i-8, ch, control, i);
					else if(i+7 == anterior) return busca(i-7, ch, control, i);
					else if(i+1 == anterior) return busca(i-1, ch, control, i);
				}
			}
		}
		return control == 4 ? true : false;
	}
	private void end() {
		String s = j1 ? "Gana el jugador 1: azul" : "Gana el jugador 2: rojo";
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
	}
	private void clear() {
		game = true;
		j1 = true;
		c = new byte[id.length];
		for(int i : id) {
			ImageButton b = (ImageButton) findViewById(i);
			b.setImageResource(R.drawable.c4_button);
			b.setTag(null);
		}
		blue = 0;
		red = 0;
	}
}
