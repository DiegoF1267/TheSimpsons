package cl.inacap.thesimpsons.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import cl.inacap.thesimpsons.R;
import cl.inacap.thesimpsons.dto.Personaje;

public class PersonajesAdapter extends ArrayAdapter<Personaje> {
    private List<Personaje> personajes;
    private Activity activity;

    public PersonajesAdapter(@NonNull Activity context, int resource, @NonNull List<Personaje> objects) {
        super(context, resource, objects);
        this.personajes= objects;
        this.activity=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.personajes_list,null,true);
        TextView personajeTxt = rowView.findViewById(R.id.personaje_pj);
        TextView citaTxt = rowView.findViewById(R.id.cita_pj);
        ImageView imagePj= rowView.findViewById(R.id.image_pj);
        personajeTxt.setText(personajes.get(position).getCharacter());
        citaTxt.setText(personajes.get(position).getQuote());
        Picasso.get().load(this.personajes.get(position).getImage()).resize(300,300).centerCrop().into(imagePj);
        return rowView;

    }


}
