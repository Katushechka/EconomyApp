package com.example.p1.listAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p1.R;
import com.example.p1.database.Income;

import java.util.ArrayList;
import java.util.List;

public class EconomyListAdapter extends RecyclerView.Adapter<EconomyListAdapter.ViewHolder>  {
    private int incomeItemLayout;
  //  private List<Income> incomeList;
    private List<Income> incomeList = new ArrayList<>();
    private List<Income> expenseList = new ArrayList<>();
    private OnItemClickListener listener;
    private int sum=0;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_incomes, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int listPosition) {
        Income currentIncome = incomeList.get(listPosition);
        holder.textViewTitle.setText(currentIncome.getTitle());
        holder.textViewCategory.setText(currentIncome.getCategory());
        holder.textViewSum.setText(String.valueOf(currentIncome.getSum()));
        holder.textViewData.setText(currentIncome.getDate());
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }

    public void setIncomes(List<Income> incomes) {
        this.incomeList = incomes;
        notifyDataSetChanged();
    }


    public Income getNoteAt(int position) {
        return incomeList.get(position);
    }


    //create ViewHolder class  (holder Views in our RecyclerView items)
     class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewCategory;
        private TextView textViewSum;
        private TextView textViewData;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewCategory = itemView.findViewById(R.id.image_view_category);
            textViewSum = itemView.findViewById(R.id.text_view_sum);
            textViewData = itemView.findViewById(R.id.text_view_data);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(incomeList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Income income);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;

    }
}
