package com.hellohasan.sqlite_project.Features.ProductCRUD.ShowProductList;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.Features.ProductCRUD.CreateProduct.Product;
import com.hellohasan.sqlite_project.Features.ProductCRUD.UpdateProductInfo.ProductUpdateDialogFragment;
import com.hellohasan.sqlite_project.Features.ProductCRUD.UpdateProductInfo.ProductUpdateListener;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;

import java.util.List;

public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductListRecyclerViewAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int listPosition = position;
        final Product product = productList.get(position);

        holder.productNameTextView.setText(product.getName());
        holder.productCodeTextView.setText(String.valueOf(product.getCode()));
        holder.productAvailabilityTextView.setText(String.valueOf(product.getAvailability()));
        holder.productPriceTextView.setText(String.valueOf(product.getPrice()));


        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this product?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteProduct(product);
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
                editProduct(product.getId(), listPosition);
            }
        });
    }

    private void editProduct(long productId, int listPosition){
        ProductUpdateDialogFragment productUpdateDialogFragment = ProductUpdateDialogFragment.newInstance(productId, listPosition, new ProductUpdateListener() {
            @Override
            public void onProductInfoUpdate(Product product, int position) {
                productList.set(position, product);
                notifyDataSetChanged();
            }
        });
        productUpdateDialogFragment.show(((ProductListActivity) context).getSupportFragmentManager(), Config.UPDATE_PRODUCT);
    }

    private void deleteProduct(Product product) {
        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(context);
        boolean isDeleted = databaseQueryClass.deleteProductById(product.getId());

        if(isDeleted) {
            productList.remove(product);
            notifyDataSetChanged();
            ((ProductListActivity) context).viewVisibility();
        } else
            Toast.makeText(context, "Cannot delete!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
