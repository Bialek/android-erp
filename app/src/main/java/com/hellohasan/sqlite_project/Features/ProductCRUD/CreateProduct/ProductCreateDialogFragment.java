package com.hellohasan.sqlite_project.Features.ProductCRUD.CreateProduct;


import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.R;

import java.text.DecimalFormat;


public class ProductCreateDialogFragment extends DialogFragment {

    private static ProductCreateListener productCreateListener;

    private EditText productNameEditText;
    private EditText productCodeEditText;
    private EditText productAvailabilityEditText;
    private EditText productPriceEditText;
    private Button createButton;
    private Button cancelButton;
    private Boolean isAllFieldsChecked;

    public ProductCreateDialogFragment() {
        // Required empty public constructor
    }

    public static ProductCreateDialogFragment newInstance(ProductCreateListener listener){
        productCreateListener = listener;

        ProductCreateDialogFragment productCreateDialogFragment = new ProductCreateDialogFragment();

        productCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return productCreateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_create_dialog, container, false);
        getDialog().setTitle(getResources().getString(R.string.add_new_product));

        productNameEditText = view.findViewById(R.id.productNameEditText);
        productCodeEditText = view.findViewById(R.id.productCodeEditText);
        productAvailabilityEditText = view.findViewById(R.id.productAvailablitiEditText);
        productPriceEditText = view.findViewById(R.id.productPriceEditText);

        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();

                if (isAllFieldsChecked) {
                    String productName = productNameEditText.getText().toString();
                    int productCode = Integer.parseInt(productCodeEditText.getText().toString());
                    int productAvailability = Integer.parseInt(productAvailabilityEditText.getText().toString());
                    double productPrice = Double.parseDouble(productPriceEditText.getText().toString());

                    Product product = new Product(-1, productName, productCode, productAvailability, productPrice);

                    DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

                    long id = databaseQueryClass.insertProduct(product);

                    if (id > 0) {
                        product.setId(id);
                        productCreateListener.onProductCreated(product);
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
