package com.thebigoceaan.smartagriculture.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thebigoceaan.smartagriculture.R;
import com.thebigoceaan.smartagriculture.models.Product;

import java.util.ArrayList;

public class ProductDetailsHomeAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    ArrayList<Product> list = new ArrayList<>();
    private RecyclerViewClickListener listener;

    public ProductDetailsHomeAdapter(Context context, RecyclerViewClickListener listener, ArrayList<Product> list) {
        this.context = context;
        this.listener= listener;
        this.list = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_details,parent,false);
        return new ProductDetailsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductDetailsVH vh = (ProductDetailsVH) holder;
        Product product = list.get(position);
        Glide.with(context).load(product.getProductImage()).placeholder(R.drawable.ic_image).fitCenter().centerCrop().into(vh.productImage);
        vh.title.setText(product.getProductTitle());
    }
    public void setItem(ArrayList<Product> product){
        list.addAll(product);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductDetailsVH  extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView productImage;
        public ProductDetailsVH(@NonNull @com.google.firebase.database.annotations.NotNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.productTitleDesc);
            productImage=itemView.findViewById(R.id.productImageDesc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getBindingAdapterPosition());
        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View view, int position);
    }
}