package com.zebpay.demo.dipen.jansari.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.zebpay.demo.dipen.jansari.R;
import com.zebpay.demo.dipen.jansari.baseclass.BaseDialog;
import com.zebpay.demo.dipen.jansari.databinding.DialogVariancePercentageBinding;
import com.zebpay.demo.dipen.jansari.interfaces.ClickListener;
import com.zebpay.demo.dipen.jansari.interfaces.ValueReturnListener;

/**
 * Created by dipen on 17/7/17.
 */

public class VarianceByPercDialog extends BaseDialog implements ClickListener {

    private DialogVariancePercentageBinding percentageBinding;
    private Context mContext;
    private ValueReturnListener returnListener;

    public VarianceByPercDialog(@NonNull Context context, ValueReturnListener returnListener) {
        super(context, R.style.CustomDialog);
        setView(R.layout.dialog_variance_percentage);

        this.mContext = context;
        this.returnListener = returnListener;
    }

    @Override
    public void initVariable() {
        percentageBinding = getBinding();
        percentageBinding.setOnClick(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set:

                if (TextUtils.isEmpty(percentageBinding.edtPercentage.getText().toString())) {
                    percentageBinding.edtPercentage.setError(mContext.getString(R.string.enter_percentage));
                } else if (Double.parseDouble(percentageBinding.edtPercentage.getText().toString()) <= 0.0 ||
                        Double.parseDouble(percentageBinding.edtPercentage.getText().toString()) > 100) {
                    percentageBinding.edtPercentage.setError(mContext.getString(R.string.percentage_in_between_zero_to_hundread));
                } else {
                    returnListener.onValueReturn(Double.parseDouble(percentageBinding.edtPercentage.getText().toString()));
                    dismiss();
                }

                break;

            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }
}
