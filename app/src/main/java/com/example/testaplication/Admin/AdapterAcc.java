package com.example.testaplication.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testaplication.R;

import java.util.ArrayList;

public class AdapterAcc extends RecyclerView.Adapter<AdapterAcc.AccHViewHolder>  {
    private Context context;
    private ArrayList<MyAccount> accList;
    private AccountAdmin acc;

    public AdapterAcc(Context context, ArrayList<MyAccount> accList) {
        this.context = context;
        this.accList = accList;
        acc = new AccountAdmin(context);
    }

    @NonNull
    @Override
    public AccHViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_account_item,parent,false);
        AccHViewHolder vh = new AccHViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AccHViewHolder holder, int position) {
        MyAccount myAccount=accList.get(position);
        String id= myAccount.getId();
        String username= myAccount.getUsername();
        String password= myAccount.getPassword();

        holder.accName.setText(username);

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.RLid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, AccDetails.class);
                intent.putExtra("accId",id);
                context.startActivity(intent);
            }
        });
//        holder.AccEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
//                Intent intent= new Intent(context, AddAccount.class);
//                intent.putExtra("id",id);
//                intent.putExtra("username",username);
//                intent.putExtra("password",password);
//                intent.putExtra("isEditMode",true);
//                context.startActivity(intent);
//            }
//        });
        holder.AccDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                acc.deleteAcc(id);
                ((AccFragment)context).onResume();


            }
        });

    }


    @Override
    public int getItemCount() {
        return accList.size();
    }

    class AccHViewHolder extends RecyclerView.ViewHolder{
        TextView accName,AccDelete;
        ImageView detail;
        RelativeLayout RLid;
        public AccHViewHolder(@NonNull View itemView) {
            super(itemView);
            accName=itemView.findViewById(R.id.accName);
            detail=itemView.findViewById(R.id.detail);
            RLid=itemView.findViewById(R.id.RLid);
            AccDelete= itemView.findViewById(R.id.AccDelete);
//            AccEdit= itemView.findViewById(R.id.AccEdit);
        }
    }
}