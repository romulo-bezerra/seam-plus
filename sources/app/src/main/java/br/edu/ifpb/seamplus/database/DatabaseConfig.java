package br.edu.ifpb.seamplus.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import br.edu.ifpb.seamplus.dao.AtelieDAO;
import br.edu.ifpb.seamplus.dao.ClienteDAO;
import br.edu.ifpb.seamplus.dao.DescontoDAO;
import br.edu.ifpb.seamplus.dao.DividaDAO;
import br.edu.ifpb.seamplus.dao.PedidoDAO;
import br.edu.ifpb.seamplus.database.converters.DateConverter;
import br.edu.ifpb.seamplus.database.converters.PrioridadeConverter;
import br.edu.ifpb.seamplus.database.converters.SexoConverter;
import br.edu.ifpb.seamplus.database.converters.SituacaoPedidoConverter;
import br.edu.ifpb.seamplus.model.Atelie;
import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.model.Desconto;
import br.edu.ifpb.seamplus.model.Divida;
import br.edu.ifpb.seamplus.model.Pedido;
import br.edu.ifpb.seamplus.model.Usuario;
import br.edu.ifpb.seamplus.dao.UsuarioDAO;

@Database(
        entities = {
                Usuario.class,
                Atelie.class,
                Cliente.class,
                Pedido.class,
                Divida.class,
                Desconto.class
        },
        version = 1,
        exportSchema = false
)
@TypeConverters(
        {
            DateConverter.class,
            SexoConverter.class,
            SituacaoPedidoConverter.class,
            PrioridadeConverter.class
        }
)
public abstract class DatabaseConfig extends RoomDatabase {

    private static DatabaseConfig databaseConfig;

    public abstract UsuarioDAO createUsuarioDAO();
    public abstract AtelieDAO createAtelieDAO();
    public abstract ClienteDAO createClienteDAO();
    public abstract PedidoDAO createPedidoDAO();
    public abstract DividaDAO createDividaDAO();
    public abstract DescontoDAO createDescontoDAO();

    public static DatabaseConfig getInstance(Context context) {
        if(databaseConfig == null) {
            databaseConfig = Room
                    .databaseBuilder(context,
                            DatabaseConfig.class, "seamplusdb")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return databaseConfig;
    }
}