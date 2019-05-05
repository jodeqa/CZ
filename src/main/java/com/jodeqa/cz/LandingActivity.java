package com.jodeqa.cz;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cashlez.android.sdk.CLErrorResponse;
import com.cashlez.android.sdk.CLPayment;
import com.cashlez.android.sdk.CLTransferDetail;
import com.cashlez.android.sdk.companion.printer.CLPrinterCompanion;
import com.cashlez.android.sdk.companion.reader.CLReaderCompanion;
import com.cashlez.android.sdk.imagehandler.CLUploadHandler;
import com.cashlez.android.sdk.imagehandler.CLUploadResponse;
import com.cashlez.android.sdk.imagehandler.ICLUploadHandler;
import com.cashlez.android.sdk.imagehandler.ICLUploadService;
import com.cashlez.android.sdk.payment.CLDimoResponse;
import com.cashlez.android.sdk.payment.CLMandiriPayResponse;
import com.cashlez.android.sdk.payment.CLPaymentResponse;
import com.cashlez.android.sdk.payment.CLTCashQRResponse;
import com.cashlez.android.sdk.payment.noncash.CLPaymentHandler;
import com.cashlez.android.sdk.payment.noncash.ICLPaymentHandler;
import com.cashlez.android.sdk.payment.noncash.ICLPaymentService;

public class LandingActivity extends AppCompatActivity implements ICLPaymentService,ICLUploadService {

    EditText amount;
    EditText description;
    TextView readerStatus;
    TextView printerStatus;

    private String fileName;

    protected ICLPaymentHandler paymentHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);


        doCreateHandler(this, savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doCloseConnection();
    }

    void doCreateHandler(Activity activity, Bundle extras) {
        paymentHandler = new CLPaymentHandler(activity, extras);
    }

    void doConnectLocationProvider() {
        paymentHandler.doConnectLocationProvider();
    }

    void doUnRegisterReceiver() {
        paymentHandler.doUnregisterReceiver();
    }

    public void doCloseConnection() {
        paymentHandler.doCloseCompanionConnection();
    }

    public void doStartPayment() {
        paymentHandler.doStartPayment(this);
    }

    public void doStopLocationProvider() {
        paymentHandler.doStopUpdateLocation();
    }


    public void goUpload(View view) {
        ApplicationState applicationState = new ApplicationState();
        ICLUploadHandler uploadHandler = new CLUploadHandler(applicationState.getContext(), this);
        uploadHandler.doUpload("/storage/emulated/0/Cashlez/123.jpg");
    }

    @Override
    public void onUploadSuccess(CLUploadResponse clUploadResponse) {
        onUploadedSuccess(clUploadResponse.getFileName());
    }

    public void onUploadedSuccess(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void onUploadError(CLErrorResponse clErrorResponse) {
        //Do Nothing
    }

    public void goPay(View view) {
        ApplicationState applicationState = new ApplicationState();
        CLPayment payment = new CLPayment();
        payment.setAmount(amount.getText().toString().trim());
        payment.setDescription(description.getText().toString().trim());
        payment.setImage(fileName);
        applicationState.setPayment(payment);

//        Intent intent = new Intent(this, PaymentActivity.class);
//        intent.putExtra(PAYMENT, payment);
//        startActivityForResult(intent, PAYMENT_REQUEST);
    }

    public void goCheckReader(View view) {
        paymentHandler.doCheckReaderCompanion();
    }

    public void goCheckPrinter(View view) {
        paymentHandler.doCheckPrinterCompanion();
    }

    public void doGetHistoryList(View view) {
//        Intent intent = new Intent(this, PaymentHistoryActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onReaderSuccess(final CLReaderCompanion clReaderCompanion) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                readerStatus.setText(String.format("%s: %s\n%s: %s",
                        getString(R.string.reader_connection_status), String.valueOf(clReaderCompanion.isConnected()),
                        getString(R.string.message), clReaderCompanion.getMessage()));
            }
        });
    }

    @Override
    public void onReaderError(final CLErrorResponse clErrorResponse) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                readerStatus.setText(clErrorResponse.getErrorMessage());
            }
        });
    }

    @Override
    public void onPrinterSuccess(final CLPrinterCompanion clPrinterCompanion) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                printerStatus.setText(String.format("%s: %s\n%s: %s",
                        getString(R.string.printer_connection_status), String.valueOf(clPrinterCompanion.isConnected()),
                        getString(R.string.message), clPrinterCompanion.getMessage()));
            }
        });
    }

    @Override
    public void onPrinterError(final CLErrorResponse clErrorResponse) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                printerStatus.setText(clErrorResponse.getErrorMessage());
            }
        });
    }

    @Override
    public void onInsertCreditCard(CLPaymentResponse clPaymentResponse) {
        //do nothing
    }

    @Override
    public void onInsertOrSwipeDebitCard(CLPaymentResponse clPaymentResponse) {
        //do nothing
    }

    @Override
    public void onSwipeDebitCard(CLPaymentResponse clPaymentResponse) {
        //do nothing
    }

    @Override
    public void onCashPaymentSuccess(CLPaymentResponse clPaymentResponse) {
        //do nothing
    }

    @Override
    public void onCashPaymentError(CLErrorResponse clErrorResponse) {
        //do nothing
    }

    @Override
    public void onPaymentSuccess(CLPaymentResponse clPaymentResponse) {
        //do nothing
    }

    @Override
    public void onRemoveCard(String s) {
        //do nothing
    }

    @Override
    public void onProvideSignatureRequest(CLPaymentResponse clPaymentResponse) {
        //do nothing
    }

    @Override
    public void onPaymentError(CLErrorResponse clErrorResponse, String s) {
        //do nothing
    }

    @Override
    public void onProvideSignatureError(CLErrorResponse clErrorResponse) {
        //do nothing
    }

    @Override
    public void onTCashQRSuccess(CLTCashQRResponse cltCashQRResponse) {
        //do nothing
    }

    @Override
    public void onTCashQRError(CLErrorResponse clErrorResponse) {
        //do nothing
    }

    @Override
    public void onCheckTCashQRStatusSuccess(CLTCashQRResponse cltCashQRResponse) {
        //do nothing
    }

    @Override
    public void onCheckTCashQRStatusError(CLErrorResponse clErrorResponse) {
        //do nothing
    }

    @Override
    public void onDimoSuccess(CLDimoResponse clDimoResponse) {
        //do nothing
    }

    @Override
    public void onDimoError(CLErrorResponse clErrorResponse) {
        //do nothing
    }

    @Override
    public void onCheckDimoStatusSuccess(CLDimoResponse clDimoResponse) {
        //do nothing
    }

    @Override
    public void onCheckDimoStatusError(CLErrorResponse clErrorResponse) {
        //do nothing
    }

    @Override
    public void onCancelDimoSuccess(CLDimoResponse clDimoResponse) {
        //do nothing
    }

    @Override
    public void onCancelDimoError(CLErrorResponse clErrorResponse) {
        //do nothing
    }

    @Override
    public void onMandiriPaySuccess(CLMandiriPayResponse clMandiriPayResponse) {
        //do nothing
    }

    @Override
    public void onMandiriPayError(CLErrorResponse clErrorResponse) {
        //do nothing
    }

    @Override
    public void onCheckMandiriPayStatusSuccess(CLMandiriPayResponse clMandiriPayResponse) {
        //do nothing
    }

    @Override
    public void onCheckMandiriPayStatusError(CLErrorResponse clErrorResponse) {
        //do nothing
    }

    @Override
    public void onPaymentDebitTransferRequestConfirmation(CLTransferDetail clTransferDetail) {
        //do nothing
    }

}