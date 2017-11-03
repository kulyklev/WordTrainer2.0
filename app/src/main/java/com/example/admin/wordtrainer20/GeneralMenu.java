package com.example.admin.wordtrainer20;

import android.content.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;

/**
 * Created by admin on 22.09.2017.
 */

public class GeneralMenu extends AppCompatActivity {

    //
    //Creates same header in all subclasses
    //

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                Toast.makeText(GeneralMenu.this, "You have tapped on SETTING.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_my_dictionaries:
                Toast.makeText(GeneralMenu.this, "You have tapped on MY DICTIONARIES.", Toast.LENGTH_SHORT).show();
                Intent openMainActivity = new Intent(GeneralMenu.this, MyDictionaries.class);
                startActivity(openMainActivity);
                break;

            case R.id.action_statistic:
                Toast.makeText(GeneralMenu.this, "You have tapped on STATISTIC.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_calendar:
                Toast.makeText(GeneralMenu.this, "You have tapped on CALENDAR.", Toast.LENGTH_SHORT).show();
                Intent openCalendarActivity = new Intent(GeneralMenu.this, CalendarActivity.class);
                startActivity(openCalendarActivity);
                break;

            case R.id.action_about_us:
                Toast.makeText(GeneralMenu.this, "You have tapped on ABOUT US.", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
