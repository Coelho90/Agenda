package br.com.alura.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by Coelho on 20/10/2017.
 */

public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {

    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    protected String doInBackground(Void... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();
        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);


        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();

        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde", "Enviando alunos...", true, true);
    }
}
