
package com.example.kissanseva;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.CropViewHolder> {

    private List<Crop> crops = new ArrayList<>();

    @NonNull
    @Override
    public CropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crop, parent, false);
        return new CropViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CropViewHolder holder, int position) {
        // Bind data to the item view
        Crop crop = crops.get(position);
        holder.bind(crop);
    }

    @Override
    public int getItemCount() {
        return crops.size();
    }

    public void setCrops(List<Crop> crops) {
        this.crops = crops;
        notifyDataSetChanged();
    }

    class CropViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView, quantityTextView, dateTextView, priceTextView;

        public CropViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
        }

        public void bind(Crop crop) {
            nameTextView.setText(crop.getCropName());
            quantityTextView.setText(String.valueOf(crop.getCropQuantity()));
            dateTextView.setText(crop.getCropDate());
            priceTextView.setText(String.valueOf(crop.getCropPrice()));
        }
    }
}
