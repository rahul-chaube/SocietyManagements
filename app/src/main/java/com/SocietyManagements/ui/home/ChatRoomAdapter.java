package com.SocietyManagements.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.SocietyManagements.R;
import com.SocietyManagements.controller.Constants;
import com.SocietyManagements.controller.PrefManager;
import com.SocietyManagements.ui.chatScreen.ChatScreenActivity;
import com.SocietyManagements.ui.chatroom.ChatMessage;
import com.SocietyManagements.ui.creategame.GameDescription;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder> {

    Context context;
    ArrayList<GameDescription>  data;
    PrefManager prefManager;

    public ChatRoomAdapter(Context context, ArrayList<GameDescription> data) {
        this.context = context;
        this.data = data;
        prefManager=new PrefManager(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_group_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GameDescription gameDescription=data.get(position);
        holder.textViewDescription.setText(gameDescription.getDescription());
        holder.textViewNoOfPlayer.setText("No of  Player : "+gameDescription.getNumberOfPlayer());
        holder.textViewPrize.setVisibility(View.INVISIBLE);
        holder.textViewNoOfPlayer.setVisibility(View.INVISIBLE);
        holder.textViewPrize.setText("Prize \u20B9 "+gameDescription.getPrice());
        holder.textViewGameName.setText(gameDescription.getTitle());
        if (gameDescription.isPubg())
            Glide.with(context).load(R.drawable.pubg).into(holder.imageViewIcon);
        else
            Glide.with(context).load(R.drawable.cs).into(holder.imageViewIcon);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatScreenActivity.class);
                intent.putExtra(Constants.GAME_DETAILS,gameDescription);
                context.startActivity(intent);
            }
        });

        setListnerOnChar(gameDescription.getGameId(),holder.textViewDescription,holder.textViewDate);
    }

    private void setListnerOnChar(String gameId, final TextView textViewDescription,final TextView textViewDateTime) {
                FirebaseDatabase.getInstance().getReference().child(Constants.CHAT_ROOM)
                .child(gameId).limitToLast(1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot data:dataSnapshot.getChildren()
                        ) {


                            ChatMessage chatMessage=data.getValue(ChatMessage.class);
                            textViewDescription.setText(String.format("  Message:  %s", chatMessage.getMessage()));
                            Date messageTime=new Date(chatMessage.getMessageTime());
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss.SSS");
                            textViewDateTime.setText(sdf.format(messageTime));

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageViewIcon;
        TextView textViewGameName,textViewPrize,textViewDate,textViewNoOfPlayer,textViewDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.main);
            imageViewIcon=itemView.findViewById(R.id.gameIcon);
            textViewGameName=itemView.findViewById(R.id.gameName);
            textViewPrize=itemView.findViewById(R.id.prize);
            textViewNoOfPlayer=itemView.findViewById(R.id.NoOfPlayer);
            textViewDate=itemView.findViewById(R.id.startDate);
            textViewDescription=itemView.findViewById(R.id.gameDescription);
        }
    }
}
