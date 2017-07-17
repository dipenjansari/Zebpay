package com.zebpay.demo.dipen.jansari.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.zebpay.demo.dipen.jansari.R;
import com.zebpay.demo.dipen.jansari.baseclass.BaseDialog;
import com.zebpay.demo.dipen.jansari.databinding.DialogVarianceRupeesBinding;
import com.zebpay.demo.dipen.jansari.interfaces.ClickListener;
import com.zebpay.demo.dipen.jansari.interfaces.ValueReturnListener;

/**
 * Created by dipen on 17/7/17.
 */

public class VarianceByRupeeDialog extends BaseDialog implements ClickListener {

    private Context mContext;
    private DialogVarianceRupeesBinding rupeesBinding;
    private ValueReturnListener valueReturnListener;
    private double marketValue;

    public VarianceByRupeeDialog(@NonNull Context context, ValueReturnListener returnListener, double marketValue) {
        super(context, R.style.CustomDialog);
        setView(R.layout.dialog_variance_rupees);

        this.mContext = context;
        this.valueReturnListener = returnListener;
        this.marketValue = marketValue;
    }

    @Override
    public void initVariable() {
        rupeesBinding = getBinding();
        rupeesBinding.setOnClick(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set:

                if (TextUtils.isEmpty(rupeesBinding.edtRupees.getText().toString())) {
                    rupeesBinding.edtRupees.setError(mContext.getString(R.string.enter_rupees));
                } else if (Double.parseDouble(rupeesBinding.edtRupees.getText().toString()) <= 0.0 ||
                        Double.parseDouble(rupeesBinding.edtRupees.getText().toString()) >= marketValue) {
                    rupeesBinding.edtRupees.setError(mContext.getString(R.string.enter_valid_rupees));
                } else {
                    valueReturnListener.onValueReturn(Double.parseDouble(rupeesBinding.edtRupees.getText().toString()));
                    dismiss();
                }

                break;

            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }
}
