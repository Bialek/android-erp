package com.hellohasan.sqlite_project.Features.ProductCRUD.UpdateProductInfo;


import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.Features.ProductCRUD.CreateProduct.Product;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;

import java.text.DecimalFormat;

public class ProductUpdateDialogFragment extends DialogFragment {

    private EditText productNameEditText;
    private EditText productCodeEditText;
    private EditText productAvailabilityEditText;
    private EditText productPriceEditText;
    private Button updateButton;
    private Button cancelButton;

    private static ProductUpdateListener productUpdateListener;
    private static long productId;
    private static int position;
    private boolean isAllFieldsChecked;

    private DatabaseQueryClass databaseQueryClass;

    public ProductUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static ProductUpdateDialogFragment newInstance(long prodId, int pos, ProductUpdateListener listener){
        productId = prodId;
        position = pos;
        productUpdateListener = listener;

        ProductUpdateDialogFragment productUpdateDialogFragment = new ProductUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update product information");
        productUpdateDialogFragment.setArguments(args);

        productUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return productUpdateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_update_dialog, container, false);

        productNameEditText = view.findViewById(R.id.productNameEditText);
        productCodeEditText = view.findViewById(R.id.productCodeEditText);
        productAvailabilityEditText = view.findViewById(R.id.productAvailablitiEditText);
        productPriceEditText = view.findViewById(R.id.productPriceEditText);
        updateButton = view.findViewById(R.id.updateButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        Product product = databaseQueryClass.getProductById(productId);

        productNameEditText.setText(product.getName());
        productCodeEditText.setText(String.valueOf(product.getCode()));
        productAvailabilityEditText.setText(String.valueOf(product.getAvailability()));
        productPriceEditText.setText(String.valueOf(product.getPrice()));

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();

                if (isAllFieldsChecked) {
                    String productName = productNameEditText.getText().toString();
                    int productCode = Integer.parseInt(productCodeEditText.getText().toString());
                    int productAvailability = Integer.parseInt(productAvailabilityEditText.getText().toString());
                    double productPrice = Double.parseDouble(productPriceEditText.getText().toString());

                    Product product = new Product(productId, productName, productCode, productAvailability, productPrice);

                    long rowCount = databaseQueryClass.updateProductInfo(product);

                    if (rowCount > 0) {
                        productUpdateListener.onProductInfoUpdate(product, position);
                        getDialog().dismiss();
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    private boolean CheckAllFields() {
        if (productNameEditText.length() == 0) {
            productNameEditText.setError("This field is required");
            return false;
        }

        if (productCodeEditText.length() == 0) {
            productCodeEditText.setError("This field is required");
            return false;
        }

        if (productAvailabilityEditText.length() == 0) {
            productAvailabilityEditText.setError("This field is required");
            return false;
        }

        if (productPriceEditText.length() == 0) {
            productPriceEditText.setError("This field is required");
            return false;
        }

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }

}
