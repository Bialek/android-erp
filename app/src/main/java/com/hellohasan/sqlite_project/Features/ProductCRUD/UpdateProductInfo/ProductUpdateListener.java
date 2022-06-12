package com.hellohasan.sqlite_project.Features.ProductCRUD.UpdateProductInfo;

import com.hellohasan.sqlite_project.Features.ProductCRUD.CreateProduct.Product;

public interface ProductUpdateListener {
    void onProductInfoUpdate(Product product, int position);
}
