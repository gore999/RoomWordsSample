package rodriguezfernandez.carlos.roomwordssample;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class WordRepository {
    //Contiene un DAO y un LiveData.
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;
    //Constructor
    WordRepository(Application application){
        WordRoomDatabase db=WordRoomDatabase.getDatabase(application);
        mWordDao=db.wordDao();//Recuperar el Dao
        mAllWords=mWordDao.getAllWords();//Recuperar todas las palabras, las vamos a mostrar en un RecyclerView.
    }
    //Creamos un metodo "envoltorio" para getAllWords
    LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }
    //Envoltorio para insert
    public void insert(Word word){
        new insertAsyncTask(mWordDao).execute(word);

    }
    private static class insertAsyncTask extends AsyncTask<Word,Void,Void> {
        private WordDao mAsyncTaskDao;//Va a necesitar el Dao.
        insertAsyncTask(WordDao dao){
            //asignar el dao.
            mAsyncTaskDao=dao;
        }
        @Override
        protected Void doInBackground(final Word... words) {
            mAsyncTaskDao.insert(words[0]);//inserta solo una, aunque se le pasen varias.
            return null;
        }

    }
}
