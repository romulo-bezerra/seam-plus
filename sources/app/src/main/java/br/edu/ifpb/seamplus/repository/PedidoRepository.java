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

import br.edu.ifpb.seamplus.dao.PedidoDAO;
import br.edu.ifpb.seamplus.database.DatabaseConfig;
import br.edu.ifpb.seamplus.model.Pedido;

public class PedidoRepository {

    private final PedidoDAO pedidoDAO;
    private static final String TAG_PEDIDODAO = "PEDIDODAO";

    public PedidoRepository(Context context){
        pedidoDAO = DatabaseConfig.getInstance(context).createPedidoDAO();
    }

    public void save(final Pedido pedido){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                pedidoDAO.insert(pedido);
                return null;
            }
        }.execute();
    }

    public void update(final Pedido pedido){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                pedidoDAO.update(pedido);
                return null;
            }
        }.execute();
    }

    public void delete(final Pedido pedido){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                pedidoDAO.delete(pedido);
                return null;
            }
        }.execute();
    }

    public Pedido getById(final long id) {

        Pedido pedido = new Pedido();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Pedido> futureResult = executor.submit(new Callable<Pedido>() {
            @Override
            public Pedido call() throws Exception {
                return pedidoDAO.getById(id);
            }
        });

        try {
            pedido = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_PEDIDODAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_PEDIDODAO, e.getMessage());
        }
        executor.shutdown();
        return pedido;
    }

    public List<Pedido> getAll() {

        List<Pedido> pedidos = new ArrayList<>();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<List<Pedido>> futureResult = executor.submit(new Callable<List<Pedido>>() {
            @Override
            public List<Pedido> call() throws Exception {
                return pedidoDAO.getAll();
            }
        });

        try {
            pedidos = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_PEDIDODAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_PEDIDODAO, e.getMessage());
        }
        executor.shutdown();
        return pedidos;
    }

    public List<Pedido> getAllByAtelieId(final long atelieId) {

        List<Pedido> pedidos = new ArrayList<>();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<List<Pedido>> futureResult = executor.submit(new Callable<List<Pedido>>() {
            @Override
            public List<Pedido> call() throws Exception {
                return pedidoDAO.getAllByAtelieId(atelieId);
            }
        });

        try {
            pedidos = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_PEDIDODAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_PEDIDODAO, e.getMessage());
        }
        executor.shutdown();
        return pedidos;
    }

    public List<Pedido> getAllByClienteId(final long clienteId) {

        List<Pedido> pedidos = new ArrayList<>();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<List<Pedido>> futureResult = executor.submit(new Callable<List<Pedido>>() {
            @Override
            public List<Pedido> call() throws Exception {
                return pedidoDAO.getAllByClienteId(clienteId);
            }
        });

        try {
            pedidos = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_PEDIDODAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_PEDIDODAO, e.getMessage());
        }
        executor.shutdown();
        return pedidos;
    }

    public List<Pedido> getAllPedidosNaoFinalizados(final long atelieId) {

        List<Pedido> pedidos = new ArrayList<>();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<List<Pedido>> futureResult = executor.submit(new Callable<List<Pedido>>() {
            @Override
            public List<Pedido> call() throws Exception {
                return pedidoDAO.getAllPedidosNaoFinalizados(atelieId);
            }
        });

        try {
            pedidos = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_PEDIDODAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_PEDIDODAO, e.getMessage());
        }
        executor.shutdown();
        return pedidos;
    }

}