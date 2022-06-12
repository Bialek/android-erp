package com.hellohasan.sqlite_project.Features.InvoiceCRUD.ShowInvoiceList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.Features.InvoiceCRUD.CreateInvoice.Invoice;
import com.hellohasan.sqlite_project.Features.InvoiceCRUD.CreateInvoice.InvoiceCreateDialogFragment;
import com.hellohasan.sqlite_project.Features.InvoiceCRUD.CreateInvoice.InvoiceCreateListener;
import com.hellohasan.sqlite_project.Features.ProductCRUD.ShowProductList.ProductListActivity;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class InvoiceListActivity extends AppCompatActivity implements InvoiceCreateListener{

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Invoice> invoiceList = new ArrayList<>();

    private TextView summaryTextView;
    private TextView invoiceListEmptyTextView;
    private RecyclerView recyclerView;
    private InvoiceListRecyclerViewAdapter invoiceListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Logger.addLogAdapter(new AndroidLogAdapter());

        recyclerView = findViewById(R.id.recyclerView);
        summaryTextView = findViewById(R.id.summaryTextView);
        invoiceListEmptyTextView = findViewById(R.id.emptyListTextView);

        invoiceList.addAll(databaseQueryClass.getAllInvoice());

        invoiceListRecyclerViewAdapter = new InvoiceListRecyclerViewAdapter(this, invoiceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(invoiceListRecyclerViewAdapter);

        viewVisibility();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInvoiceCreateDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        printSummary();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent i = new Intent(InvoiceListActivity.this,ProductListActivity.class);
        startActivity(i);

        return super.onOptionsItemSelected(item);
    }

    public void viewVisibility() {
        if(invoiceList.isEmpty())
            invoiceListEmptyTextView.setVisibility(View.VISIBLE);
        else
            invoiceListEmptyTextView.setVisibility(View.GONE);
        printSummary();
    }

    private void openInvoiceCreateDialog() {
        InvoiceCreateDialogFragment invoiceCreateDialogFragment = InvoiceCreateDialogFragment.newInstance("Create Invoice", this);
        invoiceCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_INVOICE);
    }

    private void printSummary() {
        long invoiceNum = databaseQueryClass.getNumberOfInvoice();
        long productNum = databaseQueryClass.getNumberOfProduct();

        summaryTextView.setText(getResources().getString(R.string.database_summary, invoiceNum, productNum));
    }

    @Override
    public void onInvoiceCreated(Invoice invoice) {
        invoiceList.add(invoice);
        invoiceListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
        Logger.d(invoice.getName());
    }

}
