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

import br.edu.ifpb.seamplus.dao.ClienteDAO;
import br.edu.ifpb.seamplus.database.DatabaseConfig;
import br.edu.ifpb.seamplus.model.Cliente;

public class ClienteRepository {

    private final ClienteDAO clienteDAO;
    private static final String TAG_CLIENTEDAO = "CLIENTEDAO";

    public ClienteRepository(Context context){
        clienteDAO = DatabaseConfig.getInstance(context).createClienteDAO();
    }

    public void save(final Cliente cliente){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                clienteDAO.insert(cliente);
                return null;
            }
        }.execute();
    }

    public void update(final Cliente cliente){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                clienteDAO.update(cliente);
                return null;
            }
        }.execute();
    }

    public void delete(final Cliente cliente){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                clienteDAO.delete(cliente);
                return null;
            }
        }.execute();
    }

    public Cliente getById(final long id) {

        Cliente cliente = new Cliente();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Cliente> futureResult = executor.submit(new Callable<Cliente>() {
            @Override
            public Cliente call() throws Exception {
                return clienteDAO.getById(id);
            }
        });

        try {
            cliente = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_CLIENTEDAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_CLIENTEDAO, e.getMessage());
        }
        executor.shutdown();
        return cliente;
    }

    public Cliente getByNome(final String nome) {

        Cliente cliente = new Cliente();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Cliente> futureResult = executor.submit(new Callable<Cliente>() {
            @Override
            public Cliente call() throws Exception {
                return clienteDAO.getByNome(nome);
            }
        });

        try {
            cliente = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_CLIENTEDAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_CLIENTEDAO, e.getMessage());
        }
        executor.shutdown();
        return cliente;
    }

    public List<Cliente> getAllByAtelieId(final long atelieId) {

        List<Cliente> clientes = new ArrayList<>();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<List<Cliente>> futureResult = executor.submit(new Callable<List<Cliente>>() {
            @Override
            public List<Cliente> call() throws Exception {
                return clienteDAO.getAllByAtelieId(atelieId);
            }
        });

        try {
            clientes = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_CLIENTEDAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_CLIENTEDAO, e.getMessage());
        }
        executor.shutdown();
        return clientes;
    }

    public List<Cliente> getAll() {

        List<Cliente> clientes = new ArrayList<>();

        //Criando um pool de Threads
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<List<Cliente>> futureResult = executor.submit(new Callable<List<Cliente>>() {
            @Override
            public List<Cliente> call() throws Exception {
                return clienteDAO.getAll();
            }
        });

        try {
            clientes = futureResult.get();
        } catch (ExecutionException e) {
            Log.e(TAG_CLIENTEDAO, e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_CLIENTEDAO, e.getMessage());
        }
        executor.shutdown();
        return clientes;
    }

}