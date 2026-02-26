package com.WadhuWarProject.WadhuWar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.model.PaymentResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.PaymentViewHolder> {
    private ArrayList<PaymentResponse.Payments> paymentsArrayList;
    private ArrayList<PaymentResponse.Plans> paymentsPlanArrayList; // Correct field name
    private Context context;

    // Constructor to initialize context and data lists
    public TransactionAdapter(Context context) {
        this.context = context;
        this.paymentsArrayList = new ArrayList<>();
        this.paymentsPlanArrayList = new ArrayList<>(); // Properly initialize the plans list
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_account_transaction, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        // Get the current payment object
        PaymentResponse.Payments payment = paymentsArrayList.get(position);

        // Find the corresponding plan using planId
        String planName = "Unknown Plan"; // Default value if no match is found
        String planId = payment.getPlanId();
        if (planId != null && paymentsPlanArrayList != null) {
            for (PaymentResponse.Plans plan : paymentsPlanArrayList) {
                if (plan.getId() != null && plan.getId().equals(planId)) {
                    planName = plan.getName();
                    break;
                }
            }
        }

        // Set the data to the view holder's TextViews
        holder.plan_name.setText(planName);
        holder.paid_date.setText(payment.getTranscationDate());
        holder.mode_name.setText(payment.getPaymentType());
        holder.amount.setText(payment.getPaidAmount());
        holder.transaction_id.setText(payment.getTransactionId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        holder.expiry_date.setText(payment.getLastDate() != null ? dateFormat.format(payment.getLastDate()) : "N/A");
        holder.status.setText(payment.getTransactionStatus());
    }

    @Override
    public int getItemCount() {
        return (paymentsArrayList != null) ? paymentsArrayList.size() : 0;
    }

    // Method to update the payments list
    public void setList(ArrayList<PaymentResponse.Payments> paymentsList) {
        if (paymentsList != null) {
            paymentsArrayList.clear();
            paymentsArrayList.addAll(paymentsList);
        } else {
            paymentsArrayList.clear();
        }
        notifyDataSetChanged();
    }

    // Method to update the plans list
    public void setPlansArrayList(ArrayList<PaymentResponse.Plans> plansArrayList) {
        if (plansArrayList != null) {
            paymentsPlanArrayList.clear();
            paymentsPlanArrayList.addAll(plansArrayList);
        } else {
            paymentsPlanArrayList.clear();
        }
        notifyDataSetChanged();
    }

    // ViewHolder class to hold references to views in each item
    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        TextView plan_name, paid_date, mode_name, amount, transaction_id, expiry_date, status;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            plan_name = itemView.findViewById(R.id.plan_name);
            paid_date = itemView.findViewById(R.id.paid_date);
            mode_name = itemView.findViewById(R.id.mode_name);
            amount = itemView.findViewById(R.id.amount);
            transaction_id = itemView.findViewById(R.id.transaction_id);
            expiry_date = itemView.findViewById(R.id.expiry_date);
            status = itemView.findViewById(R.id.status);
        }
    }
}