package com.ngeartstudio.maturdokter.maturdokter;

public class AppConfig {
    // Server user login url
    public static String URL_LOGIN = "http://maturdokter.ngeartstudio.com/login.php";

    // Server user register url
    public static String URL_REGISTER = "http://maturdokter.ngeartstudio.com/register.php";

    public static String URL_GETKECAMATAN = "http://maturdokter.ngeartstudio.com/getKecamatan.php";

    public final String url ="http://maturdokter.ngeartstudio.com/";
    public final String gambar_berita =url +"gambar/";
    public final String url_berita= url+"berita.php";
    public final String url_detail_berita= url+"detail_berita.php";
    public final String url_tipskesehatan= url+"tipskesehatan.php";
    public final String url_detail_tipskesehatan= url+"detail_tipskesehatan.php";
    public final String url_kecamatan= url+"list_kecamatan.php";
    public final String url_puskesmas= url+"list_puskesmas.php?id_kecamatan=";
    public final String url_dokter= url+"list_dokter.php?id_puskesmas=";
}
