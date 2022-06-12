package com.hellohasan.sqlite_project.Features.InvoiceCRUD.ShowInvoiceList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.Features.InvoiceCRUD.CreateInvoice.Invoice;
import com.hellohasan.sqlite_project.Features.InvoiceCRUD.UpdateInvoiceInfo.InvoiceUpdateDialogFragment;
import com.hellohasan.sqlite_project.Features.InvoiceCRUD.UpdateInvoiceInfo.InvoiceUpdateListener;
import com.hellohasan.sqlite_project.Features.ProductCRUD.ShowProductList.ProductListActivity;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class InvoiceListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Invoice> invoiceList;
    private DatabaseQueryClass databaseQueryClass;

    public InvoiceListRecyclerViewAdapter(Context context, List<Invoice> invoiceList) {
        this.context = context;
        this.invoiceList = invoiceList;
        databaseQueryClass = new DatabaseQueryClass(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_invoice, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Invoice invoice = invoiceList.get(position);

        holder.nameTextView.setText(invoice.getName());
        holder.createdDateTextView.setText(String.valueOf(invoice.getCreatedDate()));
        holder.phoneTextView.setText(invoice.getPhoneNumber());
        holder.addressTextView.setText(invoice.getAddress());
        holder.amountTextView.setText(String.valueOf(invoice.getAmount()));

        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this invoice?");
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        deleteInvoice(itemPosition);
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InvoiceUpdateDialogFragment invoiceUpdateDialogFragment = InvoiceUpdateDialogFragment.newInstance(invoice.getId(), itemPosition, new InvoiceUpdateListener() {
                    @Override
                    public void onInvoiceInfoUpdated(Invoice invoice, int position) {
                        invoiceList.set(position, invoice);
                        notifyDataSetChanged();
                    }
                });
                invoiceUpdateDialogFragment.show(((InvoiceListActivity) context).getSupportFragmentManager(), Config.UPDATE_INVOICE);
            }
        });
    }

    private void deleteInvoice(int position) {
        Invoice invoice = invoiceList.get(position);
        long count = databaseQueryClass.deleteInvoice(invoice.getId());

        if(count>0){
            invoiceList.remove(position);
            notifyDataSetChanged();
            ((InvoiceListActivity) context).viewVisibility();
            Toast.makeText(context, "Invoice deleted successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Invoice not deleted. Something wrong!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }
}
