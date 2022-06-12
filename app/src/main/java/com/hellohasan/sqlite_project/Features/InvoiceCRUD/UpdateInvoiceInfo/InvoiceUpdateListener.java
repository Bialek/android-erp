package com.hellohasan.sqlite_project.Features.InvoiceCRUD.UpdateInvoiceInfo;

import com.hellohasan.sqlite_project.Features.InvoiceCRUD.CreateInvoice.Invoice;

public interface InvoiceUpdateListener {
    void onInvoiceInfoUpdated(Invoice invoice, int position);
}
