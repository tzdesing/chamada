package com.example.chamada;

//import android.annotation.SuppressLint;
import android.app.Activity;
//import android.content.ClipData;
import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import br.com.chamada.model.Aluno;
import br.com.chamada.model.Turma;
//import br.com.chamada.bll.AlunoBLL;
import br.com.chamada.bll.TurmaBLL;
import android.os.Bundle;
//import android.view.DragEvent;
import android.view.Menu;
//import android.view.MotionEvent;
import android.view.View;
//import android.view.View.DragShadowBuilder;
//import android.view.View.OnDragListener;
//import android.view.View.OnTouchListener;
//import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
//import android.widget.LinearLayout;
import android.widget.Spinner;
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;


public class MainActivity extends Activity  {
	final ArrayList<String> TURMASBD = new ArrayList<String>();	
	TurmaBLL turmals = new TurmaBLL(this);	
	ArrayList<Turma> TurmaList = new ArrayList<Turma>();	
	Turma turma1 = new Turma();
	int turmaAtual;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		try{
			TurmaList = turmals.recuperarTodos();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		if(TurmaList.size() > 0){
			for (int i = 0; i < TurmaList.size(); i++){
				turma1 = TurmaList.get(i);				
				TURMASBD.add(turma1.getNome());
			}			
		}else{
			String vazio = "Cadastre uma Turma...";
			TURMASBD.add(vazio);
		}
			
		Spinner combo = (Spinner) findViewById(R.id.spinTurmas);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TURMASBD);		
		adp.setDropDownViewResource(android.R.layout.simple_spinner_item);
        combo.setAdapter(adp);        		                
        combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {             	
            	if (i >= 0){
        			Button btnChamada = (Button)findViewById(R.id.btnChamada);
        			btnChamada.setEnabled(true);
        			turmaAtual = i +1;
                return ;
            	}
            }
                public void onNothingSelected(AdapterView<?> adapterView) {
                    return;
                } 
            });         
		
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void irParaChamada(View view){
		Intent intent = new Intent(MainActivity.this, ChamadaActivity.class);
		intent.putExtra("turma", turmaAtual);		       
	    startActivity(intent);
	}
	
	public void irParaCadAluno(View view){
		Intent intent = new Intent(MainActivity.this, CadAlunoActivity.class);
		intent.putExtra("turma", "Olah");		       
	    startActivity(intent);
	}
	
	public void irParaCadastro(View view){
		Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
		intent.putExtra("parametro1", "Olah");
		intent.putExtra("parametro2", " mundo");
		intent.putExtra("parametro3", "!!!");	       
	    startActivity(intent);
	}

}
 
