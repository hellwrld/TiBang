package ecnu.cs.tibang.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ecnu.cs.tibang.R;
import ecnu.cs.tibang.bean.Question;
import ecnu.cs.tibang.ui.AddQuestionActivity;
import ecnu.cs.tibang.ui.QuestionDetailActivity;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private List<Question> data;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View questionView;
        TextView title;
        TextView describe;
        TextView time;
        TextView photo;

        public ViewHolder(View view){
            super(view);
            questionView = view;
            title = (TextView) view.findViewById(R.id.tv_title);
            describe = (TextView) view.findViewById(R.id.tv_describe);
            time = (TextView) view.findViewById(R.id.tv_time);
            photo = (TextView) view.findViewById(R.id.tv_photo);
        }
    }

    public QuestionAdapter(List<Question> datas) {
        this.data = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.questionView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Question question = data.get(position);
                Intent intent = new Intent(v.getContext(),QuestionDetailActivity.class);
                intent.putExtra("title",question.getTitle());
                intent.putExtra("photo",question.getPhone());
                intent.putExtra("describe",question.getDescribe());
                intent.putExtra("url",question.getUrl());
                v.getContext().startActivity(intent);
            }
        });
        return  holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Question question = data.get(position);
        holder.title.setText(question.getTitle());
        holder.describe.setText(question.getDescribe());
        holder.time.setText(question.getCreatedAt());
        holder.photo.setText(question.getPhone());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
