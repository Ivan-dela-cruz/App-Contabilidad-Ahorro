package co.desofsi.ahorro.sqlitehelpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    final String TABLE_CATEGORIA_INGRESO = "CREATE TABLE IF NOT EXISTS categoria_ingreso(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, imagen BLOB, estado INTEGER,id_user TEXT NOT NULL CONSTRAINT fk_id_user_cate_gasto REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE)";
    final String TABLE_CATEGORIA_GASTO = "CREATE TABLE IF NOT EXISTS categoria_gasto(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, imagen BLOB,presupuesto DOUBLE, estado INTEGER,id_user TEXT NOT NULL CONSTRAINT fk_id_user_cate_ingresos REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE)";
    final String TABLE_CATEGORIA_AHORRO = "CREATE TABLE IF NOT EXISTS categoria_ahorro(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, imagen BLOB, estado INTEGER,id_user TEXT NOT NULL CONSTRAINT fk_id_cate_ahorro REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE)";


    final String TABLE_INGRESOS = "CREATE TABLE IF NOT EXISTS ingresos(id INTEGER PRIMARY KEY AUTOINCREMENT,descripcion TEXT,fecha TEXT,imagen BLOB,valor DOUBLE, id_cat INTEGER NOT NULL CONSTRAINT fk_id_cat_ingreso REFERENCES categoria_ingreso(id) ON DELETE CASCADE ON UPDATE CASCADE ,id_user TEXT NOT NULL CONSTRAINT fk_id_user_ingreso REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE)";
    final String TABLE_GASTOS = "CREATE TABLE IF NOT EXISTS gastos(id INTEGER PRIMARY KEY AUTOINCREMENT,descripcion TEXT,fecha TEXT,imagen BLOB,valor DOUBLE, id_cat INTEGER NOT NULL CONSTRAINT fk_id_cat_ingreso REFERENCES categoria_gasto(id) ON DELETE CASCADE ON UPDATE CASCADE ,id_user TEXT NOT NULL CONSTRAINT fk_id_user_gasto REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE)";


    ///String id, String nombre, String apellido, String email, String fecha_n, String genero, byte[] imagen
    final String TABLE_USUARIOS = "CREATE TABLE IF NOT EXISTS usuarios(id TEXT PRIMARY KEY, nombre TEXT, apellido TEXT, email TEXT, fecha_n TEXT, genero TEXT, imagen BLOB)";

    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    ////CREA LAS TABLAS DESDE EL MAIN

    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }


    ///EJECUCIONES FUNCIONES ************************************************************


    ///INGRESO DE DE VALORES DE INGRESO

    public void insertDataUsuarios(String id, String nombre, String apellido, String email, String fecha_n, String genero, byte[] imagen) {

        //descripcion ,fecha ,imagen ,valor
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO usuarios VALUES(?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, id);
        statement.bindString(2, nombre);
        statement.bindString(3, apellido);
        statement.bindString(4, email);
        statement.bindString(5, fecha_n);
        statement.bindString(6, genero);
        statement.bindBlob(7, imagen);
        statement.executeInsert();

    }


    //ACTUALIZAR PRESUPUESTO

    public void insertDataPresupuesoGastos(int id, double presupuesto) {
        SQLiteDatabase database = getWritableDatabase();
        //UPDATE "main"."gastos" SET "valor" = 100.0 WHERE rowid = 3
        if (database != null) {
            String sql = "UPDATE categoria_gasto SET presupuesto = '" + presupuesto + "' WHERE id = " + id;
            database.execSQL(sql);
            database.close();
        }


    }

    ///AHORRO DE CATEGORIAS

    public void insertDataCategoriaAhorros(String nombre, byte[] image, int estado) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO categoria_ahorro VALUES(null,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, nombre);
        statement.bindBlob(2, image);
        statement.bindDouble(3, estado);
        statement.executeInsert();

    }

///INGRESO DE CATEGORIAS

    public void insertDataCategoriaIngresos(String nombre, byte[] image, int estado, String id_user) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO categoria_ingreso VALUES(null,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, nombre);
        statement.bindBlob(2, image);
        statement.bindDouble(3, estado);
        statement.bindString(4, id_user);
        statement.executeInsert();

    }


    ///INGRESO DE CATEGORIAS GASTOS
    public void insertDataCategoriaGastos(String nombre, byte[] image, double presupuesto, int estado, String id_user) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO categoria_gasto VALUES(null,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, nombre);
        statement.bindBlob(2, image);
        statement.bindDouble(3, presupuesto);
        statement.bindDouble(4, estado);
        statement.bindString(5, id_user);
        statement.executeInsert();

    }


    ///INGRESO DE DE VALORES DE INGRESO

    public void insertDataIngresos(String descripcion, String fecha, byte[] imagen, double valor, int id_cat, String id_user) {

        //descripcion ,fecha ,imagen ,valor
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO ingresos VALUES(null,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, descripcion);
        statement.bindString(2, fecha);
        statement.bindBlob(3, imagen);
        statement.bindDouble(4, valor);
        statement.bindDouble(5, id_cat);
        statement.bindString(6, id_user);
        statement.executeInsert();

    }

    ///INGRESO DE DE VALORES DE GASTOS

    public void insertDataGastos(String descripcion, String fecha, byte[] imagen, double valor, int id_cat, String id_user) {

        //descripcion ,fecha ,imagen ,valor
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO gastos VALUES(null,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, descripcion);
        statement.bindString(2, fecha);
        statement.bindBlob(3, imagen);
        statement.bindDouble(4, valor);
        statement.bindDouble(5, id_cat);
        statement.bindString(6, id_user);
        statement.executeInsert();

    }


    ////    EXRAER DATOS DE LAS TABLAS
    public Cursor getDataTable(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USUARIOS);
        db.execSQL(TABLE_CATEGORIA_GASTO);
        db.execSQL(TABLE_CATEGORIA_INGRESO);
        db.execSQL(TABLE_CATEGORIA_AHORRO);
        db.execSQL(TABLE_INGRESOS);
        db.execSQL(TABLE_GASTOS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
