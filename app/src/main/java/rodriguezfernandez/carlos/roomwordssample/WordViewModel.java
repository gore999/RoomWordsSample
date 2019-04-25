package rodriguezfernandez.carlos.roomwordssample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository=new WordRepository(application);//Crear el repositorio.
        mAllWords=mRepository.getAllWords();//Recuperar todas las palabras.
    }
    //Con este metodo, devolvemos todas las palabras a traves del viewModel.
    LiveData<List<Word>> getmAllWords(){
        return mAllWords;
    }
    //Un metodo envoltorio para insertar una palabra desde el ViewModel (que  lo inserta a través del repositorio.)
    public void insert(Word word){
        mRepository.insert(word);
    }
}
