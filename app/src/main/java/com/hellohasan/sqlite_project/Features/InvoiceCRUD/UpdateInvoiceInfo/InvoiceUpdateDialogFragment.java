package com.hellohasan.sqlite_project.Features.InvoiceCRUD.UpdateInvoiceInfo;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.Features.InvoiceCRUD.CreateInvoice.Invoice;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;


public class InvoiceUpdateDialogFragment extends DialogFragment {

    private static long invoiceId;
    private static int invoiceItemPosition;
    private static InvoiceUpdateListener invoiceUpdateListener;

    private Invoice mInvoice;

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText addressEditText;
    private Button updateButton;
    private Button cancelButton;

    private String nameString = "";
    private String createdDateString = "";
    private long idNumber = -1;
    private String phoneString = "";
    private String addressString = "";
    private Double amountDouble = 0.0;
    private Boolean isAllFieldsChecked;

    private DatabaseQueryClass databaseQueryClass;

    public InvoiceUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static InvoiceUpdateDialogFragment newInstance(long id, int position, InvoiceUpdateListener listener){
        invoiceId = id;
        invoiceItemPosition = position;
        invoiceUpdateListener = listener;
        InvoiceUpdateDialogFragment invoiceUpdateDialogFragment = new InvoiceUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update invoice information");
        invoiceUpdateDialogFragment.setArguments(args);

        invoiceUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return invoiceUpdateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_invoice_update_dialog, container, false);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        nameEditText = view.findViewById(R.id.invoiceNameEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        addressEditText = view.findViewById(R.id.addressEditText);
        updateButton = view.findViewById(R.id.updateInvoiceInfoButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        mInvoice = databaseQueryClass.getInvoiceById(invoiceId);

        if(mInvoice!=null){
            nameEditText.setText(mInvoice.getName());
            phoneEditText.setText(mInvoice.getPhoneNumber());
            addressEditText.setText(mInvoice.getAddress());

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isAllFieldsChecked = CheckAllFields();

                    if (isAllFieldsChecked) {
                        nameString = nameEditText.getText().toString();
                        phoneString = phoneEditText.getText().toString();
                        addressString = addressEditText.getText().toString();

                        mInvoice.setName(nameString);
                        mInvoice.setPhoneNumber(phoneString);
                        mInvoice.setAddress(addressString);

                        long id = databaseQueryClass.updateInvoiceInfo(mInvoice);

                        if (id > 0) {
                            invoiceUpdateListener.onInvoiceInfoUpdated(mInvoice, invoiceItemPosition);
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

        }

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
