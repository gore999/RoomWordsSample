package rodriguezfernandez.carlos.roomwordssample;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

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
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
