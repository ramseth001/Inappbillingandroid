package com.image.ram.inappbillingandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.image.ram.inappbillingandroid.util.IabHelper;
import com.image.ram.inappbillingandroid.util.IabResult;
import com.image.ram.inappbillingandroid.util.Inventory;
import com.image.ram.inappbillingandroid.util.Purchase;

public class MainActivity extends AppCompatActivity {
    static final String ITEM_SKU_1 = "android.test.purchased";
    // android.test.purchased is the default sku. You need to enter your sku name at that place
    private ImageView imageView;
    private Button button;
    private IabHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);
        button = (Button) findViewById(R.id.button);

        String base64EncodedPublicKey = "Your Base64-encoded RSA public key";
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Log.d("TAG", "In-app Billing setup failed: " +
                            result);
                } else {
                    Log.d("TAG", "In-app Billing is set up OK");
                }
            }
        });

    }

    public void doWork(View view) {

        mHelper.launchPurchaseFlow(this, ITEM_SKU_1, 10001,
                mPurchaseFinishedListener, "mypurchasetoken");

        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                imageView.setVisibility(View.VISIBLE);
                button.setEnabled(false);

            }

            public void onFinish() {
                imageView.setVisibility(View.INVISIBLE);
                button.setEnabled(true);
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase) {
            if (result.isFailure()) {
                // Handle error
                return;
            } else if (purchase.getSku().equals(ITEM_SKU_1)) {
                consumeItem();
                button.setEnabled(false);
            }

        }
    };


    public void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {


            if (result.isFailure()) {
                // Handle failure
            } else {
                mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU_1),
                        mConsumeFinishedListener);
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                        imageView.setVisibility(View.VISIBLE);
                    } else {
                        // handle error
                    }
                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }
}
