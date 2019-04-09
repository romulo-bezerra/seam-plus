package br.edu.ifpb.seamplus.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import br.edu.ifpb.seamplus.dao.DividaDAO;
import br.edu.ifpb.seamplus.database.DatabaseConfig;
import br.edu.ifpb.seamplus.model.Divida;

public class DividaRepository {

    private final DividaDAO dividaDAO;
    private static final String TAG_DIVIDADAO = "DIVIDADAO";

    public DividaRepository(Context context){
        dividaDAO = DatabaseConfig.getInstance(context).createDividaDAO();
    }

    public void save(final Divida divida){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dividaDAO.insert(divida);
                return null;
            }
        }.execute();
    }

    public void update(final Divida divida){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dividaDAO.update(divida);
                return null;
            }
        }.execute();
    }

    public void delete(final Divida divida){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dividaDAO.delete(divida);
                return null;
            }
        }.execute();
    }

    public Divida getById(final long id) {

        Divida divida = new Divida();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Divida> futureResult = executor.submit(new Callable<Divida>() {
            @Override
            public Divida call() throws Exception {
                return dividaDAO.getById(id);
            }
        });

        try {
            divida = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_DIVIDADAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_DIVIDADAO, e.getMessage());
        }
        executor.shutdown();
        return divida;
    }

    public Divida getByClienteId(final long clienteId) {

        Divida divida = new Divida();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Divida> futureResult = executor.submit(new Callable<Divida>() {
            @Override
            public Divida call() throws Exception {
                return dividaDAO.getByClienteId(clienteId);
            }
        });

        try {
            divida = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_DIVIDADAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_DIVIDADAO, e.getMessage());
        }
        executor.shutdown();
        return divida;
    }

    public List<Divida> getAll() {

        List<Divida> dividas = new ArrayList<>();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<List<Divida>> futureResult = executor.submit(new Callable<List<Divida>>() {
            @Override
            public List<Divida> call() throws Exception {
                return dividaDAO.getAll();
            }
        });

        try {
            dividas = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_DIVIDADAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_DIVIDADAO, e.getMessage());
        }
        executor.shutdown();
        return dividas;
    }

    public int checaDivida(final long clienteId) {

        int contagem = 0;

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> futureResult = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return dividaDAO.checaDivida(clienteId);
            }
        });

        try {
            contagem = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_DIVIDADAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_DIVIDADAO, e.getMessage());
        }
        executor.shutdown();

        return contagem;
    }

}