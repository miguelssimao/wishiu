package com.example.wishiu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BD extends SQLiteOpenHelper {

    private static final String BASE = "Wishiu.db";
    private static final String UTILIZADOR = "utilizador";
    private static final String PRODUTOS = "produtos";
    private static final String CHECKED = "checked";
    private static final String SCHEDULED = "scheduled";
    private static final String SAVINGS = "savings";

    public BD(Context context) {
        super(context, BASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE \"utilizador\" (\"nome\" TEXT NOT NULL DEFAULT 'default')");
        db.execSQL("CREATE TABLE \"produtos\" (\n" +
                "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"nome\"\tTEXT NOT NULL,\n" +
                "\t\"preco\"\tSTRING NOT NULL,\n" +
                "\t\"imagem\"\tBLOB,\n" +
                "\t\"categoria\"\tSTRING DEFAULT 'Misc.'\n" +
                ")");
        db.execSQL("CREATE TABLE \"savings\" (\n" +
                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"id_produto\"\tINTEGER NOT NULL,\n" +
                "\t\"valor\"\tSTRING NOT NULL,\n" +
                "\t\"data\"\tTEXT NOT NULL,\n" +
                "\t\"boolean\"\tBOOLEAN NOT NULL DEFAULT 0\n" +
                ")");
        db.execSQL("CREATE TABLE \"checked\" (\n" +
                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"id_produto\"\tINTEGER NOT NULL\n" +
                ")");
        db.execSQL("CREATE TABLE \"scheduled\" (\n" +
                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"id_produto\"\tINTEGER NOT NULL,\n" +
                "\t\"data\"\tTEXT NOT NULL\n" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+UTILIZADOR);
        db.execSQL("DROP TABLE IF EXISTS "+PRODUTOS);
        db.execSQL("DROP TABLE IF EXISTS "+SAVINGS);
        db.execSQL("DROP TABLE IF EXISTS "+CHECKED);
        db.execSQL("DROP TABLE IF EXISTS "+SCHEDULED);
    }

    public Cursor getNomeUtilizador(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor gnu = db.rawQuery("SELECT nome FROM utilizador LIMIT 1", null);
        return gnu;
    }

    public boolean inserirNome(String iNome){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inserirValores = new ContentValues();
        inserirValores.put("nome", iNome);
        long inserirR = db.insert("utilizador", null, inserirValores);
        if(inserirR == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean inserirProduto(String iCategoria, String iTitulo, String iPreco, byte[] iImagem, String iStarting){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inserirpValores = new ContentValues();
        inserirpValores.put("nome", iTitulo);
        inserirpValores.put("categoria", iCategoria);
        inserirpValores.put("preco", iPreco);
        inserirpValores.put("imagem", iImagem);
        long inserirpR = db.insert("produtos", null, inserirpValores);
        if(inserirpR == -1){
            return false;
        } else {
            if(!iStarting.isEmpty() && iStarting != "0"){
                int id_produto = (int) inserirpR;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new Date());
                ContentValues inserirsValores = new ContentValues();
                inserirsValores.put("id_produto", id_produto);
                inserirsValores.put("valor", iStarting);
                inserirsValores.put("data", date);
                long inserirsR = db.insert("savings", null, inserirsValores);
                if(inserirsR == -1){
                    return false;
                } else {
                    return true;
                }
            }
            return true;
        }
    }

    public Cursor getProdutos(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor gp = db.rawQuery("SELECT * FROM produtos EXCEPT SELECT a.* FROM produtos a JOIN checked b ON a.id = b.id_produto", null);
        return gp;
    }

    public Cursor getAchieved(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ga = db.rawQuery("SELECT * FROM checked", null);
        return ga;
    }

    public Cursor getDataOfAchieved(String idProduto){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor gada = db.rawQuery("SELECT data FROM scheduled WHERE id_produto="+idProduto+" LIMIT 1", null);
        return gada;
    }

    public Cursor getProdutoAchieved(String idProduto){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor gpa = db.rawQuery("SELECT * FROM produtos WHERE id="+idProduto+" LIMIT 1", null);
        return gpa;
    }

    public Cursor getScheduled(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor gs = db.rawQuery("SELECT * FROM scheduled", null);
        return gs;
    }

    public Cursor getProdutoInfo(String idProduto){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor gpi = db.rawQuery("SELECT * FROM produtos WHERE id="+idProduto+"", null);
        return gpi;
    }

    public Cursor getProdutoSavings(String idProduto) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor gps = db.rawQuery("SELECT * FROM savings WHERE id_produto=" + idProduto + " ORDER BY id DESC", null);
        return gps;
    }

    public int getDiferencaDeDias(String idProduto) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        Calendar agora = Calendar.getInstance();
        Date chegada = null;
        int retornado = 0;
        Cursor diferenca = db.rawQuery("SELECT data FROM scheduled WHERE id_produto="+idProduto+" LIMIT 1", null);
        if(diferenca != null && diferenca.moveToFirst()){
            SimpleDateFormat formatoDaData = new SimpleDateFormat("yyyy-MM-dd");
            chegada = formatoDaData.parse(diferenca.getString(0));
            Calendar cChegada = Calendar.getInstance();
            cChegada.setTime(chegada);
            long diff = cChegada.getTimeInMillis() - agora.getTimeInMillis();
            long days = diff / (24 * 60 * 60 * 1000);
            if(days < 0){
                days = 0;
            }
            retornado = (int)days;
        } else {
            retornado = 0;
        }
        diferenca.close();
        return retornado;
    }

    public boolean updateSavings(String valor, String dataFormatada) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues atualizarValores = new ContentValues();
        SimpleDateFormat formatoriginal = new SimpleDateFormat("dd/MM/yyyy");
        Date origem;
        origem = formatoriginal.parse(dataFormatada);
        SimpleDateFormat sdf_helper = new SimpleDateFormat("yyyy-MM-dd");
        String dataOriginal = sdf_helper.format(origem);
        valor = valor.replace("$","");
        atualizarValores.put("boolean", 0);
        long updateS = db.update("savings", atualizarValores, "valor ='"+valor+"' AND data ='"+dataOriginal+"'", null);
        return true;
    }

    public boolean updateData(String idProduto, String novaData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues atualizarData = new ContentValues();
        atualizarData.put("data", novaData);
        long updateData = db.update("scheduled", atualizarData, "id_produto ="+idProduto, null);
        return true;
    }

    public boolean inserirData(String idProduto, String novaDataInserir){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues inserirdatav = new ContentValues();
        int iDataProduto = Integer.parseInt(idProduto);
        inserirdatav.put("id_produto", iDataProduto);
        inserirdatav.put("data", novaDataInserir);
        long inserirDataR = db.insert("scheduled", null, inserirdatav);
        if(inserirDataR == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean updateProduto(String idProduto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues checkP = new ContentValues();
        int iiProduto = Integer.parseInt(idProduto);
        checkP.put("id_produto", iiProduto);
        long inserirChecked = db.insert("checked", null, checkP);
        if(inserirChecked == -1){
            return false;
        } else {
            return true;
        }
    }

    public int deleteProduto(String idProduto){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("savings", "id_produto = ?", new String[]{idProduto});
        return db.delete("produtos", "id = ?", new String[]{idProduto});
    }

    public int deleteAchieved(String idProduto){
        SQLiteDatabase db = this.getWritableDatabase();
        deleteProduto(idProduto);
        return db.delete("checked", "id_produto = ?", new String[]{idProduto});
    }

    public int deleteScheduled(String idProduto){
        SQLiteDatabase db = this.getWritableDatabase();
        deleteAchieved(idProduto);
        return db.delete("scheduled", "id_produto = ?", new String[]{idProduto});
    }

    public boolean inserirSaving(String idProduto, String valorS){
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdff.format(new Date());
        ContentValues inserirssValores = new ContentValues();
        int iiProduto = Integer.parseInt(idProduto);
        inserirssValores.put("id_produto", iiProduto);
        inserirssValores.put("valor", valorS);
        inserirssValores.put("data", nowDate);
        inserirssValores.put("boolean", 1);
        long inserirSs = db.insert("savings", null, inserirssValores);
        if(inserirSs == -1){
            return false;
        } else {
            return true;
        }
    }

}
