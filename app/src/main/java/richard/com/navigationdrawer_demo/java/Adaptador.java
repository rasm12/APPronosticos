package richard.com.navigationdrawer_demo.java;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import richard.com.navigationdrawer_demo.R;

/**
 * Created by Richard on 26/11/2017.
 */

public class Adaptador extends BaseAdapter {

    private List<Entidad> listItem;
    private Context context;

    public Adaptador(List<Entidad> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @Override
    public int getCount() {
        //indica al list view cuantos datos se van a cargar
        return listItem.size() ;
    }

    @Override
    public Object getItem(int i) {
        //devuelve de listitem el parametros que recibe el metdo
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        //se ejecuta tantas veces como se indique en el metodo getCount()
        Entidad item = (Entidad)getItem(i);

        convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_row,null);
        ImageView imgFoto = convertView.findViewById(R.id.imgFoto);
        TextView titulo = convertView.findViewById(R.id.tvTitulo);
        TextView contenido = convertView.findViewById(R.id.tvResultado);

        imgFoto.setImageResource(R.drawable.usuario);
        titulo.setText(item.getNombre());
        contenido.setText(item.getTotal().toString());

        return convertView;
    }
}
