package com.WadhuWarProject.WadhuWar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.WadhuWarProject.WadhuWar.R;
import java.util.ArrayList;
import java.util.List;

public class PhoneNumberAdapter extends RecyclerView.Adapter<PhoneNumberAdapter.ViewHolder> {
    private final List<String> phoneNumbers;
    private final OnItemClickListener onItemClick;

    // Interface for item click callback
    public interface OnItemClickListener {
        Void onItemClick(String number);
    }

    // ViewHolder class
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView phoneIcon;
        TextView tvPhoneNumber;

        ViewHolder(View itemView) {
            super(itemView);
            phoneIcon = itemView.findViewById(R.id.blue_phone); // Assuming the ImageView ID matches
            tvPhoneNumber = itemView.findViewById(R.id.tv_phone_number);
        }
    }

    public PhoneNumberAdapter(List<String> phoneNumbers, OnItemClickListener onItemClick) {
        this.phoneNumbers = (phoneNumbers != null) ? phoneNumbers : new ArrayList<>();
        this.onItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contacts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position >= 0 && position < phoneNumbers.size()) {
            String phoneNumber = phoneNumbers.get(position);
            holder.tvPhoneNumber.setText(phoneNumber);
            holder.itemView.setOnClickListener(v -> {
                if (onItemClick != null) {
                    onItemClick.onItemClick(phoneNumber);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return phoneNumbers.size();
    }
}