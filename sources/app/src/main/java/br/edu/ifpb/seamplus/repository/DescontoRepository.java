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

import br.edu.ifpb.seamplus.dao.DescontoDAO;
import br.edu.ifpb.seamplus.database.DatabaseConfig;
import br.edu.ifpb.seamplus.model.Desconto;

public class DescontoRepository {

    private final DescontoDAO descontoDAO;
    private static final String TAG_DESCONTODAO = "DESCONTODAO";

    public DescontoRepository(Context context){
        descontoDAO = DatabaseConfig.getInstance(context).createDescontoDAO();
    }

    public void save(final Desconto desconto){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                descontoDAO.insert(desconto);
                return null;
            }
        }.execute();
    }

    public void update(final Desconto desconto){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                descontoDAO.update(desconto);
                return null;
            }
        }.execute();
    }

    public void delete(final Desconto desconto){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                descontoDAO.delete(desconto);
                return null;
            }
        }.execute();
    }

    public Desconto getById(final long id) {

        Desconto desconto = new Desconto();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Desconto> futureResult = executor.submit(new Callable<Desconto>() {
            @Override
            public Desconto call() throws Exception {
                return descontoDAO.getById(id);
            }
        });

        try {
            desconto = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_DESCONTODAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_DESCONTODAO, e.getMessage());
        }
        executor.shutdown();
        return desconto;
    }

    public List<Desconto> getAllByDividaId(final long dividaId) {

        List<Desconto> descontos = new ArrayList<>();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<List<Desconto>> futureResult = executor.submit(new Callable<List<Desconto>>() {
            @Override
            public List<Desconto> call() throws Exception {
                return descontoDAO.getAllByDividaId(dividaId);
            }
        });

        try {
            descontos = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_DESCONTODAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_DESCONTODAO, e.getMessage());
        }
        executor.shutdown();
        return descontos;
    }

    public List<Desconto> getAll() {

        List<Desconto> descontos = new ArrayList<>();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<List<Desconto>> futureResult = executor.submit(new Callable<List<Desconto>>() {
            @Override
            public List<Desconto> call() throws Exception {
                return descontoDAO.getAll();
            }
        });

        try {
            descontos = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_DESCONTODAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_DESCONTODAO, e.getMessage());
        }
        executor.shutdown();
        return descontos;
    }

}