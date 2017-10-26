package com.example.chamada;

import java.util.ArrayList;


import br.com.chamada.bll.AlunoBLL;
import br.com.chamada.bll.TurmaBLL;
import br.com.chamada.model.Aluno;
import br.com.chamada.model.Turma;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class CadAlunoActivity extends Activity {
	final ArrayList<String> TURMASBD = new ArrayList<String>();	
	int turmaAtual;
	TurmaBLL turmals = new TurmaBLL(this);	
	ArrayList<Turma> TurmaList = new ArrayList<Turma>();	
	Turma turma1 = new Turma();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cad_aluno);
		
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
			
		Spinner combo = (Spinner) findViewById(R.id.spnTurmas);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TURMASBD);		
		adp.setDropDownViewResource(android.R.layout.simple_spinner_item);
        combo.setAdapter(adp);
        combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {             	
            	if (i >= 0){
        			Button btnSalvarAluno = (Button)findViewById(R.id.btnSalvarAluno);
        			btnSalvarAluno.setEnabled(true);
        			turmaAtual = i+1;
                return ;                
            	}
            }
                public void onNothingSelected(AdapterView<?> adapterView) {
                    return;
                } 
            });         
	}
	public void cadAluno(View view){
		AlunoBLL alunobll = new AlunoBLL(this);
		Aluno aluno = new Aluno();	
		RadioGroup rg = (RadioGroup) findViewById(R.id.rgopcoes);
		int op = rg.getCheckedRadioButtonId();
		EditText edtNomeAlu = (EditText) findViewById(R.id.edtNomeAlu);
		aluno.setNome(edtNomeAlu.getText().toString());
		aluno.setFk_Turma(turmaAtual);
		switch (op) {
			case R.id.rdbMasc:
				aluno.setSexo("M");
				break;
			case R.id.rdbFem:
				aluno.setSexo("F");
				break;			
		}
		
		alunobll.gravar(aluno);
		//alerta aki
		//this.irParaMain(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cad_aluno, menu);
		return true;
	}

}
