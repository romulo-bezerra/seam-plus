package br.edu.ifpb.seamplus.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.edu.ifpb.seamplus.model.Divida;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DividaDAO {

    @Insert(onConflict = REPLACE)
    public void insert(Divida divida);

    @Update
    public void update(Divida divida);

    @Delete
    public void delete(Divida divida);

    @Query("SELECT * FROM Divida WHERE id = :id")
    public Divida getById(long id);

    @Query("SELECT * FROM Divida WHERE clienteId = :clienteId")
    public Divida getByClienteId(long clienteId);

    @Query("SELECT * FROM Divida")
    public List<Divida> getAll();

    @Query("SELECT COUNT(*) FROM Divida WHERE clienteId = :clienteId")
    public int checaDivida(long clienteId);



}