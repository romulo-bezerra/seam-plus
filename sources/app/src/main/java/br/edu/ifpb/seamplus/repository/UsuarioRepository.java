package br.edu.ifpb.seamplus.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import br.edu.ifpb.seamplus.dao.UsuarioDAO;
import br.edu.ifpb.seamplus.database.DatabaseConfig;
import br.edu.ifpb.seamplus.model.Usuario;

public class UsuarioRepository {

    private final UsuarioDAO usuarioDAO;
    private static final String TAG_USUARIODAO = "UsuarioDAO";

    public UsuarioRepository(Context context){
        usuarioDAO = DatabaseConfig.getInstance(context).createUsuarioDAO();
    }

    public void save(final Usuario usuario){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                usuarioDAO.insert(usuario);
                return null;
            }
        }.execute();
    }

    public void update(final Usuario usuario){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                usuarioDAO.update(usuario);
                return null;
            }
        }.execute();
    }

    public void delete(final Usuario usuario){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                usuarioDAO.delete(usuario);
                return null;
            }
        }.execute();
    }

    public Usuario getByEmail(final String email) {

        Usuario usuario = new Usuario();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Usuario> futureResult = executor.submit(new Callable<Usuario>() {
            @Override
            public Usuario call() throws Exception {
                return usuarioDAO.getByEmail(email);
            }
        });

        try {
            usuario = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_USUARIODAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_USUARIODAO, e.getMessage());
        }
        executor.shutdown();
        return usuario;
    }

    public int verifyExistentUser(final String email) {

        int contagem = 0;

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> futureResult = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return usuarioDAO.verifyExistentUser(email);
            }
        });

        try {
            contagem = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_USUARIODAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_USUARIODAO, e.getMessage());
        }
        executor.shutdown();

        return contagem;
    }

    public Usuario getLogado() {

        Usuario usuario = new Usuario();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Usuario> futureResult = executor.submit(new Callable<Usuario>() {
            @Override
            public Usuario call() throws Exception {
                return usuarioDAO.getLogado();
            }
        });

        try {
            usuario = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_USUARIODAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_USUARIODAO, e.getMessage());
        }
        executor.shutdown();
        return usuario;
    }

}
