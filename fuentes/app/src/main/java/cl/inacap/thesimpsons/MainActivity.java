package cl.inacap.thesimpsons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.thesimpsons.adapters.PersonajesAdapter;
import cl.inacap.thesimpsons.dto.Personaje;

public class MainActivity extends AppCompatActivity {

    private List<Personaje> consejos = new ArrayList<>();
    private ListView consejosList;
    private PersonajesAdapter personajesAdapter;
    private RequestQueue queue;
    private Spinner cantidadCitas;
    private Button pedirCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.cantidadCitas = findViewById(R.id.spinner);
        this.pedirCitas = findViewById(R.id.btn);

        String [] numeros = {"1","2","3","4","5","6","7","8","9","10"};
        this.cantidadCitas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,numeros));

        this.pedirCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cantidad = cantidadCitas.getSelectedItem().toString();
                sendRequest(cantidad);

            }
        });


    }

    public void sendRequest(String num){
        queue = Volley.newRequestQueue(this);
        this.consejosList = findViewById(R.id.citapj);
        this.personajesAdapter = new PersonajesAdapter(this, R.layout.personajes_list,this.consejos);
        this.consejosList.setAdapter(this.personajesAdapter);
        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET, "https://thesimpsonsquoteapi.glitch.me/quotes?count="+num
                , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    consejos.clear();
                    for(int i=0; i<response.length(); i++){
                        JSONObject consultaApi = response.getJSONObject(i);

                        Personaje c = new Personaje();
                        c.setCharacter(consultaApi.getString("character"));
                        c.setQuote(consultaApi.getString("quote"));
                        c.setImage(consultaApi.getString("image"));
                        consejos.add(c);

                    }
                    personajesAdapter.notifyDataSetChanged();
                }catch (Exception ex){
                    consejos.clear();
                }finally {
                    personajesAdapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MAIN_ACTIVITY","F en el chat");
                personajesAdapter.notifyDataSetChanged();
            }
        });
        queue.add(jsonReq);
    }


}