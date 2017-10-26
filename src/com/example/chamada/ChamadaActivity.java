package com.example.chamada;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import br.com.chamada.bll.AlunoBLL;
import br.com.chamada.bll.ChamadaBLL;
import br.com.chamada.bll.TurmaBLL;
import br.com.chamada.model.Aluno;
import br.com.chamada.model.Chamada;
import br.com.chamada.util.Global;
import br.com.chamada.model.Turma;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
//import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ChamadaActivity extends Activity implements OnTouchListener, OnDragListener{
	Drawable enterShape;
	Drawable normalShape;
	int i = 0;
	int turmaAtual;
	String dataAtual;
	ArrayList<Chamada> chamadaList = new ArrayList<Chamada>();
	ArrayList<Aluno> alunoList = new ArrayList<Aluno>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chamada);		
		
		AlunoBLL alunobll = new AlunoBLL(this);
		TurmaBLL turmabll = new TurmaBLL(this);	
		
		Turma turm = new Turma();
		Intent thisIntent= getIntent();
		int turma = thisIntent.getIntExtra("turma", 0);
		//data
		Date hoje = new Date(); 
		SimpleDateFormat df; 
		df = new SimpleDateFormat("dd/MM/yyyy"); 
		((TextView) findViewById(R.id.txvData)).setText(df.format(hoje));
		//pega turma
		
		turm = turmabll.recuperarUm(turma);
		
		turmaAtual = turm.getPk_Turma();
		//chamada.setFk_Turma(turm.getPk_Turma());
		dataAtual = df.format(hoje);
		
		this.setTitle(turm.getNome());
		//pega alunos
		Global aluGlobal = (Global)getApplication();
		alunoList = alunobll.recuperarTurma(turma);
		
		enterShape = getResources().getDrawable(R.drawable.bg_over);
		normalShape = getResources().getDrawable(R.drawable.bg);

		ImageView i1 = (ImageView)findViewById(R.id.myimage1);	
		i1.setOnTouchListener(this);
		i1.setImageResource(R.drawable.picalu);
		//Global chaGlobal = (Global)getApplication();
		Button btnSalv = (Button)findViewById(R.id.btnSalvar);
		TextView txtnome = (TextView)findViewById(R.id.txvNomes);
		Aluno alu = new Aluno();
		if(alunoList.size() > 0){
			 alu = alunoList.get(i);
			 txtnome.setText(alu.getNome());	
			 aluGlobal.setAluGlob(alu);
			 //v.setContentDescription(String.valueOf((alu.getPk_Aluno())));
		}else{
			i1.setVisibility(0);
			txtnome.setText("N�o h� alunos cadastrados");
			i1.setEnabled(false);
			btnSalv.setEnabled(false);
		}		
		
		findViewById(R.id.topleft).setOnDragListener(this);
		findViewById(R.id.topright).setOnDragListener(this);
		findViewById(R.id.tabcentral).setOnDragListener(this);	
	}
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public boolean onDrag(View v, DragEvent event) {
				
		Aluno alu = new Aluno();
		Global aluGlobal = (Global)getApplication();
		alu = aluGlobal.getAluGlob();
		v.setContentDescription(String.valueOf((alu.getPk_Aluno())));
		
		switch (event.getAction()) {
		case DragEvent.ACTION_DRAG_ENTERED:
			// Ao entrar na �rea que pode fazer o drop
			v.setBackgroundDrawable(enterShape);
			break;
		case DragEvent.ACTION_DRAG_EXITED:
			// Ao sair da �rea que pode fazer o drop
			v.setBackgroundDrawable(normalShape);			
			break;
			
		case DragEvent.ACTION_DROP:
			int pkAluno ;//= Integer.parseInt(v.getContentDescription().toString());
			// Ao fazer o drop
			switch(v.getId()) {
			   case (R.id.topleft) :
				  Chamada chamadaL = new Chamada();
				  //chamadaL = chamada;
				  pkAluno = Integer.parseInt(v.getContentDescription().toString()); 
				  chamadaL.setData(dataAtual);
				  chamadaL.setFk_Turma(turmaAtual);
				  chamadaL.setFk_Aluno(pkAluno);
				  chamadaL.setPresenca(1);
				  chamadaList.add(chamadaL);
				  i++;
				  chamaProx(v);				 
			      break;
			   case (R.id.topright) :
				  Chamada chamadaR = new Chamada();
				  //chamadaR = chamada;
				  pkAluno = Integer.parseInt(v.getContentDescription().toString()); 
				  chamadaR.setData(dataAtual);
				  chamadaR.setFk_Turma(turmaAtual);
				  chamadaR.setFk_Aluno(pkAluno);
				  chamadaR.setPresenca(0);
				  chamadaList.add(chamadaR);
				  i++;
				  chamaProx(v);				  
			      break;
			   default :
				   v.setVisibility(View.VISIBLE);
			      break;
			   }
			
			View view = (View) event.getLocalState();
			ViewGroup owner = (ViewGroup) view.getParent();
			owner.removeView(view);
			LinearLayout container = (LinearLayout) v;
			container.addView(view);
			view.setVisibility(View.VISIBLE);		 
			
			//setar presen�a,ausencia,ou colocar na array pres aus, chamar o prox
			
			break;
		case DragEvent.ACTION_DRAG_ENDED:
			// Ao terminar de arrastar
			v.setBackgroundDrawable(normalShape);
		//	View view2 = (View) event.getLocalState();
			//view2.setVisibility(View.INVISIBLE);
		default:
			break;
		}
		return true;
	}

	@SuppressLint("NewApi")
	public boolean onTouch(View view, MotionEvent me) {		
		int action = me.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
				view.startDrag(data, shadowBuilder, view, 0);
				view.setVisibility(View.INVISIBLE);			
				return true;
			}
		return false;
	}
	
	public void salvaChamada(View view){
		Chamada chamadaS = new Chamada();
		ChamadaBLL chamBLL = new ChamadaBLL(this);
			for(int j = 0; j < chamadaList.size(); j++){
				chamadaS = chamadaList.get(j);
				chamBLL.gravar(chamadaS);
			}		
		irParaRelatorio(view);
	}
	
	public void chamaProx(View v){
		
		Global aluGlobal = (Global)getApplication();
		Aluno alu = new Aluno();
		try{
			alu = alunoList.get(i);			
			v.setContentDescription(String.valueOf((alu.getPk_Aluno())));			
			TextView txtnome = (TextView)findViewById(R.id.txvNomes);
			ImageView i1 = (ImageView)findViewById(R.id.myimage1);	
			i1.setOnTouchListener(this);
			i1.setImageResource(R.drawable.picalu);			
			txtnome.setText(alu.getNome());	
			aluGlobal.setAluGlob(alu);
			
		}catch(Exception e){
			Button btnSalv = (Button)findViewById(R.id.btnSalvar);
			TextView txtnome = (TextView)findViewById(R.id.txvNomes);
			ImageView i1 = (ImageView)findViewById(R.id.myimage1);	
			i1.setVisibility(0);
			txtnome.setText("Fim da Chamada!!!");
			i1.setEnabled(false);			
			btnSalv.setTextColor(Color.MAGENTA );
			btnSalv.setEnabled(true);
		}		
	}
	
	public void irParaMain(View view){
		Intent intent = new Intent(ChamadaActivity.this, MainActivity.class);
		intent.putExtra("turma", "Olah");		       
	    startActivity(intent);
	}	
	
	public void irParaRelatorio(View view){
		Intent intent = new Intent(ChamadaActivity.this, RelatorioActivity.class);
		intent.putExtra("data", dataAtual);	
		intent.putExtra("turma", turmaAtual);
	    startActivity(intent);
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chamada, menu);
		return true;
	}

}
