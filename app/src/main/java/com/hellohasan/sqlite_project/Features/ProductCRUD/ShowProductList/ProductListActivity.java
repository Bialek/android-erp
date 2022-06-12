package com.hellohasan.sqlite_project.Features.ProductCRUD.ShowProductList;

import android.content.DialogInterface;
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
import com.hellohasan.sqlite_project.Features.ProductCRUD.CreateProduct.Product;
import com.hellohasan.sqlite_project.Features.ProductCRUD.CreateProduct.ProductCreateDialogFragment;
import com.hellohasan.sqlite_project.Features.ProductCRUD.CreateProduct.ProductCreateListener;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductCreateListener {

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Product> productList = new ArrayList<>();

    private TextView summaryTextView;
    private TextView productListEmptyTextView;
    private RecyclerView recyclerView;
    private ProductListRecyclerViewAdapter productListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        summaryTextView = findViewById(R.id.summaryTextView);
        productListEmptyTextView = findViewById(R.id.emptyListTextView);

        productList.addAll(databaseQueryClass.getAllProduct());

        productListRecyclerViewAdapter = new ProductListRecyclerViewAdapter(this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(productListRecyclerViewAdapter);

        viewVisibility();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProductCreateDialog();
            }
        });
    }

    private void printSummary() {
        long invoiceNum = databaseQueryClass.getNumberOfInvoice();
        long productNum = databaseQueryClass.getNumberOfProduct();

        summaryTextView.setText(getResources().getString(R.string.database_summary, invoiceNum, productNum));
    }

    private void openProductCreateDialog() {
        ProductCreateDialogFragment productCreateDialogFragment = ProductCreateDialogFragment.newInstance(this);
        productCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_PRODUCT);
    }

    @Override
    public void onProductCreated(Product product) {
        productList.add(product);
        productListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
    }

    public void viewVisibility() {
        if(productList.isEmpty())
            productListEmptyTextView.setVisibility(View.VISIBLE);
        else
            productListEmptyTextView.setVisibility(View.GONE);
        printSummary();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_delete:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

}
