package com.support.android.SpeedDial3;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by rdcfuch on 4/27/2015.
 */
public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.myViewHolder> {

    private List<PhoneCallUnit> my_list;
    //    private List<Map<String, Object>> my_list;
    private LayoutInflater myInflater;
    private Context myContext;
    private int myResource;
    private Typeface tf;


    MyRecycleViewAdapter(Context context, int resource, List<PhoneCallUnit> input_list) {
        super();
        this.my_list = input_list;
        this.myContext = context;
        this.myResource = resource;
        myInflater=LayoutInflater.from(myContext);
//        this.myInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        tf = Typeface.createFromAsset(myContext.getAssets(), "fonts/Roboto-Regular.ttf");
    }


    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        myViewHolder myHolder = new myViewHolder(myInflater.inflate(R.layout.phone_item,parent,false));
        return myHolder;

    }



    @Override
    public void onBindViewHolder(myViewHolder _myHolder, int position) {
        PhoneCallUnit _phone_unit = my_list.get(position);
        _myHolder._holderPhoneAlias.setText(_phone_unit.getPhoneAlias().toString());
        if (_phone_unit.getSimSlot() == 0)

        {
//            _myHolder._holderSimImage.setImageResource(R.drawable.sim1);
            _myHolder._holderSimNum.setText("1");
//            System.out.println("Height"+_myHolder._holderSimNum.getHeight());
        } else{
//            _myHolder._holderSimImage.setImageResource(R.drawable.sim2);
            _myHolder._holderSimNum.setText("2");
//            _myHolder._holderSimNum.setWidth(_myHolder._holderSimNum.getHeight());

        }

        if("1".equals(_myHolder._holderSimNum.getText())){
            _myHolder._holderSimNum.setBackgroundColor(Color.parseColor("#FF28FF36"));
        }


        // ???????ripple????icon?alias?ripple???
        _myHolder._holderCardView.setOnClickListener(new MyOnClickListener(myContext, _phone_unit, position));

        _myHolder._holderCardView.setOnLongClickListener(new MyOnLongClickListener(myContext,_phone_unit,position));
        //setting button????
//        _myHolder._holderButton.setOnClickListener(new MyOnClickListener(myContext, _phone_unit, position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return my_list.size();
    }

    public void addItem(PhoneCallUnit pu){
        my_list.add(pu);
        int pos;
        if(my_list.size()==0){
            pos=0;
        }else
            pos=my_list.size()-1;
        notifyItemInserted(pos);
//        Toast.makeText(myContext, "add item"+pos, Toast.LENGTH_LONG).show();
    }

    public void deleteItem(int position){
        my_list.remove(position);
        notifyItemRemoved(position);
    }

    public void setItem(int position,PhoneCallUnit pu){
        my_list.set(position,pu);
        notifyItemChanged(position);
    }


    public class myViewHolder extends RecyclerView.ViewHolder  {
        //        public LayoutRipple _holderRipple;
//        public Button _holderButton;
//        public ImageButton _holderButton;
        private TextView _holderPhoneAlias,_holderSimNum;
        private ImageView _holderSimImage;
        private CardView _holderCardView;



        public myViewHolder(View itemView) {
            super(itemView);
//            this._holderButton = (Button) itemView.findViewById(R.id.Phone_setup_button);
            this._holderPhoneAlias = (TextView) itemView.findViewById(R.id.Phone_call_Name);
            this._holderSimNum = (TextView) itemView.findViewById(R.id.Phone_call_Sim);



            this._holderPhoneAlias.setTypeface(tf);
//            this._holderSimImage = (ImageView) itemView.findViewById(R.id.simIcon);
            this._holderCardView = (CardView) itemView.findViewById(R.id.Phone_Call_card);
        }

    }
}
