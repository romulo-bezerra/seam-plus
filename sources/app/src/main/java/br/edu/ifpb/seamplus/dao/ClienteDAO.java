package br.edu.ifpb.seamplus.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.edu.ifpb.seamplus.model.Cliente;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ClienteDAO {

    @Insert(onConflict = REPLACE)
    public void insert(Cliente cliente);

    @Update
    public void update(Cliente cliente);

    @Delete
    public void delete(Cliente cliente);

    @Query("SELECT * FROM Cliente WHERE id = :id")
    public Cliente getById(long id);

    @Query("SELECT * FROM Cliente WHERE nome = :nome")
    public Cliente getByNome(String nome);

    @Query("SELECT * FROM Cliente WHERE atelieId = :atelieId")
    public List<Cliente> getAllByAtelieId(long atelieId);

    @Query("SELECT * FROM Cliente")
    public List<Cliente> getAll();

}