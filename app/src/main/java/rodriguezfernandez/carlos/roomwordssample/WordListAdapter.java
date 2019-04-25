package rodriguezfernandez.carlos.roomwordssample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter <WordListAdapter.WordViewHolder>{
    private final LayoutInflater mInflater;
    private List<Word> mWords;

    public WordListAdapter(Context context) {
        //El inflater necesita un contexto para poder hacer sus operaciones.
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=mInflater.inflate(R.layout.recyclerview_item,viewGroup,false);
        return new WordViewHolder(itemView) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder viewHolder, int i) {
        if(mWords!=null){//Si la lista no está vacia
            Word current=mWords.get(i);//obtenemos la palabra numero i de la lista.
            viewHolder.wordItemView.setText(current.getWord());//Establecemos el texto del textview del viewholder al de la lista.
        }else{
            viewHolder.wordItemView.setText("No hay palabra");
        }
    }

    @Override
    public int getItemCount() {
        if(mWords!=null){
            return mWords.size();
        }else{
            return 0;
        }
    }
    //Lleva un metodo set para gestionar cuando se produzca algun cambio en la lista de palabras.
    void setWords(List<Word> words){
        mWords=words;
        notifyDataSetChanged();
    }
    class WordViewHolder extends RecyclerView.ViewHolder{
        private final TextView wordItemView;
        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordItemView=itemView.findViewById(R.id.textView);
        }
    }
}
