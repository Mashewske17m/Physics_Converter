package com.phyconvert.root.physicsconverter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private VelocityInitial vi;
    private VelocityFinal vf;
    private Acceleration a;
    private Displacement d;
    private Time t;
    private EditText entryA;
    private EditText entryD;
    private EditText entryVI;
    private EditText entryVF;
    private EditText entryT;
    private int emptyAmount;
    private int sigFigs;
    private Button clearAllButton;
    private Button clearVIButton;
    private Button clearVFButton;
    private Button clearDButton;
    private Button clearTButton;
    private Button clearAButton;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //*******Initialize Text Fields************//
        entryA = (EditText)findViewById(R.id.entryA);
        entryD = (EditText)findViewById(R.id.entryD);
        entryT = (EditText)findViewById(R.id.entryT);
        entryVI = (EditText)findViewById(R.id.entryVI);
        entryVF = (EditText)findViewById(R.id.entryVF);
        clearAllButton = (Button)findViewById(R.id.clearAllButton);
        clearAButton = (Button)findViewById(R.id.accelerationClearButton);
        clearDButton = (Button)findViewById(R.id.displacementClearButton);
        clearTButton = (Button)findViewById(R.id.timeButton);
        clearVIButton = (Button)findViewById(R.id.velocityInitialClearButton);
        clearVFButton = (Button)findViewById(R.id.velocityFinalClearButton);
        final EditText[] entryList = {entryA, entryD, entryT, entryVF, entryVI};
        //*********Initialize Physics Things**********//
        vi = new VelocityInitial(0);
        vf = new VelocityFinal(0);
        d = new Displacement(0);
        t = new Time(0);
        a = new Acceleration(0);
        sigFigs = 4;
        //**********Clear Buttons*********************//
        clearAllButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               for (EditText x : entryList) {
                                                   x.setText("");
                                               }
                                           }
                                       });
        clearAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entryA.setText("");
            }
        });
        clearDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entryD.setText("");
            }
        });
        clearTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entryT.setText("");
            }
        });
        clearVIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entryVI.setText("");
            }
        });
        clearVFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entryVF.setText("");
            }
        });
        //***********Floating Action Button***************//

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_confirm));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean nan = false;
                emptyAmount = 0;
                for(EditText x:entryList)
                {
                    if(isEmpty(x))
                    {
                        emptyAmount++;
                    }
                    else{
                        String currentText=x.getText().toString();
                        if(currentText.contains(" ")) {
                            currentText = currentText.substring(0, currentText.indexOf(" "));
                            x.setText(currentText);
                        }
                    }
                }
                if(emptyAmount>2) {
                    Snackbar.make(view, "Not Enough Information", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                if(emptyAmount<3){
                    int count=1;
                    for(EditText x: entryList){
                        if(!(isEmpty(x))){
                            double value = entryValue(x);
                            if(count==1){
                                a=new Acceleration(value);
                            }
                            if(count==2){
                                d = new Displacement(value);
                            }
                            if(count==3){
                                t= new Time(value);
                            }
                            if(count==4){
                                vf = new VelocityFinal(value);
                            }
                            if(count==5){
                                vi = new VelocityInitial(value);
                            }
                        }
                        count++;
                    }
                    if(true) {
                        if (isEmpty(entryA) && isEmpty(entryD)) {
                            d = new Displacement(vi, vf, t);
                            entryD.setText("" + d.value());
                            a = new Acceleration(vi, vf, d);
                            entryA.setText("" + a.value());
                        }
                        if (isEmpty(entryA) && isEmpty(entryT)) {
                            t = new Time(vi, vf, d);
                            entryT.setText("" + t.value());
                            a = new Acceleration(vi, vf, d);
                            entryA.setText("" + a.value());
                        }
                        if (isEmpty(entryA) && isEmpty(entryVI)) {
                            vi=new VelocityInitial(vf,t,d);
                            entryVI.setText(""+vi.value());
                            a = new Acceleration(vi,vf,t);
                            entryA.setText("" + a.value());
                        }
                        if(isEmpty(entryA)&&isEmpty(entryVF)){
                            a = new Acceleration(vi,t,d);
                            entryA.setText(""+a.value());
                            vf = new VelocityFinal(t, vi, d);
                            entryVF.setText(""+vf.value());
                        }
                        if(isEmpty(entryD)&&isEmpty(entryT)){
                            d=new Displacement(vi,vf,a);
                            entryD.setText(""+d.value());
                            t=new Time(vi,vf,a);
                            entryT.setText(""+t.value());
                        }
                        if(isEmpty(entryD)&&isEmpty(entryVI)){
                            vi=new VelocityInitial(a,vf,t);
                            entryVI.setText(""+vi.value());
                            d=new Displacement(vi,vf,t);
                            entryD.setText(""+d.value());
                        }
                        if(isEmpty(entryD)&&isEmpty(entryVF)){
                            vf=new VelocityFinal(a,vi,t);
                            entryVF.setText(""+vf.value());
                            d=new Displacement(vi,vf,t);
                            entryD.setText(""+d.value());
                        }
                        if (isEmpty(entryVI)&&isEmpty(entryVF)){
                            vi = new VelocityInitial(a, t, d);
                            entryVI.setText(""+vi.value());
                            vf = new VelocityFinal(t, vi, d);
                            entryVF.setText(""+vf.value());
                        }
                        if(isEmpty(entryVI)&&isEmpty(entryT)){
                            vi = new VelocityInitial(a, vf, d);
                            entryVI.setText(""+vi.value());
                            t = new Time(vi, vf, a);
                            entryT.setText(""+t.value());
                        }
                        if(isEmpty(entryVF)&&isEmpty(entryT)) {
                            vf = new VelocityFinal(a, vi, d);
                            entryVF.setText("" + vf.value());
                            t = new Time(vi, a, d);
                            entryT.setText("" + t.value());
                        }
                        if(Double.isNaN(a.value()) || Double.isNaN(t.value()) || Double.isNaN(vi.value())|| Double.isNaN(vf.value()) || Double.isNaN(d.value())){
                            nan=true;
                        }
                    }
                    count=1;
                    for(EditText x:entryList){
                        if((!(x.getText().toString().equals("NaN"))) && (!(x.getText().toString().equals("Infinity")))){
                            String currentText= x.getText().toString();
                            if(!(currentText.contains("."))){
                                currentText=currentText+".0";
                            }
                            if(sigFigs+1<(currentText.length()-currentText.indexOf("."))){
                                currentText=currentText.substring(0,currentText.indexOf(".")+sigFigs+1);
                            }
                                if(count==1){
                                    entryA.setText(currentText+" (M/S^2)");
                                }
                                if(count==2){
                                    entryD.setText(currentText+" (M)");
                                }
                                if(count==3){
                                    entryT.setText(currentText+" (S)");
                                }
                                if(count==4){
                                    entryVF.setText(currentText+" (M/S)");
                                }
                                if(count==5) {
                                    entryVI.setText(currentText+" (M/S)");
                                }
                        }
                        count++;
                    }

                    if(!(nan)) {
                        Snackbar.make(view, "Completed", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        Snackbar.make(view, "Invalid Data", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void clear(){

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private boolean isEmpty(EditText x){
        return(x.getText().toString().equals(""));
    }
    private double entryValue(EditText x){
        return Double.parseDouble(x.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class VelocityInitial{
        private double value;
        VelocityInitial(double v){
            value=v;
        }
        VelocityInitial(VelocityFinal vf, Time t, Displacement d){
            value=((2*d.value())/t.value())-vf.value();
        }
        VelocityInitial(Acceleration a, VelocityFinal vf,Displacement d){
            value = Math.sqrt(vf.value()*vf.value()-2*d.value()*a.value());
        }
        VelocityInitial(Acceleration a, VelocityFinal vf, Time t){
            value=vf.value()-a.value()*t.value();
        }
        VelocityInitial(Acceleration a, Time t, Displacement d){
            value=((d.value()-(.5*a.value()*t.value()*t.value()))/t.value());
        }
        public double value(){
            return(value);
        }
    }
    public class VelocityFinal{
        private double value;
        VelocityFinal(double v){
            value=v;
        }
        VelocityFinal(Time t, VelocityInitial vi, Displacement d){
            value=((2*d.value())/t.value())-vi.value();
        }
        VelocityFinal(Acceleration a, VelocityInitial vi, Displacement d){
            value=Math.sqrt(vi.value()*vi.value()+2*d.value()*a.value());
        }
        VelocityFinal(Acceleration a, VelocityInitial vi, Time t){
            value=vi.value()+a.value()*t.value();
        }
        public double value(){
            return(value);
        }
    }
    public class Acceleration{
        private double value;
        Acceleration(double v){
            value=v;
        }
        Acceleration(VelocityInitial vi,VelocityFinal vf, Time t){
            value=(vf.value()-vi.value())/t.value();
        }
        Acceleration(VelocityInitial vi, VelocityFinal vf, Displacement d){
            value=((vf.value()*vf.value())-(vi.value()*vi.value()))/(2*d.value());
        }
        Acceleration(VelocityInitial vi, Time t, Displacement d){
            value=(2*(d.value()-(vi.value()*t.value())))/(t.value()*t.value());
        }
        public double value(){
            return(value);
        }
    }
    public class Time{
        private double value;
        Time(double v){
            value=v;
        }
        Time(VelocityInitial vi, VelocityFinal vf, Acceleration a){
            value=(vf.value()-vi.value())/a.value();
        }
        Time(VelocityInitial vi, VelocityFinal vf, Displacement d){
            value=(2*d.value())/(vi.value()+vf.value());
        }
        Time(VelocityInitial vi, Acceleration a, Displacement d){
            value=(Math.sqrt(vi.value()*vi.value()+2*a.value()*d.value())-vi.value())/a.value();
        }
        public double value(){
            return(value);
        }
    }
    public class Displacement{
        private double value;
        Displacement(double v){
            value=v;
        }
        Displacement(VelocityInitial vi, VelocityFinal vf, Acceleration a, Time t){
            value=(vi.value()*t.value())+(.5*a.value()*t.value()*t.value());
        }
        Displacement(VelocityInitial vi, VelocityFinal vf, Time t){
            value=(((vi.value()+vf.value())/2)*t.value());
        }
        Displacement(VelocityInitial vi, VelocityFinal vf, Acceleration a){
            value=((vf.value()*vf.value())-(vi.value()*vi.value()))/(2*a.value());
        }
        public double value(){
            return(value);
        }
    }

}
