package com.example.example1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView myTextView;
    Button myButton;
    Button startBtn;
    EditText mveshEdit;
    EditText mrastvoritelEdit;
    EditText mrastvoraEdit;
    EditText procEdit;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        //TextView tv = findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());

        //myTextView = (TextView) findViewById(R.id.myTextView);
        /*
        QLineEdit* mveshEdit;
        QLineEdit* mrastvoritelEdit;
        QLineEdit* mrastvoraEdit;
        QLineEdit* procEdit;
         */

        mveshEdit = (EditText) findViewById(R.id.mveshEdit);
        mrastvoritelEdit = (EditText) findViewById(R.id.mrastvoritelEdit);
        mrastvoraEdit = (EditText) findViewById(R.id.mrastvoraEdit);
        procEdit = (EditText) findViewById(R.id.procEdit);
        myButton = (Button) findViewById(R.id.myButton);
        startBtn = (Button) findViewById(R.id.startBtn);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myTextView.setText("Все ок)");
                mveshEdit.setText("");
                mrastvoritelEdit.setText("");
                mrastvoraEdit.setText("");
                procEdit.setText("");
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float mv = 0;
                float mrla = 0;
                float mrra = 0;
                float proc = 0;

                Boolean ok = true;
                float tmp = 0;


                mv = GetValue( mveshEdit );
                mrla = GetValue( mrastvoritelEdit );
                mrra = GetValue( mrastvoraEdit );
                proc = GetValue( procEdit );

                /*ok = true;

                try {
                    tmp = Float.parseFloat(String.valueOf(procEdit.getText()));
                }catch (Exception e ){
                    ok = false;
                }


                if(ok) proc = tmp;*/

                //---------------------------
                if( mv == 0 && mrla == 0 && mrra == 0 && proc == 0 ){
                    Toast toast = Toast.makeText(getApplicationContext(), "Недостаточно данных!" , Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if( mv == 0 ) CalcMv( mrla, mrra, proc );
                mv = GetValue( mveshEdit );
                mrla = GetValue( mrastvoritelEdit );
                mrra = GetValue( mrastvoraEdit );
                proc = GetValue( procEdit );
                if( mrla == 0 )CalcMrla( mv, mrra, proc );
                mv = GetValue( mveshEdit );
                mrla = GetValue( mrastvoritelEdit );
                mrra = GetValue( mrastvoraEdit );
                proc = GetValue( procEdit );
                if( mrra == 0 ) CalcMrra( mv, mrla, proc );
                mv = GetValue( mveshEdit );
                mrla = GetValue( mrastvoritelEdit );
                mrra = GetValue( mrastvoraEdit );
                proc = GetValue( procEdit );
                if( proc == 0 ) CalcProc( mv, mrra, mrla );
                /*mv = GetValue( mveshEdit );
                mrla = GetValue( mrastvoritelEdit );
                mrra = GetValue( mrastvoraEdit );
                proc = GetValue( procEdit );*/
            }
        });
    }

    protected float GetValue( EditText edit ){
        boolean ok = true;
        float value = 0;
        float tmp = 0;
        try {
            tmp = Float.parseFloat(String.valueOf(edit.getText()));//0;//mveshEdit.getText()  text().toFloat(&ok);

        }catch (Exception e){
            ok = false;
        }

        if(ok) value = tmp;
        return value;
    }

//---------------------------------------------------------------------------

    protected void CalcMv(float mrla,  float mrra, float proc ) {
        if( (1-proc ) == 0 ){
            mveshEdit.setText( "Думаю, думаю..." );
            return;
        }

        float mv = 0;
        if( mrla != 0 && mrra != 0 ){
            mv = mrra - mrla;
        }
        else if( mrla != 0 ){
            mv = (mrla * proc)/(1-proc);
        }else {
            mv = proc * mrra;
        }


        mveshEdit.setText(Float.toString(mv) ); //!!!!!!!!!!

    }
//----------------------------------------------------------------------------

    protected  void CalcMrla( float mv, float mrra, float proc ) {
        float mrla = 0;
        if( mrra == 0 && proc != 0 ){
            mrra = mv / proc;
        }
        mrla = mrra - mv;


        mrastvoritelEdit.setText(Float.toString( mrla) ); //!!!!!
    }
//-----------------------------------------------------------------------------

    protected  void CalcMrra( float mv, float mrla, float proc ) {
        float mrra = mv + mrla;
        mrastvoraEdit.setText(Float.toString( mrra) ); //!!!!!!
    }
//------------------------------------------------------------------------------


    protected void CalcProc( float mv, float mrra, float mrla ) {
        if ((mv + mrla) == 0) {
            procEdit.setText("графиня изменившимся лицом бежит пруду");
        } else {
            float proc = mv / (mv + mrla);
            procEdit.setText(Float.toString(  proc )); //!!!!!!!!!
        }

    }
//------------------------------------------------------------------------------



    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}