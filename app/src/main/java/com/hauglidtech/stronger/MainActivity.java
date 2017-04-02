package com.hauglidtech.stronger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    String[] exercisesArr = {"BenchPress", "Squat", "Deadlift", "Curls", "Rowing", "Pull Ups",
            "BenchPress", "Squat", "Deadlift", "Curls", "Rowing", "Pull Ups",
            "BenchPress", "Squat", "Deadlift", "Curls", "Rowing", "Pull Ups",
            "BenchPress", "Squat", "Deadlift", "Curls", "Rowing", "Pull Ups"};


    private static final String TAG = "karlisMessage";
    EditText usernametext;
    EditText passwordText;
    EditText inputdb;
    TextView outputdb;
    MyDBHandler dbHandler;


    SeekBar seekBar;
    SeekBar seekBarSmooth;
    TextView seekBarValue;
    TextView seekBarSmoothValue;

    LinearLayout exercise;
    LinearLayout dashboard;
    RelativeLayout workout;
    LinearLayout progress;
    LinearLayout settings;
    ListView listView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_exercises:
                    workout.setVisibility(View.GONE);
                    dashboard.setVisibility(View.GONE);
                    progress.setVisibility(View.GONE);
                    settings.setVisibility(View.GONE);
                    exercise.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:

                    workout.setVisibility(View.GONE);
                    dashboard.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                    settings.setVisibility(View.GONE);
                    exercise.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_progress:

                    workout.setVisibility(View.GONE);
                    dashboard.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);
                    settings.setVisibility(View.GONE);
                    exercise.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_settings:
                    workout.setVisibility(View.GONE);
                    dashboard.setVisibility(View.GONE);
                    progress.setVisibility(View.GONE);
                    settings.setVisibility(View.VISIBLE);
                    exercise.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_workout:

                    workout.setVisibility(View.VISIBLE);
                    dashboard.setVisibility(View.GONE);
                    progress.setVisibility(View.GONE);
                    settings.setVisibility(View.GONE);
                    exercise.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }

    };

    private void populateView() {

        ListAdapter adapter = new ExerciseAdapter(this, exercisesArr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition     = position;
                String  itemValue    = (String) listView.getItemAtPosition(position);

              Toast.makeText(getApplicationContext(),
                "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_SHORT)
                .show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();

        seekBarSmooth.setOnSeekBarChangeListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        populateView();
        printDatabase();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    // A private method to help us initialize our variables.
    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBarSmooth = (SeekBar) findViewById(R.id.seekBarSmooth);

        inputdb = (EditText) findViewById(R.id.inputDB);
        outputdb = (TextView) findViewById(R.id.outputDB);
        dbHandler = new MyDBHandler(this, null, null, 1);

        seekBarSmoothValue = (TextView) findViewById(R.id.seekBarSmoothValue);
        seekBarValue = (TextView) findViewById(R.id.seekBarValue);

        listView = (ListView) findViewById(R.id.listView);

        exercise = (LinearLayout) findViewById(R.id.exercise_include );
        dashboard = (LinearLayout) findViewById(R.id.dashboard_include );
        progress = (LinearLayout) findViewById(R.id.progress_include );
        settings = (LinearLayout) findViewById(R.id.settings_include );
        workout = (RelativeLayout) findViewById(R.id.workout_include );
    }


        public void onClickLogin(View v){
        Intent i = new Intent(this, LoginActivity.class);


        i.putExtra("username", usernametext.getText().toString());
        i.putExtra("password", passwordText.getText().toString());
        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String username = data.getStringExtra("username");
                String password = data.getStringExtra("password");
                usernametext.setText(username);
                passwordText.setText(password);
            }else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(seekBar == seekBarSmooth){
            seekBarSmoothValue.setText(String.valueOf(i));
        }
        else{
            seekBarValue.setText(String.valueOf(i));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    //DB

    public void addToDB(View v){
        String input = inputdb.getText().toString();
        if(input.length()> 0){
            Products product = new Products(input);
            dbHandler.addProduct(product);
            printDatabase();
        }
    }


    public void deleteFromDB(View v){
        String input = inputdb.getText().toString();
        if(input.length() > 0){
            dbHandler.deleteProduct(input);
            printDatabase();
        }
    }

    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        outputdb.setText(dbString);
        inputdb.setText("");

    }


}
