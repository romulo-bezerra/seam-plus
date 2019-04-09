package br.edu.ifpb.seamplus.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.edu.ifpb.seamplus.model.Pedido;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface PedidoDAO {

    @Insert(onConflict = REPLACE)
    public void insert(Pedido pedido);

    @Update
    public void update(Pedido pedido);

    @Delete
    public void delete(Pedido pedido);

    @Query("SELECT * FROM Pedido WHERE id = :id")
    public Pedido getById(long id);

    @Query("SELECT * FROM Pedido WHERE atelieId = :atelieId")
    public List<Pedido> getAllByAtelieId(long atelieId);

    @Query("SELECT * FROM Pedido WHERE clienteId = :clienteId")
    public List<Pedido> getAllByClienteId(long clienteId);

    @Query("SELECT * FROM Pedido WHERE atelieId = :atelieId AND finalizado = 0")
    public List<Pedido> getAllPedidosNaoFinalizados(long atelieId);

    @Query("SELECT * FROM Pedido")
    public List<Pedido> getAll();

}