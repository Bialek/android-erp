package com.hellohasan.sqlite_project.Features.InvoiceCRUD.ShowInvoiceList;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellohasan.sqlite_project.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView nameTextView;
    TextView createdDateTextView;
    TextView phoneTextView;
    TextView addressTextView;
    TextView amountTextView;
    ImageView crossButtonImageView;
    ImageView editButtonImageView;

    public CustomViewHolder(View itemView) {
        super(itemView);

        nameTextView = itemView.findViewById(R.id.nameTextView);
        createdDateTextView = itemView.findViewById(R.id.createdDateTextView);
        phoneTextView = itemView.findViewById(R.id.phoneTextView);
        addressTextView = itemView.findViewById(R.id.addressTextView);
        amountTextView = itemView.findViewById(R.id.amountTextView);
        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);
    }
}
