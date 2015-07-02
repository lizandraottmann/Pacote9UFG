package inf.ufg.br.ex04_libraries.features.cursos;


import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import inf.ufg.br.ex04_libraries.model.Curso;

/**
 * Created by Marla Aragão.
 */

public class ConsultCurso extends AsyncTask<Void, Void, List<Curso>> {

    private ConsultCursoSituation listenerSituation;
    private final String URL_CONNECTION = "https://dl.dropboxusercontent.com/s/mologtlfcosag0n/oportunidades.json?dl=0"; //"https://dl.dropboxusercontent.com/s/ahjbm4a1qlhg0z9/oportunidades.json";
    private List<Curso> cursos= new ArrayList<>();

    public ConsultCurso(ConsultCursoSituation listenerSituation) {
        this.listenerSituation = listenerSituation;
    }

    @Override
    protected List<Curso> doInBackground(Void... params) {

        this.cursos = consultServer();

        return this.cursos;
    }

    private List<Curso> consultServer() {

        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {

            URL url = new URL(URL_CONNECTION);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            InputStream stream = conn.getInputStream();

            java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
            String data = s.hasNext() ? s.next() : "";

            try {
                JSONObject reader = new JSONObject(data);

                JSONArray information  = reader.getJSONArray("cursos");

                List<Curso> cursos = new ArrayList<>();

                for (int i = 0; i < information.length(); i++) {

                    Curso w = new Curso();
                    w.setId(((JSONObject)information.get(i)).getInt("id"));
                    w.setNome(((JSONObject)information.get(i)).getString("nome"));

                    cursos.add(w);
                }

                return cursos;

            } catch (JSONException e) {
                e.printStackTrace();
            }


            stream.close();
        }catch (IOException e){
            return null;
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    @Override
    protected void onPostExecute(List<Curso> cursos) {
        listenerSituation.onConcludeConsultCurso(cursos);
    }

    public interface ConsultCursoSituation {
        void onConcludeConsultCurso(List<Curso> cursos);
    }
}