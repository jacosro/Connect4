package com.jacosro.connect4;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final int[] ids =
        {
            R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7,
            R.id.b8, R.id.b9 ,R.id.b10,R.id.b11,R.id.b12,R.id.b13,R.id.b14,
            R.id.b15,R.id.b16,R.id.b17,R.id.b18,R.id.b19,R.id.b20,R.id.b21,
            R.id.b22,R.id.b23,R.id.b24,R.id.b25,R.id.b26,R.id.b27,R.id.b28,
            R.id.b29,R.id.b30,R.id.b31,R.id.b32,R.id.b33,R.id.b34,R.id.b35,
            R.id.b36,R.id.b37,R.id.b38,R.id.b39,R.id.b40,R.id.b41,R.id.b42,
            R.id.b43,R.id.b44,R.id.b45,R.id.b46,R.id.b47,R.id.b48,R.id.b49
        };
    private Map<Integer, Byte> dotsColor = new HashMap<>();

    private boolean gameEnd = false;
    private boolean j1 = true; // j1 turn or j2 turn
    private short blue = 0, // blue dots count
                  red = 0; // // red dots count
    private final byte BLANK = 1,
                       BLUE = 2,
                       RED = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int id : ids) {
            findViewById(id).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (!gameEnd) click(v);
                    }
                });
            dotsColor.put(id, BLANK);
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

    public void click(View v) {
        int id = v.getId();
        if(dotsColor.get(id) == BLANK) {
            ImageButton i = (ImageButton) v;
            if(j1) {
                i.setImageResource(R.drawable.c4_blue_button);
                dotsColor.put(id, BLUE);
                blue++;
            } else {
                i.setImageResource(R.drawable.c4_red_button);
                dotsColor.put(id, RED);
                red++;
            }
            if(isEnd(id)) { // Check if is the game end
                end();
            } else {
                j1 = !j1; // Switch turn
            }
        }
    }

    private boolean isEnd(int id) {
        int i = 0;
        while (this.ids[i] != id) i++; // Looks for the position of the dot
        if(blue > 3 || red > 3) {
            byte player = j1 ? BLUE : RED;
            return search(i, player, 1, -55); //params: dot position, dot color, control int, position of the last visited dot (-55 if none)
        }
        return false;
    }

    private boolean search(int i, byte color, int control, int last) {
        if(dotsColor.get(ids[i]) == color) // if the dot we visit has the color we are searching for
        {
            if(last == -55) // If we are not coming from other dot
            {
                boolean b = false;
                if( 	i == 8  || i == 9  || i == 10 || i == 11 || i == 12 ||
                        i == 15 || i == 16 || i == 17 || i == 18 || i == 19 ||   // Checks if the dot is on center positions
                        i == 22 || i == 23 || i == 24 || i == 25 || i == 26 ||
                        i == 29 || i == 30 || i == 31 || i == 32 || i == 33 ||
                        i == 36 || i == 37 || i == 38 || i == 39 || i == 40 )
                {
                    if(!b) b = search(i+8, color, control, i);
                    if(!b) b = search(i+7, color, control, i);
                    if(!b) b = search(i+6, color, control, i);
                    if(!b) b = search(i+1, color, control, i);
                    if(!b) b = search(i-1, color, control, i);
                    if(!b) b = search(i-6, color, control, i);
                    if(!b) b = search(i-7, color, control, i);
                    if(!b) b = search(i-8, color, control, i);

                    if( i == 16 || i == 17 || i == 18 ||
                        i == 23 || i == 24 || i == 25 ||
                        i == 30 || i == 31 || i == 32  )
                    {
                        if(!b) b = search(i-16, color, 0, i-24);
                        if(!b) b = search(i-14, color, 0, i-21);
                        if(!b) b = search(i-12, color, 0, i-18);
                        if(!b) b = search(i-2 , color, 0, i-3);
                        if(!b) b = search(i+2 , color, 0, i+3);
                        if(!b) b = search(i+12, color, 0, i+18);
                        if(!b) b = search(i+14, color, 0, i+21);
                        if(!b) b = search(i+16, color, 0, i+24);
                    }
                    else if( i == 8 || i == 9 || i == 10 || i == 11 || i == 12)
                    {
                        if(!b && i!=8)  b = search(i-2 , color, 0, i-3);
                        if(!b && i!=40) b = search(i+2 , color, 0, i+3);
                        if(!b && i!=8)  b = search(i+12, color, 0, i+18);
                        if(!b) 			b = search(i+14, color, 0, i+21);
                        if(!b && i!=12) b = search(i+16, color, 0, i+24);
                    }
                    else if( i == 15 || i == 22 || i == 29 )
                    {
                        if(!b) b = search(i-14, color, 0, i-21);
                        if(!b) b = search(i-12, color, 0, i-18);
                        if(!b) b = search(i+2 , color, 0, i+3);
                        if(!b) b = search(i+14, color, 0, i+21);
                        if(!b) b = search(i+16, color, 0, i+24);
                    }
                    else if( i == 36 || i == 37 || i == 38 || i == 39 || i == 40)
                    {
                        if(!b && i!=36) b = search(i-2 , color, 0, i-3);
                        if(!b && i!=40) b = search(i+2 , color, 0, i+3);
                        if(!b && i!=36) b = search(i-12, color, 0, i-18);
                        if(!b) 			b = search(i-14, color, 0, i-21);
                        if(!b && i!=40) b = search(i-16, color, 0, i-24);
                    }
                    else if( i == 19 || i == 26 || i == 33 )
                    {
                        if(!b) b = search(i-16, color, 0, i-24);
                        if(!b) b = search(i-14, color, 0, i-21);
                        if(!b) b = search(i-2 , color, 0, i-3);
                        if(!b) b = search(i+14, color, 0, i+21);
                        if(!b) b = search(i+12, color, 0, i+18);
                    }
                }
                else if(i % 7 == 0 && i!=0 && i!=42)
                {
                    if(!b) b = search(i+8, color, control, i);
                    if(!b) b = search(i+7, color, control, i);
                    if(!b) b = search(i+1, color, control, i);
                    if(!b) b = search(i-6, color, control, i);
                    if(!b) b = search(i-7, color, control, i);
                    if(!b && i!=7 ) b = search(i-14, color, 0, i-21);
                    if(!b && i!=35) b = search(i+14, color, 0, i+21);
                }
                else if(i == 13 || i == 20 || i == 27 || i == 34 || i == 41)
                {
                    if(!b) b = search(i-8, color, control, i);
                    if(!b) b = search(i-7, color, control, i);
                    if(!b) b = search(i-1, color, control, i);
                    if(!b) b = search(i+6, color, control, i);
                    if(!b) b = search(i+7, color, control, i);
                    if(!b && i!=13) b = search(i-14, color, 0, i-21);
                    if(!b && i!=41) b = search(i+14, color, 0, i+21);
                }
                else if(i == 43 || i == 44 || i == 45 || i == 46 || i == 47)
                {
                    if(!b) b = search(i-8, color, control, i);
                    if(!b) b = search(i-7, color, control, i);
                    if(!b) b = search(i-6, color, control, i);
                    if(!b) b = search(i-1, color, control, i);
                    if(!b) b = search(i+1, color, control, i);
                    if(!b && i!=43) b = search(i-2, color, 0, i-3);
                    if(!b && i!=47) b = search(i+2, color, 0, i+3);
                }
                else if(i == 1 || i == 2 || i == 3 || i == 4 || i == 5)
                {
                    if(!b) b = search(i+8, color, control, i);
                    if(!b) b = search(i+7, color, control, i);
                    if(!b) b = search(i+6, color, control, i);
                    if(!b) b = search(i-1, color, control, i);
                    if(!b) b = search(i+1, color, control, i);
                    if(!b && i!=1) b = search(i-2, color, 0, i-3);
                    if(!b && i!=5) b = search(i+2, color, 0, i+3);
                }
                else if(i == 0)
                {
                    if(!b) b = search(i+1, color, control, i);
                    if(!b) b = search(i+8, color, control, i);
                    if(!b) b = search(i+7, color, control, i);
                }
                else if(i == 42)
                {
                    if(!b) b = search(i+1, color, control, i);
                    if(!b) b = search(i-6, color, control, i);
                    if(!b) b = search(i-7, color, control, i);
                }
                else if(i == 6)
                {
                    if(!b) b = search(i-1, color, control, i);
                    if(!b) b = search(i+6, color, control, i);
                    if(!b) b = search(i+7, color, control, i);
                }
                else if(i == 48)
                {
                    if(!b) b = search(i-1, color, control, i);
                    if(!b) b = search(i-8, color, control, i);
                    if(!b) b = search(i-7, color, control, i);
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
                    if	   (i-8 == last) return search(i+8, color, control, i);
                    else if(i-7 == last) return search(i+7, color, control, i);
                    else if(i-6 == last) return search(i+6, color, control, i);
                    else if(i-1 == last) return search(i+1, color, control, i);
                    else if(i+1 == last) return search(i-1, color, control, i);
                    else if(i+6 == last) return search(i-6, color, control, i);
                    else if(i+7 == last) return search(i-7, color, control, i);
                    else if(i+8 == last) return search(i-8, color, control, i);
                }
                else if(i % 7 == 0 && i!=0 && i!=42)
                {
                    if(i==7 || i ==14)
                    {
                        if     (i-1 == last) return search(i+1, color, control, i);
                        else if(i-8 == last) return search(i+8, color, control, i);
                        else if(i-7 == last) return search(i+7, color, control, i);
                        else if(i+7 == last) return search(i-7, color, control, i);
                    }
                    else if(i==21)
                    {
                        if     (i-1 == last) return search(i+1, color, control, i);
                        else if(i-8 == last) return search(i+8, color, control, i);
                        else if(i+6 == last) return search(i-6, color, control, i);
                        else if(i-7 == last) return search(i+7, color, control, i);
                        else if(i+7 == last) return search(i-7, color, control, i);
                    }
                    else if(i==28 || i==35)
                    {
                        if     (i-1 == last) return search(i+1, color, control, i);
                        else if(i+6 == last) return search(i-6, color, control, i);
                        else if(i-7 == last) return search(i+7, color, control, i);
                        else if(i+7 == last) return search(i-7, color, control, i);
                    }
                }
                else if(i == 13 || i == 20 || i == 27 || i == 34 || i == 41)
                {
                    if(i==13 || i ==20)
                    {
                        if     (i+1 == last) return search(i-1, color, control, i);
                        else if(i-6 == last) return search(i+6, color, control, i);
                        else if(i-7 == last) return search(i+7, color, control, i);
                        else if(i+7 == last) return search(i-7, color, control, i);
                    }
                    else if(i==27)
                    {
                        if     (i+1 == last) return search(i-1, color, control, i);
                        else if(i+8 == last) return search(i-8, color, control, i);
                        else if(i-6 == last) return search(i+6, color, control, i);
                        else if(i-7 == last) return search(i+7, color, control, i);
                        else if(i+7 == last) return search(i-7, color, control, i);
                    }
                    else
                    {
                        if     (i+1 == last) return search(i-1, color, control, i);
                        else if(i+8 == last) return search(i-8, color, control, i);
                        else if(i-7 == last) return search(i+7, color, control, i);
                        else if(i+7 == last) return search(i-7, color, control, i);
                    }
                }
                else if(i == 43 || i == 44 || i == 45 || i == 46 || i == 47)
                {
                    if(i==43 || i ==44)
                    {
                        if     (i+6 == last) return search(i-6, color, control, i);
                        else if(i+7 == last) return search(i-7, color, control, i);
                        else if(i+1 == last) return search(i-1, color, control, i);
                        else if(i-1 == last) return search(i+1, color, control, i);
                    }
                    else if(i==45)
                    {
                        if     (i+6 == last) return search(i-6, color, control, i);
                        else if(i+8 == last) return search(i-8, color, control, i);
                        else if(i+7 == last) return search(i-7, color, control, i);
                        else if(i+1 == last) return search(i-1, color, control, i);
                        else if(i-1 == last) return search(i+1, color, control, i);
                    }
                    else
                    {
                        if     (i+8 == last) return search(i-8, color, control, i);
                        else if(i+7 == last) return search(i-7, color, control, i);
                        else if(i+1 == last) return search(i-1, color, control, i);
                        else if(i-1 == last) return search(i+1, color, control, i);
                    }
                }
                else if(i == 1 || i == 2 || i == 3 || i == 4 || i == 5)
                {
                    if(i==1 || i ==2)
                    {
                        if     (i-8 == last) return search(i+8, color, control, i);
                        else if(i-7 == last) return search(i+7, color, control, i);
                        else if(i+1 == last) return search(i-1, color, control, i);
                        else if(i-1 == last) return search(i+1, color, control, i);
                    }
                    else if(i==3)
                    {
                        if     (i-6 == last) return search(i+6, color, control, i);
                        else if(i-8 == last) return search(i+8, color, control, i);
                        else if(i-7 == last) return search(i+7, color, control, i);
                        else if(i+1 == last) return search(i-1, color, control, i);
                        else if(i-1 == last) return search(i+1, color, control, i);
                    }
                    else
                    {
                        if     (i-6 == last) return search(i+6, color, control, i);
                        else if(i-7 == last) return search(i+7, color, control, i);
                        else if(i+1 == last) return search(i-1, color, control, i);
                        else if(i-1 == last) return search(i+1, color, control, i);
                    }
                }
                else if(i==0)
                {
                    if     (i-8 == last) return search(i+8, color, control, i);
                    else if(i-7 == last) return search(i+7, color, control, i);
                    else if(i-1 == last) return search(i+1, color, control, i);
                }
                else if(i==6)
                {
                    if     (i-6 == last) return search(i+6, color, control, i);
                    else if(i-7 == last) return search(i+7, color, control, i);
                    else if(i+1 == last) return search(i-1, color, control, i);
                }
                else if(i==42)
                {
                    if     (i+6 == last) return search(i-6, color, control, i);
                    else if(i+7 == last) return search(i-7, color, control, i);
                    else if(i-1 == last) return search(i+1, color, control, i);
                    }
                else if(i==48)
                {
                    if     (i+8 == last) return search(i-8, color, control, i);
                    else if(i+7 == last) return search(i-7, color, control, i);
                    else if(i+1 == last) return search(i-1, color, control, i);
                }
            }
        }
        return control == 4;
    }

    private void end() {
        gameEnd = true;
        String winner = j1 ? "1 (blue)" : "2 (red)";
        String show = "Player " + winner + " wins!";
        Toast.makeText(this, show, Toast.LENGTH_LONG).show();
    }

    private void clear() {
        gameEnd = false;
        j1 = true;
        for(int id : ids) {
            ImageButton b = (ImageButton) findViewById(id);
            b.setImageResource(R.drawable.c4_button);
            dotsColor.put(id, BLANK);
        }
        blue = 0;
        red = 0;
    }
}
