package com.shata.helpmyapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shata.helpmyapp.Model.ModelDisease;
import com.shata.helpmyapp.Model.ModelTitleDisease;
import com.shata.helpmyapp.R;

import java.util.List;

public class AdapterDiseases extends ArrayAdapter<ModelTitleDisease> {

    private Context context;
    private int resourceID;
    private List<ModelTitleDisease> modelDiseaseList;


    public AdapterDiseases(@NonNull Context context, int resource, @NonNull List<ModelTitleDisease> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resourceID = resource;
        this.modelDiseaseList = objects;
    }

    @Override
    public int getCount() {
        return modelDiseaseList.size();
    }

    @Override
    public ModelTitleDisease getItem(int position) {
        return modelDiseaseList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(resourceID, parent, false);

                ModelTitleDisease disease = modelDiseaseList.get(position);
                if (disease != null) {
                    TextView title = view.findViewById(R.id.titleTVContainer);
                    title.setText(disease.getTitle());
                    ImageView imageView = view.findViewById(R.id.imagedown);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {

        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(resourceID, parent, false);

                ModelTitleDisease disease = modelDiseaseList.get(position);
                if (disease != null) {
                    TextView title = view.findViewById(R.id.titleTVContainer);
                    title.setText(disease.getTitle());
                    ImageView imageView = view.findViewById(R.id.imagedown);
                    imageView.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {

        }
        return view;
    }
}
