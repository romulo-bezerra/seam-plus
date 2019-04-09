package br.edu.ifpb.seamplus.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.edu.ifpb.seamplus.model.Usuario;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UsuarioDAO {

    @Query("SELECT * FROM Usuario")
    public List<Usuario> getAllUsuarios();

    @Insert(onConflict = REPLACE)
    public void insert(Usuario usuario);

    @Update
    public void update(Usuario usuario);

    @Delete
    public void delete(Usuario usuario);

    @Query("SELECT * FROM Usuario WHERE email = :email")
    public Usuario getByEmail(String email);

    @Query("SELECT COUNT(*) FROM Usuario WHERE email = :email")
    public int verifyExistentUser(String email);

    @Query("SELECT * FROM Usuario WHERE logado = 1")
    public Usuario getLogado();

}