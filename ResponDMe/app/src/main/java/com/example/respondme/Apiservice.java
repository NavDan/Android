package com.example.respondme;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Apiservice {

    @GET("login.php")
    Call<Usuario> login(
            @Query("user") String user,
            @Query("password") String pass
    );

    @GET("registro.php")
    Call<Usuario> registro(
            @Query("user") String user,
            @Query("password") String pass,
            @Query("nom") String nom
    );

    @GET("cargaramigos.php")
    Call<Usuario> cargauser(
            @Query("iduser") String id
    );
    @GET("cargarsolicitud.php")
    Call<Usuario> cargasol(
            @Query("iduser") String id
    );

    @GET("confirmarsol.php")
    Call<Usuario> confsol(
            @Query("iduser") String id,
            @Query("nomuser") String nom
    );

    @GET("eliminacionsol.php")
    Call<Usuario> elisol(
            @Query("iduser") String id,
            @Query("nomuser") String nom
    );

    @GET("buscarpersona.php")
    Call<Usuario> busc(
            @Query("iduser") String id,
            @Query("nom") String nom
    );

    @GET("agregaramigo.php")
    Call<Usuario> addamg(
            @Query("iduser") String id,
            @Query("nomuser") String nom
    );

    @GET("agregarpregunta.php")
    Call<Usuario> addpreg(
            @Query("pregunta") String preg,
            @Query("respc") String resc,
            @Query("res1") String res1,
            @Query("res2") String res2
    );

    @GET("rellenarpartida.php")
    Call<Juego> juego(
            @Query("idu1") String id1,
            @Query("idu2") String id2
    );

    @GET("crearpartidaaleatoria.php")
    Call<Juego> crearale(
            @Query("idu") String id
    );

    @GET("insertarrespuesta.php")
    Call<Juego> insres(
            @Query("idu1") String id1,
            @Query("idu2") String id2,
            @Query("ronda") String ronda,
            @Query("res") String res,
            @Query("usu") String usu
    );

}
