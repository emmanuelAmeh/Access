package com.example.android.access;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.VisitorHolder> {

    private List<Visitor> mVisitors = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public VisitorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visitor_list, parent, false);
        return new VisitorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorHolder holder, int position) {

        Visitor currentVisitor = mVisitors.get(position);

        //For date and time
        String date = getDate(currentVisitor.getDate());
        String time = getTime(currentVisitor.getDate());
        String fullVistorName = getVisitorName(currentVisitor.getFirstname(), currentVisitor.getLastname());

        holder.tvDate.setText(date);
        holder.tvVisitorName.setText(fullVistorName);
        holder.tvTime.setText(time);

    }

    private String getVisitorName(String firstname, String lastname) {
        return firstname + " " + lastname;
    }


    private String getTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        //You can change "yyyyMMdd_HHmmss as per your requirement
        String formattedTime = sdf.format(date);
        return formattedTime;
    }

    private String getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        //You can change "yyyyMMdd_HHmmss as per your requirement
        String formattedDate = sdf.format(date);
        return formattedDate;
    }


    @Override
    public int getItemCount() {
        return mVisitors.size();
    }

    void setVisitors(List<Visitor> visitors) {
        mVisitors = visitors;
        notifyDataSetChanged();
    }

    public Visitor getVisitor(int position) {
        return mVisitors.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    //to give recycler view on click properties
    public interface OnItemClickListener {
        void OnItemClickListener(Visitor visitor);
    }

    //visitorholder class
    public class VisitorHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvVisitorName;
        private TextView tvTime;

        public VisitorHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.date);
            tvVisitorName = itemView.findViewById(R.id.visitor);
            tvTime = itemView.findViewById(R.id.time);

            //Applying the onclick property to a recycler view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnItemClickListener(mVisitors.get(position));
                    }
                }
            });

            //Applying  onclick property to a recycler view item
            tvTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Checking my check", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
