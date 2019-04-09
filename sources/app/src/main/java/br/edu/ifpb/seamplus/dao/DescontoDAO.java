package br.edu.ifpb.seamplus.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.edu.ifpb.seamplus.model.Desconto;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DescontoDAO {

    @Insert(onConflict = REPLACE)
    public void insert(Desconto desconto);

    @Update
    public void update(Desconto desconto);

    @Delete
    public void delete(Desconto desconto);

    @Query("SELECT * FROM Desconto WHERE id = :id")
    public Desconto getById(long id);

    @Query("SELECT * FROM Desconto WHERE dividaId = :dividaId")
    public List<Desconto> getAllByDividaId(long dividaId);

    @Query("SELECT * FROM Desconto")
    public List<Desconto> getAll();

}