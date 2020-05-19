package co.desofsi.ahorro.sqlitehelpers;

import android.content.ContentValues;
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
    final String TABLE_COLOR = "CREATE TABLE IF NOT EXISTS colors(id INTEGER PRIMARY KEY AUTOINCREMENT,color_primary TEXT,color_primary_dark TEXT,color_secundary TEXT,status INTEGER ,id_user TEXT NOT NULL CONSTRAINT fk_id_user_colors REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE)";

    final String TABLE_AHORROS = "CREATE TABLE IF NOT EXISTS ahorros(id INTEGER PRIMARY KEY AUTOINCREMENT,descripcion TEXT,imagen BLOB,valor DOUBLE,fecha TEXT,porcentaje DOUBLE,mensual DOUBLE,dias INTEGER,meses INTEGER,anios INTEGER,estado INTEGER, id_cat INTEGER NOT NULL CONSTRAINT fk_id_cat_ahorro REFERENCES categoria_ahorro(id) ON DELETE CASCADE ON UPDATE CASCADE ,id_user TEXT NOT NULL CONSTRAINT fk_id_user_ahorro REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE)";


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

    public void insertDataCategoriaAhorros(String nombre, byte[] image, int estado, String id_user) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO categoria_ahorro VALUES(null,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, nombre);
        statement.bindBlob(2, image);
        statement.bindDouble(3, estado);
        statement.bindString(4, id_user);
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

    public void deletedDataTable(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        if (database != null) {
            database.execSQL(sql);
            database.close();
        }


    }


    //ACTUALIZAR CATEGORIAS

    public void updateDataTableCategory(int id, String nombre, byte[] image, double presupuesto, int estado) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("nombre", nombre);
        data.put("imagen", image);
        data.put("presupuesto", presupuesto);
        data.put("estado", estado);
        database.update("categoria_gasto", data, "id=" + id, null);

    }


    //actualizar cate ingresos

    public void updateDataTableCategoryIngresos(int id, String nombre, byte[] image, int estado) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("nombre", nombre);
        data.put("imagen", image);
        data.put("estado", estado);
        database.update("categoria_ingreso", data, "id=" + id, null);

    }


    ///GESTION COLORES TABLAS
    ///INGRESO DE DCOLORES

    public void insertDataColors(String primary, String primary_dark, String secundary, int status, String id_user) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO colors VALUES(null,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, primary);
        statement.bindString(2, primary_dark);
        statement.bindString(3, secundary);
        statement.bindDouble(4, status);
        statement.bindString(5, id_user);
        statement.executeInsert();

    }

//actualizar cate colores

    //id    ,color_primary ,color_primary_dark ,color_secundary ,status  ,id_user)";
    public void updateDataTableColors(int id, String primary, String primary_dark, String secundary, int status) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("color_primary", primary);
        data.put("color_primary_dark", primary_dark);
        data.put("color_secundary", secundary);
        data.put("status", status);
        database.update("colors", data, "id=" + id, null);

    }


    //actualizar cate ingresos

    public void updateDataTableGastos(int id, String nombre, double valor, String fecha) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("descripcion", nombre);
        data.put("valor", valor);
        data.put("fecha", fecha);
        database.update("gastos", data, "id=" + id, null);

    }


    //actualizar cate ingresos

    public void updateDataTableIngresos(int id, String nombre, double valor, String fecha) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("descripcion", nombre);
        data.put("valor", valor);
        data.put("fecha", fecha);
        database.update("ingresos", data, "id=" + id, null);

    }

    public void updateDataTableUser(String id, String nombre, String apellido, String genero, String fecha, byte[] image) {

        //id TEXT PRIMARY KEY, nombre TEXT, apellido TEXT, email TEXT, fecha_n TEXT, genero TEXT, imagen BLOB
        SQLiteDatabase database = getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("nombre", nombre);
        data.put("apellido", apellido);
        data.put("genero", genero);
        data.put("fecha_n", fecha);
        data.put("imagen", image);
        database.update("usuarios", data, "id='" + id + "'", null);

    }


    ///INGRESO DE DE VALORES DE INGRESO

    //id,descripcion,imagen,valor,fecha,porcentaje,mensual,dias,meses,anios,estado, id_cat,id_user

    public void insertDataAhorros(String descripcion,  byte[] imagen, double valor,String fecha,double porcentaje,double mensual, int dias,int meses,int anio,int estado, int id_cat, String id_user) {

        //descripcion ,fecha ,imagen ,valor
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO ahorros VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, descripcion);
        statement.bindBlob(2, imagen);
        statement.bindDouble(3, valor);
        statement.bindString(4, fecha);
        statement.bindDouble(5,porcentaje);
        statement.bindDouble(6,mensual);
        statement.bindDouble(7,dias);
        statement.bindDouble(8,meses);
        statement.bindDouble(9,anio);
        statement.bindDouble(10,estado);
        statement.bindDouble(11, id_cat);
        statement.bindString(12, id_user);
        statement.executeInsert();

    }


    public void updateDataTableAhorros (int id, String descripcion,  byte[] imagen, double valor,String fecha,double porcentaje,double mensual, int dias,int meses,int anio,int estado, int id_cat, String id_user){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues data = new ContentValues();
        //id,descripcion,imagen,valor,fecha,porcentaje,mensual,dias,meses,anios,estado, id_cat,id_user
        data.put("descripcion", descripcion);
        data.put("imagen", imagen);
        data.put("valor", valor);
        data.put("fecha", fecha);
        data.put("porcentaje", porcentaje);
        data.put("mensual", mensual);
        data.put("dias", dias);
        data.put("meses", meses);
        data.put("anios", anio);
        data.put("estado", estado);
        data.put("id_cat", id_cat);
        data.put("id_user", id_user);
        database.update("ahorros", data, "id='" + id + "'", null);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USUARIOS);
        db.execSQL(TABLE_CATEGORIA_GASTO);
        db.execSQL(TABLE_CATEGORIA_INGRESO);
        db.execSQL(TABLE_CATEGORIA_AHORRO);
        db.execSQL(TABLE_INGRESOS);
        db.execSQL(TABLE_GASTOS);
        db.execSQL(TABLE_AHORROS);
        db.execSQL(TABLE_COLOR);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
