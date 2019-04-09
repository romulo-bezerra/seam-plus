package br.edu.ifpb.seamplus.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.edu.ifpb.seamplus.model.Atelie;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface AtelieDAO {

    @Insert(onConflict = REPLACE)
    public void insert(Atelie atelie);

    @Update
    public void update(Atelie atelie);

    @Delete
    public void delete(Atelie atelie);

    @Query("SELECT * FROM Atelie WHERE id = :id")
    public Atelie getById(long id);

    @Query("SELECT * FROM Atelie WHERE nomeFantasia = :nomeFantasia")
    public Atelie getByNomeFantasia(String nomeFantasia);

    @Query("SELECT COUNT(*) FROM Atelie")
    public int countRecord();

}