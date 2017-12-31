package vn.com.fpt.frt_minventory.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.com.fpt.frt_minventory.Model.Shop;
import vn.com.fpt.frt_minventory.R;

/**
 * Created by minhtran on 12/28/17.
 */

public class AdapterChooseShop extends RecyclerView.Adapter<AdapterChooseShop.MyViewHolder> {

    private List<Shop> list;
    private Context context;

    public Choose getChoose() {
        return choose;
    }

    public void setChoose(Choose choose) {
        this.choose = choose;
    }

    private Choose choose;

    public interface Choose {
        void click(String id, String name);
    }

    public AdapterChooseShop(Context context, List<Shop> list, Choose choose) {
        this.context = context;
        this.list = list;
        this.choose = choose;
    }

    @Override
    public AdapterChooseShop.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_choose_shop, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterChooseShop.MyViewHolder holder, int position) {
        final Shop shop = list.get(position);
        if(shop.getID().contains("_")){
            String[] shopcode = shop.getID().split("_");
            holder.tvShop.setText(shopcode[1] + " - " + shop.getNAME());
            holder.tvShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    choose.click(shop.getID(), shop.getNAME());
                }
            });
        }else holder.tvShop.setText( shop.getNAME());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvShop;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvShop = itemView.findViewById(R.id.tvShop);
        }
    }
}
