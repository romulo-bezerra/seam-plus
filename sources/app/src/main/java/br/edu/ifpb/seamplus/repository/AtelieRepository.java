package br.edu.ifpb.seamplus.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import br.edu.ifpb.seamplus.dao.AtelieDAO;
import br.edu.ifpb.seamplus.database.DatabaseConfig;
import br.edu.ifpb.seamplus.model.Atelie;

public class AtelieRepository {

    private final AtelieDAO atelieDAO;
    private static final String TAG_ATELIEDAO = "ATELIEDAO";

    public AtelieRepository(Context context){
        atelieDAO = DatabaseConfig.getInstance(context).createAtelieDAO();
    }

    public void save(final Atelie atelie){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                atelieDAO.insert(atelie);
                return null;
            }
        }.execute();
    }

    public void update(final Atelie atelie){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                atelieDAO.update(atelie);
                return null;
            }
        }.execute();
    }

    public void delete(final Atelie atelie){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                atelieDAO.delete(atelie);
                return null;
            }
        }.execute();
    }

    public Atelie getAtelieById(final long id) {

        Atelie atelie = new Atelie();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Atelie> futureResult = executor.submit(new Callable<Atelie>() {
            @Override
            public Atelie call() throws Exception {
                return atelieDAO.getById(id);
            }
        });

        try {
            atelie = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_ATELIEDAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_ATELIEDAO, e.getMessage());
        }
        executor.shutdown();
        return atelie;
    }

    public Atelie getAtelieByNomeFantasia(final String nomeFantasia) {

        Atelie atelie = new Atelie();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Atelie> futureResult = executor.submit(new Callable<Atelie>() {
            @Override
            public Atelie call() throws Exception {
                return atelieDAO.getByNomeFantasia(nomeFantasia);
            }
        });

        try {
            atelie = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_ATELIEDAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_ATELIEDAO, e.getMessage());
        }
        executor.shutdown();
        return atelie;
    }

    public int getProxIdAtelie() {

        int proxIdAtelie = 0;

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> futureResult = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return atelieDAO.countRecord();
            }
        });

        try {
            proxIdAtelie = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_ATELIEDAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_ATELIEDAO, e.getMessage());
        }
        executor.shutdown();

        return ++proxIdAtelie;
    }

}
