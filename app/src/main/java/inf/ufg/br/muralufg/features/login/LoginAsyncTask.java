package inf.ufg.br.muralufg.features.login;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import inf.ufg.br.muralufg.model.User;


/**
 * Created by Marla Aragao.
 */
public class LoginAsyncTask extends AsyncTask<User, Void, String>{

    private ConsultLogin listenerSituation;
    private User user;
    private final static String CONNECTION_URL = "https://dl.dropboxusercontent.com/s/faecpt800whqwo8/login.json?dl=0";
    private int responseCode;


    public LoginAsyncTask(ConsultLogin listenerSituation) {
        this.listenerSituation = listenerSituation;
    }

    @Override
    protected String doInBackground(User... params) {

        user = params[0];
        InputStream is = null;

        try {

            URL url = new URL(CONNECTION_URL);
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

                JSONArray cursosArray  = reader.getJSONArray("users");

                for (int i = 0; i < cursosArray.length(); i++) {

                    User w = new User();
                    w.setName(((JSONObject) cursosArray.get(i)).getString("usuario"));
                    w.setPassword(((JSONObject) cursosArray.get(i)).getString("senha"));
                    w.setCurso(((JSONObject) cursosArray.get(i)).getString("curso"));

                    if (user.getName().equals(w.getName()) && user.getPassword().equals(w.getPassword())) {
                        this.responseCode = 200;
                        return w.getCurso();
                    }

                }

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

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    @Override
    protected void onPostExecute(String curso) {
        listenerSituation.onConcludeConsultLogin(curso);
    }

    public interface ConsultLogin {
        void onConcludeConsultLogin(String curso);
    }
}
