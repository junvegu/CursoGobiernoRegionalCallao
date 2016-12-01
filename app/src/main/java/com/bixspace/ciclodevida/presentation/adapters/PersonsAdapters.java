package com.bixspace.ciclodevida.presentation.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bixspace.ciclodevida.R;
import com.bixspace.ciclodevida.data.PersonaEntity;

import java.util.ArrayList;


public class PersonsAdapters extends RecyclerView.Adapter<PersonsAdapters.ViewHolder> {

    private ArrayList<PersonaEntity> contactModelResponses;
    private Context context;

    public PersonsAdapters(Context context, ArrayList<PersonaEntity> contactModelResponses
    ) {
        this.contactModelResponses = contactModelResponses;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person,
                parent, false);
        return new ViewHolder(row);
    }


    //Esta método se ejecuta por cada item que tenga el array
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PersonaEntity model = contactModelResponses.get(position);
        holder.tvName.setText(model.getNombres() + " " + model.getApellidos());
        holder.tvAge.setText(model.getEdad() + "");


        /**
         * Aqui deberia existir algun algoritmo que permita transformar cadena a imagen
         */

        if (model.getFoto() != null) {
            byte[] decodedString = Base64.decode(model.getFoto().getBytes(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.imageView.setImageBitmap(decodedByte);



        }


        // holder.imageView.setImageBitmap();


    }

    @Override
    public int getItemCount() {
        return contactModelResponses.size();
    }


    //El patron view holder permite inicializar los objetos traidos desde la vista del item (item_person)
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvAge;
        TextView tvName;

        public ViewHolder(View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.photo);
            tvAge = (TextView) view.findViewById(R.id.tv_age);
            tvName = (TextView) view.findViewById(R.id.tv_name);
        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}