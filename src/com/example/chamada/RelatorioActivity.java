package com.example.chamada;

import java.util.ArrayList;

import br.com.chamada.bll.AlunoBLL;
import br.com.chamada.bll.ChamadaBLL;
import br.com.chamada.bll.TurmaBLL;
import br.com.chamada.model.Aluno;
import br.com.chamada.model.Chamada;
import br.com.chamada.model.Turma;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
//import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.Spinner;
import android.widget.TextView;

public class RelatorioActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_relatorio);
		Turma turm = new Turma();
		TurmaBLL turmBLL = new TurmaBLL(this);
		Aluno aluno = new Aluno();
		AlunoBLL aluBLL = new AlunoBLL(this);		
		
		Intent thisIntent= getIntent();
		int turma = thisIntent.getIntExtra("turma", 0);
		turm = turmBLL.recuperarUm(turma);
		
		String data = thisIntent.getStringExtra("data");
		((TextView) findViewById(R.id.txvDataRel)).setText(data);
		((TextView) findViewById(R.id.txvTurmaRel)).setText(turm.getNome());
		
		final ArrayList<String> rAluno = new ArrayList<String>();	
		//final ArrayList<String> rPresenca = new ArrayList<String>();	
		
		ChamadaBLL chamadals = new ChamadaBLL(this);	
		ArrayList<Chamada> ChamadaList = new ArrayList<Chamada>();	
		//ArrayList<Chamada> ChamadaListFilt = new ArrayList<Chamada>();	
		Chamada chamada = new Chamada();
				
			try{
				ChamadaList = chamadals.recuperarTodos();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			ListView lista = (ListView) findViewById(R.id.listVrelatorio);
	        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rAluno);		
			adp.setDropDownViewResource(android.R.layout.simple_list_item_1);
	        lista.setAdapter(adp);
			if(ChamadaList.size() > 0){
				String concat = new String();
				for (int i = 0; i < ChamadaList.size(); i++){
					chamada = ChamadaList.get(i);
					if((chamada.getFk_Turma() == turma) && chamada.getData().equals(data)){
						aluno = aluBLL.recuperarUm(chamada.getFk_Aluno());	
						concat = aluno.getNome();
						concat += " -> ";						
						
						if(chamada.getPresenca()== 1){
							//rPresenca.add("Presente");
							concat += "Presente";
							rAluno.add(concat);
						}else{
							concat += "Ausente";
							rAluno.add(concat);
						}						
						//ChamadaListFilt.add(chamada);
					}					
				}			
			}		
	        //combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.relatorio, menu);
		return true;
	}
	
	public void irParaMain(View view){
		Intent intent = new Intent(RelatorioActivity.this, MainActivity.class);
		//intent.putExtra("turma", "Olah");		       
	    startActivity(intent);
	}	

}
