package com.example.samsung.p0501_simpleadaptercustom2;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //имена атрибутов для Map
    final String ATTR_NAME_TEXT = "text",
                 ATTR_NAME_PB = "pb",
                 ATTR_NAME_LL = "ll";
    ListView lvSimple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /**Данные находятся в файле "strings.xml" в числовом массиве "load"
         * Упаковываем их в понятную адаптеру структуру
         */
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        int index = 0;
        for (int value : getResources().getIntArray(R.array.load)) {
            map = new HashMap<String, Object>();
            map.put(ATTR_NAME_TEXT, "Day " + (index + 1) + ". Load = " + value + "%");
            map.put(ATTR_NAME_PB, value);
            map.put(ATTR_NAME_LL, value);
            data.add(map);
            index++;
        }

        //массив имён атрибутов, котоые будут сопоставлятся ID View-компонетов
        String[] from = {ATTR_NAME_TEXT, ATTR_NAME_PB, ATTR_NAME_LL};
        //массив ID View-компонентов, которым будут сопоставляться имена атрибутов
        int[] to = {R.id.tvLoad, R.id.pbLoad, R.id.llLoad};
        //создаём адаптер
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item, from, to);
        //указываем адаптеру собственноручно созданный биндер
        simpleAdapter.setViewBinder(new MyViewBinder());
        //определяем список и устанавливаем на него адаптер
        lvSimple = (ListView) findViewById(R.id.lvSimple);
        lvSimple.setAdapter(simpleAdapter);
    }

    class MyViewBinder implements SimpleAdapter.ViewBinder {

        int red = getResources().getColor(R.color.Red),
            orange = getResources().getColor(R.color.Orange),
            green = getResources().getColor(R.color.Green),
            black = getResources().getColor(R.color.Black);

        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {

            int value = 0;
            switch (view.getId()) {
                //LinearLayout
                case R.id.llLoad :
                    value = ((Integer) data).intValue();
                    if (value < 40) {
                        view.setBackgroundColor(green);
                    } else if (value < 70) {
                        view.setBackgroundColor(orange);
                    } else {
                        view.setBackgroundColor(red);
                    }
                    /**т.к. биндинг успшно выполнен, то возвращаем "true", что значит,
                     * что стандарную обработку выполнять не нужно
                     */
                    return true;
                //ProgressBar
                case R.id.pbLoad :
                    value = ((Integer) data).intValue();
                    ((ProgressBar) view).setProgress(value);
                    //аналогично предыдущему
                    return true;
            }
            /** для всех других вариантов View требуется стандартная обработка,
             *  поэтому возвращаем "false"
             */
            return false;
        }
    }
}
