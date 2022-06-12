package com.hellohasan.sqlite_project.Features.InvoiceCRUD.CreateInvoice;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hellohasan.sqlite_project.Util.Config;
import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.R;

import java.util.Calendar;


public class InvoiceCreateDialogFragment extends DialogFragment {

    private static InvoiceCreateListener invoiceCreateListener;

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText addressEditText;
    private Button createButton;
    private Button cancelButton;

    private String nameString = "";
    private String phoneString = "";
    private String addressString = "";
    private String createdDateString = "";
    private Double amountDouble = 0.0;

    private boolean isAllFieldsChecked = false;

    public InvoiceCreateDialogFragment() {
        // Required empty public constructor
    }



    public static InvoiceCreateDialogFragment newInstance(String title, InvoiceCreateListener listener){
        invoiceCreateListener = listener;
        InvoiceCreateDialogFragment invoiceCreateDialogFragment = new InvoiceCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        invoiceCreateDialogFragment.setArguments(args);

        invoiceCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return invoiceCreateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_invoice_create_dialog, container, false);

        nameEditText = view.findViewById(R.id.invoiceNameEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        addressEditText = view.findViewById(R.id.addressEditText);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);


        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isAllFieldsChecked = CheckAllFields();

                if (isAllFieldsChecked) {
                    nameString = nameEditText.getText().toString();
                    phoneString = phoneEditText.getText().toString();
                    addressString = addressEditText.getText().toString();
                    createdDateString = String.valueOf(Calendar.getInstance().getTime());

                    Invoice invoice = new Invoice(-1, nameString, createdDateString, phoneString, addressString, amountDouble);

                    DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

                    long id = databaseQueryClass.insertInvoice(invoice);

                    if(id>0){
                        invoice.setId(id);
                        invoiceCreateListener.onInvoiceCreated(invoice);
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
        if (nameEditText.length() == 0) {
            nameEditText.setError("This field is required");
            return false;
        }

        if (phoneEditText.length() == 0) {
            phoneEditText.setError("This field is required");
            return false;
        }

        if (addressEditText.length() == 0) {
            addressEditText.setError("This field is required");
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
