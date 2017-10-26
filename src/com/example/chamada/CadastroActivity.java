package com.example.chamada;



import br.com.chamada.model.Turma;
import br.com.chamada.bll.TurmaBLL;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class CadastroActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);
		//Intent thisIntent= getIntent();
	}
	
	public void cadTurma(View view){
		TurmaBLL turmabll = new TurmaBLL(this);
		Turma turma = new Turma();		
		EditText edtNomeTurma = (EditText) findViewById(R.id.edtNomeTurma);
		turma.setNome(edtNomeTurma.getText().toString());
		turmabll.gravar(turma);
		//alerta aki
		this.irParaMain(view);
	}
	public void irParaMain(View view){
		Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
		intent.putExtra("turma", "Olah");		       
	    startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastro, menu);
		return true;
	}

}
