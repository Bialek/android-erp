package com.hellohasan.sqlite_project.Features.ProductCRUD.ShowProductList;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellohasan.sqlite_project.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView productNameTextView;
    TextView productCodeTextView;
    TextView productAvailabilityTextView;
    TextView productPriceTextView;
    ImageView crossButtonImageView;
    ImageView editButtonImageView;

    public CustomViewHolder(View itemView) {
        super(itemView);

        productNameTextView = itemView.findViewById(R.id.productNameTextView);
        productCodeTextView = itemView.findViewById(R.id.productCodeTextView);
        productAvailabilityTextView = itemView.findViewById(R.id.productAvailabilityTextView);
        productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);
    }
}
