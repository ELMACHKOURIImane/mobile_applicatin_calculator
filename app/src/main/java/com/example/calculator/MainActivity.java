package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> operations = new ArrayList<String>();
    String lastValue = ""  ;
    String contenu ;
    TextView result , operation ;
    MaterialButton buttonc , Historique  ;
    MaterialButton div , mul , add , equal , moins ;
    MaterialButton btn0 , btn1 , btn2 , btn3 , btn4 , btn5 , btn6 , btn7 , btn8 , btn9 ;
    MaterialButton buttonAc , buttondot  , button_open_bracket , button_close_bracket , button_ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        operation = findViewById(R.id.operation);
        assignId(buttonc , R.id.boutton_c);
        assignId(Historique , R.id.historique);
        assignId(div , R.id.boutton_div);
        assignId(mul , R.id.boutton_mul);
        assignId(add , R.id.boutton_add);
        assignId(equal , R.id.boutton_equal);
        assignId(moins , R.id.boutton_moins);
        assignId(btn0 , R.id.boutton_0);
        assignId(btn1 , R.id.boutton_1);
        assignId(btn2 , R.id.boutton_2);
        assignId(btn3 , R.id.boutton_3);
        assignId(btn4 , R.id.boutton_4);
        assignId(btn5 , R.id.boutton_5);
        assignId(btn6 , R.id.boutton_6);
        assignId(btn7 , R.id.boutton_7);
        assignId(btn8 , R.id.boutton_8);
        assignId(btn9 , R.id.boutton_9);
        assignId(buttonAc , R.id.boutton_ac);
        assignId(buttondot, R.id.boutton_dot);
        assignId(button_close_bracket , R.id.btn_close_bracket);
        assignId(button_open_bracket , R.id.btn_open_bracket);
        assignId(button_ans , R.id.btn_ans);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            contenu = bundle.getString("operation");
            if(contenu != null){
                operation.setText(contenu);
            }
        }


    }

    void assignId(MaterialButton btn , int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        MaterialButton button = (MaterialButton) v ;

        String buttonText = button.getText().toString();

        String dataTocalculate = operation.getText().toString();

        if(buttonText.equals("ANS")){
            operation.setText(lastValue);
            result.setText(lastValue);
            return;
        }
        if(buttonText.equals("historique")){
            operation.setText("0");
            result.setText("0");
            Intent intent = new Intent(MainActivity.this,historyActivity.class);
            intent.putExtra("operations" , operations);
            startActivity(intent);
            return;
        }

        if(buttonText.equals(("D"))){
            operation.setText("");
            result.setText("0");
            return;
        }
       if(buttonText.equals("=")){
           operations.add(operation.getText().toString());
           int i = 0 ;
           while (operations.size()>10){
               operations.remove(i);
               i++;
           }
           lastValue = result.getText().toString();
           operation.setText(result.getText());
           return;
       }
       if(buttonText.equals("C")){
           dataTocalculate = dataTocalculate.substring(0,dataTocalculate.length()-1);

       }else{
           dataTocalculate = dataTocalculate + buttonText ;
       }

        operation.setText(dataTocalculate);
       String finalResult = getResult(dataTocalculate);

       if(finalResult.equals("ERROR")){
           Toast.makeText(MainActivity.this,"Erreur", Toast.LENGTH_SHORT).show();
       }

       if(!finalResult.equals("Err") && !finalResult.equals("ERROR")){
           result.setText(finalResult);
       }
    }

    String getResult(String data){
      try {
          Context context = Context.enter();
          context.setOptimizationLevel(-1);
          Scriptable scriptable = context.initSafeStandardObjects();
         String finalResult =  context.evaluateString(scriptable , data , "javascript" , 1 , null).toString();
         if(finalResult.equals("Infinity")){
             return  "ERROR";
         }
         return  finalResult ;
      }catch (Exception e){
          return "Err";
      }
    }
}