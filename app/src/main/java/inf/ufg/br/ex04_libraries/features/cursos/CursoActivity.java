package inf.ufg.br.ex04_libraries.features.cursos;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import inf.ufg.br.ex04_libraries.R;
import inf.ufg.br.ex04_libraries.model.Curso;

import java.util.List;

/**
 * Created by Marla Aragão on 01/07/2015.
 */
public class CursoActivity extends FragmentActivity implements ConsultCurso.ConsultCursoSituation {

    private Spinner spinner_cursos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ConsultCurso(this).execute();
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner(List<Curso> cursos) {

        spinner_cursos = (Spinner) findViewById(R.id.cursos);

        ArrayAdapter<Curso> dataAdapter = new ArrayAdapter<Curso>(this,
                android.R.layout.simple_spinner_item, cursos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cursos.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {

    }

    @Override
    public void onConcludeConsultCurso(List<Curso> cursos) {
        addItemsOnSpinner(cursos);
    }
}

