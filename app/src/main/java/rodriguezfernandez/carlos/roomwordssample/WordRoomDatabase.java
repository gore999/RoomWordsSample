package rodriguezfernandez.carlos.roomwordssample;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Word.class},version = 1,exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {
    private static WordRoomDatabase INSTANCE;
    //Metodo Abstracto para recuperar el Dao.
    public abstract WordDao wordDao();
    //Será una clase tipo singleton (una unica instancia para toda la app)
    public static WordRoomDatabase getDatabase(final Context context){
        if(INSTANCE==null){//Comprueba si hay una instancia ya de WordRoomDatabase
            synchronized (WordRoomDatabase.class){//Comprueba si no existe un bloqueo, si se está creando ya otra instancia en otro sitio.
                if(INSTANCE==null){//Si cuando acaba el bloqueo sigue no habiendo instancia, entonces la creamos con el Builder de Room.
                    //Necesita el contexto de la aplicacion, la clase WordRoomDatabase y el nombre de la base de datos.
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),WordRoomDatabase.class,"word_database")
                            .fallbackToDestructiveMigration()//Si hay migracion a una nueva base, destruimos los datos previos: )
                            .addCallback(sRoomDatabaseCallback)//Añadir el callback
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    //Crear un callback que se llame antes de crear la base de datos.
    private static RoomDatabase.Callback sRoomDatabaseCallback=new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    //Esta clase ejecuta en segundo plano
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;
        String[] words = {"dolphin", "crocodile", "cobra"};
        //El constructor crea el dao a partir de una base de datos que se le pase.
        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // borramos todo
           if(mDao.getAnyWord().length<1){//Si la base de datos está vacía, añadimos las del array.
               for(int i=0;i<=words.length-1;i++){
                   Word word=new Word(words[i]);
                   mDao.insert(word);
               }
           }
            return null;
        }
    }

}
